package seedu.address.logic;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.student.Days;
import seedu.address.model.student.Student;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "The student index provided is invalid";
    public static final String MESSAGE_STUDENTS_LISTED_OVERVIEW = "%1$d students listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_HAS_CLASHES = "\nYou have %d other students with clashing schedule:\n%s";
    public static final String MESSAGE_REMINDER = "Reminder(s) for %s:\n";


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
     * Formats the {@code student} for display to the user.
     */
    public static String format(Student student) {
        final StringBuilder builder = new StringBuilder();
        builder.append(student.getName())
                .append("; Phone: ")
                .append(student.getPhone())
                .append("; Email: ")
                .append(student.getEmail())
                .append("; Address: ")
                .append(student.getAddress())
                .append("; Schedule: ")
                .append(student.getSchedule())
                .append("; Subject: ")
                .append(student.getSubject())
                .append("; Fee: ")
                .append(student.getRate())
                .append("; PaidAmount: ")
                .append(student.getPaidAmount())
                .append("; Owed: ")
                .append(student.getOwedAmount());

        return builder.toString();
    }

    /**
     * Formats a list of students into a single string representation based on the specified formatter function.
     * @param students A list of {@link Student} objects to be formatted.
     * @param formatter A {@link Function} that takes a {@link Student} and returns a {@link String} representation
     *                  of that student. This allows for custom formatting of each student in the list.
     * @return A {@link String} that concatenates the formatted representations of all students in the list.
     *          If the input list is empty, an empty string will be returned.
     */
    public static String listFormat(List<Student> students, Function<Student, String> formatter) {
        StringBuilder result = new StringBuilder();

        for (Student student : students) {
            result.append(formatter.apply(student));
        }

        return result.toString();
    }

    /**
     * Generates a warning message for schedule clashes.
     *
     * @param clashes The number of students that have schedule clashes with the edited student.
     * @param clashingStudents A list of students that have schedule clashes with the edited student.
     * @return A formatted warning message indicating the number of clashes and details of the clashing students.
     */
    public static String getWarningMessageForClashes(long clashes, List<Student> clashingStudents) {
        return String.format(
                MESSAGE_HAS_CLASHES,
                clashes,
                listFormat(
                        clashingStudents,
                        student -> String.format("Name: %s | Schedule: %s\n", student.getName(), student.getSchedule()
                        )
                )
        );
    }

    /**
     * Generates a reminder message for lessons scheduled today.
     *
     * @param day The day of the week for today
     * @param reminder A list of students who has lesson for today.
     * @return A formatted reminder message indicating the day of today and details of lesson for today.
     */
    public static String getReminderMessage(Days day, List<Student> reminder) {
        if (reminder.isEmpty()) {
            return "Congratulations! You have no class for today!";
        }

        AtomicInteger index = new AtomicInteger(1); // Start the index at 1

        String studentList = Messages.listFormat(reminder, student ->
                String.format("%d) %s, %s, %s\n",
                        index.getAndIncrement(),
                        student.getName(),
                        student.getSchedule().getTime(),
                        student.getSubject())
        );

        return String.format(MESSAGE_REMINDER, day) + studentList;
    }


}
