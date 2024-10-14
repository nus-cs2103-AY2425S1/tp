package seedu.address.logic.validator;

public class NameValidator extends Validator<String> {
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
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
