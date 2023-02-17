package com.wxf;

import java.util.function.Consumer;

public class ConsumerTest<T> {

    public static void main(String[] args) {
        ConsumerTest<String> consumerTest = new ConsumerTest<>();

        consumerTest.test("1", new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

    }


    public void test(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }
}
