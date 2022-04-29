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

import java.util.Random;

import com.someguyssoftware.ddenizens.config.Config;
import com.someguyssoftware.ddenizens.config.Config.IMobConfig;
import com.someguyssoftware.ddenizens.config.Config.INetherMobConfig;
import com.someguyssoftware.ddenizens.config.Config.NetherSpawnConfig;
import com.someguyssoftware.ddenizens.config.Config.SpawnConfig;
import com.someguyssoftware.ddenizens.setup.Registration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * @author Mark Gottschling on Apr 13, 2022
 *
 */
public abstract class DDMonster extends Monster {

	private static final int UNDERGROUND_HEIGHT = 60;
	
	protected DDMonster(EntityType<? extends Monster> mob, Level level) {
		super(mob, level);
	}

	/**
	 * 
	 * @param mob
	 * @param level
	 * @param spawnType
	 * @param pos
	 * @param random
	 * @return
	 */
	public static boolean checkDDSpawnRules(EntityType<? extends Mob> mob, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
//		boolean result = false;
		IMobConfig mobConfig = Config.Mobs.MOBS.get(mob.getRegistryName());		
		SpawnConfig config = mobConfig.getSpawnConfig();
//		return pos.getY() < UNDERGROUND_HEIGHT && checkMobSpawnRules(mob, level, spawnType, pos, random);
//		if (config.minHeight.get() != SpawnConfig.IGNORE_HEIGHT) {
//			result |= pos.getY() >config.minHeight.get();
//		}
//		if (config.maxHeight.get() != SpawnConfig.IGNORE_HEIGHT) {
//			result |= pos.getY() < config.maxHeight.get();
//		}

		return pos.getY() > config.minHeight.get() && pos.getY() < config.maxHeight.get() && checkMobSpawnRules(mob, level, spawnType, pos, random);
	}
	
	public static boolean checkDDNetherSpawnRules(EntityType<? extends Mob> mob, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
//		boolean result = false;
		IMobConfig mobConfig = Config.Mobs.MOBS.get(mob.getRegistryName());
		NetherSpawnConfig config = ((INetherMobConfig)mobConfig).getNetherSpawn();
//		if (netherConfig.minHeight.get() != SpawnConfig.IGNORE_HEIGHT) {
//			result |= pos.getY() > netherConfig.minHeight.get();
//		}
//		if (netherConfig.maxHeight.get() != SpawnConfig.IGNORE_HEIGHT) {
//			result |= pos.getY() < netherConfig.maxHeight.get();
//		}
//		return result |= checkMobSpawnRules(mob, level, spawnType, pos, random);
		return pos.getY() > config.minHeight.get() && pos.getY() < config.maxHeight.get() && checkMobSpawnRules(mob, level, spawnType, pos, random);

	}
	

}
