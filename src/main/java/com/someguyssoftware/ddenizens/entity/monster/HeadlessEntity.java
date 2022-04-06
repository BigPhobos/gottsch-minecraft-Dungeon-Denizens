/**
 * 
 */
package com.someguyssoftware.ddenizens.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Headless are about 20% better at everything than Zombies
 * and are smart enough to avoid Creepers.
 * 
 * @author Mark Gottschling on Apr 1, 2022
 *
 */
public class HeadlessEntity extends Monster {

	/**
	 * 
	 * @param entityType
	 * @param level
	 */
	public HeadlessEntity(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Creeper.class, 6.0F, 1.0D, 1.2D));
		// target goals
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers(HeadlessEntity.class)); /** Add Ettins, or Gazer */
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	/**
	 * this method needs to be assigned to the EntityType during EntityAttributeCreationEvent event.
	 * see ModSetup.onAttributeCreate()
	 * @return
	 */
	public static AttributeSupplier.Builder prepareAttributes() {
		return LivingEntity.createLivingAttributes()
				.add(Attributes.ATTACK_DAMAGE, 3.5)
				.add(Attributes.ATTACK_KNOCKBACK)
				.add(Attributes.ARMOR, 1.0D)
				.add(Attributes.ARMOR_TOUGHNESS, 1.0D)
				.add(Attributes.MAX_HEALTH, 24.0)
				.add(Attributes.FOLLOW_RANGE, 40.0)
				.add(Attributes.MOVEMENT_SPEED, 0.28F);                
	}
}
