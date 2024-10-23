package seedu.address.logic.commands;

import seedu.address.logic.parser.exceptions.NoWindowException;
import seedu.address.model.Model;


/**
 * Represents a command to close the currently open view window in the application.
 * If no window is open, a {@code NoWindowException} is thrown.
 */
public class CloseCommand extends Command {

    public static final String COMMAND_WORD = "close";

    public static final String WINDOWS_CLOSED_ACKNOWLEDGEMENT = "View window closed.";


    /**
     * Executes the {@code CloseCommand}, closing the currently open view window.
     *
     * @param model The model of the application (not used in this command but required for method signature).
     * @return A {@code CommandResult} indicating that the window was successfully closed.
     * @throws NoWindowException If there are no view windows open to close.
     */

    @Override
    public CommandResult execute(Model model) throws NoWindowException {
        ViewCommand.closeCurrentWindow();
        return new CommandResult(WINDOWS_CLOSED_ACKNOWLEDGEMENT);
    }

    /**
     * Compares this {@code CloseCommand} with another object for equality.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the object is of the same type and considered equal, {@code false} otherwise.
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Check if both references point to the same object
        } else if ((obj instanceof CloseCommand)) {
            return true; // Check if obj is of the correct type
        } else {
            return false;
        }
    }
}
