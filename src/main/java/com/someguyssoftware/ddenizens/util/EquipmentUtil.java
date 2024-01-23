package com.someguyssoftware.ddenizens.util;

import net.minecraft.world.entity.EquipmentSlot;

import java.util.Arrays;
import java.util.List;

public class EquipmentUtil {
    public static final EquipmentSlot[] ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    public static final EquipmentSlot[] HANDHELD_SLOTS = new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND};
    // NOTE that this is is unmodifiable
    public static final List<EquipmentSlot> ARMOR_LIST = Arrays.asList(ARMOR_SLOTS);
    public static final List<EquipmentSlot> HANDHELD_LIST = Arrays.asList(HANDHELD_SLOTS);

}
