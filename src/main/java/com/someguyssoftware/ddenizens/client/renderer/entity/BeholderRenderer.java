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
import com.someguyssoftware.ddenizens.client.model.BeholderModel;
import com.someguyssoftware.ddenizens.client.model.BeholderModel;
import com.someguyssoftware.ddenizens.client.renderer.entity.layer.BeholderEyeLayer;
import com.someguyssoftware.ddenizens.entity.monster.Beholder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Jan 9, 2024
 *
 * @param <T>
 */
public class BeholderRenderer<T extends Beholder> extends MobRenderer<T, BeholderModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/beholder.png");

	private final float scale;

	/**
	 *
	 * @param context
	 */
	public BeholderRenderer(EntityRendererProvider.Context context) {
		super(context, new BeholderModel<>(context.bakeLayer(BeholderModel.LAYER_LOCATION)), 0.8F);
		this.addLayer(new BeholderEyeLayer<>(this));
		this.scale = 2.00F; // makes the body approx 6-9 in diameter
	}

	@Override
	protected void scale(Beholder Beholder, PoseStack pose, float scale) {
		pose.scale(this.scale, this.scale, this.scale);
	}

	@Override
	public ResourceLocation getTextureLocation(Beholder entity) {
		return TEXTURE;
	}
}
