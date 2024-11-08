
package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.person.EditCommand;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.wedding.Wedding;

/**
 * Container for user visible messages.
 */
public class Messages {

    /**
     * General Messages
     */
    public static final String MESSAGE_CLEAR_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_ENTER_VALID_INDEX = "Please enter a valid index from %d to %d.";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_NOTHING_TO_PERFORM_ON = "There are no %s to %s.";
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";

    /**
     * Messages specific to Person
     */
    public static final String MESSAGE_ADD_PERSON_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the Wedlinker.";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_INVALID_EDIT_PERSON_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
    public static final String MESSAGE_LIST_PERSON_SUCCESS = "Listed all persons";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid. \n"
            + MESSAGE_ENTER_VALID_INDEX;
    public static final String MESSAGE_PERSON_NOT_EDITED = "At least one field to edit must be provided.";


    /**
     * Messages specific to Tag
     */
    public static final String MESSAGE_ASSIGN_TAG_SUCCESS = "Added tag(s) %1$s to %2$s.";
    public static final String MESSAGE_CREATE_TAG_SUCCESS = "New tag added: %1$s.";
    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s.";
    public static final String MESSAGE_DELETE_TAG_FAILURE_STILL_TAGGED = "The Tag: %1$s is still used.";
    public static final String MESSAGE_DELETE_TAG_FAILURE_NOT_FOUND = "The Tag: %1$s does not exist.";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in the Wedlinker.";
    public static final String MESSAGE_FORCE_TAG_TO_CONTACT = "Use f/ to force the tagging of contacts."
            + " This will create the require Tags.";
    public static final String MESSAGE_FORCE_DELETE_TAG = "Use f/ to force the deletion of tags."
            + " This will unassign all people currently with the Tag.";
    public static final String MESSAGE_LIST_TAG_SUCCESS = "Listed all tags.";
    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed tag(s) %1$s from %2$s.";
    public static final String MESSAGE_TAG_NOT_FOUND = "One or more specified tags do not exist in the Wedlinker.";
    public static final String MESSAGE_TAG_NOT_FOUND_IN_CONTACT = "Some tags were not found in the person's tag list.";

    /**
     * Messages specific to Wedding
     */
    public static final String MESSAGE_ASSIGN_WEDDING_SUCCESS = "Assigned wedding(s) %1$s to %2$s.";
    public static final String MESSAGE_CREATE_WEDDING_SUCCESS = "New wedding created: %1$s.";
    public static final String MESSAGE_DELETE_WEDDING_SUCCESS = "Deleted Wedding: %1$s.";
    public static final String MESSAGE_DELETE_WEDDING_FAILURE_STILL_USED = "The Wedding: %1$s is still used.";
    public static final String MESSAGE_DELETE_WEDDING_FAILURE_NOT_FOUND = "The Wedding: %1$s does not exist.";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists in the Wedlinker.";
    public static final String MESSAGE_EDIT_WEDDING_SUCCESS = "Edited Wedding: %1$s.";
    public static final String MESSAGE_WEDDING_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_FORCE_ASSIGN_WEDDING_TO_CONTACT = "Use f/ to force the assignment of wedding(s)."
            + " This will automatically help you create the wedding required.";
    public static final String MESSAGE_FORCE_DELETE_WEDDING = "Use f/ to force the deletion of wedding."
            + " This will unassign all people currently assigned to this Wedding.";
    public static final String MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX = "The wedding index provided is invalid.";
    public static final String MESSAGE_LIST_WEDDING_SUCCESS = "Listed all weddings.";
    public static final String MESSAGE_REMOVE_WEDDING_SUCCESS = "Removed wedding(s) %1$s from %2$s.";
    public static final String MESSAGE_WEDDING_NOT_FOUND = "One or more specified weddings do not exist in "
            + "the Wedlinker.";
    public static final String MESSAGE_WEDDING_NOT_FOUND_IN_CONTACT = "Some weddings were not found in "
            + "the person's wedding list.";

    /**
     * Messages specific to Task
     */
    public static final String MESSAGE_ASSIGN_TASK_SUCCESS = "Added task(s) %1$s to %2$s.";
    public static final String MESSAGE_DUPLICATE_TASK_IN_PERSON = "Task '%s' is already assigned to %s.";
    public static final String MESSAGE_DUPLICATE_TASK_IN_WEDLINKER = "This task already exists in the Wedlinker.";
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s.";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided (%d) is invalid. \n"
            + MESSAGE_ENTER_VALID_INDEX;
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format. Expected format: yyyy-MM-dd";
    public static final String MESSAGE_LIST_TASK_SUCCESS = "Listed all tasks.";
    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked task: %1$s.";
    public static final String MESSAGE_ONLY_VENDOR_CAN_BE_ASSIGNED_TASK = "%1$s is not a Vendor, "
            + "tasks can only be assigned to vendors.";
    public static final String MESSAGE_TASK_ADDED_SUCCESS = "New task(s) added: %1$s.";
    public static final String MESSAGE_TASK_ALREADY_COMPLETED = "Some tasks specified are already completed.";
    public static final String MESSAGE_TASK_ALREADY_UNCOMPLETED = "Some tasks specified are already uncompleted.";
    public static final String MESSAGE_TASK_NOT_FOUND_IN_AB = "Task does not exist in the address book.";
    public static final String MESSAGE_TASK_NOT_FOUND_IN_CONTACT = "Some task(s) "
            + "were not found in the person's task list.";
    public static final String MESSAGE_TO_BEFORE_FROM_INVALID = "\"From\" date must be before \"To\" date.";
    public static final String MESSAGE_UNASSIGN_TASK_SUCCESS = "Removed task(s) %1$s from %2$s.";
    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmarked task: %1$s.";

    /**
     * Messages specific to Vendor
     */
    public static final String MESSAGE_ADD_VENDOR_SUCCESS = "%1$s has been added as a vendor.";
    public static final String MESSAGE_DUPLICATE_VENDOR = "%1$s is already a vendor.";
    public static final String MESSAGE_FORCE_UNASSIGN_VENDOR = "Use f/ to force the unassignment of vendors."
            + " This will unassign all tasks currently with the Vendor.";
    public static final String MESSAGE_UNASSIGN_VENDOR_FAILURE_TASK_EXISTS = "The Vendor: %1$s still has tasks"
            + " assigned to them.";
    public static final String MESSAGE_UNASSIGN_VENDOR_FAILURE_NOT_VENDOR = "%1$s is not a vendor.";
    public static final String MESSAGE_UNASSIGN_VENDOR_SUCCESS = "%1$s has been unassigned and is no longer a vendor.";

    /**
     * Messages specific to Find
     */
    public static final String MESSAGE_FIND_ADDRESS_PERSON_SUCCESS = "Search for address containing \"%s\" "
            + " was successful. Showing results:";
    public static final String MESSAGE_FIND_EMAIL_PERSON_SUCCESS = "Search for email containing \"%s\" was successful. "
            + " Showing results:";
    public static final String MESSAGE_FIND_NAME_PERSON_SUCCESS = "Search for name containing \"%s\" was successful. "
            + " Showing results:";
    public static final String MESSAGE_FIND_PHONE_PERSON_SUCCESS = "Search for phone number containing \"%s\" "
            + " was successful. Showing results:";
    public static final String MESSAGE_FIND_TAG_PERSON_SUCCESS = "Search for tag containing \"%s\" was successful. "
            + " Showing results:";
    public static final String MESSAGE_FIND_TASK_PERSON_SUCCESS = "Search for task containing \"%s\" was successful. "
            + " Showing results:";
    public static final String MESSAGE_FIND_WEDDING_PERSON_SUCCESS = "Search for wedding(s) containing "
            + "\"%s\" was successful. Showing results:";
    public static final String MESSAGE_FIND_PERSON_UNSUCCESSFUL = "No contacts found.";

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
        return String.valueOf(tag.getTagName());
    }

    public static String format(Wedding wedding) {
        return wedding.getWeddingName().toString();
    }

    public static String format(Task task) {
        return task.getDescription();
    }
}
