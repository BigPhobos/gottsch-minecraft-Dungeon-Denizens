package com.someguyssoftware.ddenizens.tags;

import com.someguyssoftware.ddenizens.DD;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class DDTags {

    public static class Items {
        public static final TagKey<Item> EGGS = mod(DD.MODID, "eggs");

        public static TagKey<Item> mod(String domain, String path) {
            return ItemTags.create(new ResourceLocation(domain, path));
        }

    }
}
