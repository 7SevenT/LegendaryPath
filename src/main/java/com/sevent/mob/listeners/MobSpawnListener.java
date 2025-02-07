package com.sevent.mob.listeners;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.sevent.mob.MobConstants;
import com.sevent.mob.MobEntity;

public class MobSpawnListener implements Listener{
    public MobSpawnListener(){};
    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        // 1.判断
        // a.不在RPG世界不行
        if (!MobConstants.RPG_WORLDS_NAME.contains(event.getEntity().getWorld().getName())) return;
        // b.不是怪物不行
        if (!MobConstants.MOB_TYPE_LIST.contains(event.getEntityType())) return;
        // 2.生成自定义怪物
        LivingEntity entity = event.getEntity();
        if (!MobConstants.SPAWN_MOB_LIST.contains(entity.getType())) event.setCancelled(true);
        Location location = entity.getLocation();
        MobEntity.spawnMonster(location, entity);
    }
}
