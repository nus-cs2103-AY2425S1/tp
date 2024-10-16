package seedu.address.logic.validator;

public class NameValidator extends Validator<String> {
    public static final String VALIDATION_REGEX = "[ A-Za-z0-9_&]*";
    private static NameValidator instance;
    public static NameValidator of() {
        if (instance == null) {
            instance = new NameValidator();
        }
        return instance;
    }
    private NameValidator() {}
    @Override
    public boolean validate(String input) {
        return input.matches(VALIDATION_REGEX);
    }
}
