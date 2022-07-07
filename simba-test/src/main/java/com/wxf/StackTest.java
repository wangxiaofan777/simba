package com.wxf;

import java.util.Stack;

/**
 * @author WangMaoSong
 * @date 2022/7/7 20:44
 */
public class StackTest {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }
        System.out.println(stack);

        for (int i = 0; i < 10; i++) {
            System.out.println(stack.pop());
        }
    }
}
