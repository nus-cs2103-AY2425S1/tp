package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_SUCCESS = "These are your clients!";
    public static final String MESSAGE_NO_CLIENT_IN_LIST = "You currently have no clients in the list.";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (model.getFilteredPersonList().isEmpty()) {
            throw new CommandException(MESSAGE_NO_CLIENT_IN_LIST);
        }
        return new CommandResult(MESSAGE_SUCCESS, false, false,
                false, true);
    }
}
