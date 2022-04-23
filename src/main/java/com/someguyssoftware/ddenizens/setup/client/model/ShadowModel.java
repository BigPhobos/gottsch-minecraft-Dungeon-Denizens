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

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

/**
 * 
 * @author Mark Gottschling on Apr 12, 2022
 *
 * @param <T>
 */
public class ShadowModel<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, "shadow_model"), "main");
	
	private final ModelPart head;
	private final ModelPart headwear;
	private final ModelPart body_legs;
	private final ModelPart body;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart left_leg;
	private final ModelPart right_leg;

	/**
	 * 
	 * @param root
	 */
	public ShadowModel(ModelPart root) {
		super(RenderType::entityTranslucentCull);
		this.head = root.getChild("head");
		this.headwear = root.getChild("headwear");
		this.body_legs = root.getChild("body_legs");
		this.body = body_legs.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = body_legs.getChild("left_leg");
		this.right_leg = body_legs.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition headwear = partdefinition.addOrReplaceChild("headwear", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body_legs = partdefinition.addOrReplaceChild("body_legs", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
		PartDefinition right_leg = body_legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(33, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));
		PartDefinition left_leg = body_legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 34).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));
		PartDefinition body = body_legs.addOrReplaceChild("body", CubeListBuilder.create().texOffs(29, 30).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(46, 13).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, -0.9599F, 0.0F, 0.0F));
		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(13, 47).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// head
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		
		// legs
		float f = 1.0F;
		float radians = 0.3490659F; // 20 degrees
		float walkSpeed = 0.5F; // half speed = 0.5
		this.right_leg.xRot = Mth.cos(limbSwing * walkSpeed) * radians * 1.4F * limbSwingAmount / f;
		this.left_leg.xRot = Mth.cos(limbSwing  * walkSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount / f;
		
		// reset arm rotations before bobbing, because bobbing is an addition to current rotation
		this.left_arm.zRot = 0F;
		this.left_arm.xRot = -0.8726646F; // 50 degrees

		this.right_arm.zRot = 0F;
		this.right_arm.xRot = -0.9599311F; // 55 dgrees

		// bob the arms
		bobModelPart(this.right_arm, ageInTicks, 1.0F);
		bobModelPart(this.left_arm, ageInTicks, -1.0F);
	}

	/**
	 * 
	 * @param part
	 * @param age
	 * @param direction
	 */
	public static void bobModelPart(ModelPart part, float age, float direction) {
		part.xRot += direction * (Mth.cos(age * /*0.09F*/ 0.15F) * 0.15F + 0.05F);
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, buffer, packedLight, packedOverlay);
//		headwear.render(poseStack, buffer, packedLight, packedOverlay);
//		body.render(poseStack, buffer, packedLight, packedOverlay);
		body_legs.render(poseStack, buffer, packedLight, packedOverlay);
		left_arm.render(poseStack, buffer, packedLight, packedOverlay);
		right_arm.render(poseStack, buffer, packedLight, packedOverlay);
//		left_leg.render(poseStack, buffer, packedLight, packedOverlay);
//		right_leg.render(poseStack, buffer, packedLight, packedOverlay);
	}
}