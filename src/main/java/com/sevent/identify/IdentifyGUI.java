package com.sevent.identify;

import com.sevent.util.CustomItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class IdentifyGUI {
    public void openCustomGUI(Player player) {
        Inventory inventory = Bukkit.createInventory(null, IdentifyConstants.INVENTORY_SIZE, IdentifyConstants.IDENTIFY_GUI_TITLE);
        // 填充背景物品
        fillBackground(inventory);
        // 添加确认和取消按钮
        addConfirmAndCancelButton(inventory);
        // 打开GUI给玩家
        player.openInventory(inventory);
    }

    private void fillBackground(Inventory inventory) {
        // 创建背景物品
        ItemStack backgroundItem = CustomItemUtils.getFillItem();
        // 填充背景
        for (int i = 0; i < IdentifyConstants.INVENTORY_SIZE; i++) {
            if (i != IdentifyConstants.MIDDLE_SLOT && i != IdentifyConstants.CONFIRM_SLOT && i != IdentifyConstants.CANCEL_SLOT && i!=IdentifyConstants.IDENTIFYBOOK_SLOT) {
                inventory.setItem(i, backgroundItem);
            }
        }
    }

    private void addConfirmAndCancelButton(Inventory inventory) {
        // 确认按钮物品制作
        ItemStack confirmButton = CustomItemUtils.getConfirmItem();
        // 取消按钮物品制作
        ItemStack cancelButton = CustomItemUtils.getCancelItem();
        // 将按钮添加到GUI
        inventory.setItem(IdentifyConstants.CONFIRM_SLOT, confirmButton);
        inventory.setItem(IdentifyConstants.CANCEL_SLOT, cancelButton);
    }
}
