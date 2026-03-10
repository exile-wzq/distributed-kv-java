package com.exile.kv.network;

import com.exile.kv.storage.Storage;
import com.exile.kv.protocol.CommandParser;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpServer {
    private final int port;
    private final Storage storage;

    //创建一个固定大小的线程池
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public TcpServer(int port, Storage storage) {
        this.port = port;
        this.storage = storage;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("KV Server 正在端口 " + port + " 监听...");
            while (true) {
                // 这里的 accept() 会让 CPU 停下来等待网络请求
                Socket clientSocket = serverSocket.accept();
                System.out.println("新客户端已连接：" + clientSocket.getInetAddress());

                //把任务丢到线程池，主线程立刻回到上面去等下一个人
                threadPool.execute(() -> handleClient((clientSocket)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {

        CommandParser parser = new CommandParser(storage);

        // try-with-resources 会自动关闭socket，防止资源泄露
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            // 循环读取客户端发来的每一条指令
            while ((line = reader.readLine()) != null) {

                line = line.trim();
                if(line.isEmpty()) continue;;

                String response = parser.handle(line);
                writer.println(response);

            }
        } catch (IOException e) {
            System.out.println("客户端断开连接");
        }
    }
}