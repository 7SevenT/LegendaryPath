package com.sevent.mob;

import com.sevent.util.RandomGet;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class MobEquipmentItem {
    private static final Set<Material> SUPER_SWORD = Set.of(Material.DIAMOND_SWORD,Material.NETHERITE_SWORD, Material.DIAMOND_AXE,Material.NETHERITE_AXE);
    private static final Set<Material> SUPER_HELMET = Set.of(Material.DIAMOND_HELMET,Material.NETHERITE_HELMET);
    private static final Set<Material> SUPER_CHESTPLATE = Set.of(Material.DIAMOND_CHESTPLATE,Material.NETHERITE_CHESTPLATE);
    private static final Set<Material> SUPER_LEGGINGS = Set.of(Material.DIAMOND_LEGGINGS,Material.NETHERITE_LEGGINGS);
    private static final Set<Material> SUPER_BOOTS = Set.of(Material.DIAMOND_BOOTS,Material.NETHERITE_BOOTS);
    // 获取剑
    public static ItemStack getSword(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String body = RandomGet.getElementOfList(MobConstants.EQUIPMENT_BODY_LIST);
            String suffix = RandomGet.getElementOfList(MobConstants.EQUIPMENT_SUFFIX_LIST);
            meta.setDisplayName(body + suffix);
            if (SUPER_SWORD.contains(material)){
                meta.addEnchant(Enchantment.SHARPNESS, 3, true);
            }else {
                meta.addEnchant(Enchantment.SHARPNESS, 2, true);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    // 获取头盔
    public static ItemStack getHelmet(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String body = RandomGet.getElementOfList(MobConstants.ARMOR_PREFIX_LIST);
            String suffix = "盔";
            meta.setDisplayName(body + suffix);
            if (SUPER_HELMET.contains(material)){
                meta.addEnchant(Enchantment.PROTECTION, 3, true);
            }else {
                meta.addEnchant(Enchantment.PROTECTION, 2, true);
            }
            item.setItemMeta(meta);
        }
        return item;
    }
    // 获取胸甲
    public static ItemStack getChestplate(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String body = RandomGet.getElementOfList(MobConstants.ARMOR_PREFIX_LIST);
            String suffix = "胸甲";
            meta.setDisplayName(body + suffix);
            if (SUPER_CHESTPLATE.contains(material)){
                meta.addEnchant(Enchantment.PROTECTION, 3, true);
            }else {
                meta.addEnchant(Enchantment.PROTECTION, 2, true);
            }
            item.setItemMeta(meta);
        }
        return item;
    }
    // 获取护腿
    public static ItemStack getLeggings(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String body = RandomGet.getElementOfList(MobConstants.ARMOR_PREFIX_LIST);
            String suffix = "护腿";
            meta.setDisplayName(body + suffix);
            if (SUPER_LEGGINGS.contains(material)){
                meta.addEnchant(Enchantment.PROTECTION, 3, true);
            }else {
                meta.addEnchant(Enchantment.PROTECTION, 2, true);
            }
            item.setItemMeta(meta);
        }
        return item;
    }
    // 获取鞋子
    public static ItemStack getBoots(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String body = RandomGet.getElementOfList(MobConstants.ARMOR_PREFIX_LIST);
            String suffix = "靴";
            meta.setDisplayName(body + suffix);
            if (SUPER_BOOTS.contains(material)) {
                meta.addEnchant(Enchantment.PROTECTION, 3, true);
            } else {
                meta.addEnchant(Enchantment.PROTECTION, 2, true);
            }
            item.setItemMeta(meta);
        }
        return item;
    }
}
