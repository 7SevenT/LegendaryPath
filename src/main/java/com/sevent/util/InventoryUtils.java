package com.sevent.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;

public class InventoryUtils {
    public static void closeInventory(Player player, Inventory inventory,int slot){
        ItemStack slotItem = inventory.getItem(slot);
        if (slotItem != null && slotItem.getType() != Material.AIR){
            HashMap<Integer, ItemStack> itemStackHashMap = player.getInventory().addItem(slotItem);
            // 如果玩家背包已满，则将剩余物品扔到地上
            if (!itemStackHashMap.isEmpty()) {
                //扔到地上
                Set<Integer> integers = itemStackHashMap.keySet();
                for (Integer integer : integers) {
                    player.getWorld().dropItem(player.getLocation(), itemStackHashMap.get(integer));
                }
            }
        }
    }

    /*
     * @description: 判断获取的这个格子是不是空的
     **/
    public static boolean isEmpty(ItemStack itemStack){
        return itemStack == null || itemStack.getType() == Material.AIR;
    }
}
