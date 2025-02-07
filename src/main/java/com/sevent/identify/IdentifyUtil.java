package com.sevent.identify;

import org.bukkit.Material;

public class IdentifyUtil {
    public static String getQualityName(Integer quality){
        return switch (quality) {
            case IdentifyConstants.QUALITY_ONE -> "§f[凡阶中品]";
            case IdentifyConstants.QUALITY_TWO -> "§a[凡阶上品]";
            case IdentifyConstants.QUALITY_THREE -> "§e[黄阶下品]";
            case IdentifyConstants.QUALITY_FOUR -> "§9[黄阶中品]";
            case IdentifyConstants.QUALITY_FIVE -> "§3[黄阶上品]";
            case IdentifyConstants.QUALITY_SIX -> "§6[地阶下品]";
            case IdentifyConstants.QUALITY_SEVEN -> "§5[地阶中品]";
            case IdentifyConstants.QUALITY_EIGHT -> "§d[地阶上品]";
            case IdentifyConstants.QUALITY_NINE -> "§4[天阶下品]";
            default -> "§f[未知]";
        };
    }

    public static String getQualityColor(Integer quality){
        return switch (quality) {
            case IdentifyConstants.QUALITY_ONE -> "§f";
            case IdentifyConstants.QUALITY_TWO -> "§a";
            case IdentifyConstants.QUALITY_THREE -> "§e";
            case IdentifyConstants.QUALITY_FOUR -> "§9";
            case IdentifyConstants.QUALITY_FIVE -> "§3";
            case IdentifyConstants.QUALITY_SIX -> "§6";
            case IdentifyConstants.QUALITY_SEVEN -> "§5";
            case IdentifyConstants.QUALITY_EIGHT -> "§d";
            case IdentifyConstants.QUALITY_NINE -> "§4";
            default -> "§未知";
        };
    }

    // 获取武器基础伤害
    public static int getBaseAttackDamage(Material material){
        return switch (material){
            // 剑
            case WOODEN_SWORD,GOLDEN_SWORD -> 4;
            case STONE_SWORD -> 5;
            case IRON_SWORD -> 6;
            case DIAMOND_SWORD -> 7;
            case NETHERITE_SWORD -> 8;
            // 斧头
            case WOODEN_AXE,GOLDEN_AXE -> 7;
            case STONE_AXE, IRON_AXE,DIAMOND_AXE -> 9;
            case NETHERITE_AXE -> 10;
            //三叉戟
            case TRIDENT -> 9;
            //重锤
            case MACE -> 6;
            default -> 0;
        };
    }
    // 获取武器基础攻击速度
    public static double getBaseAttackSpeed(Material material){
        return switch (material){
            // 剑
            case WOODEN_SWORD, GOLDEN_SWORD, STONE_SWORD, IRON_SWORD, DIAMOND_SWORD, NETHERITE_SWORD -> 1.6;
            // 斧头
            case WOODEN_AXE,STONE_AXE -> 0.8;
            case IRON_AXE -> 0.9;
            case GOLDEN_AXE, DIAMOND_AXE, NETHERITE_AXE -> 1.0;
            // 三叉戟
            case TRIDENT -> 1.1;
            // 重锤
            case MACE -> 0.6;
            default -> 0;
        };
    }
    // 获取防具基础护甲值
    public static int getBaseArmor(Material material){
        return switch (material){
            case LEATHER_HELMET, LEATHER_BOOTS,CHAINMAIL_BOOTS,GOLDEN_BOOTS -> 1;
            case CHAINMAIL_HELMET, LEATHER_LEGGINGS,IRON_HELMET,GOLDEN_HELMET,IRON_BOOTS -> 2;
            case LEATHER_CHESTPLATE,GOLDEN_LEGGINGS,DIAMOND_HELMET,NETHERITE_HELMET,DIAMOND_BOOTS,NETHERITE_BOOTS -> 3;
            case CHAINMAIL_LEGGINGS -> 4;
            case IRON_LEGGINGS, GOLDEN_CHESTPLATE,CHAINMAIL_CHESTPLATE -> 5;
            case IRON_CHESTPLATE,DIAMOND_LEGGINGS,NETHERITE_LEGGINGS -> 6;
            case DIAMOND_CHESTPLATE, NETHERITE_CHESTPLATE -> 8;
            default -> 0;
        };
    }
    // 获取防具基础护甲韧性
    public static int getBaseArmorToughness(Material material){
        return switch (material){
            case DIAMOND_HELMET,DIAMOND_CHESTPLATE,DIAMOND_LEGGINGS,DIAMOND_BOOTS -> 2;
            case NETHERITE_HELMET,NETHERITE_CHESTPLATE,NETHERITE_LEGGINGS,NETHERITE_BOOTS -> 3;
            default -> 0;
        };
    }
    // 获取防具基础护甲抵抗
    public static double getBaseArmorResistance(Material material){
        return switch (material){
            case NETHERITE_HELMET,NETHERITE_CHESTPLATE,NETHERITE_LEGGINGS,NETHERITE_BOOTS -> 0.1;
            default -> 0;
        };
    }
}
