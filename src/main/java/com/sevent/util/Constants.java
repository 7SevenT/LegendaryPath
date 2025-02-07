package com.sevent.util;

import org.bukkit.NamespacedKey;

public class Constants {
    // 攻击伤害值
    public final static String ATTACK_DAMAGE_VALUE_KEY = "attack_damage_value";
    public final static NamespacedKey attackDamageNamespacedKey = new NamespacedKey("legendary_path",ATTACK_DAMAGE_VALUE_KEY);
    // 攻击速度值
    public final static String ATTACK_SPEED_VALUE_KEY = "attack_speed_value";
    public final static NamespacedKey attackSpeedNamespacedKey = new NamespacedKey("legendary_path",ATTACK_SPEED_VALUE_KEY);
    // 护甲值
    public final static String ARMOR_VALUE_KEY = "armor_value";
    public final static NamespacedKey armorNamespacedKey = new NamespacedKey("legendary_path",ARMOR_VALUE_KEY);
    // 护甲韧性
    public final static String ARMOR_TOUGHNESS_VALUE_KEY = "armor_toughness_value";
    public final static NamespacedKey armorToughnessNamespacedKey = new NamespacedKey("legendary_path",ARMOR_TOUGHNESS_VALUE_KEY);
    // 护甲抵抗
    public final static String ARMOR_RESISTANCE_VALUE_KEY = "armor_resistance_value";
    public final static NamespacedKey armorResistanceNamespacedKey = new NamespacedKey("legendary_path",ARMOR_RESISTANCE_VALUE_KEY);
}
