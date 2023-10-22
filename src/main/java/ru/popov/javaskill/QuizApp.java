package ru.popov.javaskill;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.popov.javaskill.model.QuestionAnswerPair;
import ru.popov.javaskill.util.QuizString;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuizApp {

    private static final List<QuestionAnswerPair> pairs = new ArrayList<>();
    private static int COUNTERS = 0;

    private static final String FILE_PATH = "src/main/ext/CoreAndCo.xlsx";

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (System.getProperty("os.name", "?").toLowerCase().startsWith("windows")) {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        }


        System.out.println(QuizString.START_MESSAGE_TYPE);

        String inputValue = scanner.nextLine();
        List<QuestionAnswerPair> questionAnswerPairs =
                loadQuestionAnswerPairs(Integer.parseInt(inputValue) - 1);

        System.out.printf(QuizString.VARIABLE_THEMES_TYPE, inputValue);

        QuizLoop(questionAnswerPairs);
    }

    private static void QuizLoop(List<QuestionAnswerPair> questionAnswerPairs) {

        String inputValue = String.valueOf(scanner);

        while (!"выход".equals(inputValue)) {
            ++COUNTERS;
            int randomNum = getRandomNum(questionAnswerPairs);

            System.out.println();
            typeWriterEffect(questionAnswerPairs.get(randomNum).getQuestion());
            System.out.println();
            typeWriterEffect("Для ответа напишите \"ответ\"\nДля выхода нажмите \"выход\" (задано вопросов - %d)".formatted(COUNTERS));
            inputValue = scanner.nextLine();

            if ("ответ".equals(inputValue)) {
                typeWriterEffect(questionAnswerPairs.get(randomNum).getAnswer() + "\n");
            } else {
                inputValue = scanner.nextLine();
            }
        }
    }

    private static int getRandomNum(List<QuestionAnswerPair> questionAnswerPairs) {
        return new Random().nextInt(questionAnswerPairs.size());
    }


    private static List<QuestionAnswerPair> loadQuestionAnswerPairs(int sheetNumber) {
        try (FileInputStream fis = new FileInputStream(FILE_PATH)) {
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(sheetNumber);

            for (Row row : sheet) {
                Cell questionCell = row.getCell(0);
                Cell answerCell = row.getCell(1);

                if (questionCell != null && answerCell != null) {
                    String question = questionCell.getStringCellValue();
                    String answer = answerCell.getStringCellValue();
                    pairs.add(new QuestionAnswerPair(question, answer));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pairs;
    }

    public static void typeWriterEffect(String text) {
        System.out.println(QuizString.BOARDER_LINE);
        System.out.println();
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i)); // Вывести один символ
            int count = new Random().nextInt(50) + 20;
            try {
                Thread.sleep(count); // Задержка в миллисекундах (настраиваемая)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        System.out.println();
        System.out.println(QuizString.BOARDER_LINE);
    }
}
