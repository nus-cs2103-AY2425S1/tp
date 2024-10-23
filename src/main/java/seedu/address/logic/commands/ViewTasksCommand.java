package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.predicate.TaskListNotEmptyPredicate;
import seedu.address.ui.Ui.UiState;

/**
 * Lists all outstanding tasks for persons in the address book to the user.
 */
public class ViewTasksCommand extends Command {

    public static final String COMMAND_WORD = "viewtasks";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(new TaskListNotEmptyPredicate());

        return new CommandResult(MESSAGE_SUCCESS, UiState.TASKS);
    }

}
