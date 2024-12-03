package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Closes the currently opened attendance window.
 */
public class CloseWindowCommand extends Command {
    public static final String COMMAND_WORD = "closeat";

    public static final String MESSAGE_SUCCESS = "Attendance window(s) closed.";

    public static final String NO_WINDOW = "No attendance window is currently open.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Closes all currently opened attendance windows.";

    @Override
    public CommandResult execute(Model model) {
        boolean canClose = GetAttendanceByTgCommand.closeAllWindows();
        return canClose
                ? new CommandResult(MESSAGE_SUCCESS)
                : new CommandResult(NO_WINDOW);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CloseWindowCommand;
    }
}
