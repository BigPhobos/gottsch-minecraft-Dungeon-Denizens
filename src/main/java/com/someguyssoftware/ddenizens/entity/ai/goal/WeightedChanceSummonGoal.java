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

import com.someguyssoftware.ddenizens.DD;
import mod.gottsch.forge.gottschcore.random.RandomHelper;
import mod.gottsch.forge.gottschcore.random.WeightedCollection;
import mod.gottsch.forge.gottschcore.spatial.Coords;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * @author Mark Gottschling on Jan 11, 2024
 *
 */
public class WeightedChanceSummonGoal extends ChanceSummonGoal {
    private static final int DEFAULT_COOLDOWN_TIME = 1200;
    private final Mob mob;
    private final WeightedCollection<Double, EntityType<? extends Mob>> mobs;
    private int minMobs;
    private int maxMobs;

    public WeightedChanceSummonGoal(Mob summoner, WeightedCollection<Double, EntityType<? extends Mob>> mobs) {
        this(summoner, DEFAULT_COOLDOWN_TIME, 100, mobs, 1, 1);
    }

    public WeightedChanceSummonGoal(Mob summoner, EntityType<? extends Mob> mob) {
        this(summoner, new WeightedCollection<Double, EntityType<? extends Mob>>().add(1D, mob));
    }

    public WeightedChanceSummonGoal(Mob summoner, int cooldownTime, double chance, EntityType<? extends Mob> mob, int minMobs, int maxMobs) {
        this(summoner, cooldownTime, chance, new WeightedCollection<Double, EntityType<? extends Mob>>().add(1D, mob), minMobs, maxMobs);
    }

    public WeightedChanceSummonGoal(Mob summoner, int cooldownTime, double chance, WeightedCollection<Double, EntityType<? extends Mob>> mobs, int minMobs, int maxMobs) {
        super(cooldownTime, chance);
        this.mob = summoner;
        this.mobs = mobs;
        this.minMobs = minMobs;
        this.maxMobs = maxMobs;
    }

    @Override
    public void start() {
        this.cooldownCount = cooldownTime / 2;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            // increase cooldown count regardless is the beholderkin can see the target or not.
            ++this.cooldownCount;
            if (target.distanceToSqr(this.mob) < 1024.0D && this.mob.hasLineOfSight(target)) {
                Level level = this.mob.level();
                if (this.getCooldownCount() >= getCooldownTime()) {
                    if (RandomHelper.checkProbability(mob.getRandom(), getProbability())) {
                        int y = 0;
                        int height = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, mob.blockPosition().getX(), mob.blockPosition().getZ());
                        if (mob.blockPosition().getY() > height) {
                            // mob is above ground
                            y = height;
                        } else {
                            // find ground below mob
                            y = mob.blockPosition().below().getY();
                            while (level.getBlockState(mob.blockPosition().atY(y)).getBlock() == Blocks.AIR) {
                                y--;
                                if (Math.abs(mob.blockPosition().getY() - y) > 15) {
                                    return;
                                }
                            }
                        }
                        DD.LOGGER.debug("spawning...");
                        int numSpawns = mob.getRandom().nextInt(minMobs, maxMobs + 1);
                        for (int i = 0; i < numSpawns; i++) {
                            EntityType<? extends Mob> mob;
                            mob = mobs.next();

                            boolean spawnSuccess = super.spawn((ServerLevel) level, level.random, this.mob, mob, new Coords(this.mob.blockPosition().getX(), y + 1, this.mob.blockPosition().getZ()), target);
                            if (!level.isClientSide() && spawnSuccess) {
                                DD.LOGGER.info("debug! -> {}", this.mob.blockPosition());
                                for (int p = 0; p < 20; p++) {
                                    double xSpeed = this.mob.getRandom().nextGaussian() * 0.02D;
                                    double ySpeed = this.mob.getRandom().nextGaussian() * 0.02D;
                                    double zSpeed = this.mob.getRandom().nextGaussian() * 0.02D;
                                    ((ServerLevel) level).sendParticles(ParticleTypes.POOF, this.mob.blockPosition().getX() + 0.5D, this.mob.blockPosition().getY(), this.mob.blockPosition().getZ() + 0.5D, 1, xSpeed, ySpeed, zSpeed, (double) 0.15F);
                                }
                            }
                        }
                    }
                    // cooldown count is reset regardless of chance success or failure
                    this.cooldownCount = 0;
                }
            }
        }
    }
}
