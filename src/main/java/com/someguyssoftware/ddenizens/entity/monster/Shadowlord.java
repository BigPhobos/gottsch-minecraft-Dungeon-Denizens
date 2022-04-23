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

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.ai.goal.SummonGoal;
import com.someguyssoftware.ddenizens.entity.projectile.Harmball;
import com.someguyssoftware.ddenizens.setup.Registration;
import com.someguyssoftware.gottschcore.spatial.Coords;
import com.someguyssoftware.gottschcore.world.WorldInfo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 
 * @author Mark Gottschling on Apr 15, 2022
 *
 */
public class Shadowlord extends DDMonster {
	private static final int SUN_BURN_SECONDS = 2;
	protected static final double MELEE_DISTANCE_SQUARED = 16D;
	protected static final double SUMMON_DISTANCE_SQUARED = 1024D;
	protected static final double SHOOT_DISTANCE_SQUARED = 4096D;
	protected static final int SUMMON_CHARGE_TIME = 2400;

	private double auraOfBlindessTime;
	private int numSummonDaemons;
	

	/**
	 * 
	 * @param entityType
	 * @param level
	 */
	public Shadowlord(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
		setPersistenceRequired();
	}

	/**
	 * 
	 */
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		
		this.goalSelector.addGoal(4, new ShadowlordShootHarmGoal(this));
//		this.goalSelector.addGoal(4, new ShadowlordSummonGoal(this, SUMMON_CHARGE_TIME));
		this.goalSelector.addGoal(5, new ShadowlordMeleeAttackGoal(this, 1.0D, false));
		
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 64.0F, 0.2F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Boulder.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	/**
	 * 
	 * @return
	 */
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.ATTACK_DAMAGE, 8.0D)
				.add(Attributes.MAX_HEALTH, 50.0D)
				.add(Attributes.ARMOR, 10.0D)
				.add(Attributes.ARMOR_TOUGHNESS, 1.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
				.add(Attributes.MOVEMENT_SPEED, 0.2F)
				.add(Attributes.FOLLOW_RANGE, 80D);
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
			MobSpawnType spawnType, SpawnGroupData groupData, CompoundTag tag) {
		
		if (difficulty.getDifficulty() == Difficulty.NORMAL) {
			numSummonDaemons = 1;
		}
		else if (difficulty.getDifficulty() == Difficulty.HARD) {
			numSummonDaemons = 2;
		}
		else {
			numSummonDaemons = 0;
		}
		return super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);		
	}
	
	public static boolean checkShadowlordSpawnRules(EntityType<Shadowlord> mob, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
		return (level.getBiome(pos).getBiomeCategory() == BiomeCategory.NETHER || (level.getHeight() < 20) && checkMobSpawnRules(mob, level, spawnType, pos, random));
	}
	
	/**
	 * 
	 * @param entity
	 * @param amount
	 */
	public void drain(LivingEntity entity, float amount) {
		// add damage to Shadowlord's health
		setHealth(Math.min(getMaxHealth(), getHealth() + amount));
		// TODO could add some particles here to indicate drain
	}

	@Override
	public void aiStep() {
		/*
		 * Apply Aura of Blindness to Players
		 */
		// get all entities with radius
		double distance = 2;
		AABB aabb = AABB.unitCubeFromLowerCorner(this.position()).inflate(distance, distance, distance);
		List<? extends Player> list = this.getLevel().getEntitiesOfClass(Player.class, aabb, EntitySelector.NO_SPECTATORS);
		Iterator<? extends Player> iterator = list.iterator();
		while (iterator.hasNext()) {
			Player target = (Player)iterator.next();
			// test if player is wearing golden helmet
			ItemStack helmetStack = target.getItemBySlot(EquipmentSlot.HEAD);
			if (helmetStack.isEmpty() || helmetStack.getItem() != Items.GOLDEN_HELMET) {
				// inflict blindness
				// TEMP disable
				//				target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 10 * 20, 0), this);
			}
		}

		// set on fire if in sun
		if (this.isSunBurnTick()) {
			this.setSecondsOnFire(SUN_BURN_SECONDS);
		}
		super.aiStep();
	}
	
	/**
	 * TODO move to aiStep?
	 */
	@Override
	public void tick() {
		/*
		 * Create a ring of smoke particles to delineate the boundary of the Aura of Blindness 
		 */
		if (WorldInfo.isClientSide(this.getLevel())) {
			double x = 2D * Math.sin(auraOfBlindessTime);
			double z = 2D * Math.cos(auraOfBlindessTime);
			this.level.addParticle(ParticleTypes.SMOKE, this.position().x + x, position().y, position().z + z, 0, 0, 0);
			auraOfBlindessTime++;
			auraOfBlindessTime = auraOfBlindessTime % 360;
		}
		super.tick();
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		if (super.doHurtTarget(target)) {
			if (target instanceof Player) {
				int seconds = 10;
				// inflict poison
				((LivingEntity)target).addEffect(new MobEffectInstance(MobEffects.POISON, seconds * 20, 0), this);
			}
			return true;
		} 
		return false;
	}

	/**
	 * 
	 */
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (WorldInfo.isClientSide(this.level)) {
			return false;
		}

		if (damageSource.getEntity() != null && damageSource.getEntity() instanceof Player) {
			Player player = (Player)damageSource.getEntity();
			ItemStack heldStack = ((Player)damageSource.getEntity()).getItemInHand(InteractionHand.MAIN_HAND);

			if (!heldStack.isEmpty() && heldStack.getItem() == Items.GOLDEN_SWORD) {
				// increase damage to that of iron sword
				amount += 2.0F;
				// negate the weakness from the strike power of the sword
				if (player.hasEffect(MobEffects.WEAKNESS)) {
					amount += MobEffects.WEAKNESS.getAttributeModifierValue(0, null);
				}
			}
			// TODO add shadow sword condition
			else {
				amount = 1.0F;
			}
		}
		DD.LOGGER.info("new gold sword strike amount -> {}", amount);
		return super.hurt(damageSource, amount);
	}
	
	/**
	 * Fires harmball at target every 4 secs.
	 * @author Mark Gottschling on Apr 19, 2022
	 *
	 */
	static class ShadowlordShootHarmGoal extends Goal {
		private final Shadowlord shadowlord;
		public int chargeTime;

		public ShadowlordShootHarmGoal(Shadowlord mob) {
			this.shadowlord = mob;
		}

		@Override
		public boolean canUse() {
			return this.shadowlord.getTarget() != null && !(Shadowlord.MELEE_DISTANCE_SQUARED >= this.shadowlord.distanceToSqr(shadowlord.getTarget().getX(), shadowlord.getTarget().getY(), shadowlord.getTarget().getZ()));
		}

		@Override
		public void start() {
			this.chargeTime = 0;
		}

		@Override
		public void stop() {
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			LivingEntity livingentity = this.shadowlord.getTarget();
			if (livingentity != null) {
				if (livingentity.distanceToSqr(this.shadowlord) < SHOOT_DISTANCE_SQUARED && this.shadowlord.hasLineOfSight(livingentity)) {
					Level level = this.shadowlord.level;
					++this.chargeTime;

					// TODO could be config value
					if (this.chargeTime == 50) { // 3.5 secs
						Vec3 vec3 = this.shadowlord.getViewVector(1.0F);
						double x = livingentity.getX() - (this.shadowlord.getX() + vec3.x * 2.0D);
						double y = livingentity.getY(0.5D) - (this.shadowlord.getY(0.5D));
						double z = livingentity.getZ() - (this.shadowlord.getZ() + vec3.z * 2.0D);

						Harmball spell = new Harmball(Registration.HARMBALL_ENTITY_TYPE.get(), level);
						spell.init(this.shadowlord, x, y, z);
						spell.setPos(this.shadowlord.getX() + vec3.x * 2.0D, this.shadowlord.getY(0.5D), spell.getZ() + vec3.z * 2.0);
						level.addFreshEntity(spell);
						this.chargeTime = 0;
					}
				} else if (this.chargeTime > 0) {
					--this.chargeTime;
				}
			}
		}

		protected double getAttackReachSqr(LivingEntity entity) {
			return (double)(this.shadowlord.getBbWidth() * 2.0F * this.shadowlord.getBbWidth() * 2.0F + entity.getBbWidth());
		}
	}

	/**
	 * 
	 * @author Mark Gottschling on Apr 19, 2022
	 *
	 */
	static class ShadowlordSummonGoal extends SummonGoal {
		private final Shadowlord shadowlord;
		private int summonChargeTime;
		private int chargeTime;
		private Random random;

		public ShadowlordSummonGoal(Shadowlord mob, int summonChargeTime, boolean canSummonDaemon) {
			this.shadowlord = mob;
			this.summonChargeTime = summonChargeTime;
			
			this.random = new Random();
		}

		@Override
		public void start() {
			this.chargeTime = 100;
		}

		@Override
		public void stop() {
		}

		@Override
		public void tick() {
			// super.tick(level, random, entity, target) - abstract SummonGoal()
			// TODO getNumSpawns()
			// TODO getSpawnMob()
			LivingEntity target = this.shadowlord.getTarget();
			if (target != null) {
				if (target.distanceToSqr(this.shadowlord) < SUMMON_DISTANCE_SQUARED && this.shadowlord.hasLineOfSight(target)) {
					Level level = this.shadowlord.level;
					++this.chargeTime;

					// TODO or here summon(level, random, owner, entity list(s), quantity list, target)
					// TODO charge time can be a config
					if (this.chargeTime >= 500) { //summonChargeTime

						int y = shadowlord.blockPosition().getY();


						boolean spawnSuccess = false;
						// summon daemon is health < max/3
						if (shadowlord.getHealth() < shadowlord.getMaxHealth() / 3 && shadowlord.numSummonDaemons > 0) {
							spawnSuccess |=spawn((ServerLevel)level, random, shadowlord, Registration.DAEMON_ENTITY_TYPE.get(), new Coords(shadowlord.blockPosition().getX(), y + 1, shadowlord.blockPosition().getZ()), target);
							if (spawnSuccess) {
								shadowlord.numSummonDaemons--;
							}
						}
						else {
							// TODO can come from config
							int numSpawns = random.nextInt(1, 2);
							for (int i = 0; i < numSpawns; i++) {
								spawnSuccess |= spawn((ServerLevel)level, random, shadowlord, Registration.SHADOW_ENTITY_TYPE.get(), new Coords(shadowlord.blockPosition().getX(), y + 1, shadowlord.blockPosition().getZ()), target);
							}
							spawnSuccess |=spawn((ServerLevel)level, random, shadowlord, Registration.GHOUL_ENTITY_TYPE.get(), new Coords(shadowlord.blockPosition().getX(), y + 1, shadowlord.blockPosition().getZ()), target);
						}
						if (!WorldInfo.isClientSide(level) && spawnSuccess) {
							for (int p = 0; p < 20; p++) {
								double xSpeed = random.nextGaussian() * 0.02D;
								double ySpeed = random.nextGaussian() * 0.02D;
								double zSpeed = random.nextGaussian() * 0.02D;
								((ServerLevel)level).sendParticles(ParticleTypes.POOF, shadowlord.blockPosition().getX() + 0.5D, shadowlord.blockPosition().getY(), shadowlord.blockPosition().getZ() + 0.5D, 1, xSpeed, ySpeed, zSpeed, (double)0.15F);
							}
						}
						this.chargeTime = 0;
					}
				} else if (this.chargeTime > 0) {
					--this.chargeTime;
				}
			}
		}

		@Override
		public boolean canUse() {
			return true;
		}
	}

	/**
	 * 
	 * @author Mark Gottschling on Apr 19, 2022
	 *
	 */
	public static class ShadowlordMeleeAttackGoal extends MeleeAttackGoal {

		public ShadowlordMeleeAttackGoal(PathfinderMob mob, double walkSpeedModifier, boolean sprintSpeedModifier) {
			super(mob, walkSpeedModifier, sprintSpeedModifier);
		}

		/**
		 * 
		 */
		@Override
		public boolean canUse() {
			if (mob.getTarget() != null) {
				if (mob.distanceToSqr(mob.getTarget().getX(), mob.getTarget().getY(), mob.getTarget().getZ()) <= 100D) {
					return super.canUse();
				}
			}
			return false;
		}

		@Override
		public boolean canContinueToUse() {
			if (mob.getTarget() != null) {
				if (mob.distanceToSqr(mob.getTarget().getX(), mob.getTarget().getY(), mob.getTarget().getZ()) <= 100D) {
					return super.canContinueToUse();
				}
			}
			return false;
		}
	}
}