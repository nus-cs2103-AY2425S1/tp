package seedu.address.commons.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods for handling dates.
 */
public class DateUtil {

    /**
     * Returns true if the given dates are the same day, month and year.
     */
    public static boolean isSameDate(LocalDate date, LocalDate otherDate) {
        return date.equals(otherDate);
    }


    /**
     * Converts a list of dates in a singular string to a {@code List<LocalDateTime>}.
     *
     * @param args a list of dates in the format "yyyy-MM-dd HH:mm" as a string.
     * @return a list of {@code LocalDateTime} objects.
     * @throws ParseException if any of the strings in the list is not a valid date.
     */
    public static List<LocalDate> convertToDateList(String args) throws ParseException {
        String[] splitArgs = args.split("\\s+");
        List<LocalDate> dateList = new ArrayList<>();
        for (String s : splitArgs) {
            dateList.add(ParserUtil.parseAppointmentDate(s)); // This can throw ParseException
        }
        return dateList;
    }
}
