package com.nami.dfs.Service;

import java.io.*;

public final class SerialService {
    public static void serial(Serializable s, String serialFileName) {
        try {
            FileOutputStream fs = new FileOutputStream(serialFileName);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(s);
            os.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object deserial(String serialFileName, Object o) {
        try {
            FileInputStream fs = new FileInputStream(serialFileName);
            ObjectInputStream ois = new ObjectInputStream(fs);
            return (Object) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
