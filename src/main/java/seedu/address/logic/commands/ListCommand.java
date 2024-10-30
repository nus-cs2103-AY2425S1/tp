package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String HELP_LIST_COMMAND = "List Command\n"
            + "- Format: list\n"
            + "- Example: list\n"
            + "Using the list command will display all persons in the address book, "
            + "ordered sequentially from the earliest entry to the most recent. "
            + "This means that the first person added appears at the top of the list, "
            + "while the most recently added person appears at the bottom.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.sortPersonListByID();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
