package com.someguyssoftware.ddenizens.datagen;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.setup.Registration;
import com.someguyssoftware.ddenizens.tags.DDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DDItemTagsProvider extends ItemTagsProvider {
    public DDItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
                              CompletableFuture<TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookup, blockTagProvider, DD.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        tag(DDTags.Items.EGGS).add(Registration.BEHOLDER_EGG.get());

        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_AXE1.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_AXE2.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_SWORD1.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_SWORD2.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_SWORD3.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_SWORD4.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.GOLDEN_SWORD);
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.GOLDEN_AXE);
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.IRON_SWORD);
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.IRON_AXE);
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.STONE_SWORD);
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.STONE_AXE);
    }
}
