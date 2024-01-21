/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
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
package com.someguyssoftware.ddenizens.entity.projectile;

import com.someguyssoftware.ddenizens.setup.Registration;
import com.someguyssoftware.ddenizens.util.EquipmentUtil;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 *
 * @author Mark Gottschling on Jan 14, 2024
 *
 */
public class DisarmSpell extends AbstractDDHurtingProjectile implements ItemSupplier {
	private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(DisarmSpell.class, EntityDataSerializers.ITEM_STACK);

	/**
	 *
	 * @param level
	 * @return
	 */
	public DisarmSpell create(Level level) {
		return new DisarmSpell(Registration.DISARM_SPELL_ENTITY_TYPE.get(), level);
	}

	/**
	 *
	 * @param entityType
	 * @param level
	 */
	public DisarmSpell(EntityType<DisarmSpell> entityType, Level level) {
		super(entityType, level);
	}

	/**
	 *
	 * @param owner
	 * @param x
	 * @param y
	 * @param z
	 */
	public void init(LivingEntity owner, double x, double y, double z) {
		this.moveTo(owner.getX(), owner.getY(), owner.getZ(), this.getYRot(), this.getXRot());
		this.reapplyPosition();
		double d0 = Math.sqrt(x * x + y * y + z * z);
		if (d0 != 0.0D) {
			this.xPower = x / d0 * 0.1D;
			this.yPower = y / d0 * 0.1D;
			this.zPower = z / d0 * 0.1D;
		}
		this.setOwner(owner);
		this.setRot(owner.getYRot(), owner.getXRot());
	}

	@Override
	public void clientSideTick() {
		// TODO add a particle that is emitted in a circular (sin()) fashion as the
		// ball travels. See daemon for code of calculating circular positioning.
		super.clientSideTick();
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!this.level().isClientSide) {
			this.discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		if (!this.level().isClientSide) {
			Entity target = hitResult.getEntity();
			Entity ownerEntity = this.getOwner();
			if (target instanceof ServerPlayer) {
				this.playSound(SoundEvents.ALLAY_ITEM_TAKEN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);

				ServerPlayer player = (ServerPlayer) target;
				if (level().getGameTime() % 4 == 0) {
					EquipmentSlot slot = EquipmentUtil.ARMOR_SLOTS[player.getRandom().nextInt(EquipmentUtil.ARMOR_SLOTS.length)];
					ItemStack itemStack = player.getItemBySlot(slot);
					if (itemStack != ItemStack.EMPTY) {
						player.setItemSlot(slot, ItemStack.EMPTY);
						Containers.dropItemStack(level(),
								(double) player.blockPosition().getX(), (double) player.blockPosition().getY(), player.blockPosition().getZ(),
								itemStack);
					}
				} else {
					// disarm a held item
					InteractionHand hand;
					if (level().getGameTime() % 2 == 0) {
						hand = InteractionHand.MAIN_HAND;
					} else {
						hand = InteractionHand.OFF_HAND;
					}
					ItemStack itemStack = player.getItemInHand(hand);
					if (itemStack != ItemStack.EMPTY) {
						player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
						Containers.dropItemStack(level(),
								(double) player.blockPosition().getX(), (double) player.blockPosition().getY(), player.blockPosition().getZ(),
								itemStack);
					}
				}
			}
		}
	}

	public void setItem(ItemStack stack) {
		if (!stack.is(Registration.DISARM_SPELL_ITEM.get()) || stack.hasTag()) {
			this.getEntityData().set(DATA_ITEM_STACK, Util.make(stack.copy(), (itemStack) -> {
				itemStack.setCount(1);
			}));
		}
	}

	protected ItemStack getItemRaw() {
		return this.getEntityData().get(DATA_ITEM_STACK);
	}

	// TODO something else than smoke
	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.ENCHANT;
	}

	@Override
	public ItemStack getItem() {
		ItemStack stack = this.getItemRaw();
		return stack.isEmpty() ? new ItemStack(Registration.DISARM_SPELL_ITEM.get()) : stack;
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		ItemStack itemstack = this.getItemRaw();
		if (!itemstack.isEmpty()) {
			tag.put("Item", itemstack.save(new CompoundTag()));
		}

	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		ItemStack itemStack = ItemStack.of(tag.getCompound("Item"));
		this.setItem(itemStack);
	}
}
