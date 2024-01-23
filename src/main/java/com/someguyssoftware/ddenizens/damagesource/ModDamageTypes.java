/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2023 Mark Gottschling (gottsch)
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
package com.someguyssoftware.ddenizens.damagesource;

import com.someguyssoftware.ddenizens.DD;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

/**
 * 
 * @author Mark Gottschling on Oct 18, 2023
 *
 */
public class ModDamageTypes {
    public static final ResourceKey<DamageType> FIRESPOUT_SPELL = register("firespout_spell");
    public static final ResourceKey<DamageType> HARM_SPELL = register("harm_spell");
    public static final ResourceKey<DamageType> DISINTEGRATE_SPELL = register("disintegrate_spell");
    public static final ResourceKey<DamageType> PARALYSIS_SPELL = register("slow_spell");
    public static final ResourceKey<DamageType> DISARM_SPELL = register("disarm_spell");
    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(DD.MODID, name));
    }
}
