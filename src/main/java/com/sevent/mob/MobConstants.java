package com.sevent.mob;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.*;

public class MobConstants {
    //RpgSwitch类与MobDrops类的常量
    public final static Set<World> RPG_WORLDS = new HashSet<>();//开启RPG的世界列表
    public final static List<String> RPG_WORLDS_NAME = new ArrayList<>();//开启RPG的世界名称的列表
    //MobEntity类的常量
    public final static double MOB_HEALTH_MUL = 1.2; // 怪物的生命值乘数
    public final static double MOB_DAMAGE_MUL = 0.2; // 怪物的攻击力乘数
    public final static double MOB_ARMOR_MUL = 0.1; // 怪物的防御力乘数
    public final static List<String> MOB_PREFIX_LIST = new ArrayList<>(); //怪物的前缀
    public final static List<Material> WEAPON_LIST = new ArrayList<>();
    public final static List<Material> HELMET_LIST = new ArrayList<>();
    public final static List<Material> CHESTPLATE_LIST = new ArrayList<>();
    public final static List<Material> LEGGINGS_LIST = new ArrayList<>();
    public final static List<Material> BOOTS_LIST = new ArrayList<>();
    //MobEquipmentOfMob类的常量
    public final static List<String> EQUIPMENT_BODY_LIST = new ArrayList<>();//装备的主词
    public final static List<String> EQUIPMENT_SUFFIX_LIST = new ArrayList<>();//装备的后缀
    //MobSpawnListener类的常量
    public final static Set<EntityType> MOB_TYPE_LIST = new HashSet<>();
    public final static Set<EntityType> SPAWN_MOB_LIST = new HashSet<>();
    // 怪物防具的前缀
    public final static List<String> ARMOR_PREFIX_LIST = List.of(
            "神圣之", "暗影之", "光辉之", "神秘之", "风暴之",
             "冰霜之", "火焰之", "勇士之", "皇家之", "传奇之", "不朽之"
    );//装备的主词

    //初始化
    static {
        //MobEntity类的常量
        Collections.addAll(MOB_PREFIX_LIST,"狂暴的", "凶煞的", "邪魅的", "黑暗的", "焚天的", "绝杀的",
                "吞噬的", "血腥的", "天煞的", "鬼魅的", "幻影的", "毒辣的", "雷霆的", "寒冰的", "烈焰的", "破碎的",
                "魔化的", "诡谲的", "神秘的");
        Collections.addAll(WEAPON_LIST,Material.WOODEN_SWORD, Material.STONE_SWORD, Material.IRON_SWORD,
                Material.DIAMOND_SWORD, Material.NETHERITE_SWORD,Material.WOODEN_AXE, Material.STONE_AXE,
                Material.IRON_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE);
        Collections.addAll(HELMET_LIST,Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.IRON_HELMET,
                Material.GOLDEN_HELMET,Material.DIAMOND_HELMET, Material.NETHERITE_HELMET,Material.TURTLE_HELMET);
        Collections.addAll(CHESTPLATE_LIST,Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE,
                Material.GOLDEN_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.NETHERITE_CHESTPLATE);
        Collections.addAll(LEGGINGS_LIST,Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS,
                Material.GOLDEN_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.NETHERITE_LEGGINGS);
        Collections.addAll(BOOTS_LIST,Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS,
                Material.GOLDEN_BOOTS, Material.DIAMOND_BOOTS, Material.NETHERITE_BOOTS);

        //MobEquipmentOfMob类的常量
        Collections.addAll(EQUIPMENT_BODY_LIST,"锋利之", "冰霜之", "烈焰之", "神秘之", "迅捷之", "沉重之",
                "古老之", "雷霆之", "灵巧之", "坚固之", "邪恶之", "狂野之", "绝对之", "迷雾之", "无情之",
                "毁灭之", "禁忌之", "追踪之", "刺穿之", "破坏之", "流星之", "轻盈之", "猛击之", "吸血之",
                "幻影之", "守护之", "重击之", "烈风之", "无畏之", "绝杀之", "梦魇之", "天罚之", "飞舞之");
        Collections.addAll(EQUIPMENT_SUFFIX_LIST,"刃", "牙", "剑", "刀", "弓",  "爪", "爪刀", "匕首",
                "飞刀", "长剑", "短刀", "巨剑", "细剑", "战刀", "妖刀", "霸刀", "破天刀", "破军刀",
                "流云刀", "极霸矛", "狂风剑", "破败刀", "兽牙", "龙牙", "魔法剑", "飞鹰刀", "月影剑",
                "星辉剑", "白光剑", "黑影刀", "冥魂刀", "梦魇刃", "巨兽刀", "重剑", "轻刀", "灵动剑");

        //MobSpawnListener类的常量
        Collections.addAll(MOB_TYPE_LIST,EntityType.ZOMBIE,EntityType.SKELETON,EntityType.CREEPER,
            EntityType.ENDERMAN,EntityType.WITCH,EntityType.SPIDER,EntityType.CAVE_SPIDER,EntityType.SLIME,
            EntityType.MAGMA_CUBE,EntityType.GHAST,EntityType.BLAZE,EntityType.PIGLIN,EntityType.PIGLIN_BRUTE,
            EntityType.ZOGLIN,EntityType.HUSK,EntityType.DROWNED,EntityType.STRAY,EntityType.VEX,EntityType.VINDICATOR,
            EntityType.EVOKER,EntityType.RAVAGER,EntityType.PHANTOM,EntityType.GUARDIAN,EntityType.ELDER_GUARDIAN,
            EntityType.PILLAGER,EntityType.RAVAGER,EntityType.BOGGED,EntityType.ZOMBIE_VILLAGER);
        Collections.addAll(SPAWN_MOB_LIST,EntityType.SKELETON,EntityType.DROWNED, EntityType.BOGGED,
                EntityType.HUSK,EntityType.STRAY,EntityType.PILLAGER,EntityType.VINDICATOR,EntityType.EVOKER);
    }

    //NBT标签[是否为自定义怪物]
    public static final String CUSTOM_MOB = "custom_mob";
    public static final NamespacedKey CUSTOM_MOB_KEY = new NamespacedKey("legendary_path", CUSTOM_MOB);
    //NBT标签[怪物等级]
    public static final String MOB_LEVEL = "mob_level";
    public static final NamespacedKey MOB_LEVEL_KEY = new NamespacedKey("legendary_path", MOB_LEVEL);
}
