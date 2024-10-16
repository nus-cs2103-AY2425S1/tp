package seedu.address.logic.validator;

public class RoleValidator extends Validator<String> {
    public static final String VALIDATION_REGEX = "[ A-Za-z0-9_&/]*";
    private static RoleValidator instance;
    public static RoleValidator of() {
        if (instance == null) {
            instance = new RoleValidator();
        }
        return instance;
    }
    private RoleValidator() {}
    @Override
    public boolean validate(String input) {
        return input.matches(VALIDATION_REGEX);
    }
}
