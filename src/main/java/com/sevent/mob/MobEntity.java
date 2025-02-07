package com.sevent.mob;

import com.sevent.util.RandomGet;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class MobEntity {
    private final LivingEntity entity;
    private int level;
    private String color;
    private String levelName;

    public MobEntity(LivingEntity entity, MobDifficulty mobDifficulty) {
        this.entity = entity;
        // 根据难度设置怪物等级
        setMobDifficulty(mobDifficulty);
        // 修改自定义怪物的属性
        alterAttributes();
        // 添加装备
        addEquipment();
        // 添加唯一标识
        addNBT();
    }


    // @description:根据难度设置怪物等级和名称颜色
    private void setMobDifficulty(MobDifficulty mobDifficulty)  {
        switch (mobDifficulty) {
            case ONE -> {
                level = (int) (Math.random() * 20) + 1;
                color = "§f";
                levelName = "练气期";
            }
            case TWO -> {
                level = (int) (Math.random() * 10) + 21;
                color = "§a";
                levelName = "筑基期";
            }
            case THREE -> {
                level = (int) (Math.random() * 10) + 31;
                color = "§e";
                levelName = "结丹期";
            }
            case FOUR -> {
                level = (int) (Math.random() * 10) + 41;
                color = "§9";
                levelName = "元婴期";
            }
            case FIVE -> {
                level = (int) (Math.random() * 10) + 51;
                color = "§3";
                levelName = "化神期";
            }
            case SIX -> {
                level = (int) (Math.random() * 10) + 61;
                color = "§6";
                levelName = "和道期";
            }
            case SEVEN -> {
                level = (int) (Math.random() * 10) + 71;
                color = "§5";
                levelName = "大乘期";
            }
            case EIGHT -> {
                level = (int) (Math.random() * 10) + 81;
                color = "§d";
                levelName = "渡劫期";
            }
            case NINE -> {
                level = (int) (Math.random() * 10) + 91;
                color = "§4";
                levelName = "地仙";
            }
            default -> {
                level = 1;
                color = "§f";
            }
        }
    }
    //@description:修改自定义怪物的属性
    private void alterAttributes() {
        // 0.名称
        entity.setCustomName(color +"LV:"+level+" "+RandomGet.getElementOfList(MobConstants.MOB_PREFIX_LIST) + levelName + entity.getName());
        entity.setCustomNameVisible(true);
        // 1.生命值
        AttributeInstance maxHealthAttr = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealthAttr != null) {
            double newMaxHealth = maxHealthAttr.getValue() + MobConstants.MOB_HEALTH_MUL * level;
            maxHealthAttr.setBaseValue(newMaxHealth);
            // 确保当前生命值等于新的最大生命值
            entity.setHealth(newMaxHealth);
        }
        // 2.攻击力
        AttributeInstance attackAttr = entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE);
        if (attackAttr != null) {
            attackAttr.setBaseValue(attackAttr.getBaseValue()*2 + MobConstants.MOB_DAMAGE_MUL * level);
        }
        // 3.防御力
        AttributeInstance armorAttr = entity.getAttribute(Attribute.GENERIC_ARMOR);
        if (armorAttr != null) {
            armorAttr.setBaseValue(armorAttr.getBaseValue() + MobConstants.MOB_ARMOR_MUL * level);
        }
    }
    //@description:为自定义怪物添加装备
    private void addEquipment() {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            // 设置主副手武器(溺尸则装备三叉戟)
            if (entity.getType().equals(EntityType.DROWNED)){
                equipment.setItemInMainHand(new ItemStack(Material.TRIDENT));
            }else {
                equipment.setItemInMainHand(MobEquipmentItem.getSword(RandomGet.getElementOfList(MobConstants.WEAPON_LIST)));
            }
            equipment.setItemInOffHand(new ItemStack(Material.SHIELD));
            // 设置防具
            equipment.setHelmet(MobEquipmentItem.getHelmet(RandomGet.getElementOfList(MobConstants.HELMET_LIST)));
            equipment.setChestplate(MobEquipmentItem.getChestplate(RandomGet.getElementOfList(MobConstants.CHESTPLATE_LIST)));
            equipment.setLeggings(MobEquipmentItem.getLeggings(RandomGet.getElementOfList(MobConstants.LEGGINGS_LIST)));
            equipment.setBoots(MobEquipmentItem.getBoots(RandomGet.getElementOfList(MobConstants.BOOTS_LIST)));


        }
    }

    //@description:添加标识
    public void addNBT() {
        PersistentDataContainer persistentDataContainer = entity.getPersistentDataContainer();
        // 精英怪标识
        persistentDataContainer.set(MobConstants.CUSTOM_MOB_KEY, PersistentDataType.BOOLEAN, true);
        // 等级标识
        persistentDataContainer.set(MobConstants.MOB_LEVEL_KEY, PersistentDataType.INTEGER, level);
    }
    
    /*
     * @description: 生成怪物并自定义属性
     * @param: [location] Location对象
     **/
    public static void spawnMonster(Location location,LivingEntity entity) {
        MobDifficulty mobDifficulty;
        // 根据什么怪物,添加什么装备

        // 随xz越大生成的怪物越强,负坐标不用处理因为平方
        double radius = Math.sqrt(location.getX() * location.getX() + location.getZ() * location.getZ());
        MobDifficulty[] mobDifficultyValues = MobDifficulty.values();
        mobDifficulty = mobDifficultyValues[Math.min((int) (Math.ceil(radius / 100)-1),mobDifficultyValues.length-1)];
        // 创建一个新的自定义怪物实例
        new MobEntity(entity, mobDifficulty);
    }
}