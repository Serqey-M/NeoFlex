import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        double salaryFor12Months;
        String vacationStartDate;
        String vacationEndDate;
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Введите сумму заработной платы за последние 12 месяцев: ");
            salaryFor12Months = in.nextDouble();
            System.out.print("Введите дату начала отпуска в формате 'дд.мм.гггг': ");
            vacationStartDate = in.next();
            System.out.print("Введите дату окончания отпуска в формате 'дд.мм.гггг': ");
            vacationEndDate = in.next();
        }
        System.out.println("Размер отпускных: " + calculationOfVacationPay(salaryFor12Months, calculatingNumberVacationDays(vacationStartDate, vacationEndDate)));
    }
    

    public static double calculationOfVacationPay(double salaryFor12Months, int numberOfVacationDays) {
        double amountOfVacationPay = Math.round(salaryFor12Months / 12 / 29.3 * numberOfVacationDays * 100);
        return amountOfVacationPay / 100;
    }

    public static int calculatingNumberVacationDays(String vacationStartDate, String vacationEndDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(vacationStartDate, formatter);
        LocalDate endDate = LocalDate.parse(vacationEndDate, formatter);
        int numberCalendarDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
        String[] listHolidayDates = new String[] { "01.01.", "02.01.", "03.01.", "04.01.", "05.01.", "06.01.", "07.01.",
                "08.01.", "23.02.", "08.03.", "01.05.", "09.05.", "12.06.", "04.11." };
        int yearHheStartOfVacation = startDate.getYear();
        int numberHolidayDates = 0;
        for (String holidayDate : listHolidayDates) {
            LocalDate date = LocalDate.parse(holidayDate + yearHheStartOfVacation, formatter);
            if (date.isAfter(startDate.minusDays(1)) && date.isBefore(endDate.plusDays(1))) {
                numberHolidayDates += 1;
            }
        }
        int yearHheEndOfVacation = endDate.getYear();
        if (yearHheStartOfVacation != yearHheEndOfVacation){
            for (String holidayDate : listHolidayDates) {
                LocalDate date = LocalDate.parse(holidayDate + yearHheEndOfVacation, formatter);
                if (date.isAfter(startDate.minusDays(1)) && date.isBefore(endDate.plusDays(1))) {
                    numberHolidayDates += 1;
                }
        }
        }
        return numberCalendarDays - numberHolidayDates;
    }
}
