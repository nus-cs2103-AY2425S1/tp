package seedu.hireme.logic.validator;

/**
 * Validates name of company strings
 */
public class NameValidator extends Validator<String> {
    public static final String VALIDATION_REGEX = "[ A-Za-z0-9_&/.:()]*";

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
        return !input.trim().isEmpty() && input.matches(VALIDATION_REGEX);
    }
}
