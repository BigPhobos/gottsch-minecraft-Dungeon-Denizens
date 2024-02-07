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

import com.someguyssoftware.ddenizens.entity.monster.Beholderkin;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

/**
 * @author Mark Gottschling on Feb 5, 2024
 *
 */
public class PassiveMeleeAttackGoal extends Goal {
    private static final int DEFAULT_COOLDOWN_TIME = 20;
    private Mob mob;
    private int cooldownCount;
    private int cooldownTime;

    public PassiveMeleeAttackGoal(Mob mob) {
        this(mob, DEFAULT_COOLDOWN_TIME);
    }

    public PassiveMeleeAttackGoal(Mob mob, int cooldownTime) {
        this.mob = mob;
        this.cooldownTime = cooldownTime;
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null;
    }

    @Override
    public void start() {
        // set to the threshold so the attack can be performed right away
        // on first use.
        this.cooldownCount = cooldownTime ;
    }

    public void stop() {
        LivingEntity livingentity = this.mob.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.mob.setTarget((LivingEntity) null);
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        // cooldown regardless of criteria
        cooldownCount = Math.min(++this.cooldownCount, cooldownTime);
        if (cooldownCount >= cooldownTime) {
            if (this.getAttackReachSqr(mob.getTarget()) >= this.mob.distanceToSqr(mob.getTarget().getX(), mob.getTarget().getY(), mob.getTarget().getZ())) {
                doAttackAmin(mob);
                this.mob.doHurtTarget(mob.getTarget());
                this.cooldownCount = 0;
            }
        }
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return (double)(mob.getBbWidth() * 2.0F * mob.getBbWidth() * 2.0F + entity.getBbWidth());
    }

    public void doAttackAmin(Mob mob) {
        mob.swing(getMeleeHand(mob));
    }

    public InteractionHand getMeleeHand(Mob mob) {
        // TODO test which hand has a non-bow in it. actually call mob.getMeleeHand()
        return InteractionHand.MAIN_HAND;
    }
}
