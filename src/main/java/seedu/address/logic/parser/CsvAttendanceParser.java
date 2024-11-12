package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses attendance data from a CSV field, extracting the weeks present as a set of integers.
 */
public class CsvAttendanceParser {
    /**
     * Parses the weeksPresent field into a set of integers.
     *
     * @param weeksField string representing weeks present, separated by commas.
     * @return a set of integers representing weeks present.
     * @throws ParseException if any week value is invalid.
     */
    public static Set<Integer> parseWeeksPresent(String weeksField) throws ParseException {
        requireNonNull(weeksField);
        weeksField = weeksField.trim();
        Set<Integer> weeksPresent = new HashSet<>();
        if (weeksField.isEmpty()) {
            return weeksPresent;
        }
        // Split data by coma and parse each week value as an integer.
        String[] weeksArray = weeksField.split(",");
        for (String week : weeksArray) {
            int weekPresent = ParserUtil.parseWeek(week.trim());
            weeksPresent.add(weekPresent);
        }

        return weeksPresent;
    }
}
