package seedu.address.logic.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator extends Validator<String> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");
    private static DateValidator instance;
    public static DateValidator of() {
        if (instance == null) {
            instance = new DateValidator();
        }
        return instance;
    }
    private DateValidator() {}
    @Override
    public boolean validate(String input) {
        try {
            LocalDate.parse(input, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
