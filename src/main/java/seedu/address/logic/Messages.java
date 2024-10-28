
package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format. Expected format: yyyy-MM-dd";

    public static final String MESSAGE_INVALID_TASK_TYPE = "Unknown task type: %1$s. "
            + "Expected one of: todo, deadline, event.";

    public static final String MESSAGE_INVALID_DEADLINE_FORMAT = "Invalid deadline format. "
            + "Usage: create-task tk/deadline [description] /by [date]";
    public static final String MESSAGE_INVALID_EVENT_FORMAT = "Invalid event format."
            + " Usage: create-task tk/event [description] /from [start] /to [end]";

    public static final String MESSAGE_INCOMPLETE_TASK_DESCRIPTION = "Task description is incomplete. "
            + "Expected format: tk/[task type] [task details].";
    public static final String MESSAGE_TO_BEFORE_FROM_INVALID = "\"From\" date must be before \"To\" date.";
    public static final String MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX = "The wedding index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_TAG_NOT_FOUND = "One or more specified tags do not exist in the Wedlinker.";
    public static final String MESSAGE_TAG_NOT_FOUND_IN_CONTACT = "Some tags were not found in the person's tag list.";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added tag(s) %1$s to %2$s.";
    public static final String MESSAGE_ADD_WEDDING_SUCCESS = "Added wedding(s) %1$s to %2$s.";
    public static final String MESSAGE_REMOVE_WEDDING_SUCCESS = "Removed wedding(s) %1$s from %2$s.";
    public static final String MESSAGE_WEDDING_NOT_FOUND = "One or more specified weddings do not exist in "
            + "the Wedlinker.";
    public static final String MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT = "Some weddings were not found in "
            + "the person's wedding list.";
    public static final String MESSAGE_FORCE_ASSIGN_WEDDING_TO_CONTACT = "Use f/ to force the assignment of wedding(s)."
            + " This will create the required Wedding objects.";
    public static final String MESSAGE_FORCE_DELETE_WEDDING = "Use f/ to force the deletion of wedding."
            + " This will unassign all people for the Wedding object.";
    public static final String MESSAGE_FORCE_TAG_TO_CONTACT = "Use f/ to force the tagging of contacts."
            + " This will create the require Tags.";
    public static final String MESSAGE_FORCE_DELETE_TAG = "Use f/ to force the deletion of tags."
            + " This will unassign all contacts with the Tag.";

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
        builder.append("; Weddings: ");
        person.getWeddings().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code tag} for display to the user.
     */
    public static String format(Tag tag) {
        final StringBuilder builder = new StringBuilder();
        builder.append(tag.getTagName());
        return builder.toString();
    }

    public static String format(Wedding wedding) {
        return wedding.getWeddingName().toString();
    }

    public static String format(Task task) {
        return task.getDescription();
    }
}
