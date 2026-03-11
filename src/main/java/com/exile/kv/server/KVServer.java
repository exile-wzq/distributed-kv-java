package com.exile.kv.server;

import com.exile.kv.storage.MemoryStorage;
import com.exile.kv.storage.Storage;
import com.exile.kv.network.TcpServer;

import java.io.IOException;

public class KVServer {
    public static void main(String[] args){
        try {
            Storage storage = new MemoryStorage("appendonly.aof");

            TcpServer server = new TcpServer(8888, storage);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
