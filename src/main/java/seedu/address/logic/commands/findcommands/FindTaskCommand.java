package seedu.address.logic.commands.findcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUERY;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

public class FindTaskCommand extends Command {
    public static final String COMMAND_WORD = "find_t";
    public static final String COMMAND_WORD_ALIAS = "ft";
    public static final int LIST_TASK_MARKER = 2;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Finds all tasks whose name contain any of the specified keywords (case-insensitive) "
        + "and displays them as a list with index numbers.\n"
        + "Parameters: " + PREFIX_QUERY + "KEYWORD [" + PREFIX_QUERY + "MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + "/" + COMMAND_WORD_ALIAS + " " + PREFIX_QUERY + "Task 1 "
        + PREFIX_QUERY + "Task 2";
    public static final String NO_TASKS_FOUND = "No tasks found";

    private static final Logger logger = LogsCenter.getLogger(FindTaskCommand.class);

    private final TaskNameContainsKeywordsPredicate predicate;

    public FindTaskCommand(TaskNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTaskList(predicate);
        logger.info(String.format("Found %1s tasks with predicate - %2s", model.getFilteredTaskList().size(),
            predicate));
        if (model.getFilteredTaskList().isEmpty()) {
            return new CommandResult(NO_TASKS_FOUND, LIST_TASK_MARKER);
        }
        return new CommandResult(String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW,
            model.getFilteredTaskList().size()), LIST_TASK_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        return versionHistory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTaskCommand)) {
            return false;
        }

        FindTaskCommand otherFindTaskCommand = (FindTaskCommand) other;
        return predicate.equals(otherFindTaskCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("predicate", predicate)
            .toString();
    }
}
