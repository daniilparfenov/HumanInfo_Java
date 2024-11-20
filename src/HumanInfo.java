import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class HumanInfo {
    private final String fullName;
    private final LocalDate dateOfBirth;

    public HumanInfo(String fullName, String dateOfBirth) throws IllegalArgumentException {
        fullName = fullName.trim().replaceAll("\\s+", " ");
        dateOfBirth = dateOfBirth.trim();

        if (fullName.isEmpty()) {
            throw new IllegalArgumentException("ФИО не может быть пустым");
        }
        // Проверка, что фио - русское и удовлетворяет шаблону ФАМИЛИЯ ИМЯ ОТЧЕСТВО/-
        if (!fullName.matches("([\\p{IsCyrillic}]+(-[\\p{IsCyrillic}]+)*\\s){2}([\\p{IsCyrillic}]+|-)")) {
            throw new IllegalArgumentException("ФИО не соответствует шаблону: ФАМИЛИЯ ИМЯ ОТЧЕСТВО/-");
        }

        if (dateOfBirth.isEmpty()) {
            throw new IllegalArgumentException("Дата рождения не может быть пустой");
        }

        this.fullName = fullName;

        // Парсинг даты рождения из строки в LocalDate
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            this.dateOfBirth = LocalDate.parse(dateOfBirth, formatter);
            if (this.dateOfBirth.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Вы еще не родились!");
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Дата рождения не соответствует формату: дд-мм-гггг");
        }
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getFirstName() {
        return this.fullName.split(" ")[1];
    }

    public String getSecondName() {
        return this.fullName.split(" ")[0];
    }

    public String getMiddleName() {
        return this.fullName.split(" ")[2];
    }

    // Возвращает строку, где записан возраст в формате x лет, y месяцев, z дней с учетом словоформ
    public String getAge() {
        Period agePeriod = Period.between(this.dateOfBirth, LocalDate.now());
        String age = "";
        age += agePeriod.getYears() + " " + getAgeEndForm(agePeriod.getYears(), "год", "года", "лет") + " ";
        age += agePeriod.getMonths() + " " + getAgeEndForm(agePeriod.getMonths(), "месяц", "месяца", "месяцев") + " ";
        age += agePeriod.getDays() + " " + getAgeEndForm(agePeriod.getDays(), "день", "дня", "дней") + " ";
        return age;
    }

    // Возвращает правильную словоформу для единиц измерения возраста (лет, месяцев, дней) в зависимости от численного значения
    private String getAgeEndForm(int age, String nominativeForm, String genetiveForm, String pluralForm) {
        int lastTwoDigits = Math.abs(age) % 100;
        int lastDigit = lastTwoDigits % 10;

        if (lastTwoDigits >= 11 && lastTwoDigits <= 14) {
            return pluralForm;
        }

        return switch (lastDigit) {
            case 1 -> nominativeForm;
            case 2, 3, 4 -> genetiveForm;
            default -> pluralForm;
        };
    }

    // Определяет пол по отчеству
    public String determineGender() {
        String middleName = this.fullName.split(" ")[2];

        if (middleName.matches(".*(ович|евич|ич|ьич|иевич|ичич)$")) {
            return "мужской";
        }
        if (middleName.matches(".*(овна|евна|ична|инична|ьевна|ичевна|овична)$")) {
            return "женский";
        }
        return "неизвестно";
    }

    // Возвращает инициалы
    public String getInitials() {
        String initials = "";
        String firstName = this.fullName.split(" ")[1];
        String middleName = this.fullName.split(" ")[2];

        initials += firstName.charAt(0) + ".";
        if (!middleName.equals("-")) {
            initials += middleName.charAt(0) + ".";
        }
        return initials.toUpperCase();
    }
}
