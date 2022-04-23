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
import com.someguyssoftware.ddenizens.entity.monster.Daemon;
import com.someguyssoftware.ddenizens.entity.monster.Ettin;
import com.someguyssoftware.ddenizens.entity.monster.Gazer;
import com.someguyssoftware.ddenizens.entity.monster.Ghoul;
import com.someguyssoftware.ddenizens.entity.monster.Headless;
import com.someguyssoftware.ddenizens.entity.monster.Shadow;
import com.someguyssoftware.ddenizens.entity.monster.Shadowlord;
import com.someguyssoftware.gottschcore.world.WorldInfo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.random.Weight;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
public class CommonSetup {
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
        event.put(Registration.SHADOWLORD_ENTITY_TYPE.get(), Shadowlord.createAttributes().build());
        event.put(Registration.DAEMON_ENTITY_TYPE.get(), Daemon.createAttributes().build());
    }
    
	@SubscribeEvent
	public static void registerEntitySpawn(RegistryEvent.Register<EntityType<?>> event) {
		SpawnPlacements.register(Registration.HEADLESS_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(Registration.GHOUL_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
		SpawnPlacements.register(Registration.BOULDER_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Boulder::checkSpawnRules);

		SpawnPlacements.register(Registration.SHADOW_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Shadow::checkShadowSpawnRules);
		SpawnPlacements.register(Registration.SHADOWLORD_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Shadowlord::checkShadowlordSpawnRules);
		SpawnPlacements.register(Registration.GAZER_ENTITY_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DDMonster::checkDDSpawnRules);
		SpawnPlacements.register(Registration.DAEMON_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Daemon::checkDaemonSpawnRules);
		
	}
	
	@Mod.EventBusSubscriber(modid = DD.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class ForgeBusSucscriber {
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
				// NOTE can't do this due to ConcurrentModificationException
				Weight weight = null;
				for(SpawnerData spawnerData : event.getSpawns().getSpawner(MobCategory.MONSTER)) {
					if (spawnerData.type == EntityType.ZOMBIE) {
						weight = spawnerData.getWeight();
					}
				}
				if (weight != null) {
					event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.HEADLESS_ENTITY_TYPE.get(), weight, 1, 3));
					event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.GHOUL_ENTITY_TYPE.get(), (int) (weight.asInt() * 0.5), 1, 1));
					event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(Registration.BOULDER_ENTITY_TYPE.get(), (int) (weight.asInt() * 0.5), 1, 1));
				}
//				event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.HEADLESS_ENTITY_TYPE.get(), 75, 1, 3));
//				event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.GHOUL_ENTITY_TYPE.get(), 50, 1, 1));
//				event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(Registration.BOULDER_ENTITY_TYPE.get(), 50, 1, 1));
			}
			// overworld + nether
			else if (event.getCategory() != BiomeCategory.THEEND) {
				if (event.getCategory() == BiomeCategory.NETHER) {
					// TODO add low weight < 10
				}
				else {
					// TODO add weights based off zombie
				event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.SHADOW_ENTITY_TYPE.get(), 25, 1, 2));
				event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.SHADOWLORD_ENTITY_TYPE.get(), 15, 1, 2));
				event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.GAZER_ENTITY_TYPE.get(), 25, 1, 2));
				event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.DAEMON_ENTITY_TYPE.get(), 12, 1, 2));
				}
			}
		}
		
//	    @SubscribeEvent
//	    public static void registerParticle(ParticleFactoryRegisterEvent event){
//	        Minecraft.getInstance().particleEngine.register(Registration.SHADOW_PARTICLE.get(), ModGlowParticle.BlockOffSideProvider::new);
//	    }
	    
		@SubscribeEvent
		public static void addGoals(final EntityJoinWorldEvent event) {
			if (event.getEntity() instanceof Zombie) {
				((Zombie)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Zombie)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D));
			}
			else if (event.getEntity() instanceof Skeleton) {
				((Skeleton)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Skeleton)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D));
			}
			else if (event.getEntity() instanceof ZombieVillager) {
				((ZombieVillager)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((ZombieVillager)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D));
			}
		}
		
		@SubscribeEvent
		public static void onFeedBoulder(final PlayerInteractEvent.EntityInteract event) {
			// get item in hand
			ItemStack heldItem = event.getItemStack();

			if (!heldItem.isEmpty() && (heldItem.getItem() == Items.IRON_INGOT 
					|| heldItem.getItem() == Items.IRON_ORE 
					|| heldItem.getItem() == Items.DEEPSLATE_IRON_ORE)) {
				
				if (event.getTarget() instanceof Boulder) {
					Boulder boulder = (Boulder)event.getTarget();
					boulder.feed(event.getPlayer().getUUID());
				}
			}
		}
		
		@SubscribeEvent
		public static void hitFromShadowlord(LivingDamageEvent event) {
			if (WorldInfo.isClientSide(event.getEntity().level)) {
				return;
			}
						
			if (event.getEntity() instanceof Player && event.getSource().getEntity() instanceof Shadowlord) {
				// get the player
				ServerPlayer player = (ServerPlayer) event.getEntity();
				((Shadowlord)event.getSource().getEntity()).drain(player, event.getAmount());
			}
			else if (event.getEntity() instanceof Shadowlord && event.getSource().getEntity() instanceof Player) {

			}
		}
		
		// LivingHurtEvent on Shadowlord
		// TODO check if weapon == gold/shadow sword and add bonus to it. this is better than actuallyHurt() of the entity
		@SubscribeEvent
		public static void hitFromShadowlord(LivingHurtEvent event) {
			if (WorldInfo.isClientSide(event.getEntity().level)) {
				return;
			}

			// do something to player every update tick:
			if (event.getEntity() instanceof Player && event.getSource().getEntity() instanceof Shadowlord) {
				DD.LOGGER.info("event: hurting shadowlord from player -> {}", event.getAmount());
			}
		}
		
	}
	
}
