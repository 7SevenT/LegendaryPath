package com.sevent.identify;

import com.sevent.quench.QuenchItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class IdentifyExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        IdentifyGUI identifyGUI = new IdentifyGUI();
        identifyGUI.openCustomGUI(player);
        return true;
    }
}
