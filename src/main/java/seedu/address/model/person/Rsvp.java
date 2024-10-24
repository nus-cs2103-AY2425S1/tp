package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the RSVP status for a person.
 * The RSVP status can be one of the following: "PENDING", "ACCEPTED", "DECLINED".
 * This class enforces the following constraints:
 * RSVP Status should be either "PENDING", "ACCEPTED", or "DECLINED".</li>
 * The first character of the RSVP status must not be a whitespace.</li>
 */
public class Rsvp {

    public static final String MESSAGE_CONSTRAINTS =
            "RSVP Status should be 'PENDING', 'ACCEPTED' or 'DECLINED'";

    /**
     * Represents the RSVP status of a guest.
     *
     * The possible statuses are:
     * - PENDING
     * - ACCEPTED
     * - DECLINED
     */
    public enum Status {
        PENDING, ACCEPTED, DECLINED
    }

    /**
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";

    public final String value;

    /**
     * Constructs an {@code Rsvp} object with the specified RSVP status.
     *
     * @param rsvp A valid RSVP status.
     * @throws NullPointerException     if the given rsvp is null.
     * @throws IllegalArgumentException if the given rsvp is invalid.
     */
    public Rsvp(String rsvp) {
        requireNonNull(rsvp);
        checkArgument(isValidRsvp(rsvp), MESSAGE_CONSTRAINTS);
        this.value = rsvp.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid RSVP status.
     *
     * @param str The string to be validated.
     * @return true if the string is a valid RSVP status; false otherwise.
     */
    public static boolean isValidRsvp(String str) {

        if (!str.matches(VALIDATION_REGEX)) {
            return false;
        }

        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(str)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the string representation of the RSVP status.
     *
     * @return The RSVP status as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if this Rsvp is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if the other object is an instance of Rsvp and has the same status; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rsvp otherName)) {
            return false;
        }

        return value.equals(otherName.value);
    }

    /**
     * Returns the hash code of this Rsvp.
     *
     * @return The hash code of the RSVP status.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
