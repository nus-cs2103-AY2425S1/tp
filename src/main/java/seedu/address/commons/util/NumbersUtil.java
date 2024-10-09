package seedu.address.commons.util;

import seedu.address.logic.parser.exceptions.ParseException;

public class NumbersUtil {
    public static int parseInt(String value, String message) throws ParseException {
        int parsedValue;
        try {
            parsedValue = Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ParseException(message);
        }
        return parsedValue;
    }

    public static double parseDouble(String value, String message) throws ParseException {
        double parsedValue;
        try {
            parsedValue = Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new ParseException(message);
        }
        return parsedValue;
    }
}
