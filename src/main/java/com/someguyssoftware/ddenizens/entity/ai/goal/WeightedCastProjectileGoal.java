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
package com.someguyssoftware.ddenizens.entity.ai.goal;

import com.someguyssoftware.ddenizens.entity.projectile.AbstractDDHurtingProjectile;
import mod.gottsch.forge.gottschcore.random.WeightedCollection;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * @author Mark Gottschling on Jan 13, 2024
 *
 */
public class WeightedCastProjectileGoal extends Goal {
    private static final int DEFAULT_CHARGE_TIME = 80;
    private final Mob mob;
    private final WeightedCollection<Integer, AbstractDDHurtingProjectile> spells;
    public int chargeTime;
    public int chargeTimeCount;

    public WeightedCastProjectileGoal(Mob caster, WeightedCollection<Integer, AbstractDDHurtingProjectile> spells) {
        this(caster, DEFAULT_CHARGE_TIME, spells);
    }

    /**
     *
     * @param caster
     * @param chargeTime
     * @param spells
     */
    public WeightedCastProjectileGoal(Mob caster, int chargeTime, WeightedCollection<Integer, AbstractDDHurtingProjectile> spells) {
        super();
        // TODO should have a CastSpellGoal abstract
        this.mob = caster;
        this.spells = spells;
        this.chargeTime = chargeTime;
    }

    @Override
    public boolean canUse() {
        return this.mob.getTarget() != null && !(this.getAttackReachSqr(mob.getTarget()) >= this.mob.distanceToSqr(mob.getTarget().getX(), mob.getTarget().getY(), mob.getTarget().getZ()));
    }

    @Override
    public void start() {
        this.chargeTimeCount = 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null) {
            if (livingentity.distanceToSqr(this.mob) < 4096.0D && this.mob.hasLineOfSight(livingentity)) {
                Level level = this.mob.level();
                ++this.chargeTimeCount;

                if (this.chargeTimeCount >= chargeTime) {
                    Vec3 vec3 = this.mob.getViewVector(1.0F);
                    double x = livingentity.getX() - (this.mob.getX() + vec3.x * 2.0D);
                    double y = livingentity.getY(0.5D) - (this.mob.getY(0.5D));
                    double z = livingentity.getZ() - (this.mob.getZ() + vec3.z * 2.0D);

                    // select a spell
                    AbstractDDHurtingProjectile spell = this.spells.next().create(level);
                    spell.init(this.mob, x, y, z);
                    spell.setPos(this.mob.getX() + vec3.x * 2.0D, this.mob.getY(0.5D), mob.getZ() + vec3.z * 2.0);
                    level.addFreshEntity(spell);
                    this.chargeTimeCount = 0;
                    // play sound
                    this.mob.playSound(SoundEvents.SPLASH_POTION_THROW, 0.5F, 0.4F / (mob.getRandom().nextFloat() * 0.4F + 0.8F));

                }
            } else if (this.chargeTimeCount > 0) {
                --this.chargeTimeCount;
            }
        }
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }
}
