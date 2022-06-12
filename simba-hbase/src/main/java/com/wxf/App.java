package com.wxf;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3);
        System.out.println(list.stream().count());

    }
}
