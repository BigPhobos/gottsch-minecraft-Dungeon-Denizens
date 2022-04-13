package com.someguyssoftware.ddenizens.setup;


import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.setup.client.model.BoulderModel;
import com.someguyssoftware.ddenizens.setup.client.model.EttinModel;
import com.someguyssoftware.ddenizens.setup.client.model.GazerModel;
import com.someguyssoftware.ddenizens.setup.client.model.GhoulModel;
import com.someguyssoftware.ddenizens.setup.client.model.HeadlessModel;
import com.someguyssoftware.ddenizens.setup.client.model.ShadowModel;
import com.someguyssoftware.ddenizens.setup.client.renderer.entity.BoulderRenderer;
import com.someguyssoftware.ddenizens.setup.client.renderer.entity.EttinRenderer;
import com.someguyssoftware.ddenizens.setup.client.renderer.entity.GazerRenderer;
import com.someguyssoftware.ddenizens.setup.client.renderer.entity.GhoulRenderer;
import com.someguyssoftware.ddenizens.setup.client.renderer.entity.HeadlessRenderer;
import com.someguyssoftware.ddenizens.setup.client.renderer.entity.ShadowRenderer;

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
		event.registerLayerDefinition(GhoulModel.LAYER_LOCATION, GhoulModel::createBodyLayer);
		event.registerLayerDefinition(EttinModel.LAYER_LOCATION, EttinModel::createBodyLayer);
		event.registerLayerDefinition(GazerModel.LAYER_LOCATION, GazerModel::createBodyLayer);
		event.registerLayerDefinition(BoulderModel.LAYER_LOCATION, BoulderModel::createBodyLayer);
		event.registerLayerDefinition(ShadowModel.LAYER_LOCATION, ShadowModel::createBodyLayer);
	}

	/**
	 * register renderers
	 * @param event
	 */
	@SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.HEADLESS_ENTITY_TYPE.get(), HeadlessRenderer::new);
        event.registerEntityRenderer(Registration.GHOUL_ENTITY_TYPE.get(), GhoulRenderer::new);
        event.registerEntityRenderer(Registration.ETTIN_ENTITY_TYPE.get(), EttinRenderer::new);
        event.registerEntityRenderer(Registration.GAZER_ENTITY_TYPE.get(), GazerRenderer::new);
        event.registerEntityRenderer(Registration.BOULDER_ENTITY_TYPE.get(), BoulderRenderer::new);
        event.registerEntityRenderer(Registration.SHADOW_ENTITY_TYPE.get(), ShadowRenderer::new);
	}
}
