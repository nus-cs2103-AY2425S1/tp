package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the RSVP status of a person to the wedding.
 * The RSVP status can be one of the following: 'P' for pending, 'A' for accepted or 'D' for declined.
 * This class enforces the following constraints:
 * RSVP should be either "P", "A", or "D".</li>
 */
public class Rsvp {

    public static final String MESSAGE_CONSTRAINTS =
            "Relation should be 'P' for pending, 'A' for accepted or 'D' for declined!";
    /**
     * Represents the RSVP status of a guest.
     *
     * The possible statuses are:
     * - P: Pending
     * - A: Accepted
     * - D: Declined
     */
    public enum Status {
        P, A, D;
    }

    public static final String VALIDATION_REGEX = "[\\p{Alpha}]";

    public final String rsvp;

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
        this.rsvp = rsvp.toUpperCase();
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
        if (rsvp.equals("P")) {
            return "Pending";

        } else if (rsvp.equals("A")) {
            return "Accepted";

        } else {
            return "Declined";
        }
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

        return rsvp.equals(otherName.rsvp);
    }

    /**
     * Returns the hash code of this Rsvp.
     *
     * @return The hash code of the RSVP status.
     */
    @Override
    public int hashCode() {
        return rsvp.hashCode();
    }

}
