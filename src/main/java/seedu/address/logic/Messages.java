package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Teacher;
import seedu.address.model.person.exceptions.InvalidPersonTypeException;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_DUPLICATE_PERSON_DISPLAYED_INDEX = "There was a duplicate index provided";

    public static final String MESSAGE_INVALID_STUDENT_INDEX = "Only Students can have their attendance unmarked";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_ATTENDANCE = "Only students who have attended at "
            + "least one day can be unmarked.";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

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
     * Formats the {@code Student} for display to the user.
     */
    public static String format(Student student) {
        final StringBuilder builder = new StringBuilder();
        builder.append(student.getName())
                .append("; Gender: ")
                .append(student.getGender())
                .append("; Phone: ")
                .append(student.getPhone())
                .append("; Email: ")
                .append(student.getEmail())
                .append("; Address: ")
                .append(student.getAddress())
                .append("; Subject: ")
                .append(student.getSubjects())
                .append("; Classes: ")
                .append(String.join(", ", student.getClasses()))
                .append("; Days attended: ")
                .append(student.getDaysAttended())
                .append("; Tags: ");
        student.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code Teacher} for display to the user.
     */
    public static String format(Teacher teacher) {
        final StringBuilder builder = new StringBuilder();
        builder.append(teacher.getName())
                .append("; Gender: ")
                .append(teacher.getGender())
                .append("; Phone: ")
                .append(teacher.getPhone())
                .append("; Email: ")
                .append(teacher.getEmail())
                .append("; Address: ")
                .append(teacher.getAddress())
                .append("; Subject: ")
                .append(teacher.getSubjects())
                .append("; Classes: ")
                .append(String.join(", ", teacher.getClasses()))
                .append("; Tags: ");
        teacher.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code Person} for display to the user.
     *
     * @param person The person to format.
     * @return A formatted string representing the person.
     * @throws InvalidPersonTypeException if the person is not a Student or Teacher.
     */
    public static String format(Person person) {
        if (person instanceof Student) {
            return format((Student) person);
        } else if (person instanceof Teacher) {
            return format((Teacher) person);
        } else {
            throw new InvalidPersonTypeException();
        }
    }
}
