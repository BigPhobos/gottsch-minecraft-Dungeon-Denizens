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

import net.minecraft.client.model.EntityModel;
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
 * @author Mark Gottschling on Apr 4, 2022
 *
 * @param <T>
 */
public class GazerModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "custom_model"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart mouth;
	private final ModelPart tentacle1;
	private final ModelPart tentacle2;
	private final ModelPart tentacle3;
	private final ModelPart tentacle4;
	private final ModelPart tentacle5;
	private final ModelPart eye1;
	private final ModelPart eye2;
	private final ModelPart eye3;
	private final ModelPart eye4;
	private final ModelPart eye5;
	
	
	public GazerModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.mouth = root.getChild("mouth");
		this.tentacle1 = root.getChild("tenticle1");
		this.tentacle2 = root.getChild("tenticle2");
		this.tentacle3 = root.getChild("tenticle3");
		this.tentacle4 = root.getChild("tenticle4");
		this.tentacle5 = root.getChild("tenticle5");
		this.eye1 = tentacle1.getChild("eye");
		this.eye2 = tentacle2.getChild("eye2");
		this.eye3 = tentacle3.getChild("eye3");
		this.eye4 = tentacle4.getChild("eye4");
		this.eye5 = tentacle5.getChild("eye5");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(51, 45).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.0F, -6.0F));
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -19.0F, -9.0F, 18.0F, 18.0F, 18.0F, new CubeDeformation(0.0F))
		.texOffs(0, 62).addBox(-6.0F, -17.0F, -11.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 0.0F));
		PartDefinition mouth = partdefinition.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 37).addBox(-8.0F, -3.0F, -10.0F, 16.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
		PartDefinition tenticle1 = partdefinition.addOrReplaceChild("tenticle1", CubeListBuilder.create().texOffs(55, 7).addBox(-1.0F, -1.5F, -1.5F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 19.5F, -0.5F, 0.0F, 0.0F, 0.4363F));
		PartDefinition eye = tenticle1.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(21, 68).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));
		PartDefinition tenticle2 = partdefinition.addOrReplaceChild("tenticle2", CubeListBuilder.create().texOffs(55, 0).addBox(-1.0F, -2.0F, -2.0F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, 11.0F, 2.0F, 0.0F, 0.0F, -0.4363F));
		PartDefinition eye2 = tenticle2.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(0, 67).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.5F, -0.5F, -0.5F, 0.0F, 0.0F, 0.4363F));
		PartDefinition tenticle3 = partdefinition.addOrReplaceChild("tenticle3", CubeListBuilder.create().texOffs(0, 55).addBox(-11.0F, -1.5F, -1.5F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 19.5F, -1.5F, 0.0F, 0.0F, -0.4363F));
		PartDefinition eye3 = tenticle3.addOrReplaceChild("eye3", CubeListBuilder.create().texOffs(66, 66).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));
		PartDefinition tenticle4 = partdefinition.addOrReplaceChild("tenticle4", CubeListBuilder.create().texOffs(47, 37).addBox(-11.0F, -1.5F, -1.5F, 12.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 10.5F, 1.5F, 0.0F, 0.0F, 0.4363F));
		PartDefinition eye4 = tenticle4.addOrReplaceChild("eye4", CubeListBuilder.create().texOffs(45, 66).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));
		PartDefinition tenticle5 = partdefinition.addOrReplaceChild("tenticle5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -7.5F, -1.5F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 1.5F, 0.4363F, 0.0F, 0.0F));
		PartDefinition eye5 = tenticle5.addOrReplaceChild("eye5", CubeListBuilder.create().texOffs(29, 57).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot = Math.min(45, netHeadYaw) * ((float)Math.PI / 180F);
		this.head.xRot = Math.min(45, headPitch) * ((float)Math.PI / 180F);
		
		// reset tentacle rotations before bobbing
		float tentacleRotation = 0.4363323F; // 25 degrees
		this.tentacle1.xRot = 0F;
		this.tentacle1.zRot = tentacleRotation;
		
		this.tentacle2.xRot = 0F;
		this.tentacle2.zRot = -tentacleRotation;
		
		this.tentacle3.xRot = 0F;
		this.tentacle3.zRot = -tentacleRotation;
		
		this.tentacle4.xRot = 0F;
		this.tentacle4.zRot = tentacleRotation;
		
		this.tentacle5.xRot = tentacleRotation;
		this.tentacle5.zRot = 0F;
		
		// bob eyes
		bobSideTentaclePart(tentacle1, eye1, ageInTicks, 0.612F, 1.0F);
		bobSideTentaclePart(tentacle2, eye2, ageInTicks, 0.35F, -1.0F);
		bobSideTentaclePart(tentacle3, eye3, ageInTicks, 0.55F, 1.0F);
		bobSideTentaclePart(tentacle4, eye4, ageInTicks, 0.45F, 1.0F);
		bobTopTentaclePart(tentacle5, eye5, ageInTicks, 0.3F, 1.0F);
	}

	public static void bobSideTentaclePart(ModelPart tentacle, ModelPart eye, float age, float radians, float direction) {
		float speed = 0.05F; // 20th speed
		tentacle.zRot += direction * (Mth.cos(age * speed) *  radians + 0.05F);
		eye.zRot = -tentacle.zRot;
	}
	
	public static void bobTopTentaclePart(ModelPart tentacle, ModelPart eye, float age, float radians, float direction) {
		tentacle.xRot += direction * (Mth.cos(age * radians) * 0.05F + 0.05F);
		eye.xRot = -tentacle.xRot;
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, buffer, packedLight, packedOverlay);
		body.render(poseStack, buffer, packedLight, packedOverlay);
		mouth.render(poseStack, buffer, packedLight, packedOverlay);
		tentacle1.render(poseStack, buffer, packedLight, packedOverlay);
		tentacle2.render(poseStack, buffer, packedLight, packedOverlay);
		tentacle3.render(poseStack, buffer, packedLight, packedOverlay);
		tentacle4.render(poseStack, buffer, packedLight, packedOverlay);
		tentacle5.render(poseStack, buffer, packedLight, packedOverlay);
	}
}