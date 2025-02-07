package com.sevent.mob;

import com.sevent.exchange.ExchangeItem;
import com.sevent.quench.QuenchConstants;
import com.sevent.quench.QuenchItem;
import org.bukkit.inventory.ItemStack;

public enum MobDifficulty {
    ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE;

    // 获取武器掉落概率
    public double getWeaponDropRate() {
        return switch (this) {
            case ONE, TWO -> 0.3;
            case THREE, FOUR, FIVE -> 0.2;
            case SIX, SEVEN, EIGHT -> 0.1;
            case NINE -> 0.05;
        };
    }
    // 获取装备掉落概率
    public double getArmorDropRate() {
        return switch (this) {
            case ONE, TWO -> 0.3;
            case THREE, FOUR, FIVE -> 0.2;
            case SIX, SEVEN, EIGHT -> 0.1;
            case NINE -> 0.03;
        };
    }
    // 获取鉴定书掉落概率
    public double getIdentifyBookDropRate() {
        return switch (this) {
            case ONE, TWO -> 0.2;
            case THREE, FOUR, FIVE -> 0.3;
            case SIX, SEVEN, EIGHT -> 0.4;
            case NINE -> 0.5;
        };
    }
    // 获取 Soul Fragments 掉落质量
    public ItemStack getSoulFragments() {
        return switch (this) {
            case ONE, TWO -> ExchangeItem.getExchangeItem(ExchangeItem.EXCHANGE_LEVEL_ONE,1);
            case THREE -> ExchangeItem.getExchangeItem(ExchangeItem.EXCHANGE_LEVEL_TWO,1);
            case FOUR, FIVE -> ExchangeItem.getExchangeItem(ExchangeItem.EXCHANGE_LEVEL_TWO,2);
            case SIX -> ExchangeItem.getExchangeItem(ExchangeItem.EXCHANGE_LEVEL_THREE,1);
            case SEVEN, EIGHT -> ExchangeItem.getExchangeItem(ExchangeItem.EXCHANGE_LEVEL_THREE,2);
            case NINE -> ExchangeItem.getExchangeItem(ExchangeItem.EXCHANGE_LEVEL_FOUR,1);
        };
    }
    // 获取淬炼石掉落质量及概率
    public ItemStack getQuenchStone() {
        return switch (this) {
            case ONE, TWO -> QuenchItem.getQuenchStone(QuenchConstants.QUENCH_STONE_FAN);
            case THREE, FOUR, FIVE -> QuenchItem.getQuenchStone(QuenchConstants.QUENCH_STONE_HUANG);
            case SIX, SEVEN, EIGHT -> QuenchItem.getQuenchStone(QuenchConstants.QUENCH_STONE_DI);
            case NINE -> QuenchItem.getQuenchStone(QuenchConstants.QUENCH_STONE_TIAN);
        };
    }
    public double getQuenchStoneDropRate() {
        return switch (this) {
            case ONE, TWO -> 0.6;
            case THREE, FOUR, FIVE -> 0.5;
            case SIX, SEVEN, EIGHT -> 0.3;
            case NINE -> 0.2;
        };
    }
}
