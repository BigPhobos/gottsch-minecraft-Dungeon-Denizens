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

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.OrcModel;
import com.someguyssoftware.ddenizens.client.model.SkeletonWarriorModel;
import com.someguyssoftware.ddenizens.entity.monster.Orc;
import com.someguyssoftware.ddenizens.entity.monster.SkeletonWarrior;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Jan 19, 2024
 *
 * @param <T>
 */
public class SkeletonWarriorRenderer<T extends SkeletonWarrior> extends HumanoidMobRenderer<T, SkeletonWarriorModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/skeleton_warrior.png");

	/**
	 *
	 * @param context
	 */
	public SkeletonWarriorRenderer(EntityRendererProvider.Context context) {
		this(context,
				SkeletonWarriorModel.LAYER_LOCATION,
				ModelLayers.SKELETON_INNER_ARMOR,
				ModelLayers.SKELETON_OUTER_ARMOR);
	}

	public SkeletonWarriorRenderer(EntityRendererProvider.Context context,
								   ModelLayerLocation modelLocation, ModelLayerLocation innerArmor, ModelLayerLocation outerArmor) {

		super(context, new SkeletonWarriorModel<>(context.bakeLayer(modelLocation)), 0.8F);
		this.addLayer(
				new HumanoidArmorLayer<>(this, new SkeletonWarriorModel(context.bakeLayer(innerArmor)),
				new SkeletonWarriorModel(context.bakeLayer(outerArmor)),
						context.getModelManager()));
	}

	@Override
	public ResourceLocation getTextureLocation(SkeletonWarrior entity) {
		return TEXTURE;
	}
}
