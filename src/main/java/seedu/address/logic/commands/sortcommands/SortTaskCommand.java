package seedu.address.logic.commands.sortcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.task.Task;

/**
 * Sorts all the tasks in the address book.
 */
public class SortTaskCommand extends Command {

    public static final String COMMAND_WORD = "sort_t";
    public static final String COMMAND_WORD_ALIAS = "st";
    public static final int LIST_TASK_MARKER = 2;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
            + ": Sorts all tasks.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all tasks";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.sortTaskList(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getDeadline().compareTo(t2.getDeadline());
            }
        });
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.setStateTasks();
        return new CommandResult(MESSAGE_SUCCESS, LIST_TASK_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
