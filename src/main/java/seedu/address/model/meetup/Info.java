package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a meetup's details in the meetup list.
 */
public class Info {

    public static final String MESSAGE_CONSTRAINTS =
            "Meetup details should not be blank.";

    /*
     * Must have at least one non-whitespace character.
     */
    public static final String VALIDATION_REGEX = "(.|\\s)*\\S(.|\\s)*";

    public final String meetUpInfo;

    /**
     * Constructs a {@code Info}.
     *
     * @param meetUpInfo A valid meetup detail.
     */
    public Info(String meetUpInfo) {
        requireNonNull(meetUpInfo);
        checkArgument(isValidMeetUpInfo(meetUpInfo), MESSAGE_CONSTRAINTS);
        this.meetUpInfo = meetUpInfo;
    }

    /**
     * Returns true if a given string is a valid info.
     */
    public static boolean isValidInfo(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidMeetUpInfo(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return meetUpInfo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Info)) {
            return false;
        }

        Info otherInfo = (Info) other;
        return meetUpInfo.equals(otherInfo.meetUpInfo);
    }

    @Override
    public int hashCode() {
        return meetUpInfo.hashCode();
    }
}
