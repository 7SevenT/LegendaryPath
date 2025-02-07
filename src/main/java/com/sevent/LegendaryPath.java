package com.sevent;

import com.sevent.commands.LaoBa;
import com.sevent.commands.RpgSwitch;
import com.sevent.identify.IdentifyClickListener;
import com.sevent.identify.IdentifyExecutor;
import com.sevent.mob.MobConstants;
import com.sevent.mob.listeners.MobDropsListener;
import com.sevent.mob.listeners.MobSpawnListener;

import com.sevent.quench.QuenchClickListener;
import com.sevent.quench.QuenchExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class LegendaryPath extends JavaPlugin {
    private static FileConfiguration config;
    private static File dataFolder;
    private static Logger logger;
    @Override
    public void onEnable() {
        // 0.初始化变量
        initVariable();
        // 启动开始
        logger.info("----------LegendaryPath 正在启动!----------");
        // 1.文件与文件夹的创建
        createFolderAndInitVariable();
        createConfigAndInitVariable();
        // 2.初始化其他类
        initOtherClass();
        // 3.注册监听器
        getServer().getPluginManager().registerEvents(new MobDropsListener(),this);
        getServer().getPluginManager().registerEvents(new IdentifyClickListener(),this);
        getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new QuenchClickListener(), this);
        // 4.注册命令
        getCommand("laoba").setExecutor(new LaoBa());
        getCommand("lp").setExecutor(new RpgSwitch());
        getCommand("identify").setExecutor(new IdentifyExecutor());
        getCommand("quench").setExecutor(new QuenchExecutor());
        //启动结束
        logger.info("----------LegendaryPath 启动完成!----------");
    }

    // 0.初始化变量
    private void initVariable() {
        logger = getLogger();
        config = getConfig();
    }
    // 1.检查并创建文件
    private void createConfigAndInitVariable() {
        if (config == null){
            saveDefaultConfig();
            logger.info("配置文件创建完成！");
            config = getConfig();
        }else logger.info("配置文件已存在！");
    }
    private void createFolderAndInitVariable() {
        if (!getDataFolder().exists() && getDataFolder().mkdirs()){
            logger.info("数据文件夹创建完成！");
        }else logger.info("数据文件夹已存在！");
        dataFolder = getDataFolder();
    }
    // 2.初始化其他类
    private void initOtherClass() {
        QuenchClickListener.plugin = this;
        IdentifyClickListener.plugin = this;
        MobDropsListener.plugin=this;
        RpgSwitch.plugin=this;
        RpgSwitch.init(config);
    }


    @Override
    public void onDisable() {
        // 1.数据保存到config对象
        config.set("RPGWorlds",MobConstants.RPG_WORLDS_NAME);
        // 2.保存配置到磁盘
        try {
            saveConfig();
            logger.info("配置已成功保存！");
        } catch (Exception e) {
            logger.severe("保存配置失败: " + e.getMessage());
        }
    }
}
