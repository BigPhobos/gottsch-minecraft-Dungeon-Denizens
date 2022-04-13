/**
 * 
 */
package com.someguyssoftware.ddenizens.entity.monster;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * @author Mark Gottschling on Apr 6, 2022
 *
 */
public class Gazer extends FlyingMob {

	public Gazer(EntityType<? extends FlyingMob> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new Gazer.GazerMoveControl(this);
	}

	@Override
	protected void registerGoals() {
//		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(5, new Gazer.RandomFloatAroundGoal(this));
		this.goalSelector.addGoal(7, new Gazer.GazerLookGoal(this));
		//		this.goalSelector.addGoal(7, new Ghast.GazerShootParalysisGoal(this));
		//		this.goalSelector.addGoal(7, new Ghast.GazerSummon(this));
		//	      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_32755_) -> {
		//	          return Math.abs(p_32755_.getY() - this.getY()) <= 4.0D;
		//	       }));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false ));

	}

	/**
	 * this method needs to be assigned to the EntityType during EntityAttributeCreationEvent event.
	 * see ModSetup.onAttributeCreate()
	 * @return
	 */
	public static AttributeSupplier.Builder prepareAttributes() {
		// TODO update values
		return Mob.createMobAttributes()
				.add(Attributes.ATTACK_DAMAGE, 3.5)
				.add(Attributes.ATTACK_KNOCKBACK)
				.add(Attributes.ARMOR, 3.0D)
				.add(Attributes.ARMOR_TOUGHNESS, 1.0D)
				.add(Attributes.MAX_HEALTH, 18.0)
				.add(Attributes.FOLLOW_RANGE, 100.0)
				.add(Attributes.MOVEMENT_SPEED, 0.28F);
	}

	/**
	 * 
	 * @author Mark Gottschling on Apr 6, 2022
	 *
	 */
	static class RandomFloatAroundGoal extends Goal {
		private final Gazer gazer;

		public RandomFloatAroundGoal(Gazer gazer) {
			this.gazer = gazer;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		public boolean canUse() {
			MoveControl moveControl = this.gazer.getMoveControl();
			if (!moveControl.hasWanted()) {
				return true;
			} else {
				double d0 = moveControl.getWantedX() - this.gazer.getX();
				double d1 = moveControl.getWantedY() - this.gazer.getY();
				double d2 = moveControl.getWantedZ() - this.gazer.getZ();
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}

		public boolean canContinueToUse() {
			return false;
		}

		/**
		 * 
		 */
		public void start() {
			Random random = this.gazer.getRandom();
			double x = this.gazer.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 8.0F);
			// TODO need a max y off the ground, ie 15 blocks above floor.
			double y = this.gazer.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 8.0F);
			double z = this.gazer.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 8.0F);
			this.gazer.getMoveControl().setWantedPosition(x, y, z, 1.0D);
		}
	}

	/**
	 * 
	 * @author Mark Gottschling on Apr 6, 2022
	 *
	 */
	static class GazerLookGoal extends Goal {
		private final Gazer gazer;

		public GazerLookGoal(Gazer gazer) {
			this.gazer = gazer;
			this.setFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean canUse() {
			return true;
		}

		public boolean requiresUpdateEveryTick() {
			return true;
		}

		public void tick() {
			if (this.gazer.getTarget() == null) {
				Vec3 vec3 = this.gazer.getDeltaMovement();
				this.gazer.setYRot(-((float)Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI));
				this.gazer.yBodyRot = this.gazer.getYRot();
			} else {
				LivingEntity livingentity = this.gazer.getTarget();
				double d0 = 64.0D;
				if (livingentity.distanceToSqr(this.gazer) < 4096.0D) {
					double d1 = livingentity.getX() - this.gazer.getX();
					double d2 = livingentity.getZ() - this.gazer.getZ();
					this.gazer.setYRot(-((float)Mth.atan2(d1, d2)) * (180F / (float)Math.PI));
					this.gazer.yBodyRot = this.gazer.getYRot();
				}
			}
		}
	}

	/**
	 * 
	 * @author Mark Gottschling on Apr 6, 2022
	 *
	 */
	static class GazerMoveControl extends MoveControl {
		private final Gazer gazer;
		private int floatDuration;

		public GazerMoveControl(Gazer gazer) {
			super(gazer);
			this.gazer = gazer;
		}

		public void tick() {
			if (this.operation == MoveControl.Operation.MOVE_TO) {
				if (this.floatDuration-- <= 0) {
					this.floatDuration += this.gazer.getRandom().nextInt(5) + 2;
					Vec3 vec3 = new Vec3(this.wantedX - this.gazer.getX(), this.wantedY - this.gazer.getY(), this.wantedZ - this.gazer.getZ());
					double d0 = vec3.length();
					vec3 = vec3.normalize();
					if (this.canReach(vec3, Mth.ceil(d0))) {
						this.gazer.setDeltaMovement(this.gazer.getDeltaMovement().add(vec3.scale(0.1D)));
					} else {
						this.operation = MoveControl.Operation.WAIT;
					}
				}

			}
		}

		private boolean canReach(Vec3 p_32771_, int p_32772_) {
			AABB aabb = this.gazer.getBoundingBox();

			for(int i = 1; i < p_32772_; ++i) {
				aabb = aabb.move(p_32771_);
				if (!this.gazer.level.noCollision(this.gazer, aabb)) {
					return false;
				}
			}

			return true;
		}
	}
}
