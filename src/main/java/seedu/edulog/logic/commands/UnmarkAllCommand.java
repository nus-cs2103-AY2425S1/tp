package seedu.edulog.logic.commands;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;

/**
 * Unmark all students, usually when students have not paid.
 */
public class UnmarkAllCommand extends Command {
    public static final String COMMAND_WORD = "unmarkall";

    public static final String MESSAGE_UNMARK_ALL_SUCCESS = "Unmark all successful";

    public static final String MESSAGE_USAGE = "unmarkall";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.unmarkAllStudents();
        return new CommandResult(MESSAGE_UNMARK_ALL_SUCCESS);
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof UnmarkAllCommand);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
