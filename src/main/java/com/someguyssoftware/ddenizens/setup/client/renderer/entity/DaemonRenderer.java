/**
 * 
 */
package com.someguyssoftware.ddenizens.setup.client.renderer.entity;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.Daemon;
import com.someguyssoftware.ddenizens.setup.client.model.DaemonModel;

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
    }

     @Override
    public ResourceLocation getTextureLocation(Daemon entity) {
        return TEXTURE;
    }
}
