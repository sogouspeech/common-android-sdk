// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.sogocommon.utils;

/**
 * 缓存队列，循环保存数据
 *
 * @author lijunchen
 */
public class CircleCacheQueue {

    private short[] data;
    private int capacity;
    private int index; // 拷贝时的初始下标

    public CircleCacheQueue(int size) {
        data = new short[size];
        capacity = size;
        index = 0;
    }

    public void put(short[] objs) {
        if (objs == null || objs.length == 0)
            return;

        int len = objs.length;
        if (capacity - index >= len) {
            System.arraycopy(objs, 0, data, index, len);
            index += len;
        } else {
            int delCount = len - (capacity - index);
            short[] tmp = new short[capacity];
            System.arraycopy(data, 0, tmp, 0, index);
            System.arraycopy(tmp, delCount, data, 0, index - delCount);
            System.arraycopy(objs, 0, data, capacity - 1 - len, len);
            index = capacity;
            tmp = null;
        }

    }

    /**
     * 返回缓存的数据,当数据未占满则返回0-index-1的数据，满时全部返回
     * speex处理时需要是320的整数倍
     *
     * @return
     */
    public short[] get() {
        if (index == capacity) {
            return data;
        } else {
            int len = (index / 320) * 320;
            short[] tmp = new short[len];
            System.arraycopy(data, 0, tmp, 0, len);
            return tmp;
        }
    }

    public void reset() {
        data = new short[capacity];
        index = 0;
    }
}