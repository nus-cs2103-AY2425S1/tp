package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Closes the currently opened attendance window.
 */
public class CloseWindowCommand extends Command {
    public static final String COMMAND_WORD = "closeat";

    public static final String MESSAGE_SUCCESS = "Attendance window closed.";

    public static final String NO_WINDOW = "No attendance window is currently open.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Closes the currently opened attendance window.";

    @Override
    public CommandResult execute(Model model) {
        boolean canClose = GetAttendanceByTgCommand.closeCurrentWindow();
        return canClose
                ? new CommandResult(MESSAGE_SUCCESS)
                : new CommandResult(NO_WINDOW);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CloseWindowCommand;
    }
}
