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

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;
import java.util.function.Predicate;

/**
 *
 * @author Mark Gottschling on Jan 14, 2024
 *
 */
public abstract class DenizensFlyingMonster extends FlyingMob implements IDenizensMonster {

    private MonsterSize size;

    public final Predicate<LivingEntity> playerNonOwner = (entity) -> {
        if (entity instanceof Player) {
            return getSummonedOwner() == null || !(getSummonedOwner() instanceof Player);
        }
        return true;
    };

    protected DenizensFlyingMonster(EntityType<? extends FlyingMob> type, Level level, MonsterSize size) {
        super(type, level);
        setMonsterSize(size);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        if (this.getSummonedOwner() != null) {
            if (this.getSummonedOwner() instanceof Player) {
                tag.putBoolean(PLAYER_OWNER, true);
            } else {
                tag.putBoolean(PLAYER_OWNER, false);
            }
            tag.putUUID(SUMMONED_OWNER, getSummonedOwner().getUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.hasUUID(SUMMONED_OWNER)) {
            UUID uuid = tag.getUUID(SUMMONED_OWNER);

            LivingEntity owner = null;
            if (tag.contains(PLAYER_OWNER) && tag.getBoolean(PLAYER_OWNER)) {
                owner = this.level().getPlayerByUUID(uuid);
            } else {
                owner = (LivingEntity)((ServerLevel)this.level()).getEntity(uuid);
            }
            this.setSummonedOwner(owner);
        }
    }

    public MonsterSize getMonsterSize() {
        return size;
    }

    public void setMonsterSize(MonsterSize size) {
        this.size = size;
    }
}
