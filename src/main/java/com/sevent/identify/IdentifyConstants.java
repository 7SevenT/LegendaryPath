package com.sevent.identify;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.List;

public class IdentifyConstants {
    // GUI相关
    public static final int INVENTORY_SIZE = 9 * 6; // 3行9列
    public static final int MIDDLE_SLOT = 21; // 中间格子的位置
    public static final int IDENTIFYBOOK_SLOT = 23; // 鉴定书格子的位置
    public static final int CONFIRM_SLOT = 38; // 确认按钮位置
    public static final int CANCEL_SLOT = 42; // 取消按钮位置
    public static final String IDENTIFY_GUI_TITLE = "§6鉴定系统";

    //NBT标签[是否已鉴定]
    public static final String IDENTIFIABLE_KEY = "identifiable";
    public static final NamespacedKey IDENTIFIABLE = new NamespacedKey("legendary_path", IDENTIFIABLE_KEY);
    public static final int IDENTIFIABLE_VALUE = 1;
    public static final int NOT_IDENTIFIABLE_VALUE = 0;

    //NBT标签[鉴定书]
    public static final String IDENTIFYBOOK_KEY = "identifybook";
    public static final NamespacedKey IDENTIFYBOOK = new NamespacedKey("legendary_path", IDENTIFYBOOK_KEY);

    // NBT标签[物品品质]
    public static final String ITEM_QUALITY_KEY = "item_quality";
    public static final NamespacedKey ITEM_QUALITY = new NamespacedKey("legendary_path", ITEM_QUALITY_KEY);
    public static final int QUALITY_ONE = 1;
    public static final int QUALITY_TWO = 2;
    public static final int QUALITY_THREE = 3;
    public static final int QUALITY_FOUR = 4;
    public static final int QUALITY_FIVE = 5;
    public static final int QUALITY_SIX = 6;
    public static final int QUALITY_SEVEN = 7;
    public static final int QUALITY_EIGHT = 8;
    public static final int QUALITY_NINE = 9;

    // 武器类型
    public static final List<Material> SWORD_LIST = List.of(Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD,
            Material.DIAMOND_SWORD, Material.NETHERITE_SWORD);
    public static final List<Material> AXE_LIST = List.of(Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE,
            Material.DIAMOND_AXE, Material.NETHERITE_AXE);
}
