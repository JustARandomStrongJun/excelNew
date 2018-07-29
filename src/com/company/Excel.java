package com.company;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class Excel {
   // private static String path = new File("").getAbsolutePath();
    private static File file;
    static private Workbook wb; // книга
    private static Date date = new Date();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static FileInputStream inputStream;
    static private Sheet sheet;
    static private boolean isFullMessage;
    static private TotalLines t = new TotalLines();
    static private RWSetting v = new RWSetting();


    public static void addToExcell(List<String> flexor, String log) throws IOException {file = new File("Data.xls");

        // Read XSL fil
        if (file.exists()) {

            int totalLines = t.count(log);
            inputStream = new FileInputStream(file);
            wb = new HSSFWorkbook(inputStream);
            // Get first sheet from the workbook
            sheet = wb.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            int excelLines = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // Get iterator to all cells of current row
                Iterator<Cell> cellIterator = row.cellIterator();
                excelLines++;
                System.out.println(excelLines);
            }
            //v.WriteValues("TotalExcelLines", excelLines);
            inputStream.close();
            System.out.println(excelLines + " eto ono");
            for (int i = excelLines; i < flexor.size() + excelLines; i++) {
                String s = flexor.get(i - excelLines).replaceAll("[;]+", ";");
                String s1 = s.replaceAll("\\s+", "");
                isFullMessage = s1.endsWith("]"); // Ищем символ конца сообщения
                int b = excelLines;
                if (isFullMessage) {
                    String[] parts = s1.split(";");
                    String patientNum = parts[3];
                    for (int j = 4; j < parts.length - 1; j++) {
                        if (parts[j].equals("TRIG") || parts[j].equals("GLU") || parts[j].equals("CHO") || parts[j].equals("HDL")) {
                            Row row = sheet.createRow(b);
                            Cell patientCell = row.createCell(0);
                            Cell analysisCell = row.createCell(1);
                            Cell resultCell = row.createCell(2);
                            CellStyle cellStyle = wb.createCellStyle();//добаляем стиль
                            cellStyle.setDataFormat(wb.createDataFormat().getFormat("0.000"));//Сейчас цифровой формат ячейки текст - это @
                            Cell dateCell = row.createCell(3);
                            patientCell.setCellValue(patientNum);
                            analysisCell.setCellValue(parts[j]);
                            try {
                                resultCell.setCellStyle(cellStyle);
                                resultCell.setCellType(CellType.NUMERIC);
                                String res = parts[j + 1].replaceAll(",", ".");
                                double result = Double.parseDouble(res);
                                resultCell.setCellValue(result);
                            } catch (Exception ex) {
                            }
                            dateCell.setCellValue(dateFormat.format(date));
                            j++;
                            b++;

                        }

                    }
                } else {
                    totalLines = t.count(log) - 1;
                }
            }


            try {
                FileOutputStream fos = new FileOutputStream(file);
                wb.write(fos);
                fos.close();
                v.WriteValues("Line Counter", totalLines);
                System.out.println("New Line Counter: " + t.count(log));
                //System.out.println("Total Excel Lines: " + excelLines);


            } catch (Exception ex) {
                System.out.println(ex.toString());
                System.out.println("New Data can't be written!");

            }


        } else {
            System.out.println("No Excel file");
            System.out.println("New file will be created");
            System.out.println();
            Workbook wb = new HSSFWorkbook();
            sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(dateFormat.format(date)));
            Row rw = sheet.createRow(0);
            rw.createCell(0).setCellValue("Ід. Пацієнта");
            rw.createCell(1).setCellValue("Ід. Аналізу");
            rw.createCell(2).setCellValue("Результат");
            rw.createCell(3).setCellValue("Дата");
            FileOutputStream fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
            System.out.println("Was created new file in: " + file.getAbsolutePath());
            System.out.println();
        }

    }

}
