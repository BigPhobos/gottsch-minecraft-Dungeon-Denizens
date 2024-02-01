package com.someguyssoftware.ddenizens.client.model;

import net.minecraft.client.model.geom.ModelPart;

public record Positions(float x, float y, float z) {
    public Positions(ModelPart part) {
        this(part.x, part.y, part.z);
    }
}
