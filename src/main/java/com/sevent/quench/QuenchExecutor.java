package com.sevent.quench;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuenchExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        QuenchGUI quenchGUI = new QuenchGUI();
        quenchGUI.openQuenchGUI(player);
        return true;
    }
}
