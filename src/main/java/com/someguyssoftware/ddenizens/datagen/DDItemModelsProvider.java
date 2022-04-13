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
package com.someguyssoftware.ddenizens.datagen;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.setup.Registration;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Mark Gottschling on Apr 6, 2022
 *
 */
public class DDItemModelsProvider extends ItemModelProvider {

    public DDItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DD.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    	withExistingParent(Registration.HEADLESS_EGG.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(Registration.GHOUL_EGG.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(Registration.GAZER_EGG.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(Registration.BOULDER_EGG.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(Registration.SHADOW_EGG.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
