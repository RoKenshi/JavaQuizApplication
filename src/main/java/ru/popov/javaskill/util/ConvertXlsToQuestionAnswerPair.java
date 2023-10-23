package ru.popov.javaskill.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.popov.javaskill.model.QuestionAnswerPair;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConvertXlsToQuestionAnswerPair {

    public static final List<QuestionAnswerPair> pairs = new ArrayList<>();
    public static final String FILE_PATH = "src/main/ext/CoreAndCo.xlsx";
    public static List<QuestionAnswerPair> loadQuestionAnswerPairs(int sheetNumber) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            sheetIterator(sheet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pairs;
    }

    private static void sheetIterator(Sheet sheet) {

        for (Row row : sheet) {
            Cell questionCell = row.getCell(0);
            Cell answerCell = row.getCell(1);

            if (questionCell != null && answerCell != null) {
                String question = questionCell.getStringCellValue();
                String answer = answerCell.getStringCellValue();
                pairs.add(new QuestionAnswerPair(question, answer));
            }
        }
    }
}
