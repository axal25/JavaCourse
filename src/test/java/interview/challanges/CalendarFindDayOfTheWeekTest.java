package interview.challanges;

import org.junit.jupiter.api.*;

import java.util.Date;

import static com.google.common.truth.Truth.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test for CalendarFindDayOfTheWeek")
public class CalendarFindDayOfTheWeekTest {
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("My Data 30 January (1) 2023")
    public class MyData1 {
        private static final int DAY = 30;
        private static final int MONTH = 1;
        private static final int YEAR = 2023;
        private static final String EXPECTED_WEEK_DAY = "Monday";

        @Test
        @Order(1)
        public void getDate() {
            assertThat(CalendarFindDayOfTheWeek.getDate(MONTH, DAY, YEAR))
                    .isEqualTo(new Date(YEAR, MONTH, DAY));
        }

        @Test
        @Order(2)
        public void getDateFromParse() {
            assertThat(CalendarFindDayOfTheWeek.getDateFromParse(MONTH, DAY, YEAR))
                    .isEqualTo(new Date(YEAR, MONTH, DAY));
        }

        @Test
        @Order(3)
        public void findDayFromCalendarThenDateThenTime() {
            assertThat(CalendarFindDayOfTheWeek.findDayFromCalendarThenDateThenTime(MONTH, DAY, YEAR))
                    .isEqualTo(EXPECTED_WEEK_DAY);
        }

        @Test
        @Order(4)
        public void findDayFromDate() {
            assertThat(CalendarFindDayOfTheWeek.findDayFromDate(MONTH, DAY, YEAR))
                    .isEqualTo(EXPECTED_WEEK_DAY);
        }

        @Test
        @Order(5)
        public void findDayFromCalendar() {
            assertThat(CalendarFindDayOfTheWeek.findDayFromCalendar(MONTH, DAY, YEAR))
                    .isEqualTo(EXPECTED_WEEK_DAY);
        }

        @Test
        @Order(6)
        public void findDayFromLocalDate() {
            assertThat(CalendarFindDayOfTheWeek.findDayFromLocalDate(MONTH, DAY, YEAR))
                    .isEqualTo(EXPECTED_WEEK_DAY.toUpperCase());
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("Challenge Data 5th August (8) 2015")
    public class ChallengeData {

        private static final int DAY = 5;
        private static final int MONTH = 8;
        private static final int YEAR = 2015;
        private static final String EXPECTED_WEEK_DAY = "Wednesday";

        @Test
        @Order(1)
        public void getDate() {
            assertThat(CalendarFindDayOfTheWeek.getDate(MONTH, DAY, YEAR))
                    .isEqualTo(new Date(YEAR, MONTH, DAY));
        }

        @Test
        @Order(2)
        public void getDateFromParse() {
            assertThat(CalendarFindDayOfTheWeek.getDateFromParse(MONTH, DAY, YEAR))
                    .isEqualTo(new Date(YEAR, MONTH, DAY));
        }

        @Test
        @Order(3)
        public void getDateFromSimpleDateFormatParse() {
            assertThat(CalendarFindDayOfTheWeek.getDateFromSimpleDateFormatParse(MONTH, DAY, YEAR))
                    .isEqualTo(new Date(YEAR, MONTH, DAY));
        }

        @Test
        @Order(4)
        public void findDayFromCalendarThenDateThenTime() {
            assertThat(CalendarFindDayOfTheWeek.findDayFromCalendarThenDateThenTime(MONTH, DAY, YEAR))
                    .isEqualTo(EXPECTED_WEEK_DAY);
        }

        @Test
        @Order(5)
        public void findDayFromDate() {
            assertThat(CalendarFindDayOfTheWeek.findDayFromDate(MONTH, DAY, YEAR))
                    .isEqualTo(EXPECTED_WEEK_DAY);
        }

        @Test
        @Order(6)
        public void findDayFromCalendar() {
            assertThat(CalendarFindDayOfTheWeek.findDayFromCalendar(MONTH, DAY, YEAR))
                    .isEqualTo(EXPECTED_WEEK_DAY);
        }

        @Test
        @Order(7)
        public void findDayFromCalendar_switchedMonthDay() {
            assertThat(CalendarFindDayOfTheWeek.findDayFromCalendar(DAY, MONTH, YEAR))
                    .isEqualTo(EXPECTED_WEEK_DAY);
        }

        @Test
        @Order(8)
        public void findDayFromGregorianCalendar() {
            assertThat(CalendarFindDayOfTheWeek.findDayFromGregorianCalendar(DAY, MONTH, YEAR))
                    .isEqualTo(EXPECTED_WEEK_DAY);
        }

        @Test
        @Order(9)
        public void findDayFromLocalDate() {
            assertThat(CalendarFindDayOfTheWeek.findDayFromLocalDate(MONTH, DAY, YEAR))
                    .isEqualTo(EXPECTED_WEEK_DAY.toUpperCase());
        }
    }
}
