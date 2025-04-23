import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        double salaryFor12Months;
        LocalDate vacationStartDate;
        LocalDate vacationEndDate;
        salaryFor12Months = enteringTheAmount(scan);
        vacationStartDate = enteringTheDate("Введите дату начала отпуска в формате 'дд.мм.гггг': ", scan);
        vacationEndDate = enteringTheDate("Введите дату окончания отпуска в формате 'дд.мм.гггг': ", scan);
        if (vacationStartDate.isAfter(vacationEndDate)) {
            System.out.println("Не возможно посчитать размер отпускных. Дата начала отпуска позже даты окончания.");
        } else {
            int numberVacationDays = calculatingNumberVacationDays(vacationStartDate, vacationEndDate);
            System.out.println("Размер отпускных за " + numberVacationDays + " дней " + calculationOfVacationPay(salaryFor12Months, numberVacationDays) + " рублей.");
        }
    }
    
    public static final Scanner scan = new Scanner(System.in);

    public static double enteringTheAmount(Scanner scan) {
        System.out.print("Введите сумму заработной платы за последние 12 месяцев: ");
        try {
            double x = scan.nextDouble();
            scan.nextLine();

            return x;
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Не верный формат ввода суммы заработной платы.");
            return enteringTheAmount(scan);
        } 
    }
    
    public static LocalDate enteringTheDate(String message, Scanner scan) {
        System.out.print(message);
        try {
            String strDate = scan.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate Date = LocalDate.parse(strDate, formatter);
            return Date;
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Не верный формат ввода даты.");
            return enteringTheDate(message, scan);
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