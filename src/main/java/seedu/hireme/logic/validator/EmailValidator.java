package seedu.hireme.logic.validator;

import java.util.logging.Logger;

import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.logic.parser.AddressBookParser;

/**
 * Validates email strings
 */
public class EmailValidator extends Validator<String> {
    private static final String SPECIAL_CHARACTERS = "+_.-";
    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+"; // alphanumeric characters except underscore
    private static final String LOCAL_PART_REGEX = "^" + ALPHANUMERIC_NO_UNDERSCORE + "([" + SPECIAL_CHARACTERS + "]"
            + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";
    private static final String DOMAIN_LAST_PART_REGEX = "(" + DOMAIN_PART_REGEX + "){2,}$"; // At least two chars
    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)+" + DOMAIN_LAST_PART_REGEX;
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);
    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;
    private static EmailValidator instance;

    private EmailValidator() {}

    /**
     * Returns an instance of {@code EmailValidator}.
     * Ensures only one instance of {@code EmailValidator} is created.
     *
     * @return EmailValidator the instance of {@code EmailValidator}.
     */
    public static EmailValidator of() {
        if (instance == null) {
            instance = new EmailValidator();
        }
        return instance;
    }
    @Override
    public boolean validate(String input) {
        if (input == null) {
            logger.warning("Email input is null");
            return false;
        }
        return !input.trim().isEmpty() && input.matches(VALIDATION_REGEX);
    }
}
