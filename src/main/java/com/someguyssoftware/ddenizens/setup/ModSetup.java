package com.someguyssoftware.ddenizens.setup;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.HeadlessEntity;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
        event.put(Registration.HEADLESS_ENTITY_TYPE.get(), HeadlessEntity.prepareAttributes().build());

    }
}
