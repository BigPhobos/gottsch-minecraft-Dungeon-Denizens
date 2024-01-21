package com.someguyssoftware.ddenizens.client.model;

import net.minecraft.client.model.geom.ModelPart;

public record Rotations(float x, float y, float z) {
    public Rotations(ModelPart part) {
        this(part.xRot, part.yRot, part.zRot);
    }
}
