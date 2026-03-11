package com.exile.kv.storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import com.exile.kv.storage.Storage;

public class MemoryStorage implements Storage {

    private final Map<String, String> map = new ConcurrentHashMap<>();
    private final Wal wal;

    public MemoryStorage(String walPath) throws IOException {
        this.wal = new Wal(walPath);

        // 启动时恢复
        for (String line : wal.readAll()) {
            applyLog(line);
        }
    }

    private void applyLog(String line) {
        String[] parts = line.split("\\s+", 3);
        if (parts.length < 2) return;
        String cmd = parts[0].toUpperCase();
        String key = parts[1];

        switch (cmd) {
            case "PUT":
                if (parts.length < 3) return;
                String value = parts[2];
                map.put(key, value);
                break;
            case "DEL":
                map.remove(key);
                break;
        }
    }

    @Override
    public synchronized boolean put(String key, String value) {
        if (key == null || value == null) return false;
        try {
            wal.append("PUT " + key + " " + value);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        map.put(key, value);
        return true;
    }

    @Override
    public synchronized String get(String key) {
        if (key == null) return null;
        return map.get(key);
    }

    @Override
    public synchronized void delete(String key) {
        if (key == null) return;
        try {
            wal.append("DEL " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.remove(key);
    }

    @Override
    public int size()
    {
        return map.size();
    }

    public void close() throws IOException {
        wal.close();
    }
    //内存快照方法，用于打印状态测试
    public synchronized Map<String, String> getMapSnapshot(){
        return new HashMap<>(map);
    }
}