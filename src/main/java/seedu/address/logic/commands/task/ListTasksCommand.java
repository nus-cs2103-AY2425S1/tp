package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResult.SwitchView;
import seedu.address.model.Model;


/**
 * Lists all tasks in the address book to the user.
 */
public class ListTasksCommand extends Command {

    public static final String COMMAND_WORD = "list-tasks";

    public static final String COMMAND_KEYWORD = "lt";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS, SwitchView.TASK);
    }
}
