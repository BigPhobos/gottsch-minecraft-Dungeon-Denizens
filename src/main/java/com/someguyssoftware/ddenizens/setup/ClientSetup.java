package com.someguyssoftware.ddenizens.setup;


import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.setup.client.model.HeadlessModel;
import com.someguyssoftware.ddenizens.setup.client.renderer.entity.HeadlessRenderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client only event bus subscriber.
 * @author Mark Gottschling on Apr 2, 2022
 *
 */
@Mod.EventBusSubscriber(modid = DD.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
    	
    }
    
	/**
	 * register layers
	 * @param event
	 */
	@SubscribeEvent()
	public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(HeadlessModel.LAYER_LOCATION, HeadlessModel::createBodyLayer);
	}

	/**
	 * register renderers
	 * @param event
	 */
	@SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.HEADLESS_ENTITY_TYPE.get(), HeadlessRenderer::new);
    }
}
