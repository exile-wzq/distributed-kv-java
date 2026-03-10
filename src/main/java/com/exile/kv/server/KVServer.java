package com.exile.kv.server;

import com.exile.kv.storage.MemoryStorage;
import com.exile.kv.storage.Storage;  //导入接口

public class KVServer {
    public static void main(String[] args){
        Storage storage = new MemoryStorage();

        boolean success = storage.put("name", "exile");
        if(success){
            String val = storage.get("name");
            System.out.println("成功获取：" + val);
        }

        storage.delete("name");
        System.out.println("删除后查询：" + storage.get("name"));
    }
}
