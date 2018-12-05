package com.sogou.sogocommon.utils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * created by zhouwei 2018/6/15
 */
public class CloseUtil {
    public static void close(InputStream... ins) {
        try {
            if (ins != null) {
                for (InputStream in : ins) {
                    if (in != null) in.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close(OutputStream... outs) {
        try {
            if (outs != null) {
                for (OutputStream out : outs) {
                    if (out != null) out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
