package com.sevent.identify;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com.sevent.util.Color;

import java.util.List;

public class IdentifyItem {
    //@description:获取可鉴定物品
    public static ItemStack getIdentifiableItem(Material material, String displayName, int quality){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            // 设置名称与描述
            meta.setDisplayName(IdentifyUtil.getQualityColor(quality)+displayName + "§b[未鉴定]");
            meta.setLore(List.of(IdentifyUtil.getQualityName(quality)));
            // 隐藏属性
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            // 标记此物品为可鉴定物品
            meta.getPersistentDataContainer().set(IdentifyConstants.IDENTIFIABLE, PersistentDataType.INTEGER,IdentifyConstants.IDENTIFIABLE_VALUE);
            // 标记装备品质
            meta.getPersistentDataContainer().set(IdentifyConstants.ITEM_QUALITY, PersistentDataType.INTEGER,quality);
            // 添加到原物品上
            item.setItemMeta(meta);
        }
        return item;
    }

    // @description:鉴定书
    public static ItemStack getIdentifyBook(){
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        ItemMeta meta = item.getItemMeta();
        if (meta!=null) {
            // 设置名称与描述
            meta.setDisplayName(Color.GOLD+"[远古鉴定书]");
            meta.setLore(List.of("这本神秘卷册蕴","含智者的远见，","能揭示任何未知","物品的真正属性","与潜力。"));
            // 设置显示附魔效果
            meta.setEnchantmentGlintOverride(true);
            // 标记装备品质
            meta.getPersistentDataContainer().set(IdentifyConstants.IDENTIFYBOOK, PersistentDataType.BOOLEAN,true);
            // 添加到原物品上
            item.setItemMeta(meta);
        }   
        return item;
    }


}
