/**
 * 
 */
package com.someguyssoftware.ddenizens.setup.client.renderer.entity;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.HeadlessEntity;
import com.someguyssoftware.ddenizens.setup.client.model.HeadlessModel;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Mark Gottschling on Apr 1, 2022
 *
 */
public class HeadlessRenderer<T extends HeadlessEntity> extends MobRenderer<T, HeadlessModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/headless.png");
	
	/**
	 * 
	 * @param context
	 */
	public HeadlessRenderer(EntityRendererProvider.Context context) {
        super(context, new HeadlessModel<>(context.bakeLayer(HeadlessModel.LAYER_LOCATION)), 0.8F);
    }

     @Override
    public ResourceLocation getTextureLocation(HeadlessEntity entity) {
        return TEXTURE;
    }
}