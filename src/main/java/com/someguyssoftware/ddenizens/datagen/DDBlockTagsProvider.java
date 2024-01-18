
package com.someguyssoftware.ddenizens.datagen;

import com.someguyssoftware.ddenizens.DD;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author Mark Gottschling on Jan 17, 2024
 *
 */
public class DDBlockTagsProvider extends BlockTagsProvider {

    public DDBlockTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider,
                               ExistingFileHelper existingFileHelper) {
    	super(output, lookupProvider, DD.MODID, existingFileHelper);
	}

	@Override
    protected void addTags(Provider provider) {
    }

}
