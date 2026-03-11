package com.exile.kv.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Wal {

    private final File walFile;
    private final BufferedWriter writer;

    public Wal(String path) throws IOException {
        this.walFile = new File(path);
        if (!walFile.exists()) {
            walFile.createNewFile();
        }
        this.writer = new BufferedWriter(new FileWriter(walFile, true)); // append模式
    }

    // 追加日志
    public synchronized void append(String commandLine) throws IOException {
        writer.write(commandLine);
        writer.newLine();
        writer.flush(); // 确保写入磁盘
    }

    // 读取所有日志，用于启动时恢复
    public List<String> readAll() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(walFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public void close() throws IOException {
        writer.close();
    }
}