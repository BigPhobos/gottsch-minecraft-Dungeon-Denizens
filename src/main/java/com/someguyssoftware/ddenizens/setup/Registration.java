package com.someguyssoftware.ddenizens.setup;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.Boulder;
import com.someguyssoftware.ddenizens.entity.monster.Daemon;
import com.someguyssoftware.ddenizens.entity.monster.Ettin;
import com.someguyssoftware.ddenizens.entity.monster.Gazer;
import com.someguyssoftware.ddenizens.entity.monster.Ghoul;
import com.someguyssoftware.ddenizens.entity.monster.Headless;
import com.someguyssoftware.ddenizens.entity.monster.Shadow;
import com.someguyssoftware.ddenizens.entity.monster.Shadowlord;
import com.someguyssoftware.ddenizens.entity.projectile.FireSpout;
import com.someguyssoftware.ddenizens.entity.projectile.Harmball;
import com.someguyssoftware.ddenizens.entity.projectile.Slowball;

import net.minecraft.core.particles.ParticleType;
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
	private static final String SHADOWLORD = "shadowlord";
	private static final String GAZER = "gazer";
	private static final String DAEMON = "daemon";
	private static final String BOULDER = "boulder";
	
	// projectile names
	private static final String SLOWBALL = "slowball";
	private static final String HARMBALL = "harmball";
	private static final String FIRESPOUT = "firespout";
	
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
	
	// entities
	public static final RegistryObject<EntityType<Headless>> HEADLESS_ENTITY_TYPE = Registration.ENTITIES.register(HEADLESS, () -> EntityType.Builder.of(Headless::new, MobCategory.MONSTER)
			.sized(0.6F, 1.5F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(50)
			.build(HEADLESS));
	
	public static final RegistryObject<EntityType<Ghoul>> GHOUL_ENTITY_TYPE = Registration.ENTITIES.register(GHOUL, () -> EntityType.Builder.of(Ghoul::new, MobCategory.MONSTER)
			.sized(0.6F, 1.68F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.build(GHOUL));
	
	public static final RegistryObject<EntityType<Ettin>> ETTIN_ENTITY_TYPE = Registration.ENTITIES.register(ETTIN, () -> EntityType.Builder.of(Ettin::new, MobCategory.MONSTER)
			.sized(1.25F, 1.125F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(ETTIN));
	
	public static final RegistryObject<EntityType<Gazer>> GAZER_ENTITY_TYPE = Registration.ENTITIES.register(GAZER, () -> EntityType.Builder.of(Gazer::new, MobCategory.MONSTER)
			.sized(1.125F, 1.125F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(GAZER));
	
	public static final RegistryObject<EntityType<Boulder>> BOULDER_ENTITY_TYPE = Registration.ENTITIES.register(BOULDER, () -> EntityType.Builder.of(Boulder::new, MobCategory.CREATURE)
			.sized(1F, 1F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(BOULDER));
	
	public static final RegistryObject<EntityType<Shadow>> SHADOW_ENTITY_TYPE = Registration.ENTITIES.register(SHADOW, () -> EntityType.Builder.of(Shadow::new, MobCategory.MONSTER)
			.sized(0.6F, 1.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SHADOW));
	
	public static final RegistryObject<EntityType<Shadowlord>> SHADOWLORD_ENTITY_TYPE = Registration.ENTITIES.register(SHADOWLORD, () -> EntityType.Builder.of(Shadowlord::new, MobCategory.MONSTER)
			.sized(0.625F, 2.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SHADOWLORD));
	
	public static final RegistryObject<EntityType<Daemon>> DAEMON_ENTITY_TYPE = Registration.ENTITIES.register(DAEMON, () -> EntityType.Builder.of(Daemon::new, MobCategory.MONSTER)
			.sized(1F, 3F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.fireImmune()
			.build(DAEMON));
	
	// projectile entities
	public static final RegistryObject<EntityType<Slowball>> SLOWBALL_ENTITY_TYPE = 
			Registration.ENTITIES.register(SLOWBALL, () -> EntityType.Builder.of(Slowball::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SLOWBALL));
	
	public static final RegistryObject<EntityType<Harmball>> HARMBALL_ENTITY_TYPE = 
			Registration.ENTITIES.register(HARMBALL, () -> EntityType.Builder.of(Harmball::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(HARMBALL));
	
	public static final RegistryObject<EntityType<FireSpout>> FIRESPOUT_ENTITY_TYPE = 
			Registration.ENTITIES.register("firespout", () -> EntityType.Builder.of(FireSpout::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build("firespout"));
	
	
	// mod eggs
	public static final RegistryObject<Item> HEADLESS_EGG = Registration.ITEMS.register(HEADLESS, () -> new ForgeSpawnEggItem(HEADLESS_ENTITY_TYPE, 0xc8b486, 0x6f5e48, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> GHOUL_EGG = Registration.ITEMS.register(GHOUL, () -> new ForgeSpawnEggItem(GHOUL_ENTITY_TYPE, 0xffffff, 0x6f5e48, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> ETTIN_EGG = Registration.ITEMS.register(ETTIN, () -> new ForgeSpawnEggItem(ETTIN_ENTITY_TYPE, 0x626262, 0x6f5e48, Registration.ITEM_PROPERTIES));
	
	public static final RegistryObject<Item> GAZER_EGG = Registration.ITEMS.register(GAZER, () -> new ForgeSpawnEggItem(GAZER_ENTITY_TYPE, 0xff0000, 0x00ff00, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> BOULDER_EGG = Registration.ITEMS.register(BOULDER, () -> new ForgeSpawnEggItem(BOULDER_ENTITY_TYPE, 0xff0000, 0x00ff00, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> SHADOW_EGG = Registration.ITEMS.register(SHADOW, () -> new ForgeSpawnEggItem(SHADOW_ENTITY_TYPE, 0x000000, 0x00ff00, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> SHADOWLORD_EGG = Registration.ITEMS.register(SHADOWLORD, () -> new ForgeSpawnEggItem(SHADOWLORD_ENTITY_TYPE, 0x000000, 0x0c0c0c, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> DAEMON_EGG = Registration.ITEMS.register(DAEMON, () -> new ForgeSpawnEggItem(DAEMON_ENTITY_TYPE, 0xff0000, 0xfc0000, Registration.ITEM_PROPERTIES));
	
	// projectiles
	public static final RegistryObject<Item> SLOWBALL_ITEM = Registration.ITEMS.register(SLOWBALL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> HARMBALL_ITEM = Registration.ITEMS.register(HARMBALL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FIRESPOUT_ITEM = Registration.ITEMS.register("firespout", () -> new Item(new Item.Properties()));
	
	// particles
//	public static final RegistryObject<SimpleParticleType> SHADOW_PARTICLE = Registration.PARTICLES.register("shadow_particle", () -> new SimpleParticleType(true));
}
