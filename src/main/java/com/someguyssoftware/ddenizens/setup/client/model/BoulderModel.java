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
package com.someguyssoftware.ddenizens.setup.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.Boulder;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

/**
 * 
 * @author Mark Gottschling on Apr 8, 2022
 *
 * @param <T>
 */
public class BoulderModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, "boulder"), "main");
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart frontLeftLeg;
	private final ModelPart front_right_leg;
	private final ModelPart back_left_leg;
	private final ModelPart back_right_leg;

	private final static PartPose FL_LEG_POSE = PartPose.offset(7.0F, 19.0F, -7.0F);
	private final static PartPose FR_LEG_POSE = PartPose.offset(-7.0F, 19.0F, -7.0F);
	private final static PartPose BL_LEG_POSE = PartPose.offset(7.0F, 19.0F, 7.0F);
	private final static PartPose BR_LEG_POSE = PartPose.offset(-7.0F, 19.0F, 7.0F);
	private final static PartPose BODY_POSE = PartPose.offset(0.0F, 24.0F, 0.0F);

	/**
	 * 
	 * @param root
	 */
	public BoulderModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.frontLeftLeg = root.getChild("front_left_leg");
		this.front_right_leg = root.getChild("front_right_leg");
		this.back_left_leg = root.getChild("back_left_leg");
		this.back_right_leg = root.getChild("back_right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -18.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), BODY_POSE);
		PartDefinition front_left_leg = partdefinition.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(0, 43).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), FL_LEG_POSE);
		PartDefinition front_right_leg = partdefinition.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(34, 33).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), FR_LEG_POSE);
		PartDefinition back_left_leg = partdefinition.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(17, 33).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), BL_LEG_POSE);
		PartDefinition back_right_leg = partdefinition.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(0, 33).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), BR_LEG_POSE);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		Boulder boulder = (Boulder) entity;

		// legs
		float f = 1.0F;
		float radians = 0.6662F; // 30 degrees
		float walkSpeed = 1.0F; // half speed = 0.5

		// awake - animate regular walk
		if (boulder.isActive()) {
//			this.frontLeftLeg.x = FL_LEG_POSE.x;
//			this.frontLeftLeg.z = FL_LEG_POSE.z;

			this.front_right_leg.xRot = Mth.cos(limbSwing * walkSpeed) * radians  * 1.4F * limbSwingAmount / f;
			this.frontLeftLeg.xRot = Mth.cos(limbSwing  * walkSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount / f;

			this.back_right_leg.xRot = Mth.cos(limbSwing  * walkSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount / f;
			this.back_left_leg.xRot = Mth.cos(limbSwing * walkSpeed) * radians  * 1.4F * limbSwingAmount / f;
		}
		else {
			if (boulder.getAmount() <= 1.0F) {
				// update poses
				this.frontLeftLeg.x = FL_LEG_POSE.x - (boulder.getAmount() * Boulder.MAX_LEG_AMOUNT);
				this.frontLeftLeg.z = FL_LEG_POSE.z + (boulder.getAmount() * 2.0F);
				this.frontLeftLeg.xRot = 0;

				this.front_right_leg.x = FR_LEG_POSE.x + (boulder.getAmount() * 2.0F);
				this.front_right_leg.z = FR_LEG_POSE.z + (boulder.getAmount() * 2.0F);
				this.front_right_leg.xRot = 0;

				this.back_left_leg.x = BL_LEG_POSE.x - (boulder.getAmount() * 2.0F);
				this.back_left_leg.z = BL_LEG_POSE.z - (boulder.getAmount() * 2.0F);
				this.back_left_leg.xRot = 0;

				this.back_right_leg.x = BR_LEG_POSE.x + (boulder.getAmount() * 2.0F);
				this.back_right_leg.z = BR_LEG_POSE.z - (boulder.getAmount() * 2.0F);
				this.back_right_leg.xRot = 0;
			}
			if (boulder.getBodyAmount() <= 1.0F) {
				this.body.y = BODY_POSE.y + (boulder.getBodyAmount() * 2.0F);
			}
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, buffer, packedLight, packedOverlay);
		frontLeftLeg.render(poseStack, buffer, packedLight, packedOverlay);
		front_right_leg.render(poseStack, buffer, packedLight, packedOverlay);
		back_left_leg.render(poseStack, buffer, packedLight, packedOverlay);
		back_right_leg.render(poseStack, buffer, packedLight, packedOverlay);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}