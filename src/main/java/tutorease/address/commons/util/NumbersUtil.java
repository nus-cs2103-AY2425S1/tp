package tutorease.address.commons.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.logic.commands.AddLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods for parsing numbers.
 */
public class NumbersUtil {
    private static Logger logger = LogsCenter.getLogger(AddLessonCommand.class);

    /**
     * Parses a string into an integer.
     *
     * @param value   The string to be parsed.
     * @param message The message to be displayed if the parsing fails.
     * @return The parsed integer.
     * @throws ParseException If the string cannot be parsed into an integer.
     */
    public static int parseInt(String value, String message) throws ParseException {
        logger.log(Level.INFO, "Parsing integer from string: " + value);
        int parsedValue;
        try {
            parsedValue = Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Failed to parse integer from string: " + value);
            throw new ParseException(message);
        }
        return parsedValue;
    }
    /**
     * Parses a string into a double.
     *
     * @param value   The string to be parsed.
     * @param message The message to be displayed if the parsing fails.
     * @return The parsed double.
     * @throws ParseException If the string cannot be parsed into a double.
     */
    public static double parseDouble(String value, String message) throws ParseException {
        logger.log(Level.INFO, "Parsing double from string: " + value);
        double parsedValue;
        try {
            parsedValue = Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Failed to parse double from string: " + value);
            throw new ParseException(message);
        }
        return parsedValue;
    }
}
