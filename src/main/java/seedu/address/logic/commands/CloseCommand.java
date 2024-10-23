package seedu.address.logic.commands;

import seedu.address.logic.parser.exceptions.NoWindowException;
import seedu.address.model.Model;

public class CloseCommand extends Command {

    public static final String COMMAND_WORD = "close";

    public static final String WINDOWS_CLOSED_ACKNOWLEDGEMENT = "View window closed.";

    @Override
    public CommandResult execute(Model model) throws NoWindowException {
        ViewCommand.closeCurrentWindow();
        return new CommandResult(WINDOWS_CLOSED_ACKNOWLEDGEMENT);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true; // Check if both references point to the same object
        } else if ((obj instanceof CloseCommand)) {
            return true; // Check if obj is of the correct type
        } else {
            return false;
        }
    }
}
