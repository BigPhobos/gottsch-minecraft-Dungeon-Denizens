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
import java.util.Random;

import com.someguyssoftware.ddenizens.DD;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * 
 * @author Mark Gottschling on Apr 27, 2022
 *
 */
public class Orc extends Monster {
	private static final EntityDataAccessor<Byte> DATA = SynchedEntityData.defineId(Boulder.class, EntityDataSerializers.BYTE);
	private static final String DATA_TAG = "data";
	private static final byte RIGHT_SHOULDER_PAD = 0;
	private static final byte LEFT_SHOULDER_PAD = 1;
	private static final byte HAIR = 2;
	private static final byte BRACERS = 3;
	
	public Orc(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Boulder.class, 6.0F, 1.0D, 1.2D, (entity) -> {
			if (entity instanceof Boulder) {
				 return ((Boulder)entity).isActive();
			}
			return false;
		}));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(5, new MoveThroughVillageGoal(this, 1.0D, true, 4, () -> true));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}
	
	/**
	 * this method needs to be assigned to the EntityType during EntityAttributeCreationEvent event.
	 * see ModSetup.onAttributeCreate()
	 * @return
	 */
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.ATTACK_DAMAGE, 3D)
				.add(Attributes.ATTACK_KNOCKBACK)
				.add(Attributes.MAX_HEALTH, 30.0)
				.add(Attributes.FOLLOW_RANGE, 20.0)
				.add(Attributes.MOVEMENT_SPEED, 0.25F);                
	}
	
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
			MobSpawnType spawnType, SpawnGroupData groupData, CompoundTag tag) {
		groupData =  super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);

		double healthBonus = round(random.nextDouble() * 10D, 2);
		this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", healthBonus, AttributeModifier.Operation.ADDITION));

		double attackBonus = round(random.nextDouble() * 2.5D, 2);
		this.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", attackBonus, AttributeModifier.Operation.ADDITION));

		double armorBonus = round(random.nextDouble() * 2D, 2);
		this.getAttribute(Attributes.ARMOR).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", armorBonus, AttributeModifier.Operation.ADDITION));

		double armorToughBonus = round(random.nextDouble() * 5D, 2);
		this.getAttribute(Attributes.ARMOR_TOUGHNESS).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", armorToughBonus, AttributeModifier.Operation.ADDITION));
		
		double knockbackResistanceBonus = round(random.nextDouble() * 1.5D, 2);
		this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", knockbackResistanceBonus, AttributeModifier.Operation.ADDITION));
		
		double attackKnockbackBonus = round(random.nextDouble() * 1.5D, 2);
		this.getAttribute(Attributes.ATTACK_KNOCKBACK).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", attackKnockbackBonus, AttributeModifier.Operation.ADDITION));
				
		double chance = this.random.nextDouble() * 1.5D * (double)difficulty.getSpecialMultiplier();
		if (chance > 1.0D) {
			this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", chance, AttributeModifier.Operation.MULTIPLY_TOTAL));
		}

		Random random = new Random();
		byte data = 0;
		if (random.nextBoolean()) {
			data |= 1 << RIGHT_SHOULDER_PAD; // 0 = right shoulder pad
		}
		if (random.nextBoolean()) {
			data |= 1 << LEFT_SHOULDER_PAD;
		};
		if (random.nextBoolean()) {
			data |= 1 << HAIR;
		}
		if (random.nextBoolean()) {
			data |= 1 << BRACERS;
		}		
		setData(data);
		
		return groupData;
	}
	
	/**
	 * Set initial values of synced data
	 */
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA, (byte)0);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);

		tag.putByte(DATA_TAG, getData());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains(DATA_TAG)) {
			this.setData(tag.getByte(DATA_TAG));
		}
	}
	

	public byte getDataBit(int position) {
		   return (byte) ((getData() >> position) & 1);
		}
	
	public static double round(double x, long fraction) {
	    return (double) Math.round(x * fraction) / fraction;
	}

	public boolean hasRightShoulderPad() {
		return getDataBit(RIGHT_SHOULDER_PAD) == 1;
	}

	public boolean hasLeftShoulderPad() {
		return getDataBit(LEFT_SHOULDER_PAD) == 1;
	}

	public boolean hasHair() {
		return getDataBit(HAIR) == 1;
	}

	public boolean hasBracers() {
		return getDataBit(BRACERS) == 1;
	}
	
	public byte getData() {
		return this.entityData.get(DATA);
	}
	
	public void setData(byte data) {
		this.entityData.set(DATA, data);
	}
}
