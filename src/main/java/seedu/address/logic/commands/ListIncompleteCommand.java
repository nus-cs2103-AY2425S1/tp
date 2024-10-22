package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Lists all incomplete tasks in the task book to the user.
 */
public class ListIncompleteCommand extends Command {

    public static final String COMMAND_WORD = "listincomplete";
    public static final String MESSAGE_SUCCESS = "Listed all incomplete tasks";

    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredTaskList(task -> !task.isCompleteProperty().get());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
