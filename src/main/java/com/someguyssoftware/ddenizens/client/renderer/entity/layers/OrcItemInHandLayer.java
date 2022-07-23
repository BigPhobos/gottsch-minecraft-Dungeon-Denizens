/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2021, Mark Gottschling (gottsch)
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
package com.someguyssoftware.ddenizens.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.someguyssoftware.ddenizens.entity.monster.Orc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * 
 * @author Mark Gottschling on May 4, 2022
 *
 * @param <T>
 * @param <M>
 */
public class OrcItemInHandLayer<T extends Orc, M extends EntityModel<T> & ArmedModel> extends ItemInHandLayer<T, M> {

	public OrcItemInHandLayer(RenderLayerParent<T, M> renderLayerParent) {
		super(renderLayerParent);
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource buffer, int age, T entity, float p_117208_, float p_117209_, float p_117210_, float p_117211_, float p_117212_, float p_117213_) {
		ItemStack itemStack = entity.getMainHandItem();
		this.renderArmWithItem(entity, itemStack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, buffer, age);
	}

	@Override
	protected void renderArmWithItem(LivingEntity entity, ItemStack itemStack, ItemTransforms.TransformType transformType, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int age) {
		if (!itemStack.isEmpty()) {
			poseStack.pushPose();

			// first, get to the correct pivot position ie. shoulder joint/pivot
			this.getParentModel().translateToHand(arm, poseStack);
			
//			 // this is within another pushPose() so that the original pivot point remains intact and this translate is relative to it.
			// NOTE putting it in embedded pushPose doesn't do anything
			poseStack.translate(0, 1/16, -1); // (-z) down (because was rotated on x-axis so y became z and vice versa)
			Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, itemStack, transformType, false, poseStack, buffer, age);

			poseStack.popPose();
		}
	}
}
