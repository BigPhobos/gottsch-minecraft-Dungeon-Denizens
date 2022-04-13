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
package com.someguyssoftware.ddenizens.setup;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.Boulder;
import com.someguyssoftware.ddenizens.entity.monster.DDMonster;
import com.someguyssoftware.ddenizens.entity.monster.Ettin;
import com.someguyssoftware.ddenizens.entity.monster.Gazer;
import com.someguyssoftware.ddenizens.entity.monster.Ghoul;
import com.someguyssoftware.ddenizens.entity.monster.Headless;
import com.someguyssoftware.ddenizens.entity.monster.Shadow;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Common event bus subscriber.
 * @author Mark Gottschling on Apr 2, 2022
 *
 */
@Mod.EventBusSubscriber(modid = DD.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {
	public static void init(final FMLCommonSetupEvent event) {
    }
	
	/**
	 * attach defined attributes to the entity.
	 * @param event
	 */
    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(Registration.HEADLESS_ENTITY_TYPE.get(), Headless.createAttributes().build());
        event.put(Registration.GHOUL_ENTITY_TYPE.get(), Ghoul.createAttributes().build());
        event.put(Registration.ETTIN_ENTITY_TYPE.get(), Ettin.createAttributes().build());
        
        event.put(Registration.GAZER_ENTITY_TYPE.get(), Gazer.prepareAttributes().build());
        
        event.put(Registration.BOULDER_ENTITY_TYPE.get(), Boulder.createAttributes().build());
        event.put(Registration.SHADOW_ENTITY_TYPE.get(), Shadow.createAttributes().build());
    }
    
	@SubscribeEvent
	public static void registerEntitySpawn(RegistryEvent.Register<EntityType<?>> event) {
		// TODO add custom spawnRules that checks the Y value + light .. ie anything below sea level
		SpawnPlacements.register(Registration.HEADLESS_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
		SpawnPlacements.register(Registration.GHOUL_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
		
		//Boulder::checkSpawnRules);
		SpawnPlacements.register(Registration.SHADOW_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Shadow::checkShadowSpawnRules);
		
	}
	
	@Mod.EventBusSubscriber(modid = DD.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class BiomeRegistrationHandler {
		/*
		 * Register the Features with Biomes
		 */
		@SubscribeEvent
		public static void onBiomeLoading(final BiomeLoadingEvent event) {
			DD.LOGGER.info("event for biome -> {}, category -> {}", event.getName(), event.getCategory().getName());
			/* 
			 * register mob spawns to biomes
			 */
			// overworld
			if (event.getCategory() != BiomeCategory.NETHER &&
					event.getCategory() != BiomeCategory.THEEND) {
				event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.HEADLESS_ENTITY_TYPE.get(), 100, 1, 3));
				event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.GHOUL_ENTITY_TYPE.get(), 50, 1, 1));
			}
			// overworld + nether
			else if (event.getCategory() != BiomeCategory.THEEND) {
				event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.SHADOW_ENTITY_TYPE.get(), 25, 1, 2));
			}
		}
		
//	    @SubscribeEvent
//	    public static void registerParticle(ParticleFactoryRegisterEvent event){
//	        Minecraft.getInstance().particleEngine.register(Registration.SHADOW_PARTICLE.get(), ModGlowParticle.BlockOffSideProvider::new);
//	    }
	    
		@SubscribeEvent
		public static void onFeedBoulder(final PlayerInteractEvent.EntityInteract event) {
			// get item in hand
			ItemStack heldItem = event.getItemStack();

			if (!heldItem.isEmpty() && heldItem.getItem() == Items.GLOW_LICHEN) {
				if (event.getTarget() instanceof Boulder) {
					Boulder boulder = (Boulder)event.getTarget();
					boulder.feed(event.getPlayer().getUUID());
				}
			}
		}
	}
	
}
