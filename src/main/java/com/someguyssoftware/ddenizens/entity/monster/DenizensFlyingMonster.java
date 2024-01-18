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

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.level.Level;

/**
 *
 * @author Mark Gottschling on Jan 14, 2024
 *
 */
public abstract class DenizensFlyingMonster extends FlyingMob implements IDenizensMonster {

    private MonsterSize size;

    protected DenizensFlyingMonster(EntityType<? extends FlyingMob> type, Level level, MonsterSize size) {
        super(type, level);
        setMonsterSize(size);
    }

    public MonsterSize getMonsterSize() {
        return size;
    }

    public void setMonsterSize(MonsterSize size) {
        this.size = size;
    }
}
