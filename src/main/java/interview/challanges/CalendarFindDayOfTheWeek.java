package interview.challanges;

import java.io.*;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarFindDayOfTheWeek {

    /*
     * Complete the 'findDay' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. INTEGER month
     *  2. INTEGER day
     *  3. INTEGER year
     */

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // original writer
        // Writer fileWriter = new FileWriter(System.getenv("OUTPUT_PATH"));
        // my writer
        PrintStream printStream = System.out;
        Writer outputWriter = new OutputStreamWriter(printStream);

        BufferedWriter bufferedWriter = new BufferedWriter(outputWriter);

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int month = Integer.parseInt(firstMultipleInput[0]);

        int day = Integer.parseInt(firstMultipleInput[1]);

        int year = Integer.parseInt(firstMultipleInput[2]);

        String res = findDay(month, day, year);

        bufferedWriter.write(res);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }

    /*
     * Complete the 'findDay' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. INTEGER month
     *  2. INTEGER day
     *  3. INTEGER year
     */
    private static String findDay(int month, int day, int year) {
        return findDayFromLocalDate(month, day, year);
    }

    static String findDayFromLocalDate(int month, int day, int year) {
        LocalDate localDate = LocalDate.of(year, month, day);
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);
        return dayOfWeek.name();
    }

    static String findDayFromCalendar(int month, int day, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);
        int weekDayNumber = calendar.get(Calendar.DAY_OF_WEEK);
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        return dateFormatSymbols.getWeekdays()[weekDayNumber];
    }

    static String findDayFromGregorianCalendar(int month, int day, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);
        int weekDayNumber = calendar.get(Calendar.DAY_OF_WEEK);
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        return dateFormatSymbols.getWeekdays()[weekDayNumber];
    }

    static String findDayFromDate(int month, int day, int year) {
        SimpleDateFormat format = new SimpleDateFormat("EEEE", Locale.getDefault());
        Date date = new Date(year, month, day);
        return format.format(date);
    }

    static String findDayFromCalendarThenDateThenTime(int month, int day, int year) {
        SimpleDateFormat format = new SimpleDateFormat("EEEE", Locale.getDefault());
        Date date = new Date(year, month, day);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        return format.format(calendar.getTime());
    }

    static Date getDate(int month, int day, int year) {
        return new Date(year, month, day);
    }

    static Date getDateFromParse(int month, int day, int year) {
        return new Date(Date.parse("Sat, " + day + " Aug " + year + " 00:00:00 GMT-0600"));
    }

    static Date getDateFromSimpleDateFormatParse(int month, int day, int year) {
        try {
            return new SimpleDateFormat("dd mm yyyy").parse(day + " " + month + " " + year);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
