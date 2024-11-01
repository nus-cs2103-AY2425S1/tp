package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Sorts all persons in the address book by name in alphabetical order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = "Sorts the address book in alphabetical order";

    public static final String MESSAGE_SUCCESS = "Address book sorted in alphabetical order";
    public static final String HELP_SORT_COMMAND = "Sort Command\n"
            + "- Format: sort\n"
            + "- Example: sort\n"
            + "Using the sort command will arrange all persons in the address book in alphabetical order by name. "
            + "The list will remain in this sorted order even after restarting the application. "
            + "To view the list in its original order (by the sequence in which persons were added), "
            + "use the list command.";



    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortPersonListByName();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
