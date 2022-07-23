/**
 * 
 */
package com.someguyssoftware.ddenizens.client.renderer.entity;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.OrcModel;
import com.someguyssoftware.ddenizens.entity.monster.Orc;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * 
 * @author Mark Gottschling on Apr 28, 2022
 *
 * @param <T>
 */
public class _OrcRenderer<T extends Orc> extends MobRenderer<T, OrcModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/orc.png");
	ItemStack itemStack = new ItemStack(Items.GOLDEN_AXE);

	/**
	 * 
	 * @param context
	 */
	public _OrcRenderer(EntityRendererProvider.Context context) {
		super(context, new OrcModel<>(context.bakeLayer(OrcModel.LAYER_LOCATION)), 0.8F);
		//        this.addLayer(new OrcItemInHandLayer<>(this));
	}

	@Deprecated
	public OrcModel<T> getParentModel() {
		return this.getModel();
	}

	@Override
	public ResourceLocation getTextureLocation(Orc entity) {
		return TEXTURE;
	}
}
