package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_SORT_NOT_IMPLEMENTED = "Sorting not yet implemented.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all persons in the address book.\n"
            + "Optional parameters: [s/SORT_OPTION]\n"
            + "Example: " + COMMAND_WORD + " s/alphabet";

    private final String sortOption;

    public ListCommand() {
        this.sortOption = null;
    }

    public ListCommand(String sortOption) {
        this.sortOption = sortOption;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        if (sortOption != null) {
            // For now, we just inform the user that sorting is not implemented
            return new CommandResult(MESSAGE_SORT_NOT_IMPLEMENTED);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // If both commands have the same sortOption, they are considered equal
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherCommand = (ListCommand) other;
        return (sortOption == null && otherCommand.sortOption == null)
                || (sortOption != null && sortOption.equals(otherCommand.sortOption));
    }
}
