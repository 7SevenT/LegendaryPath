package com.sevent.quench;

import com.sevent.LegendaryPath;
import com.sevent.mob.MobConstants;
import com.sevent.util.AnimationUtils;
import com.sevent.util.Constants;
import com.sevent.util.InventoryUtils;
import com.sevent.identify.IdentifyConstants;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class QuenchClickListener implements Listener {
    public static LegendaryPath plugin;
    private static final Set<Inventory> playingAnimation = new HashSet<>();
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // 检查是否为鉴定系统的GUI,不是则不处理
        String title = event.getView().getTitle();
        if (!title.equals(QuenchConstants.QUENCH_GUI_TITLE)) return;
        // 检查点击的是否为该GUI中格子,不是则不处理
        int slot = event.getRawSlot();
        if (slot >=QuenchConstants.INVENTORY_SIZE) return;
        // 为鉴定GUI,则取消事件的默认行为
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        // 特使的格子处理
        switch (slot) {
            case QuenchConstants.EQUIPMENT_SLOT, QuenchConstants.QUENCH_STONE_SLOT -> {
                // 允许玩家在装备格子与淬炼石格子放物品
                if (!playingAnimation.contains(inventory)) event.setCancelled(false);
            }
            case IdentifyConstants.CONFIRM_SLOT ->
                // 处理确认按钮点击事件
                confirmClicked(player, inventory);
            case IdentifyConstants.CANCEL_SLOT -> {
                // 处理取消按钮点击事件
                player.sendMessage("§6[淬炼系统]操作已取消！");
                player.closeInventory();
            }
            default -> player.sendMessage("§6[淬炼系统]请勿在淬炼系统内进行其他操作！");
        }
    }

    private void confirmClicked(Player player, Inventory inventory) {
        //1.判断装备格子
        ItemStack equipmentSlotItem = inventory.getItem(QuenchConstants.EQUIPMENT_SLOT);
        // a.判断装备格子是否有物品
        if (InventoryUtils.isEmpty(equipmentSlotItem)) {
            player.sendMessage("§6[淬炼系统]请先放置一个物品！");
            return;
        }
        // b.判断装备格子物品是否可淬炼
        ItemMeta equipmentMeta = equipmentSlotItem.getItemMeta();
        if (equipmentMeta == null || !equipmentMeta.getPersistentDataContainer().has(QuenchConstants.QUENCHABLE_KEY)) {
            player.sendMessage("§6[淬炼系统]你选择了: " + equipmentSlotItem.getType().name());
            player.sendMessage("§6[淬炼系统]该物品不可淬炼！");
            return;
        }
        // c.获取物品的淬炼次数
        Integer quenchableCount = equipmentMeta.getPersistentDataContainer().get(QuenchConstants.QUENCHABLE_KEY, PersistentDataType.INTEGER);
        if (quenchableCount == null || quenchableCount == 0) {
            player.sendMessage("§6[淬炼系统]该物品可淬炼次数为0！");
            return;
        }

        // 2.判断淬炼石格子
        ItemStack quenchItem = inventory.getItem(QuenchConstants.QUENCH_STONE_SLOT);
        // a.判断淬炼石格子是否有物品
        if (InventoryUtils.isEmpty(quenchItem)) {
            player.sendMessage("§6[淬炼系统]请放置淬炼石！");
            return;
        }
        // b.判断淬炼石格子物品是否为鉴定书
        ItemMeta stoneMeta = quenchItem.getItemMeta();
        if (stoneMeta == null || !stoneMeta.getPersistentDataContainer().has(QuenchConstants.QUENCH_STONE_KEY)) {
            player.sendMessage("§6[淬炼系统]请放置淬炼石！");
            return;
        }
        // c.不用判断鉴定值,因为鉴定书是消耗品

        // 获取物品名称
        String itemName = equipmentMeta.hasDisplayName() ? equipmentMeta.getDisplayName() : equipmentSlotItem.getType().name();
        player.sendMessage("§6[淬炼系统]你选择了: " + itemName);
        // 开始淬炼
        cutAnimation(player, inventory, equipmentSlotItem);
    }

    private void cutAnimation(Player player,Inventory inventory, ItemStack quenchedItem) {
        // 设置不可移动
        playingAnimation.add(inventory);
        // 播放鉴定音效，2次铁砧使用音效，1次升级音效使用
        AnimationUtils.playProcessSound(player);
        // 播放鉴定动画
        AnimationUtils.playAnimation(inventory, QuenchConstants.EQUIPMENT_SLOT, () -> quench(player, inventory, quenchedItem));
    }

    private void quench(Player player,Inventory inventory, ItemStack quenchedItem) {
        // 清空装备格子
        inventory.clear(QuenchConstants.EQUIPMENT_SLOT);
        // 淬炼石格子处理
        ItemStack quenchStoneItem = inventory.getItem(QuenchConstants.QUENCH_STONE_SLOT);
        int quenchStoneAmount = quenchStoneItem.getAmount();
        inventory.clear(QuenchConstants.QUENCH_STONE_SLOT);
        if (quenchStoneAmount > 1) {
            quenchStoneItem.setAmount(quenchStoneAmount-1);
            inventory.setItem(QuenchConstants.QUENCH_STONE_SLOT, quenchStoneItem);
        }
        // 设置可移动
        playingAnimation.remove(inventory);
        // 根据装备等级随机添加效果
        if (MobConstants.WEAPON_LIST.contains(quenchedItem.getType()) || quenchedItem.getType().equals(Material.TRIDENT)){
            quenchedItem = quenchWeapon(quenchedItem,quenchStoneItem,player);
        }else {
            quenchedItem = quenchArmor(quenchedItem,quenchStoneItem,player);
        }

        // 将淬炼完的物品添加到装备格子
        inventory.setItem(QuenchConstants.EQUIPMENT_SLOT, quenchedItem);
    }

    private ItemStack quenchWeapon (ItemStack quenchedItem,ItemStack quenchStoneItem,Player player) {
        ItemMeta itemMeta = quenchedItem.getItemMeta();
        if (itemMeta != null) {
            // 1.获取装备的属性
            // a.获取攻击伤害
            AttributeModifier attackDamageModifier = null;
            for (AttributeModifier modifier : itemMeta.getAttributeModifiers(Attribute.GENERIC_ATTACK_DAMAGE)) {
                if (modifier.getKey().equals(Constants.attackDamageNamespacedKey)) { // 替换为你的实际NamespacedKey
                    attackDamageModifier = modifier;
                }
            }
            if (attackDamageModifier == null) return null;
            // b.获取攻击速度
            AttributeModifier attackSpeedModifier = null;
            for (AttributeModifier modifier : itemMeta.getAttributeModifiers(Attribute.GENERIC_ATTACK_SPEED)) {
                if (modifier.getKey().equals(Constants.attackSpeedNamespacedKey)) {
                    attackSpeedModifier = modifier;
                }
            }
            if (attackSpeedModifier == null) return null;
            // 2.概率增加属性
            double startAttackDamage = attackDamageModifier.getAmount();
            double endAttackDamage = startAttackDamage;
            double startAttackSpeed = attackSpeedModifier.getAmount();
            double endAttackSpeed = startAttackSpeed;
            Random random = new Random();
            if (Math.random() < 0.3){
                Integer quenchStoneLevel = quenchStoneItem.getItemMeta().getPersistentDataContainer().get(QuenchConstants.QUENCH_STONE_KEY, PersistentDataType.INTEGER);
                switch (quenchStoneLevel){
                    case QuenchConstants.QUENCH_STONE_FAN -> {
                        endAttackDamage = startAttackDamage + random.nextInt(3)+1;
                        endAttackSpeed = startAttackSpeed + random.nextDouble(0.05)+0.01;
                    }
                    case QuenchConstants.QUENCH_STONE_HUANG -> {
                        endAttackDamage = startAttackDamage + random.nextInt(5)+3;
                        endAttackSpeed = startAttackSpeed + random.nextDouble(0.1)+0.05;
                    }
                    case QuenchConstants.QUENCH_STONE_DI -> {
                        endAttackDamage = startAttackDamage + random.nextInt(7)+5;
                        endAttackSpeed = startAttackSpeed + random.nextDouble(0.15)+0.1;
                    }
                    case QuenchConstants.QUENCH_STONE_TIAN -> {
                        endAttackDamage = startAttackDamage + random.nextInt(9)+7;
                        endAttackSpeed = startAttackSpeed + random.nextDouble(0.2)+0.15;
                    }
                    default -> {
                        endAttackDamage = startAttackDamage;
                        endAttackSpeed = startAttackSpeed;
                    }
                }
                player.sendMessage("§6[淬炼系统]淬炼成功！");
            }else player.sendMessage("§6[淬炼系统]淬炼失败！");

            //3.移除原有并新增属性修改器
            // a.攻击伤害
            itemMeta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,attackDamageModifier);
            AttributeModifier newAttackDamageModifier = new AttributeModifier(Constants.attackDamageNamespacedKey, endAttackDamage, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,newAttackDamageModifier);
            // b.攻击速度
            itemMeta.removeAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,attackSpeedModifier);
            AttributeModifier newAttackSpeedModifier = new AttributeModifier(Constants.attackSpeedNamespacedKey, endAttackSpeed, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
            itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,newAttackSpeedModifier);

            // 4.减少可淬炼次数
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            Integer quenchableCount = persistentDataContainer.get(QuenchConstants.QUENCHABLE_KEY, PersistentDataType.INTEGER) - 1;
            persistentDataContainer.set(QuenchConstants.QUENCHABLE_KEY, PersistentDataType.INTEGER, quenchableCount);
            List<String> itemLore = itemMeta.getLore();
            itemLore.set(1, "§b[淬炼次数]:§2 " + quenchableCount);
            itemMeta.setLore(itemLore);

            // 5.将属性添加到物品元数据
            quenchedItem.setItemMeta(itemMeta);

        }
        return quenchedItem;
    }
    private ItemStack quenchArmor (ItemStack quenchedItem,ItemStack quenchStoneItem,Player player){
        ItemMeta itemMeta = quenchedItem.getItemMeta();
        if (itemMeta != null) {
            // 1.获取装备的属性
            // a.获取护甲值
            AttributeModifier armorModifier = null;
            for (AttributeModifier modifier : itemMeta.getAttributeModifiers(Attribute.GENERIC_ARMOR)) {
                if (modifier.getKey().equals(Constants.armorNamespacedKey)) { // 替换为你的实际NamespacedKey
                    armorModifier = modifier;
                }
            }
            if (armorModifier == null) return null;
            // b.获取护甲韧性
            AttributeModifier armorToughnessModifier = null;
            for (AttributeModifier modifier : itemMeta.getAttributeModifiers(Attribute.GENERIC_ARMOR_TOUGHNESS)) {
                if (modifier.getKey().equals(Constants.armorToughnessNamespacedKey)) {
                    armorToughnessModifier = modifier;
                }
            }
            if (armorToughnessModifier == null) return null;
            // b.获取护甲抵抗
            AttributeModifier armorResistanceModifier = null;
            for (AttributeModifier modifier : itemMeta.getAttributeModifiers(Attribute.GENERIC_KNOCKBACK_RESISTANCE)) {
                if (modifier.getKey().equals(Constants.armorResistanceNamespacedKey)) {
                    armorResistanceModifier = modifier;
                }
            }
            if (armorResistanceModifier == null) return null;
            // 2.概率增加属性
            double startArmor = armorModifier.getAmount();
            double endArmor = startArmor;
            double startArmorToughness = armorToughnessModifier.getAmount();
            double endArmorToughness = startArmorToughness;
            double startArmorResistance = armorResistanceModifier.getAmount();
            double endArmorResistance = startArmorResistance;
            Random random = new Random();
            if (Math.random() < 0.4){
                Integer quenchStoneLevel = quenchStoneItem.getItemMeta().getPersistentDataContainer().get(QuenchConstants.QUENCH_STONE_KEY, PersistentDataType.INTEGER);
                switch (quenchStoneLevel){
                    case QuenchConstants.QUENCH_STONE_FAN -> {
                        endArmor = startArmor + random.nextInt(3)+1;
                        endArmorToughness = startArmorToughness + random.nextInt(2)+1;
                        endArmorResistance = startArmorResistance + random.nextDouble(0.1);
                    }
                    case QuenchConstants.QUENCH_STONE_HUANG -> {
                        endArmor = startArmor + random.nextInt(5)+2;
                        endArmorToughness = startArmorToughness + random.nextInt(3)+2;
                        endArmorResistance = startArmorResistance + random.nextDouble(0.3)+0.2;
                    }
                    case QuenchConstants.QUENCH_STONE_DI -> {
                        endArmor = startArmor + random.nextInt(7)+4;
                        endArmorToughness = startArmorToughness + random.nextInt(4)+3;
                        endArmorResistance = startArmorResistance + random.nextDouble(0.4)+0.3;
                    }
                    case QuenchConstants.QUENCH_STONE_TIAN -> {
                        endArmor = startArmor + random.nextInt(9)+6;
                        endArmorToughness = startArmorToughness + random.nextInt(5)+4;
                        endArmorResistance = startArmorResistance + random.nextDouble(0.5)+0.4;
                    }
                    default -> {
                        endArmor = startArmor;
                        endArmorToughness = startArmorToughness;
                        endArmorResistance = startArmorResistance;
                    }
                }
                player.sendMessage("§6[淬炼系统]淬炼成功！");
            }else player.sendMessage("§6[淬炼系统]淬炼失败！");

            //3.移除原有并新增属性修改器
            // a.护甲值
            itemMeta.removeAttributeModifier(Attribute.GENERIC_ARMOR,armorModifier);
            AttributeModifier newArmorModifier = new AttributeModifier(Constants.armorNamespacedKey, endArmor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ARMOR);
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR,newArmorModifier);
            // b.护甲韧性
            itemMeta.removeAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,armorToughnessModifier);
            AttributeModifier newArmorToughnessModifier = new AttributeModifier(Constants.armorToughnessNamespacedKey, endArmorToughness, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ARMOR);
            itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,newArmorToughnessModifier);
            // c.护甲抵抗
            itemMeta.removeAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,armorResistanceModifier);
            AttributeModifier newArmorResistanceModifier = new AttributeModifier(Constants.armorResistanceNamespacedKey, endArmorResistance, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.ARMOR);
            itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,newArmorResistanceModifier);

            // 4.减少可淬炼次数
            PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
            Integer quenchableCount = persistentDataContainer.get(QuenchConstants.QUENCHABLE_KEY, PersistentDataType.INTEGER) - 1;
            persistentDataContainer.set(QuenchConstants.QUENCHABLE_KEY, PersistentDataType.INTEGER, quenchableCount);
            List<String> itemLore = itemMeta.getLore();
            itemLore.set(1, "§b[淬炼次数]:§2 " + quenchableCount);
            itemMeta.setLore(itemLore);

            // 5.将属性添加到物品元数据
            quenchedItem.setItemMeta(itemMeta);

        }
        return quenchedItem;
    }







    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // 检查是否为鉴定系统的GUI
        String title = event.getView().getTitle();
        if (!title.equals(QuenchConstants.QUENCH_GUI_TITLE)) return;
        // 获取玩家和GUI
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        // 将中间格子与鉴定书格子的物品返回给玩家
        InventoryUtils.closeInventory(player, inventory, QuenchConstants.EQUIPMENT_SLOT);
        InventoryUtils.closeInventory(player, inventory, QuenchConstants.QUENCH_STONE_SLOT);
    }
}
