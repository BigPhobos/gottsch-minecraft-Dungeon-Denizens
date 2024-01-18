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

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

/**
 *
 * @author Mark Gottschling on Jan 10, 2024
 *
 * @param <T>
 */
public abstract class BeholderkinModel<T extends Entity> extends EntityModel<T> {

    public BeholderkinModel() {
        super();
    }

    public BeholderkinModel(Function<ResourceLocation, RenderType> renderType) {
        super(renderType);
    }
    /**
     *
     * @param netYaw
     * @param pitch
     * @param minYaw
     * @param maxYaw
     * @param minPitch
     * @param maxPitch
     */
    public void setRestrictedEyeRotations(float netYaw, float pitch, float minYaw, float maxYaw, float minPitch, float maxPitch) {
        // reset head
        getEye().xRot = getBody().xRot;

        if (netYaw < 0) {
            getEye().yRot = Math.max(minYaw, netYaw) * ((float)Math.PI / 180F);
        }
        else {
            getEye().yRot = Math.min(maxYaw, netYaw) * ((float)Math.PI / 180F);
        }

        if (pitch < 0) {
            getEye().xRot = Math.max(minPitch, pitch) * ((float)Math.PI / 180F);
        }
        else {
            getEye().xRot = Math.min(maxPitch, pitch) * ((float)Math.PI / 180F);
        }
    }

    public void resetTopEyeStalk(ModelPart eyeStalk, float xRot, float zRot) {
        eyeStalk.xRot = xRot;
        eyeStalk.zRot = zRot;
    }

    public void resetSideEyeStalk(ModelPart eyeStalk, float yRot, float zRot) {
        eyeStalk.yRot = yRot;
        eyeStalk.zRot = zRot;
    }

    /**
     *
     * @param stalk
     * @param eye
     * @param age
     * @param radians
     * @param direction
     * @param stalkOffset
     * @param stalkSpeed
     */
    public void bobTopEyeStalk(ModelPart stalk, ModelPart eye, float age, float radians, float direction, int stalkOffset, float stalkSpeed) {
        float speed = 0.05F; // 1/20th speed
        float eyeSpeed = 0.1F;
        stalk.xRot += direction * (Mth.cos((age + stalkOffset) * stalkSpeed) * radians + 0.05F);
        stalk.zRot += direction * (Mth.cos((age + stalkOffset) * stalkSpeed) * radians + 0.05F);

        eye.yRot = -direction * (Mth.cos((age + stalkOffset) * eyeSpeed) * 0.3490659F + 0.05F);
    }

    /**
     *
     * @param stalk
     * @param eye
     * @param age
     * @param radians
     * @param direction
     * @param stalkOffset
     * @param stalkSpeed
     * @param eyeDirection
     */
    public void bobSideEyeStalk(ModelPart stalk, ModelPart eye, float age, float radians, float direction, int stalkOffset, float stalkSpeed, float eyeDirection) {
        float speed = 0.05F; // 1/20th speed
        float eyeSpeed = 0.1F;
        stalk.yRot += direction * (Mth.sin((age + stalkOffset) * stalkSpeed) * radians + 0.05F);
        stalk.zRot += direction * (Mth.sin((age + stalkOffset) * stalkSpeed) *  radians + 0.05F);

        eye.yRot = eyeDirection * (Mth.cos((age + stalkOffset) * eyeSpeed) * 0.3490659F + 0.05F);
    }

    public void bobMouthPart(ModelPart mouth, float age, float speed, float radians) {
        mouth.xRot -= Mth.cos(age * speed) * radians + 0.05F;
    }

    public abstract ModelPart getBody();
    public abstract ModelPart getEye();
}
