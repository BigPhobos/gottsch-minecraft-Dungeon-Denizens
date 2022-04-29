/**
 * 
 */
package com.someguyssoftware.ddenizens.client.renderer.entity;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.HeadlessModel;
import com.someguyssoftware.ddenizens.client.model.OrcModel;
import com.someguyssoftware.ddenizens.entity.monster.Headless;
import com.someguyssoftware.ddenizens.entity.monster.Orc;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Apr 28, 2022
 *
 * @param <T>
 */
public class OrcRenderer<T extends Orc> extends MobRenderer<T, OrcModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/orc.png");
	
	/**
	 * 
	 * @param context
	 */
	public OrcRenderer(EntityRendererProvider.Context context) {
        super(context, new OrcModel<>(context.bakeLayer(OrcModel.LAYER_LOCATION)), 0.8F);
    }

     @Override
    public ResourceLocation getTextureLocation(Orc entity) {
        return TEXTURE;
    }
}
