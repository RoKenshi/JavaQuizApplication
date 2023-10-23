package ru.popov.javaskill;

import ru.popov.javaskill.model.QuestionAnswerPair;
import ru.popov.javaskill.util.ConvertXlsToQuestionAnswerPair;
import ru.popov.javaskill.util.QuizString;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuizApp {


    private static int COUNTERS = 0;



    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if (System.getProperty("os.name", "?").toLowerCase().startsWith("windows")) {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        }


        System.out.println(QuizString.START_MESSAGE_TYPE);

        String inputValue = scanner.nextLine();
        List<QuestionAnswerPair> questionAnswerPairs =
                ConvertXlsToQuestionAnswerPair.loadQuestionAnswerPairs(Integer.parseInt(inputValue) - 1);

        System.out.printf(QuizString.VARIABLE_THEMES_TYPE, inputValue);

        QuizLoop(questionAnswerPairs);
    }

    private static void QuizLoop(List<QuestionAnswerPair> questionAnswerPairs) {

        String inputValue;

        do {
            ++COUNTERS;
            int randomNum = getRandomNum(questionAnswerPairs);

            typeWriterEffect(
                    questionAnswerPairs.get(randomNum).getQuestion());

            typeWriterEffect(
                    QuizString.VARIABLE_ANSWER_TYPE.formatted(COUNTERS));

            inputValue = scanner.nextLine();

            switch (inputValue) {
                case "ответ" -> typeWriterEffect(questionAnswerPairs.get(randomNum).getAnswer());
            }
        } while (true);
    }



    private static int getRandomNum(List<QuestionAnswerPair> questionAnswerPairs) {
        return new Random().nextInt(questionAnswerPairs.size());
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
