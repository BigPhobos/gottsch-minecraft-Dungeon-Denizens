package com.someguyssoftware.ddenizens.util;

import net.minecraft.world.entity.EquipmentSlot;

import java.util.Arrays;
import java.util.List;

public class EquipmentUtil {
    public static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final List<EquipmentSlot> EQUIPMENT_SLOTS = Arrays.asList(SLOT_IDS);
}
