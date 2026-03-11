package com.exile.kv.protocol;

import com.exile.kv.storage.Storage;

public class CommandParser {

    private final Storage storage;

    public CommandParser(Storage storage) {
        this.storage = storage;
    }

    /**
     * line是客户端输入的第一行
     * - PUT key length
     * - GET key
     * - DEL key
     * value会单独传进来
     */

    public String handle(String line, String value) {

        if (line == null || line.trim().isEmpty()) {
            return "ERROR";
        }

        String[] parts = line.trim().split("\\s+");
        String cmd = parts[0].toUpperCase();

        switch (cmd) {

            case "PUT":
                if (parts.length < 3) return "ERROR: PUT key length";
                String key = parts[1];
                storage.put(key, value);
                return "OK";

            case "GET":
                if (parts.length < 2) return "ERROR: GET key";
                String val = storage.get(parts[1]);
                return val != null ? val : "(nil)";

            case "DEL":
                if (parts.length < 2) return "ERROR: DEL key";
                storage.delete(parts[1]);
                return "OK";

            default:
                return "ERROR: Unknown Command";
        }
    }
}