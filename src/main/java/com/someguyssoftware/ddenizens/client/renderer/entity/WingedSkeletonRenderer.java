/**
 * 
 */
package com.someguyssoftware.ddenizens.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.ShadowlordModel;
import com.someguyssoftware.ddenizens.client.model.WingedSkeletonModel;
import com.someguyssoftware.ddenizens.client.renderer.entity.layer.ShadowlordEyeLayer;
import com.someguyssoftware.ddenizens.entity.monster.Shadowlord;
import com.someguyssoftware.ddenizens.entity.monster.WingedSkeleton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Apr 18, 2022
 *
 * @param <T>
 */
public class WingedSkeletonRenderer<T extends WingedSkeleton> extends HumanoidMobRenderer<T, WingedSkeletonModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/winged_skeleton.png");

	/**
	 *
	 * @param context
	 */
	public WingedSkeletonRenderer(EntityRendererProvider.Context context) {
        super(context, new WingedSkeletonModel<>(context.bakeLayer(WingedSkeletonModel.LAYER_LOCATION)), 0.8F);
     }

     @Override
    public ResourceLocation getTextureLocation(WingedSkeleton entity) {
        return TEXTURE;
    }
}
