package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Person's major in the address book.
 */
public class Major {

    public static final String MESSAGE_CONSTRAINTS =
            "Major is invalid. Please choose one of the following: ‘cs’, ‘bza’, ‘ceg’, ‘isys’, ‘isec’.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[a-zA-Z]+";

    public static final List<String> VALID_MAJORS = Arrays.asList("cs", "bza", "ceg", "isys", "isec");
    private String major;

    /**
     * Constructs a {@code Major}.
     *
     * @param major a valid major.
     */
    public Major(String major) {
        requireNonNull(major);
        checkArgument(isValidMajor(major), MESSAGE_CONSTRAINTS);
        this.major = major;
    }

    /**
     * Returns if a given major string is a valid role.
     */
    public static boolean isValidMajor(String test) {
        return test.matches(VALIDATION_REGEX) && VALID_MAJORS.contains(test);
    }

    @Override
    public String toString() {
        return this.major;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceOf handles nulls
        if (!(other instanceof Major)) {
            return false;
        }

        Major otherMajor = (Major) other;
        return this.major.equals(otherMajor.major);
    }

    @Override
    public int hashCode() {
        return this.major.hashCode();
    }
}

