package com.wxf;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Objects;

/**
 * Hello world!
 */
public class WatchServiceApp {

    private static final String LISTENING_PATH = "D:/tmp/watch_service";

    public static void main(String[] args) {
        WatchService watchService = null;
        try {
            // 1. 获取文件系统监听器
            watchService = FileSystems.getDefault().newWatchService();

            // 2.注册监听事件，增删改
            Paths.get(LISTENING_PATH).register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                WatchKey watchKey = watchService.take();
                System.out.println("========================= START ==========================");

                if (!watchKey.isValid()) {
                    continue;
                }

                watchKey.pollEvents().forEach(watchEvent -> {
                    WatchEvent.Kind<?> kind = watchEvent.kind();

                    Object context = watchEvent.context();
                    if (StandardWatchEventKinds.ENTRY_CREATE.equals(kind)) {
                        System.out.println("创建文件: " + context);
                    } else if (StandardWatchEventKinds.ENTRY_DELETE.equals(kind)) {
                        System.out.println("删除文件：" + context);
                    } else if (StandardWatchEventKinds.ENTRY_MODIFY.equals(kind)) {
                        System.out.println("修改文件：" + context);
                    } else {
                        System.out.println("未找到对应的操作");
                    }
                });

                //
                watchKey.reset();

                System.out.println("========================= END ==========================");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(watchService)) {
                    watchService.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
