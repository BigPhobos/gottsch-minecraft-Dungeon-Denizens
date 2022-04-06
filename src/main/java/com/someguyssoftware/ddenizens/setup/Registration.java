package com.someguyssoftware.ddenizens.setup;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.HeadlessEntity;

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

	/*
	 * TODO could place these in their own classes, but init() here. ex. TreasureBlocks, TreasureItems, TreasureEntities etc.
	 */
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DD.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, DD.MODID);
	
	public static void init() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(eventBus);	
		ENTITIES.register(eventBus);		
	}

	public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(CreativeModeTab.TAB_MISC);
	
	public static final RegistryObject<EntityType<HeadlessEntity>> HEADLESS_ENTITY_TYPE = Registration.ENTITIES.register("headless", () -> EntityType.Builder.of(HeadlessEntity::new, MobCategory.MONSTER)
			.sized(0.6f, 1.95f)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(50)
			.fireImmune()
			.build("headless"));
	public static final RegistryObject<Item> HEADLESS_EGG = Registration.ITEMS.register("headless", () -> new ForgeSpawnEggItem(HEADLESS_ENTITY_TYPE, 0xff0000, 0x00ff00, Registration.ITEM_PROPERTIES));
	
}
