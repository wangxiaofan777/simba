package com.wxf;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author WangMaoSong
 * @date 2022/7/22 14:14
 */
public class TreeMapTest {

    public static void main(String[] args) {
        Map<String, String> treeMap = new TreeMap<>();
        for (int i = 10; i > 0; i--) {
            treeMap.put(i + "", i + "");
        }

        System.out.println(treeMap);
    }
}
