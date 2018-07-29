package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;

/**
 * Created by zZzZz on 15.07.2018.
 */
public class LogListener extends TimerTask {
    public void run() {
        File file = new File("log.txt");
        try {
            if(file.exists()){
            ReadLog.readData(file);}
            else {FileWriter writer = new FileWriter(file, true);
                writer.write("Recived data log file \n");
                writer.flush();
                writer.close();}
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Какая-то ошибка");

        }
    }
}




