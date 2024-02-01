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
package com.someguyssoftware.ddenizens.entity.monster;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

/**
 *
 * @author Mark Gottschling on Jan 9, 2024
 *
 */
public interface IDenizensMonster {
    public static final String SUMMONED_OWNER = "summonedOwner";
    public static final String PLAYER_OWNER = "playerOwner";

    public static Predicate<LivingEntity> avoidBoulder = (entity) -> {
        if (entity instanceof Boulder) {
            return ((Boulder)entity).isActive();
        }
        return false;
    };

    public MonsterSize getMonsterSize();
    public void setMonsterSize(MonsterSize size);

    default public boolean canSummonedHaveOwner() {
        return false;
    }

    default public int getSummonedLifespan() {
        return 0;
    }

    default public void setSummonedLifespan(int lifespan) { }

    default public LivingEntity getSummonedOwner() {
        return null;
    }

    default public void setSummonedOwner(LivingEntity entity) {}
}
