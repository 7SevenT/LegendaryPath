package com.sevent.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class AnimationUtils {
    public static void playProcessSound(Player player){
        final int[] counter = {0};
        new BukkitRunnable() {
            @Override
            public void run() {
                if (counter[0] == 2) { //循环2次后结束音效
                    this.cancel();
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                }else {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
                    counter[0]++;
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugins()[0], 0L, 20L);
    }

    public static void playAnimation(Inventory inventory,int slot,Runnable onAnimationEnd){
        final int[] counter = {0};
        List<Material> materialList = Arrays.asList(Material.values());
        new BukkitRunnable() {
            @Override
            public void run() {
                // 清空中间格子后添加随机物品
                inventory.clear(slot);
                inventory.setItem(slot,new ItemStack(RandomGet.getElementOfList(materialList)));
                counter[0]++;
                if (counter[0] == 8){ //循环3次后结束动画
                    this.cancel();
                    onAnimationEnd.run();
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugins()[0], 0L, 5L);
    }
}
