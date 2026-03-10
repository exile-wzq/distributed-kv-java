package com.exile.kv.server;

import com.exile.kv.storage.MemoryStorage;
import com.exile.kv.storage.Storage;
import com.exile.kv.network.TcpServer;

public class KVServer {
    public static void main(String[] args){
        Storage storage = new MemoryStorage();

        TcpServer server = new TcpServer(8888, storage);
        server.start();
    }
}
