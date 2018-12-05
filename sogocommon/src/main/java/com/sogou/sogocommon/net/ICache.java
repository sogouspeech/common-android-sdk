package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/14
 */
public interface ICache<E, T> {
    void store(E key, T value);

    T fetch(E key);

    void delete(E key);

    void clearAll();
}
