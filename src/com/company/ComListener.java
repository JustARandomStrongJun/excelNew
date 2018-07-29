package com.company;

import jssc.*;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;

/**
 * Created by zZzZz on 16.07.2018.
 */
public class ComListener {/*Класс чтения из порта*/
    private static SerialPort serialPort; /*Создаем объект типа SerialPort*/
    static StringBuilder sb = new StringBuilder();

    public void stopPort() throws SerialPortException {
        serialPort.removeEventListener();
        serialPort.closePort();
    }

    public String[] getPorts() {
        //Метод getPortNames() возвращает массив строк. Элементы массива уже отсортированы.
        String[] portNames = SerialPortList.getPortNames();
        return portNames;
    }



    public void startPort() throws IOException {
        /* Точка входа в программу*/
        serialPort = new SerialPort(RWSetting.ReadString("Port")); /*Передаем в конструктор суперкласса имя порта с которым будем работать*/
        try {
            serialPort.openPort(); /*Метод открытия порта*/
            serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE); /*Задаем основные параметры протокола UART*/
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR); /*Устанавливаем маску или список события на которые будет происходить реакция. В данном случае это приход данных в буффер порта*/
            serialPort.addEventListener(new EventListener()); /*Передаем экземпляр класса EventListener порту, где будет обрабатываться события. Ниже описан класс*/
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

   /* public static void main(String[] args) {
        }*/

    private static class EventListener implements SerialPortEventListener { /*Слушатель срабатывающий по появлению данных на COM-порт*/
        public void serialEvent(SerialPortEvent event) {
            Date dateNow = new Date();
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy/MM/dd 'at time:'  HH:MM:SS");
            String data = null; /*Объявляем переменную типа String*/
            String data1 = null;
            String data2 = null;
            File file = new File("log.txt");
            if (event.isRXCHAR() && event.getEventValue() > 0) { /*Если происходит событие установленной маски и количество байтов в буфере более 0*/

                try {
                    data = serialPort.readString(event.getEventValue()); /*Создаем строковую переменную  data, куда и сохраняем данные*/
                    data1 = data.replaceAll("\\n", "");
                    data2 = data1.replaceAll("\\s", "");

                    boolean isDataStarts = data1.startsWith("\u0002"); // Ищем символ конца сообщения
                    if (isDataStarts) {
                        System.out.println("New Data:");
                    }
                    System.out.print(data2);
                    boolean isDataEnding = data2.endsWith("\u0003"); // Ищем символ конца сообщения
                    if (isDataEnding) {
                        System.out.print(" End of the message");
                        System.out.println();
                    }
                    try (FileWriter writer = new FileWriter(file, true)) {
                        // запись всей строки
                        writer.write(data2);
                        if (isDataEnding) {
                            writer.write(" [End of the message " + formatForDateNow.format(dateNow) + "]\r\n");
                        }
                        writer.flush();
                        writer.close();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                } catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }
}

