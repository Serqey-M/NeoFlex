import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        double salaryFor12Months;
        LocalDate vacationStartDate;
        LocalDate vacationEndDate;
        salaryFor12Months = enteringTheAmount();
        vacationStartDate = enteringTheDate("Введите дату начала отпуска в формате 'дд.мм.гггг': ");
        vacationEndDate = enteringTheDate("Введите дату окончания отпуска в формате 'дд.мм.гггг': ");
        if (vacationStartDate.isAfter(vacationEndDate)) {
            System.out.println("Не возможно посчитать размер отпускных. Дата начала отпуска позже даты окончания.");
        } else {
        System.out.println("Размер отпускных: " + calculationOfVacationPay(salaryFor12Months,
                calculatingNumberVacationDays(vacationStartDate, vacationEndDate)) + " рублей.");
    }}
    
    public static double enteringTheAmount() {
        System.out.print("Введите сумму заработной платы за последние 12 месяцев: ");
        Scanner in = new Scanner(System.in);
        try {
            double amount = in.nextDouble();
            return amount;
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Не верный формат ввода суммы заработной платы.");
            return enteringTheAmount();
        }  
    }
    
    public static LocalDate enteringTheDate(String message) {
        System.out.print(message);
        Scanner in = new Scanner(System.in);
        try {
            String strDate = in.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate Date = LocalDate.parse(strDate, formatter);
            return Date;
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Не верный формат ввода даты.");
            return enteringTheDate(message);
        }
    }
    
    public static double calculationOfVacationPay(double salaryFor12Months, int numberOfVacationDays) {
        double amountOfVacationPay = Math.round(salaryFor12Months / 12 / 29.3 * numberOfVacationDays * 100);
        return amountOfVacationPay / 100;
    }

    public static int calculatingNumberVacationDays(LocalDate vacationStartDate, LocalDate vacationEndDate) {
        int numberCalendarDays = (int) ChronoUnit.DAYS.between(vacationStartDate, vacationEndDate) + 1;
        String[] listHolidayDates = new String[] { "01.01.", "02.01.", "03.01.", "04.01.", "05.01.", "06.01.", "07.01.",
                "08.01.", "23.02.", "08.03.", "01.05.", "09.05.", "12.06.", "04.11." };
        int yearHheStartOfVacation = vacationStartDate.getYear();
        int numberHolidayDates = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (String holidayDate : listHolidayDates) {
            LocalDate date = LocalDate.parse(holidayDate + yearHheStartOfVacation, formatter);
            if (date.isAfter(vacationStartDate.minusDays(1)) && date.isBefore(vacationEndDate.plusDays(1))) {
                numberHolidayDates += 1;
            }
        }
        int yearHheEndOfVacation = vacationEndDate.getYear();
        if (yearHheStartOfVacation != yearHheEndOfVacation){
            for (String holidayDate : listHolidayDates) {
                LocalDate date = LocalDate.parse(holidayDate + yearHheEndOfVacation, formatter);
                if (date.isAfter(vacationStartDate.minusDays(1)) && date.isBefore(vacationEndDate.plusDays(1))) {
                    numberHolidayDates += 1;
                }
        }
        }
        return numberCalendarDays - numberHolidayDates;
    }
}
