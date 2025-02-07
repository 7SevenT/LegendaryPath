package com.sevent.quench;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class QuenchItem {
    public static ItemStack getQuenchStone(int quality){
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = item.getItemMeta();
        if (meta!=null) {
            switch (quality){
                case QuenchConstants.QUENCH_STONE_FAN -> {
                    meta.setDisplayName("§f[凡阶]§l§5淬炼石");
                    meta.setLore(List.of("一块蕴含着微弱灵力的紫色晶石","表面闪烁着淡淡的光芒，","他的力量尚显稚嫩","但却是淬炼装备的入门之选"));
                }
                case QuenchConstants.QUENCH_STONE_HUANG -> {
                    meta.setDisplayName("§a[黄阶]§l§5淬炼石");
                    meta.setLore(List.of("一块蕴含着灵力的黄色晶石","内部流淌着灵力，","是人们梦寐以求的宝物"));
                }
                case QuenchConstants.QUENCH_STONE_DI -> {
                    meta.setDisplayName("§9[地阶]§l§5淬炼石");
                    meta.setLore(List.of("一块散发着大地气息的墨绿色晶石","仿佛蕴含着无尽的生命力，","是淬炼师手中的瑰宝"));
                }
                case QuenchConstants.QUENCH_STONE_TIAN -> {
                    meta.setDisplayName("§6[天阶]§l§5淬炼石");
                    meta.setLore(List.of("一块纯净无暇的白色晶石","表面环绕着淡淡的光晕，","仿佛来自天外","是传说中的神器"));
                }
                default -> {}
            }
            // 设置显示附魔效果
            meta.setEnchantmentGlintOverride(true);
            // 标记淬炼石等级
            meta.getPersistentDataContainer().set(QuenchConstants.QUENCH_STONE_KEY, PersistentDataType.INTEGER,quality);
            // 添加到原物品上
            item.setItemMeta(meta);
        }
        return item;
    }
}
