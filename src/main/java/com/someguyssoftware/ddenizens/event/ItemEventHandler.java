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
package com.someguyssoftware.ddenizens.event;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.item.DDEggItem;
import com.someguyssoftware.ddenizens.tags.DDTags;
import com.someguyssoftware.ddenizens.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Objects;

/**
 * 
 * @author Mark Gottschling Jan 17, 2024
 *
 */
//@Mod.EventBusSubscriber(modid = DD.MODID)
public class ItemEventHandler {

//	@SubscribeEvent
	public static void onItemInfo(ItemTooltipEvent event) {
		if (event.getItemStack().is(DDTags.Items.EGGS)) {
//			event.getToolTip().add(Component.translatable(LangUtil.tooltip("eggs")).withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
			List<Component> tooltip = event.getToolTip();
//			((DDEggItem)event.getItemStack().app)

		}
	}
}
