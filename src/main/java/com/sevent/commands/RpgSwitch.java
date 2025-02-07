package com.sevent.commands;

import com.sevent.LegendaryPath;
import com.sevent.mob.MobConstants;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class RpgSwitch implements CommandExecutor {
    public static LegendaryPath plugin;
    private final static Set<World> worlds = MobConstants.RPG_WORLDS;
    public static void init(FileConfiguration config) {
        loadWorlds(config);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        switch (strings[0].toLowerCase()) {
            case "open":
                openRPGWorld(commandSender, strings[1]);
                break;
            case "close":
                closeRPGWorld(commandSender, strings[1]);
                break;
            case "list":
                commandSender.sendMessage(getRpgWorldsStr());
                break;
            default:
                commandSender.sendMessage("指令错误！");
                break;
        }
        return true;
    }

    // 开启RPG世界
    private static void openRPGWorld(CommandSender commandSender, String worldName) {
        if (existsWorld(worldName)){
            MobConstants.RPG_WORLDS_NAME.add(worldName);
            worlds.add(getWorld(commandSender, worldName));
            commandSender.sendMessage(worldName + "世界的RPG已开启！");
        }else commandSender.sendMessage(worldName + "世界不存在！");
    }
    // 关闭RPG世界
    private static void closeRPGWorld(CommandSender commandSender, String worldName) {
        if (existsWorld(worldName)){
            MobConstants.RPG_WORLDS_NAME.remove(worldName);
            worlds.remove(getWorld(commandSender, worldName));
            commandSender.sendMessage(worldName + "世界的PRG已关闭！");
        }else commandSender.sendMessage(worldName + "世界不存在！");
    }
    // 判断世界是否存在
    private static boolean existsWorld(String worldName) {
        List<World> worldList = plugin.getServer().getWorlds();
        List<String> worldNameList = new ArrayList<>();
        for (World world : worldList){
            worldNameList.add(world.getName());
        }
        return worldNameList.contains(worldName);
    }


    // 获取黑界列表字符串
    private String getRpgWorldsStr() {
        StringBuilder sb = new StringBuilder("RPG世界列表:[");
        for (String worldName : MobConstants.RPG_WORLDS_NAME) {
            sb.append(worldName).append(",");
        }
        if (sb.toString().endsWith(",")){
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");
        return sb.toString();
    }

    // 获取世界对象
    private static World getWorld(CommandSender commandSender,String worldName) {
        World world = plugin.getServer().getWorld(worldName);
        if (world == null) {
            commandSender.sendMessage("世界不存在！");
        }
        return world;
    }

    // 加载黑界世界列表
    private static void loadWorlds(FileConfiguration config) {
        // 1.获取所有世界的名称
        List<World> worldList = plugin.getServer().getWorlds();
        Set<String> worldNameList = new HashSet<>();
        for (World world : worldList){
            worldNameList.add(world.getName());
        }
        // 2.获取RPG世界名称列表,如果有这个世界在加入的RPG世界列表,防止崩溃
        MobConstants.RPG_WORLDS_NAME.addAll(config.getStringList("RPGWorlds"));
        List<String> worldsWillRemove = new ArrayList<>(); //不能在遍历的同时修改列表
        for (String worldName : MobConstants.RPG_WORLDS_NAME) {
            if (worldNameList.contains(worldName)){
                worlds.add(plugin.getServer().getWorld(worldName));
            }else {
                worldsWillRemove.add(worldName);
                plugin.getLogger().info(worldName+"黑界世界不存在！");
            }
        }
        MobConstants.RPG_WORLDS_NAME.removeAll(worldsWillRemove);
        plugin.getLogger().info("黑界读取成功！"+worlds.toString());
    }
}
