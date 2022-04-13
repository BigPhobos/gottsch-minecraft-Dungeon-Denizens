package com.someguyssoftware.ddenizens.setup;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.Boulder;
import com.someguyssoftware.ddenizens.entity.monster.Ettin;
import com.someguyssoftware.ddenizens.entity.monster.Gazer;
import com.someguyssoftware.ddenizens.entity.monster.Ghoul;
import com.someguyssoftware.ddenizens.entity.monster.Headless;
import com.someguyssoftware.ddenizens.entity.monster.Shadow;

import net.minecraft.client.particle.Particle;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Setup deferred registries. Original developer defined all block, items, entities etc here.
 * @author Mark Gottschling on Apr 1, 2022
 *
 */
public class Registration {
	private static final String HEADLESS = "headless";
	private static final String GHOUL = "ghoul";
	private static final String ETTIN = "ettin";
	private static final String SHADOW = "shadow";
	
	/*
	 * TODO could place these in their own classes, but init() here. ex. TreasureBlocks, TreasureItems, TreasureEntities etc.
	 */
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DD.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, DD.MODID);
	
	// particles
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DD.MODID);
		
	/**
	 * 
	 */
	public static void init() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(eventBus);	
		ENTITIES.register(eventBus);		
		PARTICLES.register(eventBus);
	}

	public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(CreativeModeTab.TAB_MISC);
	
	public static final RegistryObject<EntityType<Headless>> HEADLESS_ENTITY_TYPE = Registration.ENTITIES.register(HEADLESS, () -> EntityType.Builder.of(Headless::new, MobCategory.MONSTER)
			.sized(0.6f, 1.5f)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(50)
			.fireImmune()
			.build(HEADLESS));
	
	public static final RegistryObject<EntityType<Ghoul>> GHOUL_ENTITY_TYPE = Registration.ENTITIES.register("ghoul", () -> EntityType.Builder.of(Ghoul::new, MobCategory.MONSTER)
			.sized(0.6f, 1.68f)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.build("ghoul"));
	
	public static final RegistryObject<EntityType<Ettin>> ETTIN_ENTITY_TYPE = Registration.ENTITIES.register(ETTIN, () -> EntityType.Builder.of(Ettin::new, MobCategory.MONSTER)
			.sized(1.25f, 1.125f)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(ETTIN));
	
	public static final RegistryObject<EntityType<Gazer>> GAZER_ENTITY_TYPE = Registration.ENTITIES.register("gazer", () -> EntityType.Builder.of(Gazer::new, MobCategory.MONSTER)
			.sized(1.25f, 1.125f)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build("gazer"));
	
	public static final RegistryObject<EntityType<Boulder>> BOULDER_ENTITY_TYPE = Registration.ENTITIES.register("boulder", () -> EntityType.Builder.of(Boulder::new, MobCategory.CREATURE)
			.sized(1f, 1f)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build("boulder"));
	
	public static final RegistryObject<EntityType<Shadow>> SHADOW_ENTITY_TYPE = Registration.ENTITIES.register(SHADOW, () -> EntityType.Builder.of(Shadow::new, MobCategory.MONSTER)
			.sized(0.6f, 1.95f)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SHADOW));
	
	// mod eggs
	public static final RegistryObject<Item> HEADLESS_EGG = Registration.ITEMS.register(HEADLESS, () -> new ForgeSpawnEggItem(HEADLESS_ENTITY_TYPE, 0xc8b486, 0x6f5e48, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> GHOUL_EGG = Registration.ITEMS.register(GHOUL, () -> new ForgeSpawnEggItem(GHOUL_ENTITY_TYPE, 0xffffff, 0x6f5e48, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> ETTIN_EGG = Registration.ITEMS.register(ETTIN, () -> new ForgeSpawnEggItem(ETTIN_ENTITY_TYPE, 0x626262, 0x6f5e48, Registration.ITEM_PROPERTIES));
	
	public static final RegistryObject<Item> GAZER_EGG = Registration.ITEMS.register("gazer", () -> new ForgeSpawnEggItem(GAZER_ENTITY_TYPE, 0xff0000, 0x00ff00, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> BOULDER_EGG = Registration.ITEMS.register("boulder", () -> new ForgeSpawnEggItem(BOULDER_ENTITY_TYPE, 0xff0000, 0x00ff00, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> SHADOW_EGG = Registration.ITEMS.register(SHADOW, () -> new ForgeSpawnEggItem(SHADOW_ENTITY_TYPE, 0x000000, 0x00ff00, Registration.ITEM_PROPERTIES));
	
	// particles
//	public static final RegistryObject<SimpleParticleType> SHADOW_PARTICLE = Registration.PARTICLES.register("shadow_particle", () -> new SimpleParticleType(true));
}
