package com.exile.kv.storage;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Objects;

//先定义接口，让MemoryStorage去实现它，这样server只需要知道自己在操作一个存储器，不用关心数据是在内存还是硬盘
public class MemoryStorage implements
Storage{
    private final Map<String, String> map = new ConcurrentHashMap<>();  //这个map的线程更安全，为网络并发做准备

    @Override
    public boolean put(String key, String value){
        if(key == null || value == null){
            return false;
        }
        map.put(key, value);
        return true;
    }

    @Override
    public String get(String key){
        if(key == null) return null;
        return map.get(key);
    }

    @Override
    public void delete(String key){
        if(key != null){
            map.remove(key);
        }
    }
}

