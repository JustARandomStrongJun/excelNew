package com.company;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by zZzZz on 12.07.2018.
 */
public class ReadLog {
    public static List<String> readData(File path) throws IOException{
        RWSetting v = new RWSetting();
        TotalLines t = new TotalLines();
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy/MM/dd ' '  HH:MM:SS");
        List<String> data = new ArrayList<>();
        FileReader reader = new FileReader(path);
        FileInputStream fs = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fs));

        int lineCounter = v.ReadValues("Line Counter");
        int i = 0;
        int totalLines = t.count(String.valueOf(path));

        if (lineCounter == totalLines) {
            System.out.println("No new Data ");

        }
        System.out.println ("Last Line Counter " + lineCounter);
        System.out.println();
        if (lineCounter > totalLines) {
            lineCounter = 1;
            v.WriteValues("Line Counter", 1);
            System.out.println("line counter was changed to 1");
        }
        if (lineCounter < totalLines) {
            int countLines = 0;
            for (int j = 0; j < totalLines; ++j) {
                if (countLines < lineCounter) {
                    br.readLine();
                    countLines++;
                } else {
                    data.add(br.readLine());
                    Excel.addToExcell(data, "log.txt");
                    System.out.println();
                }

            }
            System.out.println(formatForDateNow.format(dateNow) + " Trying to write next new data to the Excel: " + (totalLines - countLines) + "massages: ");
            for (String s : data) {i++;
                System.out.println(i + " " + s);
            }

        }
        reader.close();
        return data;
    }
}




