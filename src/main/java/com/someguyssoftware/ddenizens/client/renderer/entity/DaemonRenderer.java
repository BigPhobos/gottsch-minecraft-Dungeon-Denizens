/**
 * 
 */
package com.someguyssoftware.ddenizens.client.renderer.entity;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.DaemonModel;
import com.someguyssoftware.ddenizens.client.renderer.entity.layer.BeholderEyeLayer;
import com.someguyssoftware.ddenizens.client.renderer.entity.layer.DaemonEyeLayer;
import com.someguyssoftware.ddenizens.entity.monster.Daemon;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Apr 21, 2022
 *
 * @param <T>
 */
public class DaemonRenderer<T extends Daemon> extends MobRenderer<T, DaemonModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/daemon.png");
	
	/**
	 * 
	 * @param context
	 */
	public DaemonRenderer(EntityRendererProvider.Context context) {
        super(context, new DaemonModel<>(context.bakeLayer(DaemonModel.LAYER_LOCATION)), 0F);
        this.addLayer(new DaemonEyeLayer<>(this));
    }

     @Override
    public ResourceLocation getTextureLocation(Daemon entity) {
        return TEXTURE;
    }
}
