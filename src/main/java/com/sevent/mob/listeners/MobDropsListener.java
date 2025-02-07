package com.sevent.mob.listeners;

import com.sevent.LegendaryPath;
import com.sevent.identify.IdentifyItem;
import com.sevent.mob.MobConstants;
import com.sevent.mob.MobDifficulty;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Random;

public class MobDropsListener implements Listener{
    public static LegendaryPath plugin;
    private static final MobDifficulty[] mobDifficultyValues = MobDifficulty.values();
    public MobDropsListener(){}

    private final Random random = new Random();
    private LivingEntity mobEntity;
    private PersistentDataContainer persistentDataContainer;
    private int quality;
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // 0.初始化
        mobEntity = event.getEntity();
        persistentDataContainer = mobEntity.getPersistentDataContainer();
        // 1.判断是否处理事件
        if (!isProcessEvent()) return;
        // 2.处理精英怪掉落物
        List<ItemStack> drops = event.getDrops();
        drops.clear();
        MobDifficulty mobDifficulty = getMobDifficulty();
        addWeaponDrop(drops, mobDifficulty.getWeaponDropRate());
        addArmorDrop(drops, mobDifficulty.getArmorDropRate());
        addIdentifyBookDrop(drops, mobDifficulty.getIdentifyBookDropRate());
        addSoulDrop(drops, mobDifficulty.getSoulFragments());
        addQuenchStoneDrop(drops,mobDifficulty.getQuenchStoneDropRate(),mobDifficulty.getQuenchStone());
    }


    // 1.是否处理事件
    private boolean isProcessEvent(){
        // a.不在 RPG_WORLDS 中, 则不处理事件
        if (!MobConstants.RPG_WORLDS.contains(mobEntity.getWorld()))  return false;
        // b.不是自定义怪物, 则不处理事件
        if (!persistentDataContainer.has(MobConstants.CUSTOM_MOB_KEY) || !persistentDataContainer.has(MobConstants.MOB_LEVEL_KEY)) return false;
        // 全通过则处理
        return true;
    }
    // 2.处理掉落物
    // 获取怪物难度与装备质量
    private MobDifficulty getMobDifficulty(){
        Integer mobLevel = persistentDataContainer.get(MobConstants.MOB_LEVEL_KEY, PersistentDataType.INTEGER);
        MobDifficulty mobDifficulty;
        int index = (int)Math.ceil(mobLevel/10.0) - 2;
        if (index == -1) {
            mobDifficulty = MobDifficulty.ONE;
            quality = 1;
        }else {
            mobDifficulty = mobDifficultyValues[Math.min(index, mobDifficultyValues.length-1)];
            quality = index+1;
        }
        return mobDifficulty;
    }
    // 掉落物添加
    private void addWeaponDrop(List<ItemStack> drops,double probability){
        if (random.nextDouble() > probability) return;
        EntityEquipment equipment = mobEntity.getEquipment();
        if (equipment == null) return;
        ItemStack itemInMainHand = equipment.getItemInMainHand();
        ItemMeta itemMeta = itemInMainHand.getItemMeta();
        if (itemMeta == null) return;
        drops.add(IdentifyItem.getIdentifiableItem(itemInMainHand.getType(),itemMeta.getDisplayName(),quality));
    }
    private void addIdentifyBookDrop(List<ItemStack> drops,double probability){
        if (random.nextDouble() > probability) return;
        drops.add(IdentifyItem.getIdentifyBook());
    }
    private void addArmorDrop(List<ItemStack> drops,double probability){
        if (random.nextDouble() > probability) return;
        EntityEquipment equipment = mobEntity.getEquipment();
        if (equipment == null) return;
        // 随机一件装备
        ItemStack[] armorContents = equipment.getArmorContents();
        ItemStack armor = armorContents[random.nextInt(armorContents.length)];
        ItemMeta itemMeta = armor.getItemMeta();
        if (itemMeta == null) return;
        drops.add(IdentifyItem.getIdentifiableItem(armor.getType(),itemMeta.getDisplayName(),quality));
    }
    private void addSoulDrop(List<ItemStack> drops, ItemStack soulFragments) {
        drops.add(soulFragments);
    }
    private void addQuenchStoneDrop(List<ItemStack> drops,double probability,ItemStack quenchStone) {
        if (random.nextDouble() > probability) return;
        drops.add(quenchStone);
    }
}