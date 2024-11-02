package seedu.hireme.logic.validator;

import java.util.logging.Logger;

import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.logic.parser.AddressBookParser;
import seedu.hireme.model.internshipapplication.Status;

/**
 * Validates status of internship applications
 */
public class StatusValidator extends Validator<String> {
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);
    private static StatusValidator instance;

    private StatusValidator() {}

    /**
     * Returns an instance of {@code StatusValidator}.
     * Ensures only one instance of {@code StatusValidator} is created.
     *
     * @return StatusValidator the instance of {@code StatusValidator}.
     */
    public static StatusValidator of() {
        if (instance == null) {
            instance = new StatusValidator();
        }
        return instance;
    }

    private static boolean isValid(String status) {
        try {
            Status.getValueOf(status);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
    @Override
    public boolean validate(String input) {
        if (input == null) {
            logger.warning("Status input is null");
            return false;
        }
        return !input.trim().isEmpty() && isValid(input);
    }
}
