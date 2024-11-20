import javax.crypto.spec.PSource;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main() {
        Scanner scanner = new Scanner(System.in);
        String fullName = "";
        String dateOfBirth = "";
        String isExit = "нет";
        int consoleWidth = 170;

        printLogo(consoleWidth);

        // main loop
        while (!isExit.equals("да")) {
            // Считывание ФИО и даты рождения
            System.out.println("Введите ФИО на русском языке в формате ФАМИЛИЯ ИМЯ ОТЧЕСТВО");
            System.out.println("В случае если отчества нет, введите \"-\"");
            fullName = scanner.nextLine();
            System.out.println("Введите дату рождения в формате дд-мм-гггг");
            dateOfBirth = scanner.nextLine();

            // Обработка информации и вывод результата
            try {
                HumanInfo humanInfo = new HumanInfo(fullName, dateOfBirth);
                System.out.println("Фамилия и инициалы: " + humanInfo.getSecondName() + " " + humanInfo.getInitials());
                System.out.println("Пол: " + humanInfo.determineGender());
                System.out.println("Возраст: " + humanInfo.getAge());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            // Запрос на выход
            System.out.println("Выйти? (да/нет)");
            isExit = scanner.nextLine();

            System.out.println("=".repeat(consoleWidth) + "\n");
        }
    }

    // Выводит логотип программы в ширину консоли
    private static void printLogo(int consoleWidth) {
        String logoText = "HumanInfo";
        int paddingLength = (consoleWidth - logoText.length()) / 2;
        String padding = "=".repeat(paddingLength);
        System.out.println(padding + "|" + logoText + "|" + padding);
    }

}
