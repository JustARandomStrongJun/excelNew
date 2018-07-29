package com.company;

import jssc.SerialPortException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by zZzZz on 16.07.2018.
 */
public class FlexorListener {


    public JPanel getRootPanel() {
        return rootPanel;
    }

    private JPanel rootPanel;
    private JButton button1;
    private JButton stopButton;
    private JTextArea output;
    private JComboBox portSwicher;
    private ComListener comListener;
    private Thread t1;
    private Main obj;
    private Time time;
    static PrintStream printStream;


    public FlexorListener() throws IOException {
        comListener = new ComListener();
        obj = new Main();

        printStream = new PrintStream(new CustomOutputStream(output));
        System.setOut(printStream);
        System.setErr(printStream);
        for (String s : comListener.getPorts()) {
            portSwicher.addItem(s);
        }
        try {
            portSwicher.setSelectedItem(RWSetting.ReadString("Port"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    comListener.startPort();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                t1 = new Thread(obj);
                t1.start();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    time = new Time();
                    comListener.stopPort();
                    time.stop();

                } catch (SerialPortException e1) {
                    System.out.println(e1);
                }
            }

        });
        portSwicher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(portSwicher.getSelectedItem());

                try {
                    RWSetting.writeString("Port", String.valueOf(portSwicher.getSelectedItem()));
                } catch (IOException e1) {
                    System.out.println(e1.toString());
                }

            }
        });
    }
}
