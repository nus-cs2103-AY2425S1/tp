package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Eta {
    public static final String MESSAGE_CONSTRAINTS =
            "Incorrect ETA format. Expected format: YYYY-MM-DD";
    public final LocalDate value;

    public Eta(String eta) {
        requireNonNull(eta);
        checkArgument(isValidEta(eta), MESSAGE_CONSTRAINTS);
        value = LocalDate.parse(eta);
    }

    public static boolean isValidEta(String test) {
        try {
            LocalDate parsedEta = LocalDate.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ETA: " + value;
    }
}
