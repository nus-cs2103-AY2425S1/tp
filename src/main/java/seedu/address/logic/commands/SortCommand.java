package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Command to sort the contacts in the address book.
 * The contacts can be sorted in either ascending (A-Z) or descending (Z-A) order based on their names.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS_ASC = "Contacts sorted A-Z";
    public static final String MESSAGE_SUCCESS_DESC = "Contacts sorted Z-A";
    public static final String MESSAGE_INVALID_ORDER =
            "Error: Invalid sorting order. Use 'asc' for ascending or 'desc' for descending.";
    public static final String MESSAGE_NO_ORDER = "Error: No sorting order provided. Please specify 'asc' or 'desc'.";

    private final String order;

    /**
     * Constructs a SortCommand with the specified sorting order.
     *
     * @param order The order in which to sort the contacts (either "asc" or "desc").
     */
    public SortCommand(String order) {
        this.order = order.toLowerCase().trim();
    }

    /**
     * Executes the sort command, sorting the contact list based on the specified order.
     *
     * @param model The model containing the list of contacts.
     * @return The result of executing the sort command, including a success message and the sorted contact list.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (order.equals("asc")) {
            model.sortPersonsAsc();
            return new CommandResult(MESSAGE_SUCCESS_ASC);
        } else if (order.equals("desc")) {
            model.sortPersonsDesc();
            return new CommandResult(MESSAGE_SUCCESS_DESC);
        } else {
            return new CommandResult(MESSAGE_INVALID_ORDER);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Check if both references point to the same object
        }
        if (!(obj instanceof SortCommand)) {
            return false; // Check if obj is of the correct type
        }

        SortCommand that = (SortCommand) obj; // Cast obj to SortCommand
        return order.equals(that.order); // Compare the order strings for equality
    }
}
