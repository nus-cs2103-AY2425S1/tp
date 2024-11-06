package seedu.address.logic;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.model.person.Subject;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
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
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(person.getName())
                .append("\n Phone: ")
                .append(person.getPhone())
                .append("\n Email: ")
                .append(person.getEmail())
                .append("\n Address: ")
                .append(person.getAddress())
                .append("\n Hours: ")
                .append(person.getHours());

        builder.append("\n Subjects: ");
        person.getSubjects().forEach(p -> builder.append(p).append("; "));
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person, List<Map.Entry<? extends Person, Subject>> associatedPeople) {
        final StringBuilder builder = new StringBuilder();
        builder.append(format(person));

        if (person.isTutor()) {
            builder.append("\n Tutees:\n");
        } else {
            builder.append("\n Tutors:\n");
        }

        associatedPeople.forEach(p -> builder.append("\t").append(p.getKey().getName()).append(" - ")
                .append(p.getValue()).append("\n"));
        return builder.toString();
    }

    /**
     * Formats the {@code lesson} for display to the user.
     */
    public static String format(Lesson lesson) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\n Tutor: ")
                .append(lesson.getTutorName())
                .append("\n Tutee: ")
                .append(lesson.getTuteeName())
                .append("\n Subject: ")
                .append(lesson.getSubject());
        return builder.toString();
    }
}
