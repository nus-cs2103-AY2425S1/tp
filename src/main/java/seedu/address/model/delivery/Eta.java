package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a delivery's Estimated Time of Arrival (ETA).
 * Guarantees: is valid as declared in {@link #isValidEta(String)}
 */
public class Eta {
    public static final String MESSAGE_CONSTRAINTS =
            "Incorrect ETA format. Expected format: YYYY-MM-DD";
    public final LocalDate value;

    /**
     * Constructs a {@code Eta}.
     *
     * @param eta A valid ETA.
     */
    public Eta(String eta) {
        requireNonNull(eta);
        checkArgument(isValidEta(eta), MESSAGE_CONSTRAINTS);
        value = LocalDate.parse(eta);
    }

    /**
     * Returns true if a given string is a valid eta.
     */
    public static boolean isValidEta(String test) {
        try {
            LocalDate parsedEta = LocalDate.parse(test);
            if (parsedEta.toString().charAt(0) == '-') {
                throw new DateTimeParseException("", test, 0);
            }
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instance of handles nulls
        if (!(other instanceof Eta)) {
            return false;
        }

        Eta otherEta = (Eta) other;
        return value.equals(otherEta.value);
    }

    @Override
    public String toString() {
        return "ETA: " + value;
    }
}
