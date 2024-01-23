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
package com.someguyssoftware.ddenizens.entity.ai.goal.target;

import com.someguyssoftware.ddenizens.entity.monster.IDenizensMonster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

/**
 *
 * @author Mark Gottschling on Jan 14, 2024
 *
 */
public class SummonedOwnerTargetGoal extends TargetGoal {
    private final IDenizensMonster mob;
    private LivingEntity ownerLastHurtBy;

    private int timestamp;

    public SummonedOwnerTargetGoal(IDenizensMonster mob) {
        super((Mob)mob, false);
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.mob.getSummonedOwner();
        LivingEntity target = ((Mob)this.mob).getTarget();
        if (owner == null || (target != null && !target.equals(owner))) {
            return false;
        }
        return true;
    }

    @Override
    public void start() {
        if (this.mob.getSummonedOwner() instanceof Player) {
            Player owner = (Player) mob.getSummonedOwner();
            if (owner.getLastHurtByMob() != null) {
                ((Mob)this.mob).setTarget(owner.getLastHurtByMob());
            } else if (owner.getLastHurtMob() != null) {
                ((Mob)this.mob).setTarget(owner.getLastHurtMob());
            }
        } else {
            ((Mob)this.mob).setTarget(((Mob)this.mob.getSummonedOwner()).getTarget());
        }
        super.start();
    }
}
