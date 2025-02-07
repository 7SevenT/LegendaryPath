package com.sevent.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;


public class LaoBa implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // 检查发送者是否为玩家
        Player player;
        if (commandSender instanceof Player) {
            player = (Player)commandSender;
            laoBaAppear(player);
            return true; // 命令成功处理
        } else {
            commandSender.sendMessage("只有玩家才能使用此命令！");
            return false; // 命令未成功处理
        }
    }

    public static void laoBaAppear(Player player){
        //发送标题
        player.sendTitle("§l§6嗨嗨嗨来了奥", "§e碎然不是同一个时间，但是是同一个撤硕er", 10, 70, 10);
        //给予粑粑
        player.getInventory().addItem(createShit());
        //播放声音
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1.0F, 1.0F);// 音量为1.0，音调为1.0
        //延迟0.5秒播放
        new BukkitRunnable() {
            @Override
            public void run() {
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0F, 1.0F);// 音量为1.0，音调为1.0
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugins()[0], 10L); // 20 ticks = 1 second
    }

    // 创建ItemStack
    public static ItemStack createShit() {
        ItemStack item = new ItemStack(Material.COCOA_BEANS);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6粑粑");
            meta.setLore(List.of("§6恶心的小吃"));
            meta.addEnchant(Enchantment.PROJECTILE_PROTECTION, 1, true);
            meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        return item;
    }
}
