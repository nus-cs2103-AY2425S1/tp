package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "The student provided"
            + "at index %1$d is invalid";
    public static final String MESSAGE_INVALID_INDEX_SHOWN = "The target provided at index/indices %1$s is invalid";
    public static final String MESSAGE_STUDENTS_LISTED_OVERVIEW = "%1$d students listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS = "Multiple values specified for "
            + "the following single-valued field(s): ";
    public static final String MESSAGE_INVALID_CONSULTATION_DISPLAYED_INDEX = "The consultation provided "
            + "at index %1$d is invalid";
    public static final String MESSAGE_INVALID_LESSON_DISPLAYED_INDEX = "The lesson provided at index %1$d is invalid";
    public static final String MESSAGE_LESSONS_LISTED_OVERVIEW = "%1$d lessons listed!";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields = Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code student} for display to the user.
     */
    public static String format(Student student) {
        final StringBuilder builder = new StringBuilder();
        builder.append(student.getName())
                .append("; Phone: ")
                .append(student.getPhone())
                .append("; Email: ")
                .append(student.getEmail())
                .append("; Courses: ");
        student.getCourses().forEach(course -> builder.append(course).append(" "));
        return builder.toString().trim();
    }

    /**
     * Formats the {@code consult} for display to the user.
     */
    public static String format(Consultation consult) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(consult.getDate().toString())
                .append("; Time: ")
                .append(consult.getTime().toString());
        return builder.toString();
    }

    /**
     * Formats the {@code lesson} for display to the user.
     */
    public static String format(Lesson lesson) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(lesson.getDate().toString())
                .append("; Time: ")
                .append(lesson.getTime().toString())
                .append("; Students: ");
        lesson.getStudents().forEach(student -> builder.append(student.getName()).append(", "));
        if (!lesson.getStudents().isEmpty()) {
            builder.setLength(builder.length() - 2); // Remove trailing comma and space
        }
        return builder.toString();
    }
}
