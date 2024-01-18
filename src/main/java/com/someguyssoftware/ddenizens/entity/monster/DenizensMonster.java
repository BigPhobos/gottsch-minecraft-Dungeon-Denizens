/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2022 Mark Gottschling (gottsch)
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

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.config.Config;
import com.someguyssoftware.ddenizens.config.Config.CommonSpawnConfig;
import com.someguyssoftware.ddenizens.config.Config.IMobConfig;
import com.someguyssoftware.ddenizens.config.Config.INetherMobConfig;
import com.someguyssoftware.ddenizens.config.Config.NetherSpawnConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

/**
 * @author Mark Gottschling on Apr 13, 2022
 *
 */
public abstract class DenizensMonster extends Monster implements IDenizensMonster {

	private static final int UNDERGROUND_HEIGHT = 60;

	private MonsterSize size;

	protected DenizensMonster(EntityType<? extends Monster> mob, Level level, MonsterSize size) {
		super(mob, level);
		setMonsterSize(size);
	}

	public static boolean checkDDMonsterSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		IMobConfig mobConfig = Config.Mobs.MOBS.get(EntityType.getKey(mob));		
		CommonSpawnConfig config = mobConfig.getSpawnConfig();
		return config.enabled.get()
				&& level.getDifficulty() != Difficulty.PEACEFUL
				&& isValidHeight(pos, config)
				&& isDarkEnoughToSpawn(level, pos, random)
				&& checkMobSpawnRules(mob, level, spawnType, pos, random);
	}
	
	public static boolean isValidHeight(BlockPos pos, CommonSpawnConfig config) {
		return pos.getY() > config.minHeight.get() && pos.getY() < config.maxHeight.get();
	}
	
	public static boolean checkDDMonsterNetherSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
//		DD.LOGGER.info("checking nether spawn rules at -> {}", pos);
		IMobConfig mobConfig = Config.Mobs.MOBS.get(EntityType.getKey(mob));
		NetherSpawnConfig config = ((INetherMobConfig)mobConfig).getNetherSpawn();
		return config.enabled.get()
				&& level.getDifficulty() != Difficulty.PEACEFUL
				&& isValidHeight(pos, config)
				&& isDarkEnoughToSpawn(level, pos, random)
				&& checkMobSpawnRules(mob, level, spawnType, pos, random);
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
	public static boolean checkIsNetherSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		if (level.getBiome(pos).is(BiomeTags.IS_NETHER) ) {
			return DenizensMonster.checkDDMonsterNetherSpawnRules(mob, level, spawnType, pos, random);
		}
		else {
			return DenizensMonster.checkDDMonsterSpawnRules(mob, level, spawnType, pos, random);
		}
	}

	public MonsterSize getMonsterSize() {
		return size;
	}

	public void setMonsterSize(MonsterSize size) {
		this.size = size;
	}

}
