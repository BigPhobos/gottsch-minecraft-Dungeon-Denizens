/**
 * 
 */
package com.someguyssoftware.ddenizens.client.renderer.entity;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.ShadowlordModel;
import com.someguyssoftware.ddenizens.entity.monster.Shadowlord;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Apr 18, 2022
 *
 * @param <T>
 */
public class ShadowlordRenderer<T extends Shadowlord> extends MobRenderer<T, ShadowlordModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/shadowlord.png");
	
	/**
	 * 
	 * @param context
	 */
	public ShadowlordRenderer(EntityRendererProvider.Context context) {
        super(context, new ShadowlordModel<>(context.bakeLayer(ShadowlordModel.LAYER_LOCATION)), 0F);
    }

     @Override
    public ResourceLocation getTextureLocation(Shadowlord entity) {
        return TEXTURE;
    }
}
