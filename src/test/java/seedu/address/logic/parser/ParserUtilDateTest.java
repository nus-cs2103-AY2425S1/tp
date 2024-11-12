package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class ParserUtilDateTest {

    public static final String[] INVALID_DEADLINES_WRONG_DELIMITER = new String[] {
        "5?10?2025",
        "15)11)2024",
        "10a12b13",
        "10_12*2024"
    };
    public static final String[] INVALID_DEADLINES_WRONG_NUMBERS = new String[] {
        "-10/9/2024",
        "12/-7/2023",
        "ab/cd/efgh"
    };
    public static final String[] INVALID_DEADLINES_NUMBERS_OUTSIDE_RANGE = new String[] {
        "100_10_2020",
        "10-100-2020",
        "100-100-2020",
        "0-10-2023",
        "10|0|2023",
        "4/14/2023",
        "10-10-123",
        "0-0-321",
        "0/10/123"
    };
    public static final String[] INVALID_DEADLINES_BORDER_VALUES = new String[] {
        "31-2-2024",
        "31|4|2023",
        "32-1-2025",
        "12/13/2023"
    };

    public static final String[] VALID_DEADLINES_EASY = new String[] {
        "10-10-2025",
        "01-01-2022",
        "24-12-2002"
    };
    public static final String[] VALID_DEADLINES_DIFFERENT_DELIMITERS = new String[] {
        "10/10/2025",
        "10-10-2025",
        "10|10|2025",
        "10_10_2025",
    };
    public static final String[] VALID_DEADLINES_SHORT_STRINGS = new String[] {
        "1-10-2025",
        "10-1-2025",
        "10-10-25",
        "1-1-2025",
        "10-1-25",
        "1-10-25",
        "1-1-25"
    };
    public static final String[] VALID_DEADLINES_BORDER_VALUES = new String[] {
        "28-2-2024",
        "29-2-2024",
        "31-10-2024",
        "5-1-2026",
        "30-6-2045",
        "15-12-2012",
        "31-12-2000",
    };
    public static final String[] VALID_DEADLINES_MIXED = new String[] {
        "10/12/2024",
        "1-10-24",
        "13_9_24",
        "03|06|10",
        "12-12-12"
    };

    @Test
    public void getDay_validDates_returnsCorrectDay() {
        int[] expected = new int[]{10, 1, 24};
        for (int i = 0; i < 3; i++) {
            assertEquals(expected[i], ParserUtil.getDay(VALID_DEADLINES_EASY[i]));
        }
    }

    @Test
    public void getDay_validDatesDifferentDelimiters_returnsCorrectDay() {
        for (String date : VALID_DEADLINES_DIFFERENT_DELIMITERS) {
            assertEquals(10, ParserUtil.getDay(date));
        }
    }

    @Test
    public void getDay_validDatesShortened_returnsCorrectDay() {
        int[] expected = new int[]{1, 10, 10, 1, 10, 1, 1};
        for (int i = 0; i < 7; i++) {
            assertEquals(expected[i], ParserUtil.getDay(VALID_DEADLINES_SHORT_STRINGS[i]));
        }
    }

    @Test
    public void getDay_validDatesBorderValues_returnsCorrectDay() {
        int[] expected = new int[]{28, 29, 31, 5, 30, 15, 31};
        for (int i = 0; i < 7; i++) {
            assertEquals(expected[i], ParserUtil.getDay(VALID_DEADLINES_BORDER_VALUES[i]));
        }
    }

    @Test
    public void getDay_validDatesMixed_returnsCorrectDay() {
        int[] expected = new int[]{10, 1, 13, 3, 12};
        for (int i = 0; i < 5; i++) {
            assertEquals(expected[i], ParserUtil.getDay(VALID_DEADLINES_MIXED[i]));
        }
    }

    @Test
    public void getMonth_validDates_returnsCorrectMonth() {
        int[] expected = new int[]{10, 1, 12};
        for (int i = 0; i < 3; i++) {
            assertEquals(expected[i], ParserUtil.getMonth(VALID_DEADLINES_EASY[i]));
        }
    }

    @Test
    public void getMonth_validDatesDifferentDelimiters_returnsCorrectMonth() {
        for (String date : VALID_DEADLINES_DIFFERENT_DELIMITERS) {
            assertEquals(10, ParserUtil.getMonth(date));
        }
    }

    @Test
    public void getMonth_validDatesShortened_returnsCorrectMonth() {
        int[] expected = new int[]{10, 1, 10, 1, 1, 10, 1};
        for (int i = 0; i < 7; i++) {
            assertEquals(expected[i], ParserUtil.getMonth(VALID_DEADLINES_SHORT_STRINGS[i]));
        }
    }

    @Test
    public void getMonth_validDateBorderValues_returnsCorrectMonth() {
        int[] expected = new int[]{2, 2, 10, 1, 6, 12, 12};
        for (int i = 0; i < 7; i++) {
            assertEquals(expected[i], ParserUtil.getMonth(VALID_DEADLINES_BORDER_VALUES[i]));
        }
    }

    @Test
    public void getMonth_validDatesMixed_returnsCorrectMonth() {
        int[] expected = new int[]{12, 10, 9, 6, 12};
        for (int i = 0; i < 5; i++) {
            assertEquals(expected[i], ParserUtil.getMonth(VALID_DEADLINES_MIXED[i]));
        }
    }

    @Test
    public void getYear_validDates_returnsCorrectYear() {
        int[] expected = new int[]{2025, 2022, 2002};
        for (int i = 0; i < 3; i++) {
            assertEquals(expected[i], ParserUtil.getYear(VALID_DEADLINES_EASY[i]));
        }
    }

    @Test
    public void getYear_validDatesDifferentDelimiters_returnsCorrectYear() {
        for (String date : VALID_DEADLINES_DIFFERENT_DELIMITERS) {
            assertEquals(2025, ParserUtil.getYear(date));
        }
    }

    @Test
    public void getYear_validDatesShortened_returnsCorrectYear() {
        for (String date : VALID_DEADLINES_SHORT_STRINGS) {
            assertEquals(2025, ParserUtil.getYear(date));
        }
    }

    @Test
    public void getYear_validDateBorderValues_returnsCorrectYear() {
        int[] expected = new int[]{2024, 2024, 2024, 2026, 2045, 2012, 2000};
        for (int i = 0; i < 7; i++) {
            assertEquals(expected[i], ParserUtil.getYear(VALID_DEADLINES_BORDER_VALUES[i]));
        }
    }

    @Test
    public void getYear_validDatesMixed_returnsCorrectYear() {
        int[] expected = new int[]{2024, 2024, 2024, 2010, 2012};
        for (int i = 0; i < 5; i++) {
            assertEquals(expected[i], ParserUtil.getYear(VALID_DEADLINES_MIXED[i]));
        }
    }

    @Test
    public void getDay_wrongString_returnsZero() {
        assertEquals(0, ParserUtil.getDay(""));
        assertEquals(0, ParserUtil.getDay("hello world"));
        assertEquals(0, ParserUtil.getDay("12102024"));
    }

    @Test
    public void getDay_wrongDelimiter_returnsZero() {
        for (String date : INVALID_DEADLINES_WRONG_DELIMITER) {
            assertEquals(0, ParserUtil.getDay(date));
        }
    }

    @Test
    public void getDay_wrongNumbers_returnsZero() {
        for (String date : INVALID_DEADLINES_WRONG_NUMBERS) {
            assertEquals(0, ParserUtil.getDay(date));
        }
    }

    @Test
    public void getDay_invalidDatesNumbersOutsideRange_returnsAsExpected() {
        int[] expected = new int[]{100, 10, 100, 0, 10, 4, 10, 0, 0};
        for (int i = 0; i < 9; i++) {
            assertEquals(expected[i], ParserUtil.getDay(INVALID_DEADLINES_NUMBERS_OUTSIDE_RANGE[i]));
        }
    }

    @Test
    public void getDay_invalidDatesBorderValues_returnsAsExpected() {
        int[] expected = new int[]{31, 31, 32, 12};
        for (int i = 0; i < 4; i++) {
            assertEquals(expected[i], ParserUtil.getDay(INVALID_DEADLINES_BORDER_VALUES[i]));
        }
    }

    @Test
    public void getMonth_wrongString_returnsZero() {
        assertEquals(0, ParserUtil.getMonth(""));
        assertEquals(0, ParserUtil.getMonth("hello world"));
        assertEquals(0, ParserUtil.getMonth("12102024"));
    }

    @Test
    public void getMonth_wrongDelimiter_returnsZero() {
        for (String date : INVALID_DEADLINES_WRONG_DELIMITER) {
            assertEquals(0, ParserUtil.getMonth(date));
        }
    }

    @Test
    public void getMonth_wrongNumbers_returnsZero() {
        for (String date : INVALID_DEADLINES_WRONG_NUMBERS) {
            assertEquals(0, ParserUtil.getMonth(date));
        }
    }

    @Test
    public void getMonth_invalidDatesNumbersOutsideRange_returnsAsExpected() {
        int[] expected = new int[]{10, 100, 100, 10, 0, 14, 10, 0, 10};
        for (int i = 0; i < 9; i++) {
            assertEquals(expected[i], ParserUtil.getMonth(INVALID_DEADLINES_NUMBERS_OUTSIDE_RANGE[i]));
        }
    }

    @Test
    public void getMonth_invalidDatesBorderValues_returnsAsExpected() {
        int[] expected = new int[]{2, 4, 1, 13};
        for (int i = 0; i < 4; i++) {
            assertEquals(expected[i], ParserUtil.getMonth(INVALID_DEADLINES_BORDER_VALUES[i]));
        }
    }

    @Test
    public void getYear_wrongString_returnsFalseFlag() {
        assertEquals(0, ParserUtil.getYear(""));
        assertEquals(0, ParserUtil.getYear("hello world"));
        assertEquals(0, ParserUtil.getYear("12102024"));
    }

    @Test
    public void getYear_wrongDelimiter_returnsFalseFlag() {
        for (String date : INVALID_DEADLINES_WRONG_DELIMITER) {
            assertEquals(0, ParserUtil.getYear(date));
        }
    }

    @Test
    public void getYear_wrongNumbers_returnsFalseFlag() {
        for (String date : INVALID_DEADLINES_WRONG_NUMBERS) {
            assertEquals(0, ParserUtil.getYear(date));
        }
    }

    @Test
    public void getYear_invalidDatesNumbersOutsideRange_returnsAsExpected() {
        int[] expected = new int[]{2020, 2020, 2020, 2023, 2023, 2023, 0, 0, 0};
        for (int i = 0; i < 9; i++) {
            assertEquals(expected[i], ParserUtil.getYear(INVALID_DEADLINES_NUMBERS_OUTSIDE_RANGE[i]));
        }
    }

    @Test
    public void getYear_invalidDatesBorderValues_returnsAsExpected() {
        int[] expected = new int[]{2024, 2023, 2025, 2023};
        for (int i = 0; i < 4; i++) {
            assertEquals(expected[i], ParserUtil.getYear(INVALID_DEADLINES_BORDER_VALUES[i]));
        }
    }

    @Test
    public void parseDeadline_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDeadline((String) null));
    }

    @Test
    public void parseDeadline_emptyString_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeadline(""));
    }

    @Test
    public void parseDeadline_notDeadline_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDeadline("hello world"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDeadline("abcd1234"));
    }
}
