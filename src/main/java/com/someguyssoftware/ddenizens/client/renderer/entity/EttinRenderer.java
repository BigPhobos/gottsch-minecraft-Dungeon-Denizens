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
package com.someguyssoftware.ddenizens.client.renderer.entity;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.client.model.EttinModel;
import com.someguyssoftware.ddenizens.entity.monster.Ettin;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * 
 * @author Mark Gottschling on Apr 8, 2022
 *
 * @param <T>
 */
public class EttinRenderer<T extends Ettin> extends MobRenderer<T, EttinModel<T>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(DD.MODID, "textures/entity/ettin.png");
	
	/**
	 * 
	 * @param context
	 */
	public EttinRenderer(EntityRendererProvider.Context context) {
        super(context, new EttinModel<>(context.bakeLayer(EttinModel.LAYER_LOCATION)), 0.8F);
    }

     @Override
    public ResourceLocation getTextureLocation(Ettin entity) {
        return TEXTURE;
    }
}
