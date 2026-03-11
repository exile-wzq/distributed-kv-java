package com.exile.kv.client;

import java.io.*;
import java.net.Socket;

public class KVClient {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public KVClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public String put(String key, String value) throws IOException {
        writer.println("PUT " + key + " " + value.length());
        writer.print(value); // 直接写整个 value
        writer.flush();
        return reader.readLine();
    }

    public String get(String key) throws IOException {
        writer.println("GET " + key);
        return reader.readLine();
    }

    public String del(String key) throws IOException {
        writer.println("DEL " + key);
        return reader.readLine();
    }

    public void close() throws IOException {
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        KVClient client = new KVClient("localhost", 8888);
        System.out.println(client.put("name", "Alice"));
        System.out.println(client.get("name"));
        System.out.println(client.del("name"));
        System.out.println(client.get("name"));
        client.close();
    }
}