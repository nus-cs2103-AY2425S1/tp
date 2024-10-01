package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Rsvp {

    public static final String MESSAGE_CONSTRAINTS =
            "RSVP Status should be 'ACCEPTED', 'DECLINED'";
    private static final String[] RSVP_STATUS = {"PENDING", "ACCEPTED", "DECLINED"};
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private String rsvp;

    public Rsvp(String rsvp) {
        requireNonNull(rsvp);
        checkArgument(isValidRsvp(rsvp), MESSAGE_CONSTRAINTS);
        this.rsvp = rsvp;
    }

    public static boolean isValidRsvp(String str) {

        if (!str.matches(VALIDATION_REGEX)) {
            return false;
        }

        for (String status : RSVP_STATUS) {
            if (status.equalsIgnoreCase(str)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return rsvp;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Rsvp)) {
            return false;
        }

        Rsvp otherName = (Rsvp) other;
        return rsvp.equals(otherName.rsvp);
    }

    @Override
    public int hashCode() {
        return rsvp.hashCode();
    }

}
