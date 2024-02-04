/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
 * 
 * All rights reserved.
 *
 * Dungeon Denizens is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dungeon Denizens is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Dungeon Denizens.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package com.someguyssoftware.ddenizens.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.IronSkeletonModel;
import com.someguyssoftware.ddenizens.entity.monster.Shadowlord;
import com.someguyssoftware.ddenizens.entity.monster.skeleton.IronSkeleton;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Feb 3, 2024
 *
 * @param <T>
 */
public class IronSkeletonRenderer<T extends IronSkeleton> extends HumanoidMobRenderer<T, IronSkeletonModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/iron_skeleton.png");

	private final float scale;

	/**
	 *
	 * @param context
	 */
	public IronSkeletonRenderer(EntityRendererProvider.Context context) {
		this(context,
				IronSkeletonModel.LAYER_LOCATION,
				ModelLayers.SKELETON_INNER_ARMOR,
				ModelLayers.SKELETON_OUTER_ARMOR);
	}

	public IronSkeletonRenderer(EntityRendererProvider.Context context,
                                ModelLayerLocation modelLocation, ModelLayerLocation innerArmor, ModelLayerLocation outerArmor) {

		super(context, new IronSkeletonModel<>(context.bakeLayer(modelLocation)), 0.6F);
		this.addLayer(
				new HumanoidArmorLayer<>(this, new IronSkeletonModel(context.bakeLayer(innerArmor)),
				new IronSkeletonModel(context.bakeLayer(outerArmor)),
						context.getModelManager()));
		this.scale = 1.05F;
	}

	@Override
	protected void scale(IronSkeleton ironSkeleton, PoseStack pose, float scale) {
		pose.scale(this.scale, this.scale, this.scale);
	}

	@Override
	public ResourceLocation getTextureLocation(IronSkeleton entity) {
		return TEXTURE;
	}
}
