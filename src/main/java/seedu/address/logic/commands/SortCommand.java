package seedu.address.logic.commands;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Sorts all persons in the address book by name, then lists to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the client list based on the criteria given\n"
            + "Parameters: SORT_TYPE (must be either name or latest)\n"
            + "Examples: " + COMMAND_WORD + " name" + ", " + COMMAND_WORD + " latest";

    public static final Comparator<Client> COMPARATOR_BY_NAME = (o1, o2) ->
            o1.getName().toString().compareTo(o2.getName().toString());

    public static final String MESSAGE_SUCCESS_NAME = "Listed all persons by name in alphabetical order";
    public static final String MESSAGE_SUCCESS_LATEST = "Listed all persons by order of addition";

    private final String sortType;

    public SortCommand(String sortType) {
        this.sortType = sortType;
    }


    @Override
    public CommandResult execute(Model model) {
        assert model != null;
        if (sortType.equals("name")) {
            model.updateSortedPersonList(COMPARATOR_BY_NAME);
            return new CommandResult(MESSAGE_SUCCESS_NAME);
        } else {
            //Setting the comparator to null causes the SortedList to revert back to an unordered state
            //The unordered state is listed by order of addition
            model.updateSortedPersonList(null);
            return new CommandResult(MESSAGE_SUCCESS_LATEST);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return sortType.equals(otherSortCommand.sortType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sortType", sortType)
                .toString();
    }
}
