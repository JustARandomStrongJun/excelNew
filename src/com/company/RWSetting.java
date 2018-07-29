package com.company;

import java.io.*;
import java.util.Properties;

/**
 * Created by zZzZz on 14.07.2018.
 */
  class RWSetting {
    static private Properties properties = new Properties();
    public static void WriteValues(String name, int i) throws IOException {

        properties.setProperty(name, String.valueOf(i));
        //System.out.println(properties.getProperty("Line Counter"));
        FileOutputStream out = new FileOutputStream("settings");
        properties.store(out, "Settings");
        out.close();
    }
    public static void writeString(String name, String value) throws IOException {

        properties.setProperty(name, value);
        //System.out.println(properties.getProperty("Line Counter"));
        FileOutputStream out = new FileOutputStream("settings");
        properties.store(out, "Settings");
        out.close();
    }

   public static int ReadValues(String name) throws IOException {
       FileInputStream in = new FileInputStream("settings");
        properties.load(in);
        //System.out.println(properties.getProperty(name));
        in.close();
        int j = Integer.parseInt(properties.getProperty(name));
        return j;
    }
    public static String ReadString (String name) throws IOException {
    FileInputStream in = new FileInputStream("settings");
        properties.load(in);
        //System.out.println(properties.getProperty(name));
        in.close();
        return properties.getProperty(name).toString();
    }

}
