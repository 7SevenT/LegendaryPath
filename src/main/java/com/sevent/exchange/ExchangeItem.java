package com.sevent.exchange;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ExchangeItem {
    public static final String SOUL = "soul_fragments";
    public static final NamespacedKey SOUL_KEY = new NamespacedKey("legendary_path", SOUL);
    public static final int EXCHANGE_LEVEL_ONE = 1;
    public static final int EXCHANGE_LEVEL_TWO = 2;
    public static final int EXCHANGE_LEVEL_THREE = 3;
    public static final int EXCHANGE_LEVEL_FOUR = 4;

    public static ItemStack getExchangeItem(int EXCHANGE_LEVEL,int amount){
        ItemStack itemStack = new ItemStack(Material.DRAGON_BREATH);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(SOUL_KEY, org.bukkit.persistence.PersistentDataType.INTEGER, EXCHANGE_LEVEL);
        switch (EXCHANGE_LEVEL) {
            case EXCHANGE_LEVEL_ONE -> {
                itemMeta.setDisplayName("§f初级灵魂瓶");
                itemMeta.setLore(List.of("散发着微弱蓝光的", "小型晶体，似乎蕴", "含着新生生物的力",
                        "量。它是初出茅庐", "的勇士们战胜初级", "挑战后得到的第一", "份奖励，象征着勇",
                        "气与成长的开端。"));
            }
            case EXCHANGE_LEVEL_TWO -> {
                itemMeta.setDisplayName("§b中级灵魂瓶");
                itemMeta.setLore(List.of("闪烁着青色光芒的", "晶石，它比初级碎", "片更加明亮和稳定。",
                        "仿佛其中的灵魂已", "经历了多次战斗的", "洗礼。对于那些渴", "望提升自我的冒险",
                        "者而言，这是一份", "珍贵的认可。"));
            }
            case EXCHANGE_LEVEL_THREE -> {
                itemMeta.setDisplayName("§9高级灵魂瓶");
                itemMeta.setLore(List.of("深邃而神秘的紫色", "水晶，其内跳跃着", "幽灵般的火焰。只",
                        "有在击败经验丰--", "富的怪物之后才会", "出现，是实力证明", "的标志，也是通往",
                        "更强力量之路的关", "键。"));
            }
            case EXCHANGE_LEVEL_FOUR -> {
                itemMeta.setDisplayName("§5传奇灵魂瓶");
                itemMeta.setLore(List.of("一颗耀眼的红色宝", "石，几乎能感受到", "其中蕴藏的强大灵",
                        "魂之力。它是传说", "中的存在，仅由最", "英勇无畏的战士，", "在战胜最为恐怖的",
                        "敌人时获得。持有", "者将被铭记为真正", "的英雄。"));
            }
        }
        itemMeta.setEnchantmentGlintOverride(true);
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);
        return itemStack;
    }
}
