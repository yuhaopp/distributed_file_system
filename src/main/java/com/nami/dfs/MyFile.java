package com.nami.dfs;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by lu on 2017/1/9.
 */
public class MyFile {
    private static int totalBlock = 0;
    private static Hashtable<Integer,ArrayList<Integer>> storeInfo = new Hashtable<>();

    public MyFile() {
    }

    public static int getTotalBlock() {
        return totalBlock;
    }

    public static void setTotalBlock(int totalBlock) {
        MyFile.totalBlock = totalBlock;
    }

    public static Hashtable<Integer, ArrayList<Integer>> getStoreInfo() {
        return storeInfo;
    }

    public static void setStoreInfo(Hashtable<Integer, ArrayList<Integer>> storeInfo) {
        MyFile.storeInfo = storeInfo;
    }
}
