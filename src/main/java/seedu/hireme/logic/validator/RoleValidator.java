package seedu.hireme.logic.validator;

/**
 * Validates role strings
 */
public class RoleValidator extends Validator<String> {
    public static final String VALIDATION_REGEX = "[ A-Za-z0-9/]*";
    private static RoleValidator instance;
    private RoleValidator() {}

    /**
     * Returns an instance of {@code RoleValidator}.
     * Ensures only one instance of {@code RoleValidator} is created.
     *
     * @return RoleValidator the instance of {@code RoleValidator}.
     */
    public static RoleValidator of() {
        if (instance == null) {
            instance = new RoleValidator();
        }
        return instance;
    }
    @Override
    public boolean validate(String input) {
        return !input.trim().isEmpty() && input.matches(VALIDATION_REGEX);
    }
}
