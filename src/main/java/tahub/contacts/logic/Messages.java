package tahub.contacts.logic;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tahub.contacts.logic.parser.Prefix;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.person.Person;
import tahub.contacts.model.studentcourseassociation.StudentCourseAssociation;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_COMMAND_MISSING_FIELDS =
            "Invalid command format, required fields are missing! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No student with this "
            + "matriculation number exists!";
    public static final String MESSAGE_COURSE_NOT_FOUND = "No course with this course code exists!";
    public static final String MESSAGE_NO_EXISTING_COURSE = "The course with course code provided does not exist";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code course} for display to the user.
     */
    public static String format(Course course) {
        // Implement the formatting logic for Course objects
        return course.toString();
    }

    /**
     * Formats the {@link StudentCourseAssociation} for display to the user.
     * <p></p>
     * This is in the form {@code <matricNumber>; <tutorialId>-<course>}
     */
    public static String format(StudentCourseAssociation sca) {
        requireNonNull(sca);
        return sca.getStudent().toStringShort()
                + "; " + sca.getTutorial()
                + "-" + sca.getCourse();
    }
}
