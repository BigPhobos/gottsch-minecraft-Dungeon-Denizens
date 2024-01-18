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

import com.someguyssoftware.ddenizens.entity.projectile.HarmSpell;
import com.someguyssoftware.ddenizens.setup.Registration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * @author Mark Gottschling on Jan 13, 2024
 *
 */
public class CastHarmGoal extends Goal {
    private static final int DEFAULT_CHARGE_TIME = 50;
    private static final double DEFAULT_CAST_DISTANCE = 4096D;
    private final Mob mob;
    public int maxChargeTime;
    public int chargeTime;
    public double castDistance;
    public double meleeDistance;

    public CastHarmGoal(Mob mob) {
        this(mob,
                DEFAULT_CHARGE_TIME, DEFAULT_CAST_DISTANCE,
                0);
    }

    public CastHarmGoal(Mob mob, int maxChargeTime, double castDistance, double meleeDistance) {
        this.mob = mob;
        this.maxChargeTime = maxChargeTime;
        this.castDistance = castDistance;
        this.meleeDistance = meleeDistance;
    }

    @Override
    public boolean canUse() {
        return this.mob.getTarget() != null;
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
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null) {
            if (livingentity.distanceToSqr(this.mob) < this.castDistance && this.mob.hasLineOfSight(livingentity)
                    && livingentity.distanceToSqr(this.mob) > Math.max(this.meleeDistance, this.getAttackReachSqr(mob.getTarget()))) {

                Level level = this.mob.level();
                ++this.chargeTime;

                if (this.chargeTime >= maxChargeTime) {
                    Vec3 vec3 = this.mob.getViewVector(1.0F);
                    double x = livingentity.getX() - (this.mob.getX() + vec3.x * 2.0D);
                    double y = livingentity.getY(0.5D) - (this.mob.getY(0.5D));
                    double z = livingentity.getZ() - (this.mob.getZ() + vec3.z * 2.0D);

                    HarmSpell spell = new HarmSpell(Registration.HARM_SPELL_ENTITY_TYPE.get(), level);
                    spell.init(this.mob, x, y, z);
                    spell.setPos(this.mob.getX() + vec3.x * 2.0D, this.mob.getY(0.5D), spell.getZ() + vec3.z * 2.0);
                    level.addFreshEntity(spell);
                    this.chargeTime = 0;
                }
            } else if (this.chargeTime > 0) {
                --this.chargeTime;
            }

        }
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth() + 0.5F);
    }
}
