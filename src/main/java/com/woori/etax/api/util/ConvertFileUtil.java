package com.woori.etax.api.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class ConvertFileUtil {

    private String patternIn = "yyyymmdd";

    private String patternOut = "dd/mm/yyyy";

    DateFormat dfIn = new SimpleDateFormat(patternIn);

    DateFormat dfOut = new SimpleDateFormat(patternOut);

    private String[] headerExcelArr = {"STT", "Mã số thuế", "Loại giấy tờ", "Số giấy tờ", "Tên chủ tài khoản", "Số hiệu tài khoản",
            "Ngày mở tài khoản (dd/mm/yyyy)", "Ngày đóng tài khoản (dd/mm/yyyy)", "Loại khách hàng", "Mã Loại giấy tờ", "Mã loại khách hàng"};
    public void convertFile(String csvFileName, String xmlFileName, String delimiter) throws IOException {

        File file = new File(csvFileName);
        BufferedReader reader = null;
        List<String> headers = new ArrayList<>();

        Element root = new Element("CONFIGURATION");
        Document doc=new Document();

        try{
            reader = new BufferedReader(new FileReader(file));
            int line = 0;

            String text = null;
            while ((text = reader.readLine()) != null) {

                StringTokenizer st = new StringTokenizer(text, delimiter, false);
                String[] rowValues = new String[st.countTokens()];
                int index = 0;
                while (st.hasMoreTokens()) {

                    String next = st.nextToken();
                    rowValues[index++] = next;

                }

                if (line == 0) { // Header row
                    for (String col : rowValues) {
                        headers.add(col);
                    }
                } else { // Data row
//                    Element rowElement = newDoc.createElement("row");
//                    rootElement.appendChild(rowElement);
                    for (int col = 0; col < headers.size(); col++) {
                        String header = headers.get(col);
                        String value = null;

                        Element element =new Element(header);
                        element.addContent(rowValues[col]);

                        root.addContent(element);
                    }
                }
                line++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        doc.setRootElement(root);
        XMLOutputter outter=new XMLOutputter();
        outter.setFormat(Format.getPrettyFormat());
        outter.output(doc, new FileWriter(new File(xmlFileName)));

    }

    public void convertToExcelFile(String csvFileName, String resultName, String delimiter, String fileBackUp) throws IOException, ParseException {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("01_SHTK");

            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("DANH SÁCH SỐ HIỆU TÀI KHOẢN THANH TOÁN");
            try {
                FileOutputStream out = new FileOutputStream(new
                        File(resultName));
                workbook.write(out);
                out.close();
                System.out.println("success");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // read from excel file ->>>
        FileInputStream file = new FileInputStream(resultName);

//            Files.copy(new File(FILE_LOCATION).toPath(), new File("./tmp.xlsx").toPath(), StandardCopyOption.REPLACE_EXISTING);

        //create tmp file
//            FileInputStream file = new FileInputStream(new File("./tmp.xlsx"));


//            Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook2 = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet2 = workbook2.getSheetAt(0);

        //Iterate through each rows one by one
        Iterator<Row> rowIterator = sheet2.iterator();

        //create header
        Row row = sheet2.createRow(1);
        Iterator<Cell> cellIterator = row.cellIterator();
        for (int i = 0; i < headerExcelArr.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headerExcelArr[i]);
        }
//        boolean firstRow = true;
        int tmp = 2;
//        List<String> data = new ArrayList<>();
//        while (tmp < 3) {
//
//            //For each row, iterate through all the columns
//            if (firstRow) {
//                //create header
//
//                firstRow = false;
//            } else {
//
//            tmp++;
//        }

//            Iterator<Cell> cellIterator = row.cellIterator();
//            int index = 0;
            File fileCsv = new File(csvFileName);
            BufferedReader reader = null;

            reader = new BufferedReader(new FileReader(fileCsv));

            String text = null;
            while ((text = reader.readLine()) != null) {

                String[] rowValues = text.split(delimiter, -1);
//                    StringTokenizer st = new StringTokenizer(text, delimiter, false);
//                    String[] rowValues = new String[st.countTokens()];
//                    int indexTmp = 0;
//                    while (st.hasMoreTokens()) {
//
//                        String next = st.nextToken();
//                        String next2 = st.nextToken(delimiter);
//                        rowValues[indexTmp++] = next;
//
//                    }
                Row rowD = sheet2.createRow(tmp);
                Iterator<Cell> cellIteratorD = rowD.cellIterator();

                for (int i = 0; i < rowValues.length; i++) {
                    Cell cell = rowD.createCell(i);
                    if ((i == 2) && rowValues[i] != null && !rowValues[i].isEmpty() && rowValues[i].contains("số thuế")){
                        cell.setCellValue("Mã số doanh nghiệp");
                    }else
                    cell.setCellValue(rowValues[i]);
                }
            tmp++;
            }

//                while (cellIterator.hasNext()) {
//                }
//        }

        FileOutputStream out = new FileOutputStream(new File(fileBackUp));
//        logger.info("Convert Ip address to location: Start writing to file: " + FILE_OUTPUT);
        FileOutputStream output = new FileOutputStream(new File(resultName));
        workbook2.write(out);
        workbook2.write(output);
        file.close();
        output.close();
    }

}
