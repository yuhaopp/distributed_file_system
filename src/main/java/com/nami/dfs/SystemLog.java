package com.nami.dfs;

import java.util.Hashtable;

/**
 * Created by lu on 2017/1/9.
 */
public class SystemLog {
    private static float totalSize = 0;
    private static float emptySize = 0;
    private static Hashtable<Integer,Float> vmEmptySize = null;
    private static Hashtable<String, MyFile> files = null;

    public SystemLog() {
    }

    public static float getTotalSize() {
        return totalSize;
    }

    public static void setTotalSize(float totalSize) {
        SystemLog.totalSize = totalSize;
    }

    public static Hashtable<String, MyFile> getFiles() {
        return files;
    }

    public static void setFiles(Hashtable<String, MyFile> files) {
        SystemLog.files = files;
    }

    public static Hashtable<Integer, Float> getVmEmptySize() {
        return vmEmptySize;
    }

    public static void setVmEmptySize(Hashtable<Integer, Float> vmEmptySize) {
        SystemLog.vmEmptySize = vmEmptySize;
    }

    public static float getEmptySize() {
        return emptySize;
    }

    public static void setEmptySize(float emptySize) {
        SystemLog.emptySize = emptySize;
    }
}
