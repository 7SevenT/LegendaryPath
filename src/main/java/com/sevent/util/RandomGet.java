package com.sevent.util;

import java.util.List;
import java.util.Random;

public class RandomGet {
    private static Random random = new Random();

    //1.获取随机颜色
    public static String getColor() {
        String[] colors = {
                "§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9", "§a", "§b", "§c", "§d", "§e", "§f"
        };
        int index = random.nextInt(colors.length);
        return colors[index];
    }

    /*
     * @description:获取列表中的随机一个元素
     * @param: [list] 传入List列表
     * @return: 列表中的一个随机元素
     **/
    public static <T> T getElementOfList(List<T> list) {
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
