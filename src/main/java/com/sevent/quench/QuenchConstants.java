package com.sevent.quench;

import org.bukkit.NamespacedKey;

public class QuenchConstants {
    public static final String QUENCH_GUI_TITLE = "§6淬炼系统";
    public static final int INVENTORY_SIZE = 9 * 6; // 3行9列
    public static final int EQUIPMENT_SLOT = 21; // 中间格子的位置
    public static final int QUENCH_STONE_SLOT = 23; // 淬炼石的位置
    public static final int CONFIRM_SLOT = 38; // 确认按钮位置
    public static final int CANCEL_SLOT = 42; // 取消按钮位置

    //持久化存储[可淬炼次数]
    public static final String QUENCHABLE_COUNT = "quenchable_count";
    public static final NamespacedKey QUENCHABLE_KEY = new NamespacedKey("legendary_path", QUENCHABLE_COUNT);
    public static final int QUENCHABLE_COUNT_ZERO = 0;
    public static final int QUENCHABLE_COUNT_ONE = 1;
    public static final int QUENCHABLE_COUNT_TWO = 2;
    public static final int QUENCHABLE_COUNT_THREE = 3;

    //持久化存储[淬炼石等级]
    public static final String QUENCH_STONE = "quench_stone";
    public static final NamespacedKey QUENCH_STONE_KEY = new NamespacedKey("legendary_path", QUENCH_STONE);
    public static final int QUENCH_STONE_FAN = 1;
    public static final int QUENCH_STONE_HUANG = 2;
    public static final int QUENCH_STONE_DI = 3;
    public static final int QUENCH_STONE_TIAN = 4;
}
