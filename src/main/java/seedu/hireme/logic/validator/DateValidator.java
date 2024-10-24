package seedu.hireme.logic.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Validates date strings
 */
public class DateValidator extends Validator<String> {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uu")
            .withResolverStyle(ResolverStyle.STRICT);
    private static DateValidator instance;

    private DateValidator() {}

    /**
     * Returns an instance of {@code DateValidator}.
     * Ensures only one instance of {@code DateValidator} is created.
     *
     * @return the instance of {@code DateValidator}.
     */
    public static DateValidator of() {
        if (instance == null) {
            instance = new DateValidator();
        }
        return instance;
    }

    /**
     * Validates the given {@code input} string to check if it follows the expected date format.
     * The expected date format is {@code "dd/MM/yy"}.
     *
     * @param input the string representation of the date to validate.
     * @return {@code true} if the input string is a valid date in the expected format,
     *         {@code false} otherwise.
     */
    @Override
    public boolean validate(String input) {
        try {
            LocalDate date = LocalDate.parse(input, FORMATTER);
            return date.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
