package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the relation of a person to the wedding.
 * The relation can be one of the following: "H" for husband, "W" for wife, "U" for unknown.
 * This class enforces the following constraints:
 * Relation should be either "H", "W", or "U".</li>
 * The relation should be represented with only one of the aforementioned characters.</li>
 */
public class Relation {

    public static final String MESSAGE_CONSTRAINTS =
            "Relation should be 'H' for husband, 'W' for wife or 'U' for unknown!";

    /**
     * Represents the relation of a guest to the wedding.
     *
     * The possible statuses are:
     * - H: Husband
     * - W: Wife
     * - U: Unknown
     */
    public enum Status {
        H, W, U
    }

    /**
     * Regular expression that validates a string to only contain one alphabetic
     * characters. The string must be an alphabetic character
     * (either uppercase or lowercase) H, W or U.
     * Example valid inputs:
     * - "H"
     * - "U"
     * Example invalid inputs:
     * - " H" (starts with a space)
     * - "a" (contains an alphabet that is not H, W or U)
     * - "H " (contains more than 1 character)
     */
    public static final String VALIDATION_REGEX = "[\\p{Alpha}]";

    public final String relation;

    /**
     * Constructs an {@code Relation} object with the specified relation.
     *
     * @param relation A valid relation status.
     * @throws NullPointerException     if the given relation is null.
     * @throws IllegalArgumentException if the given relation is invalid.
     */
    public Relation(String relation) {
        requireNonNull(relation);
        checkArgument(isValidRelation(relation), MESSAGE_CONSTRAINTS);
        this.relation = relation.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid relation.
     *
     * @param str The string to be validated.
     * @return true if the string is a valid relation; false otherwise.
     */
    public static boolean isValidRelation(String str) {

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
     * Returns the string representation of the relation.
     *
     * @return The relation as a string.
     */
    @Override
    public String toString() {
        return relation;
    }

    /**
     * Returns true if this Relation is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if the other object is an instance of Relation and has the same status; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Relation otherName)) {
            return false;
        }

        return relation.equals(otherName.relation);
    }

    /**
     * Returns the hash code of this Relation.
     *
     * @return The hash code of the relation.
     */
    @Override
    public int hashCode() {
        return relation.hashCode();
    }

}
