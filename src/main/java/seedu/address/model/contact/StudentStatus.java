package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.List;

/**
 * Represents a Contact's student status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStudentStatus(String)}
 */
public class StudentStatus {

    public static final String UNDERGRADUATE_LOWERCASE = "undergraduate";
    public static final String UNDERGRADUATE_OFFICAL_CASE = toOfficialCase(UNDERGRADUATE_LOWERCASE);
    public static final String UNDERGRADUATE_START_YEAR = "1";
    public static final String UNDERGRADUATE_END_YEAR = "6"; // 6 - maximum candidature for medicine students
    public static final String MASTERS_LOWERCASE = "masters";
    public static final String MASTERS_OFFICIAL_CASE = toOfficialCase(MASTERS_LOWERCASE);
    public static final String PHD_LOWERCASE = "phd";
    public static final String PHD_OFFICIAL_CASE = toOfficialCase(PHD_LOWERCASE);

    public static final String MESSAGE_CONSTRAINTS = "Student statuses must take one of the values below:\n"
            + "1. " + UNDERGRADUATE_OFFICAL_CASE + " x, where x: An integer value between "
            + UNDERGRADUATE_START_YEAR + " and " + UNDERGRADUATE_END_YEAR + " inclusive\n"
            + "2. " + MASTERS_OFFICIAL_CASE + "\n"
            + "3. " + PHD_OFFICIAL_CASE + "\n"
            + "For " + UNDERGRADUATE_OFFICAL_CASE + ", There must only be one whitespace between the word "
            + "and the integer value. E.g. Undergraduate 4";

    public static final String VALIDATION_REGEX =
            String.format("^((%s \\b[%s-%s]\\b)|%s|%s)$",
                    UNDERGRADUATE_LOWERCASE, UNDERGRADUATE_START_YEAR, UNDERGRADUATE_END_YEAR,
                    MASTERS_LOWERCASE, PHD_LOWERCASE);

    public final String value;

    /**
     * Constructs an {@code StudentStatus}.
     *
     * @param studentStatus A valid student status.
     */
    public StudentStatus(String studentStatus) {
        requireNonNull(studentStatus);
        checkArgument(isValidStudentStatus(studentStatus), MESSAGE_CONSTRAINTS);
        value = toOfficialCase(studentStatus.trim()); // add command shouldn't need this. but edit need hmm
    }

    /**
     * Returns true if a given string is a valid student status.
     */
    public static boolean isValidStudentStatus(String test) {
        return test.toLowerCase().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentStatus)) {
            return false;
        }

        StudentStatus otherStudentStatus = (StudentStatus) other;
        return value.equals(otherStudentStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    private static String toOfficialCase(String input) {
        // to check if double spaces okay
        List<String> wordsOfStudentStatus = List.of(input.split(" "));
        assert wordsOfStudentStatus.size() <= 2;

        String studentStatusCategory = wordsOfStudentStatus.get(0);
        assert (studentStatusCategory.equalsIgnoreCase(UNDERGRADUATE_LOWERCASE)
                || studentStatusCategory.equalsIgnoreCase(MASTERS_LOWERCASE)
                || studentStatusCategory.equalsIgnoreCase(PHD_LOWERCASE));

        switch (studentStatusCategory.toLowerCase()) {
        case UNDERGRADUATE_LOWERCASE:
            // consider undergraduate and undergraduate 1
            return wordsOfStudentStatus.stream().reduce(
                    "", (finalString, word) -> finalString + word.toLowerCase().replaceFirst("u", "U") + " ")
                    .trim();
        case MASTERS_LOWERCASE:
            return MASTERS_LOWERCASE.replaceFirst("m", "M");
        case PHD_LOWERCASE:
            return PHD_LOWERCASE.toUpperCase().replaceFirst("H", "h");
        default:
            assert false;
            return MESSAGE_CONSTRAINTS; // shoudn't come here
        }
    }
}
