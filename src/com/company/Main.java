package com.company;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class Main extends Thread {
private static File settings = new File("settings");
    public static void main(String[] args) throws IOException{


        JFrame frame = new JFrame();
        FlexorListener form = new FlexorListener();
        frame.setContentPane(form.getRootPanel());
        frame.setTitle("TAG");
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
	// write your code here

    }
    public  void run() {Time time = new Time();
    time.start();
    }

}
