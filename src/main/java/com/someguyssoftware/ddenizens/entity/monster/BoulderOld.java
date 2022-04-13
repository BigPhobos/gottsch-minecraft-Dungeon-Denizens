/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2021, Mark Gottschling (gottsch)
 * 
 * All rights reserved.
 *
 * Dungeon Denizens is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dungeon Denizens is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Dungeon Denizens.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package com.someguyssoftware.ddenizens.entity.monster;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.someguyssoftware.ddenizens.DD;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * @author Mark Gottschling on Apr 6, 2022
 *
 */
public class BoulderOld extends Monster {
	private static final EntityDataAccessor<Boolean> DATA_DORMANT = SynchedEntityData.defineId(BoulderOld.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> DATA_LOYAL_TICKS = SynchedEntityData.defineId(BoulderOld.class, EntityDataSerializers.INT);
	protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(BoulderOld.class, EntityDataSerializers.OPTIONAL_UUID);

	private static final String DORMANT = "dormant";
	private static final String LOYALTY_TICKS = "loyaltyTicks";
	private static final String OWNER = "owner";
	private static final int MAX_LOYALTY_TICKS = 6000; // 5 minutes
	
	private float previousAmount;
	// the current amount of transition (between 0 and 1, ie. %)
	private float amount;

	private boolean fallingAsleep;
	private boolean wakingUp;

	// TODO these should probably be constants
	private float maxLegAmount = 2;
	private float maxBodyAmount = 2;

	public BoulderOld(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	/**
	 * 
	 */
	protected void registerGoals() {
		this.goalSelector.addGoal(5, new BoulderGoDormantGoal(this));
		this.goalSelector.addGoal(5, new BoulderWakeUpGoal(this));
		this.goalSelector.addGoal(6, new BoulderRandomStrollGoal(this, 1.0D));

		// TODO need a "follow" goal like a wolf or cow
		// TODO the goal's canUse() method must check against the hibernate state.
	}

	/**
	 * 
	 * @return
	 */
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.2F);
	}

	@Override
	public void tick() {
		if (this.getLevel().getGameTime() % 2 == 0) {
		// decrement the loyalty ticks
		int ticks = getLoyaltyTicks();
		ticks--;
		if (--ticks <= 0) {
			ticks = 0;
		}
		setLoyaltyTicks(ticks);
		
		DD.LOGGER.info("isDormant -> {}", isDormant());
		DD.LOGGER.info("isFallingAsleep -> {}", isFallingAsleep());
		DD.LOGGER.info("isWakingUp -> {}", isWakingUp());
		DD.LOGGER.info("isActive -> {}", isActive());
		previousAmount = amount;
		if (isDormant()) {
			amount = 1F;
		}
		else if (isFallingAsleep()) {
			if (amount < 1F) {
				amount += 0.1;
			}
			if (amount >= 1F) {
				amount = 1F;
				hibernate();
			}
		}
		else if (isWakingUp()) {
			if (amount > 0F) {
				DD.LOGGER.info("waking up amount -> {}", amount);
				
				amount -= 0.1;
			}
			if (amount <= 0F) {
				amount = 0F;
				setWakingUp(false);
			}
		}
		else {
			amount = 0F;
		}
		}
		super.tick();
	}

	public void goToSleep() {
		setFallingAsleep(true);
	}

	public void hibernate() {
		setFallingAsleep(false);
		this.entityData.set(DATA_DORMANT, true);
		setOwnerUUID(null);
	}

	public void wakeUp() {
		setWakingUp(true);
		this.entityData.set(DATA_DORMANT, false);
	}

	public void feed(UUID owner) {
		setLoyaltyTicks(MAX_LOYALTY_TICKS);
		if (getOwnerUUID() == null || isDormant()) {
			setOwnerUUID(owner);
		}
	}
	
	/**
	 * TODO review: this should be an indication of completion not transitory. maybe rename?
	 * ex. isDormant
	 * @return
	 */
	public boolean isDormant() {
		return this.entityData.get(DATA_DORMANT);
	}
	
	public boolean isActive() {
		return !isDormant() && !isFallingAsleep() && !isWakingUp();
	}

	/**
	 * Set initial values of synced data
	 */
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_DORMANT, true);
		this.entityData.define(DATA_LOYAL_TICKS, 0);
		this.entityData.define(DATA_OWNER_UUID, Optional.empty());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);

		tag.putBoolean(DORMANT, isDormant());
		tag.putInt(LOYALTY_TICKS, getLoyaltyTicks());

		if (this.getOwnerUUID() != null) {
			tag.putUUID(OWNER, this.getOwnerUUID());
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.entityData.set(DATA_DORMANT, tag.getBoolean(DORMANT));

		if (tag.hasUUID(OWNER)) {
			UUID uuid = tag.getUUID(OWNER);
			try {
				this.setOwnerUUID(uuid);
			} catch (Throwable throwable) {
				// TODO
			}
		}
	}

	/**
	 * 
	 * @author Mark Gottschling on Apr 9, 2022
	 *
	 */
	public static class BoulderRandomStrollGoal extends WaterAvoidingRandomStrollGoal {
		private BoulderOld boulder;
		public BoulderRandomStrollGoal(BoulderOld boulder, double speedModifier) {
			super(boulder, speedModifier);
			this.boulder = boulder;
		}

		@Override
		public boolean canUse() {
			if (!boulder.isActive() || boulder.getOwnerUUID() != null) { //this.boulder.isDormant() || boulder.isFallingAsleep() || boulder.isWakingUp() /* || isFallingAsleep() */) {
				return false;
			}
			return super.canUse();
		}
	}

	public static class BoulderGoDormantGoal extends Goal {
		private BoulderOld boulder;

		public BoulderGoDormantGoal(BoulderOld boulder) {
			this.boulder = boulder;
		}

		@Override
		public boolean canUse() {
			// TODO in future include check for energy/follow time
			if (boulder.isActive() && boulder.getLoyaltyTicks() <= 0) {// this.boulder.isDormant() || boulder.isFallingAsleep() || boulder.isWakingUp()) {
				return true;
			}
			return false;
		}	

		@Override
		public void start() {
			super.start();
			this.boulder.goToSleep();
		}
	}

	public static class BoulderWakeUpGoal extends Goal {
		private BoulderOld boulder;

		public BoulderWakeUpGoal(BoulderOld boulder) {
			this.boulder = boulder;
		}
		
		@Override
		public boolean canUse() {
			if (boulder.isDormant() && boulder.getLoyaltyTicks() > 0) {
				return true;
			}
			return false;
		}
		
		@Override
		public void start() {
			super.start();
			this.boulder.wakeUp();
		}
	}
	
	public float getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(float previousAmount) {
		this.previousAmount = previousAmount;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getMaxLegAmount() {
		return maxLegAmount;
	}

	public void setMaxLegAmount(float maxLegAmount) {
		this.maxLegAmount = maxLegAmount;
	}

	public float getMaxBodyAmount() {
		return maxBodyAmount;
	}

	public void setMaxBodyAmount(float maxBodyAmount) {
		this.maxBodyAmount = maxBodyAmount;
	}

	public boolean isFallingAsleep() {
		return fallingAsleep;
	}

	private void setFallingAsleep(boolean fallingAsleep) {
		this.fallingAsleep = fallingAsleep;
	}

	public boolean isWakingUp() {
		return wakingUp;
	}

	private void setWakingUp(boolean wakingUp) {
		this.wakingUp = wakingUp;
	}

	public static EntityDataAccessor<Boolean> getDataHibernate() {
		return DATA_DORMANT;
	}

	public static String getHibernate() {
		return DORMANT;
	}

	@Nullable
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_OWNER_UUID).orElse((UUID)null);
	}

	public void setOwnerUUID(@Nullable UUID p_21817_) {
		this.entityData.set(DATA_OWNER_UUID, Optional.ofNullable(p_21817_));
	}
	
	public int getLoyaltyTicks() {
		return this.entityData.get(DATA_LOYAL_TICKS);
	}
	
	public void setLoyaltyTicks(int ticks) {
		this.entityData.set(DATA_LOYAL_TICKS, ticks);
	}
}
