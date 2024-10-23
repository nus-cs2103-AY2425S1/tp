package seedu.edulog.logic.commands;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;

/**
 * Mark all students, usually after student has paid
 */
public class MarkAllCommand extends Command {
    public static final String COMMAND_WORD = "markall";

    public static final String MESSAGE_MARK_ALL_SUCCESS = "Mark all successful";

    public static final String MESSAGE_USAGE = "markall";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.markAllStudents();
        return new CommandResult(MESSAGE_MARK_ALL_SUCCESS);
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof MarkAllCommand);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
