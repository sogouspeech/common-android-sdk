// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.sogocommon.net;


import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * created by zhouwei 2018/6/15
 */
public class DefaultCache extends LinkedHashMap<String, SoftReference<String>> implements ICache<String, String> {
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();

    private final int capacity = 50;

    @Override
    public boolean removeEldestEntry(Entry<String, SoftReference<String>> eldest) {
        return super.size() > capacity;
    }

    @Override
    public String fetch(String url) {
        String res = null;
        try {
            readLock.lock();
            SoftReference<String> s = this.get(url);
            if (null != s) {
                res = s.get();
            }
        } finally {
            readLock.unlock();
        }
        return res;
    }

    @Override
    public void delete(String key) {
        writeLock.lock();
        super.remove(key);
        writeLock.unlock();
    }

    @Override
    public void store(String url, String v) {
        writeLock.lock();
        super.put(url, new SoftReference<>(v));
        writeLock.unlock();
    }

    @Override
    public void clearAll() {
        writeLock.lock();
        super.clear();
        writeLock.unlock();
    }

    private static class DefaultCacheHolder {
        public static DefaultCache instance = new DefaultCache();
    }

    private DefaultCache() {

    }

    public static DefaultCache getInstance() {
        return DefaultCacheHolder.instance;
    }
}