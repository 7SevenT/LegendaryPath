package com.sevent.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItemUtils {
    /*
     * @description:获取填充物品
     **/
    public static ItemStack getFillItem() {
        ItemStack backgroundItem = new ItemStack(Material.WHITE_STAINED_GLASS_PANE, 1);
        ItemMeta backgroundMeta = backgroundItem.getItemMeta();
        if (backgroundMeta != null){
            backgroundMeta.setDisplayName("装备放左边");
            backgroundMeta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
            backgroundItem.setItemMeta(backgroundMeta);
        }
        return backgroundItem;
    }

    public static ItemStack getConfirmItem() {
        ItemStack confirmButton = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta confirmButtonMeta = confirmButton.getItemMeta();
        if (confirmButtonMeta != null){
            confirmButtonMeta.setDisplayName("确认");
            confirmButton.setItemMeta(confirmButtonMeta);
        }
        return confirmButton;
    }

    public static ItemStack getCancelItem() {
        ItemStack cancelButton = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta cancelButtonMeta = cancelButton.getItemMeta();
        if (cancelButtonMeta != null){
            cancelButtonMeta.setDisplayName("取消");
            cancelButton.setItemMeta(cancelButtonMeta);
        }
        return cancelButton;
    }
}
