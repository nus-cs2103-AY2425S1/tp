package seedu.hireme.logic.validator;

import java.util.logging.Logger;

import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.logic.parser.AddressBookParser;

/**
 * Validates name of company strings
 */
public class NameValidator extends Validator<String> {

    /*
     * The name should contain alphanumeric characters and may contain spaces between words.
     * No special characters are allowed, except underscores, ampersand, dot, colon and brackets.
     */
    public static final String VALIDATION_REGEX = "[ A-Za-z0-9_&/.:()]*";
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    private static NameValidator instance;

    private NameValidator() {}

    /**
     * Returns an instance of {@code NameValidator}.
     * Ensures only one instance of {@code NameValidator} is created.
     *
     * @return NameValidator the instance of {@code NameValidator}.
     */
    public static NameValidator of() {
        if (instance == null) {
            instance = new NameValidator();
        }
        return instance;
    }
    @Override
    public boolean validate(String input) {
        if (input == null) {
            logger.warning("Name input is null");
            return false;
        }
        return !input.trim().isEmpty() && input.matches(VALIDATION_REGEX);
    }
}
