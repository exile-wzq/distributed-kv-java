package com.exile.kv.client;

import com.exile.kv.storage.MemoryStorage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WalTest {

    public static void main(String[] args) {
        String walPath = "appendonly.aof";

        // 删除旧 WAL，保证测试可重复
        File walFile = new File(walPath);
        if (walFile.exists()) walFile.delete();

        try {
            MemoryStorage storage = new MemoryStorage(walPath);

            // 使用固定线程池模拟 10 个客户端
            ExecutorService pool = Executors.newFixedThreadPool(10);

            for (int i = 0; i < 10; i++) {
                final int clientId = i;
                pool.submit(() -> {
                    try {
                        // 每个客户端写入 10 个键值
                        for (int j = 0; j < 10; j++) {
                            String key = "client" + clientId + "_key" + j;
                            String value = "val" + j;
                            storage.put(key, value);
                            if (j % 3 == 0) {
                                // 每隔3条删一个键
                                storage.delete(key);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            pool.shutdown();
            pool.awaitTermination(30, TimeUnit.SECONDS);

            // 打印最终内存状态
            System.out.println("\n=== 内存状态 ===");
            System.out.println("Total keys in memory: " + storage.size());
            storage.getMapSnapshot().forEach((k, v) -> System.out.println(k + " -> " + v));

            storage.close();

            // --- 模拟服务器重启 ---
            System.out.println("\n=== 模拟重启后恢复 ===");
            MemoryStorage storage2 = new MemoryStorage(walPath);
            System.out.println("Total keys after recovery: " + storage2.size());
            storage2.getMapSnapshot().forEach((k, v) -> System.out.println(k + " -> " + v));
            storage2.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}