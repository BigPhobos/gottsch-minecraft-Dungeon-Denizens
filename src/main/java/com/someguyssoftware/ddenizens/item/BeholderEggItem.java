package com.someguyssoftware.ddenizens.item;

import com.someguyssoftware.ddenizens.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class BeholderEggItem extends DDEggItem {

    public BeholderEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, worldIn, tooltip, flag);

        LangUtil.appendAdvancedHoverText(tooltip, tt -> {
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.number_appearing"),1));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.size"), Component.translatable(LangUtil.tooltip("stats.size.large"))));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.level"), Component.translatable(LangUtil.tooltip("stats.level.mini_boss"))));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.rarity"), Component.translatable(LangUtil.tooltip("stats.rarity.rare"))));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.movement"), Component.translatable(LangUtil.tooltip("stats.movement.flies"))));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.speed"), Component.translatable(LangUtil.tooltip("stats.speed.normal"))));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.health"), 36));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.damage"), 8));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.daylight"), Component.translatable(LangUtil.tooltip("boolean.yes"))));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.despawn"), Component.translatable(LangUtil.tooltip("boolean.yes"))));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.spawns"), "Underworld, Nether"));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.specials")));
            tooltip.add(Component.literal("- Randomly casts one of four spells: Paralysis, Harm, Disintegrate and Disarm."));
            tooltip.add(Component.literal("- Can summon one minion at a time: Headless, Orc, Spectator, or Blaze."));
            tooltip.add(Component.literal("- Every 2 minutes, has a 10% chance to summon a Daemon."));
        });
    }
}
