package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.model.Model;

/**
 * Lists all incomplete tasks in the task book to the user.
 */
public class ListIncompleteCommand extends Command {

    public static final String COMMAND_WORD = "listincomplete";
    public static final String MESSAGE_SUCCESS = "Listed all incomplete tasks";
    public static final String MESSAGE_NO_INCOMPLETE_TASKS = "There are no incomplete tasks";

    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.updateFilteredTaskList(task -> !task.isCompleteProperty().get());

        if (model.getFilteredTaskList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_INCOMPLETE_TASKS);
        } else {
            return new CommandResult(MESSAGE_SUCCESS);
        }
    }
}
