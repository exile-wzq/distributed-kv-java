package com.exile.kv.storage;

/**
 * KV存储引擎核心接口
 */
public interface Storage {
    /**
     * 写入键值对
     * @param key (不能为空)
     * @param value (不能为空)
     * @return 写入是否为空
     */
    boolean put(String key, String value);

    /**
     * 写入对应的值
     * @param key
     * @return 对应的值，若不存在则返回null
     */

    String get(String key);

    /**
     * 删除指定的键
     * @param key
     */

    void delete(String key);

    boolean size();
}
