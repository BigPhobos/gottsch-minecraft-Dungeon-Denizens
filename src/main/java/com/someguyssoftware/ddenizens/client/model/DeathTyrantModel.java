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
package com.someguyssoftware.ddenizens.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.someguyssoftware.ddenizens.DD;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.Random;

/**
 * @author Mark Gottschling on Jan 11, 2024
 *
 */
public class DeathTyrantModel<T extends Entity> extends BeholderkinModel<T> {
	public static final String MODEL_NAME = "death_tyrant_model";
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, MODEL_NAME), "main");
	private final ModelPart body;
	private final ModelPart lower;

	private final ModelPart eyeStalk1;
	private final ModelPart eyeStalk2;
	private final ModelPart eyeStalk3;
	private final ModelPart eyeStalk4;
	private final ModelPart eyeStalk5;
	private final ModelPart eyeStalk6;

	private final ModelPart eye1;
	private final ModelPart eye2;
	private final ModelPart eye3;
	private final ModelPart eye4;
	private final ModelPart eye5;
	private final ModelPart eye6;

	private float bodyY;
	private float headY;

	private ModelPart[] eyeStalks = new ModelPart[6];
	private Rotations[] eyeStalkRotations = new Rotations[6];
	private int[] eyeStalkOffsets = new int[6];
	private float[] eyeStalkSpeeds = new float[6];
	private float[] directions = new float[6];

	public DeathTyrantModel(ModelPart root) {
		super(RenderType::entityTranslucentCull);
		this.body = root.getChild("body");
		this.lower = body.getChild("lower");
		this.eyeStalk1 = body.getChild("upper").getChild("stalks").getChild("s1");
		this.eyeStalk2 = body.getChild("upper").getChild("stalks").getChild("s2");
		this.eyeStalk3 = body.getChild("upper").getChild("stalks").getChild("s3");
		this.eyeStalk4 = body.getChild("upper").getChild("stalks").getChild("s4");
		this.eyeStalk5 = body.getChild("upper").getChild("stalks").getChild("s5");
		this.eyeStalk6 = body.getChild("upper").getChild("stalks").getChild("s6");

		eyeStalks[0] = eyeStalk1;
		eyeStalks[1] = eyeStalk2;
		eyeStalks[2] = eyeStalk3;
		eyeStalks[3] = eyeStalk4;
		eyeStalks[4] = eyeStalk5;
		eyeStalks[5] = eyeStalk6;

		this.eye1 = eyeStalk1.getChild("s1m").getChild("s1t").getChild("eye1");
		this.eye2 = eyeStalk2.getChild("s2m").getChild("s2t").getChild("eye2");
		this.eye3 = eyeStalk3.getChild("s3m").getChild("s3t").getChild("eye3");
		this.eye4 = eyeStalk4.getChild("s4m").getChild("s4t").getChild("eye4");
		this.eye5 = eyeStalk5.getChild("s5m").getChild("s5t").getChild("eye5");
		this.eye6 = eyeStalk6.getChild("s6m").getChild("s6t").getChild("eye6");

		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			eyeStalkRotations[i] = new Rotations(eyeStalks[i].xRot, eyeStalks[i].yRot, eyeStalks[i].zRot);
			eyeStalkOffsets[i] = random.nextInt(0, 180);
			eyeStalkSpeeds[i] = random.nextFloat(0.02F, 0.05F);
			directions[i] = random.nextInt() % 2 == 0 ? 1F: -1F;
		}

		bodyY = body.y;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition upper = body.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(0, 49).addBox(-7.0F, -20.0076F, -15.1743F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(65, 33).addBox(-5.0F, -17.0F, -16.0F, 10.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(57, 49).addBox(-9.0F, -18.0F, -17.0F, 18.0F, 12.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-9.0F, -18.0F, -12.0F, 18.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 7.0F));

		PartDefinition stalks = upper.addOrReplaceChild("stalks", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -7.0F));

		PartDefinition s1 = stalks.addOrReplaceChild("s1", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.0F, -23.0F, -1.0F, 0.0436F, 0.0F, -0.2618F));

		PartDefinition s1m = s1.addOrReplaceChild("s1m", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.4669F, 7.3761F, 6.4117F, 0.7418F, 0.0F, 0.0F));

		PartDefinition s1t = s1m.addOrReplaceChild("s1t", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -11.75F, 3.75F, -0.2182F, 0.0F, 0.0F));

		PartDefinition eye1 = s1t.addOrReplaceChild("eye1", CubeListBuilder.create(), PartPose.offset(-1.5F, 0.2348F, -6.3785F));

		PartDefinition eye_r1 = eye1.addOrReplaceChild("eye_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0331F, -7.5354F, -2.7794F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 5.5F, -2.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition s2 = stalks.addOrReplaceChild("s2", CubeListBuilder.create(), PartPose.offsetAndRotation(5.0F, -16.0F, 6.0F, 0.0436F, 0.0F, 0.2618F));

		PartDefinition s2m = s2.addOrReplaceChild("s2m", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.4471F, -8.804F, -0.0178F, 0.9163F, 0.0F, 0.0F));

		PartDefinition s2t = s2m.addOrReplaceChild("s2t", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -4.5006F, -0.1802F, -0.6109F, 0.0F, 0.0F));

		PartDefinition eye2 = s2t.addOrReplaceChild("eye2", CubeListBuilder.create(), PartPose.offset(-2.5529F, -0.7652F, -6.3785F));

		PartDefinition eye_r2 = eye2.addOrReplaceChild("eye_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0208F, -7.0602F, -3.0269F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5529F, 6.5F, -2.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition s3 = stalks.addOrReplaceChild("s3", CubeListBuilder.create(), PartPose.offsetAndRotation(9.0F, -14.0F, 6.0F, -0.0873F, -0.3491F, -0.2618F));

		PartDefinition s3m = s3.addOrReplaceChild("s3m", CubeListBuilder.create(), PartPose.offsetAndRotation(4.25F, 0.0F, 0.5F, 0.0F, 1.1345F, 0.0F));

		PartDefinition s3t = s3m.addOrReplaceChild("s3t", CubeListBuilder.create(), PartPose.offsetAndRotation(5.25F, 0.0F, 0.25F, 0.0F, -0.7854F, 0.0F));

		PartDefinition eye3 = s3t.addOrReplaceChild("eye3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition s4 = stalks.addOrReplaceChild("s4", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, -12.0F, 6.0F, 0.0F, 0.0873F, 0.5672F));

		PartDefinition s4m = s4.addOrReplaceChild("s4m", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.25F, 0.0F, 0.5F, 0.0F, -0.9599F, 0.0F));

		PartDefinition s4t = s4m.addOrReplaceChild("s4t", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.25F, 0.0F, 0.25F, 0.0F, 0.829F, 0.0F));

		PartDefinition eye4 = s4t.addOrReplaceChild("eye4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition s5 = stalks.addOrReplaceChild("s5", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -8.0F, 2.0F, -0.0873F, -0.6545F, -0.0873F));

		PartDefinition s5m = s5.addOrReplaceChild("s5m", CubeListBuilder.create(), PartPose.offsetAndRotation(4.25F, 0.0F, 0.5F, 0.0F, 1.1345F, 0.0F));

		PartDefinition s5t = s5m.addOrReplaceChild("s5t", CubeListBuilder.create(), PartPose.offsetAndRotation(5.25F, 0.0F, 0.25F, 0.0F, -0.7854F, 0.0F));

		PartDefinition eye5 = s5t.addOrReplaceChild("eye5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition s6 = stalks.addOrReplaceChild("s6", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.0F, -8.0F, 1.0F, 0.0F, 0.6545F, 0.0F));

		PartDefinition s6m = s6.addOrReplaceChild("s6m", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.25F, 0.0F, 0.5F, 0.0F, -0.9599F, 0.0F));

		PartDefinition s6t = s6m.addOrReplaceChild("s6t", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.25F, 0.0F, 0.25F, 0.0F, 0.829F, 0.0F));

		PartDefinition eye6 = s6t.addOrReplaceChild("eye6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition topTeeth = upper.addOrReplaceChild("topTeeth", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -8.0F, -15.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition tt1 = topTeeth.addOrReplaceChild("tt1", CubeListBuilder.create(), PartPose.offset(6.0F, 9.0F, 15.0F));

		PartDefinition cube_r1 = tt1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 7).addBox(-1.4005F, -0.4863F, -15.7654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition tt2 = topTeeth.addOrReplaceChild("tt2", CubeListBuilder.create(), PartPose.offset(3.0F, 9.0F, 15.0F));

		PartDefinition cube_r2 = tt2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 7).addBox(-1.4005F, -0.4863F, -16.2654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition tt4 = topTeeth.addOrReplaceChild("tt4", CubeListBuilder.create(), PartPose.offset(-2.0F, 9.0F, 15.0F));

		PartDefinition cube_r3 = tt4.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 7).addBox(-1.4005F, -0.4863F, -15.7654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition tt3 = topTeeth.addOrReplaceChild("tt3", CubeListBuilder.create(), PartPose.offset(1.0F, 9.0F, 15.0F));

		PartDefinition cube_r4 = tt3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 7).addBox(-1.4005F, -0.4863F, -15.7654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition tt5 = topTeeth.addOrReplaceChild("tt5", CubeListBuilder.create(), PartPose.offset(-4.0F, 9.0F, 15.0F));

		PartDefinition cube_r5 = tt5.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 7).addBox(-1.4005F, -0.4863F, -16.2654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition tt6 = topTeeth.addOrReplaceChild("tt6", CubeListBuilder.create(), PartPose.offset(-7.0F, 9.0F, 15.0F));

		PartDefinition cube_r6 = tt6.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 7).addBox(-1.4005F, -0.4863F, -15.7654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition lower = body.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(0, 26).addBox(-8.0F, 0.0F, -16.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(0, 66).addBox(6.0F, -6.0F, -7.0F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 66).mirror().addBox(-8.0F, -6.0F, -7.0F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(50, 0).addBox(-8.0F, 0.0F, -14.0F, 16.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(49, 10).addBox(-6.0F, 0.0F, -16.0F, 0.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(49, 10).mirror().addBox(6.0F, 0.0F, -16.0F, 0.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -5.0F, 7.0F, 0.48F, 0.0F, 0.0F));

		PartDefinition teeth = lower.addOrReplaceChild("teeth", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -1.6779F, -13.7705F, -0.3054F, 0.0F, 0.0F));

		PartDefinition bt5 = teeth.addOrReplaceChild("bt5", CubeListBuilder.create(), PartPose.offsetAndRotation(6.3536F, 7.9393F, -3.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bt1 = teeth.addOrReplaceChild("bt1", CubeListBuilder.create(), PartPose.offset(3.0F, 8.0F, 11.0F));

		PartDefinition cube_r7 = bt1.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 26).addBox(-6.3588F, -5.4446F, -11.8236F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bt2 = teeth.addOrReplaceChild("bt2", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 11.0F));

		PartDefinition cube_r8 = bt2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 26).addBox(-6.3588F, -5.4446F, -11.9236F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bt3 = teeth.addOrReplaceChild("bt3", CubeListBuilder.create(), PartPose.offset(-2.0F, 8.0F, 11.0F));

		PartDefinition cube_r9 = bt3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 26).addBox(-6.3588F, -5.4446F, -11.8236F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bt4 = teeth.addOrReplaceChild("bt4", CubeListBuilder.create(), PartPose.offset(-4.0F, 8.0F, 11.0F));

		PartDefinition cube_r10 = bt4.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 26).addBox(-6.3588F, -5.4446F, -11.9236F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bt6 = teeth.addOrReplaceChild("bt6", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.6464F, 7.9393F, -3.0F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		// reset mouth
		this.lower.xRot = 0.479966F;

		// bob mouth
		bobMouthPart(lower, ageInTicks, 0.05F, -0.2181662F);

		// reset eye stalk rotations before bobbing
//		resetTopEyeStalk(eyeStalk1, -0.349066F, -0.261799F);
//		resetTopEyeStalk(eyeStalk2, -0.043633F, 0.261799F); // -2.5, 15
//		resetSideEyeStalk(eyeStalk3, (0.087266F), -0.261799F); // 20, -15
//		resetSideEyeStalk(eyeStalk4, -(0.349066F), 0.567232F); // -5, 32.5
//		resetSideEyeStalk(eyeStalk5, -(0.654498F),  -0.087266F); // 37.5, -5
//		resetSideEyeStalk(eyeStalk6, (0.654498F),  0F); // -37.5, 0

		resetTopEyeStalk(eyeStalk1, eyeStalkRotations[0].x(), eyeStalkRotations[0].z());
		resetTopEyeStalk(eyeStalk2, eyeStalkRotations[1].x(), eyeStalkRotations[1].z());

		for (int i = 2; i < 6; i++) {
			resetSideEyeStalk(eyeStalks[i], eyeStalkRotations[i].y(), eyeStalkRotations[i].z());
		}
//		resetSideEyeStalk(eyeStalk4, eyeStalkRotations[3].y(), eyeStalkRotations[3].z());
//		resetSideEyeStalk(eyeStalk5, eyeStalkRotations[4].y(), eyeStalkRotations[4].z());
//		resetSideEyeStalk(eyeStalk6, eyeStalkRotations[5].y(), eyeStalkRotations[5].z());

		// bob eye stalks
		bobTopEyeStalk(eyeStalk1, eye1, ageInTicks, 0.3F, directions[0], eyeStalkOffsets[0], eyeStalkSpeeds[0]);
		bobTopEyeStalk(eyeStalk2, eye2, ageInTicks, 0.3F, directions[1], eyeStalkOffsets[1], eyeStalkSpeeds[1]);

		bobSideEyeStalk(eyeStalk3, eye3, ageInTicks, 0.3F, directions[2], eyeStalkOffsets[2], eyeStalkSpeeds[2], directions[5]);
		bobSideEyeStalk(eyeStalk4, eye4, ageInTicks, 0.3F, directions[3], eyeStalkOffsets[3], eyeStalkSpeeds[3], directions[4]);
		bobSideEyeStalk(eyeStalk5, eye5, ageInTicks, 0.3F, directions[4], eyeStalkOffsets[4], eyeStalkSpeeds[4], directions[3]);
		bobSideEyeStalk(eyeStalk6, eye6, ageInTicks, 0.3F, directions[5], eyeStalkOffsets[5], eyeStalkSpeeds[5], directions[2]);

		// bob entire body
		body.y = bodyY + (Mth.cos(ageInTicks * 0.15F) * 0.5F + 0.05F);

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setRestrictedEyeRotations(float netYaw, float pitch, float minYaw, float maxYaw, float minPitch, float maxPitch) {
	}

	@Override
	public ModelPart getBody() {
		return this.body;
	}

	@Override
	public ModelPart getEye() {
		return null;
	}
}