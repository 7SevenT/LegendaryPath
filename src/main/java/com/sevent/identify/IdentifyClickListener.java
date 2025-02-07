package com.sevent.identify;

import com.sevent.LegendaryPath;
import com.sevent.inlay.InlayConstants;
import com.sevent.mob.MobConstants;
import com.sevent.quench.QuenchConstants;
import com.sevent.util.AnimationUtils;
import com.sevent.util.Constants;
import com.sevent.util.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class IdentifyClickListener implements Listener {
    public static LegendaryPath plugin;
    private static final Set<Inventory> playingAnimation = new HashSet<>();
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // 检查是否为鉴定系统的GUI,不是则不处理
        String title = event.getView().getTitle();
        if (!title.equals(IdentifyConstants.IDENTIFY_GUI_TITLE)) return;
        // 检查点击的是否为该GUI中格子,不是则不处理
        int slot = event.getRawSlot();
        if (slot >=IdentifyConstants.INVENTORY_SIZE) return;
        // 为鉴定GUI,则取消事件的默认行为
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        // 特使的格子处理
        switch (slot){
            case IdentifyConstants.MIDDLE_SLOT, IdentifyConstants.IDENTIFYBOOK_SLOT:
                // 允许玩家在中间格子与鉴定书格子放物品
                if (!playingAnimation.contains(inventory)) event.setCancelled(false);
                break;
            case IdentifyConstants.CONFIRM_SLOT:
                // 处理确认按钮点击事件
                confirmClicked(player, inventory);
                break;
            case IdentifyConstants.CANCEL_SLOT:
                // 处理取消按钮点击事件
                player.sendMessage("§6[鉴定系统]操作已取消！");
                player.closeInventory();
                break;
            default:
                player.sendMessage("§6[鉴定系统]请勿在鉴定系统内进行其他操作！");
        }
    }

    private void confirmClicked(Player player, Inventory inventory) {
        //1.判断物品格子
        ItemStack middleSlotItem = inventory.getItem(IdentifyConstants.MIDDLE_SLOT);
        // a.判断中间格子是否有物品
        if (middleSlotItem == null || middleSlotItem.getType() == Material.AIR) {
            player.sendMessage("§6[鉴定系统]请先放置一个物品！");
            return;
        }
        // b.判断中间格子物品是否可鉴定
        ItemMeta midMeta = middleSlotItem.getItemMeta();
        if (midMeta == null || !midMeta.getPersistentDataContainer().has(IdentifyConstants.IDENTIFIABLE, PersistentDataType.INTEGER)) {
            player.sendMessage("§6[鉴定系统]你选择了: " + middleSlotItem.getType().name());
            player.sendMessage("§6[鉴定系统]该物品不可鉴定！");
            return;
        }
        // c.获取物品的鉴定值
        Integer identifiableValue = midMeta.getPersistentDataContainer().get(IdentifyConstants.IDENTIFIABLE, PersistentDataType.INTEGER);
        if (identifiableValue == null) {
            player.sendMessage("§6[鉴定系统]该物品不可鉴定！");
            return;
        }

        //2.判断鉴定书格子
        ItemStack identifyBookItem = inventory.getItem(IdentifyConstants.IDENTIFYBOOK_SLOT);
        // a.判断鉴定书格子是否有物品
        if (identifyBookItem == null || identifyBookItem.getType()== Material.AIR) {
            player.sendMessage("§6[鉴定系统]请放置鉴定书！");
            return;
        }
        // b.判断鉴定书格子物品是否为鉴定书
        ItemMeta bookMeta = identifyBookItem.getItemMeta();
        if (bookMeta == null || !bookMeta.getPersistentDataContainer().has(IdentifyConstants.IDENTIFYBOOK, PersistentDataType.BOOLEAN)) {
            player.sendMessage("§6[鉴定系统]请放置鉴定书！");
            return;
        }
        // c.不用判断鉴定值,因为鉴定书是消耗品

        // 获取物品名称
        String itemName = midMeta.hasDisplayName() ? midMeta.getDisplayName() : middleSlotItem.getType().name();
        player.sendMessage("§6[鉴定系统]你选择了: " + itemName);
        // 判断是否为已鉴定
        switch (identifiableValue) {
            case IdentifyConstants.IDENTIFIABLE_VALUE -> {
                cutAnimation(player, inventory, middleSlotItem);
                player.sendMessage("§6[鉴定系统]鉴定成功！");
            }
            case IdentifyConstants.NOT_IDENTIFIABLE_VALUE -> player.sendMessage("§6[鉴定系统]该物品已被鉴定！");
            default -> player.sendMessage("§6[鉴定系统]该物品不可鉴定！");
        }
    }

    private void cutAnimation(Player player,Inventory inventory, ItemStack identifiedItem) {
        // 设置不可移动
        playingAnimation.add(inventory);
        // 播放鉴定音效，2次铁砧使用音效，1次升级音效使用
        AnimationUtils.playProcessSound(player);
        // 播放鉴定动画
        AnimationUtils.playAnimation(inventory, IdentifyConstants.MIDDLE_SLOT, () -> identify(inventory, identifiedItem));
    }

    private void identify(Inventory inventory, ItemStack needIdentifiedItem) {
        // 清空中间格子
        inventory.clear(IdentifyConstants.MIDDLE_SLOT);
        // 鉴定书格子处理
        ItemStack bookItem = inventory.getItem(IdentifyConstants.IDENTIFYBOOK_SLOT);
        int bookAmount = bookItem.getAmount();
        inventory.clear(IdentifyConstants.IDENTIFYBOOK_SLOT);
        if (bookAmount > 1) {
            bookItem.setAmount(bookAmount-1);
            inventory.setItem(IdentifyConstants.IDENTIFYBOOK_SLOT, bookItem);
        }
        // 设置可移动
        playingAnimation.remove(inventory);
        // 根据装备等级随机添加效果
        ItemStack identifiedItem = null;


        Material type = needIdentifiedItem.getType();
        if (IdentifyConstants.SWORD_LIST.contains(type)){
            identifiedItem = identifySword(needIdentifiedItem);
        } else if (IdentifyConstants.AXE_LIST.contains(type)){
            identifiedItem = identifyAxe(needIdentifiedItem);
        } else if (Material.TRIDENT.equals(type)) {
            identifiedItem = identifyTrident(needIdentifiedItem);
        } else {
            identifiedItem = identifyArmor(needIdentifiedItem);
        }
        // 将鉴定完的物品添加到中间格子
        inventory.setItem(IdentifyConstants.MIDDLE_SLOT, identifiedItem);
    }

    // 鉴定剑
    private ItemStack identifySword(ItemStack identifiedItem) {
        ItemMeta itemMeta = identifiedItem.getItemMeta();
        if (itemMeta == null) return null;
        // 1.设置基础数据
        itemMeta = setBaseData(itemMeta);
        // 2.设置随机属性
        Random random = new Random();
        Integer quality = itemMeta.getPersistentDataContainer().get(IdentifyConstants.ITEM_QUALITY, PersistentDataType.INTEGER);
        // a.攻击力
        int baseAttackDamage = IdentifyUtil.getBaseAttackDamage(identifiedItem.getType());
        int attackDamageValue = baseAttackDamage + random.nextInt(5+quality*2)- (2 + quality*2/3);
        AttributeModifier attackDamageModifier = new AttributeModifier(Constants.attackDamageNamespacedKey,attackDamageValue,Operation.ADD_NUMBER,EquipmentSlotGroup.MAINHAND);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);
        // b.攻速
        double baseAttackSpeed = IdentifyUtil.getBaseAttackSpeed(identifiedItem.getType());
        double attackSpeedValue = baseAttackSpeed + random.nextDouble(0.4+quality/10.0) - (0.2 + quality/20.0);
        AttributeModifier attackSpeedModifier = new AttributeModifier(Constants.attackSpeedNamespacedKey,attackSpeedValue,Operation.ADD_NUMBER,EquipmentSlotGroup.MAINHAND);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);
        //c.附魔
        itemMeta.addEnchant(Enchantment.SHARPNESS,random.nextInt(3+quality/2)+1,true); //锋利 1-7
        itemMeta.addEnchant(Enchantment.UNBREAKING,random.nextInt(3)+1,true); //耐久 1-3
        itemMeta.addEnchant(Enchantment.SWEEPING_EDGE,random.nextInt(3+quality/3)+1,true);//横扫 1-5
        itemMeta.addEnchant(Enchantment.SMITE,random.nextInt(3+quality/3)+1,true);//亡灵杀手 1-6
        // 3.将属性添加到物品元数据
        identifiedItem.setItemMeta(itemMeta);
        return identifiedItem;
    }
    // 鉴定斧头
    private ItemStack identifyAxe(ItemStack identifiedItem) {
        ItemMeta itemMeta = identifiedItem.getItemMeta();
        if (itemMeta == null) return null;
        // 1.设置基础数据
        itemMeta = setBaseData(itemMeta);
        // 2.设置随机属性
        Random random = new Random();
        Integer quality = itemMeta.getPersistentDataContainer().get(IdentifyConstants.ITEM_QUALITY, PersistentDataType.INTEGER);
        // a.攻击力
        int baseAttackDamage = IdentifyUtil.getBaseAttackDamage(identifiedItem.getType());
        int attackDamageValue = baseAttackDamage + random.nextInt(8+quality*2)- (2 + quality*2/3);
        AttributeModifier attackDamageModifier = new AttributeModifier(Constants.attackDamageNamespacedKey,attackDamageValue,Operation.ADD_NUMBER,EquipmentSlotGroup.MAINHAND);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);
        // b.攻速
        double baseAttackSpeed = IdentifyUtil.getBaseAttackSpeed(identifiedItem.getType());
        double attackSpeedValue = baseAttackSpeed + random.nextDouble(0.2+quality/10.0) - (quality/20.0);
        AttributeModifier attackSpeedModifier = new AttributeModifier(Constants.attackSpeedNamespacedKey,attackSpeedValue,Operation.ADD_NUMBER,EquipmentSlotGroup.MAINHAND);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);
        //c.附魔
        itemMeta.addEnchant(Enchantment.SHARPNESS,random.nextInt(3+quality/2)+1,true); //锋利 1-7
        itemMeta.addEnchant(Enchantment.UNBREAKING,random.nextInt(3)+1,true); //耐久 1-3
        itemMeta.addEnchant(Enchantment.SMITE,random.nextInt(3+quality/3)+1,true);//亡灵杀手 1-6
        // 3.将属性添加到物品元数据
        identifiedItem.setItemMeta(itemMeta);
        return identifiedItem;
    }
    // 鉴定斧头
    private ItemStack identifyTrident(ItemStack identifiedItem) {
        ItemMeta itemMeta = identifiedItem.getItemMeta();
        if (itemMeta == null) return null;
        // 1.设置基础数据
        itemMeta = setBaseData(itemMeta);
        // 2.设置随机属性
        Random random = new Random();
        Integer quality = itemMeta.getPersistentDataContainer().get(IdentifyConstants.ITEM_QUALITY, PersistentDataType.INTEGER);
        // a.攻击力
        int baseAttackDamage = IdentifyUtil.getBaseAttackDamage(identifiedItem.getType());
        int attackDamageValue = baseAttackDamage + random.nextInt(5+quality*2)- (2 + quality*2/3);
        AttributeModifier attackDamageModifier = new AttributeModifier(Constants.attackDamageNamespacedKey,attackDamageValue,Operation.ADD_NUMBER,EquipmentSlotGroup.MAINHAND);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamageModifier);
        // b.攻速
        double baseAttackSpeed = IdentifyUtil.getBaseAttackSpeed(identifiedItem.getType());
        double attackSpeedValue = baseAttackSpeed + random.nextDouble(0.4+quality/10.0) - (0.2+quality/20.0);
        AttributeModifier attackSpeedModifier = new AttributeModifier(Constants.attackSpeedNamespacedKey,attackSpeedValue,Operation.ADD_NUMBER,EquipmentSlotGroup.MAINHAND);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeedModifier);
        //c.附魔
        itemMeta.addEnchant(Enchantment.LOYALTY,1,true); //忠诚
        itemMeta.addEnchant(Enchantment.UNBREAKING,random.nextInt(3)+1,true); //耐久 1-3
        itemMeta.addEnchant(Enchantment.IMPALING,1,true);//穿刺
        // 3.将属性添加到物品元数据
        identifiedItem.setItemMeta(itemMeta);
        return identifiedItem;
    }
    // 鉴定头盔
    private ItemStack identifyArmor(ItemStack identifiedItem){
        ItemMeta itemMeta = identifiedItem.getItemMeta();
        if (itemMeta == null) return null;
        // 1.设置基础数据
        itemMeta = setBaseData(itemMeta);
        // 2.设置随机属性
        Random random = new Random();
        Integer quality = itemMeta.getPersistentDataContainer().get(IdentifyConstants.ITEM_QUALITY, PersistentDataType.INTEGER);
        // a.护甲值
        int baseArmor = IdentifyUtil.getBaseArmor(identifiedItem.getType());
        int armorValue = baseArmor + random.nextInt(5+quality*2) - (2 + quality*2/3);
        AttributeModifier armorModifier = new AttributeModifier(Constants.armorNamespacedKey,armorValue,Operation.ADD_NUMBER,EquipmentSlotGroup.ARMOR);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
        // b.护甲韧性
        int baseArmorToughness = IdentifyUtil.getBaseArmorToughness(identifiedItem.getType());
        int armorToughnessValue = baseArmorToughness + random.nextInt(2+quality*2/3);
        AttributeModifier armorToughnessModifier = new AttributeModifier(Constants.armorToughnessNamespacedKey,armorToughnessValue,Operation.ADD_NUMBER,EquipmentSlotGroup.ARMOR);
        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, armorToughnessModifier);
        // c.护甲抵抗
        double baseArmorResistance = IdentifyUtil.getBaseArmorResistance(identifiedItem.getType());
        double armorResistanceValue = baseArmorResistance + random.nextDouble(0.1+quality/30.0);
        AttributeModifier armorResistanceModifier = new AttributeModifier(Constants.armorResistanceNamespacedKey,armorResistanceValue,Operation.ADD_NUMBER,EquipmentSlotGroup.ARMOR);
        itemMeta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, armorResistanceModifier);
        //d.附魔
        itemMeta.addEnchant(Enchantment.PROTECTION,random.nextInt(2+quality/3)+4,true); //保护 4-8
        itemMeta.addEnchant(Enchantment.UNBREAKING,random.nextInt(2)+2,true); //耐久 2-3
        itemMeta.addEnchant(Enchantment.BLAST_PROTECTION,random.nextInt(3+quality/3),true);//爆炸保护 0-4
        itemMeta.addEnchant(Enchantment.FIRE_PROTECTION,random.nextInt(3+quality/3),true);//亡灵杀手 0-4
        // 3.将属性添加到物品元数据
        identifiedItem.setItemMeta(itemMeta);
        return identifiedItem;
    }

    // 设置基础数据及可淬炼可镶嵌
    private ItemMeta setBaseData(ItemMeta itemMeta){
        // 1.设置基础数据
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("§b[未鉴定]",  ""));
        itemMeta.getPersistentDataContainer().set(IdentifyConstants.IDENTIFIABLE, PersistentDataType.INTEGER,IdentifyConstants.NOT_IDENTIFIABLE_VALUE);
        Integer quality = itemMeta.getPersistentDataContainer().get(IdentifyConstants.ITEM_QUALITY, PersistentDataType.INTEGER);
        itemMeta.setLore(List.of(IdentifyUtil.getQualityName(quality))); //
        itemMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        // 2.将装备设为可淬炼
        itemMeta.getPersistentDataContainer().set(QuenchConstants.QUENCHABLE_KEY, PersistentDataType.INTEGER,QuenchConstants.QUENCHABLE_COUNT_THREE);
        List<String> itemLore = itemMeta.getLore();
        itemLore.add("§b[可淬炼次数]:§2 3");
        // 3.将装备设为可镶嵌
        itemMeta.getPersistentDataContainer().set(InlayConstants.INLAY_COUNT_KEY, PersistentDataType.INTEGER,InlayConstants.INLAY_COUNT_THREE);
        itemLore.add(" §a◇§2可镶嵌孔位1");
        itemLore.add(" §a◇§2可镶嵌孔位2");
        itemLore.add(" §a◇§2可镶嵌孔位3");
        itemMeta.setLore(itemLore);
        return itemMeta;
    }







    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        // 检查是否为鉴定系统的GUI
        String title = event.getView().getTitle();
        if (!title.equals(IdentifyConstants.IDENTIFY_GUI_TITLE)) return;
        // 获取玩家和GUI
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        // 将中间格子与鉴定书格子的物品返回给玩家
        InventoryUtils.closeInventory(player,inventory,IdentifyConstants.MIDDLE_SLOT);
        InventoryUtils.closeInventory(player,inventory,IdentifyConstants.IDENTIFYBOOK_SLOT);
    }
}
