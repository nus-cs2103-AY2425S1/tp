package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Requests confirmation before clear operation.
 */
public class ConfirmPendingCommand extends Command {
    public static final String DELETE_CONFIRMATION_MESSAGE = "Are you sure you want to delete this person? " +
            "All events tied to this person will be deleted " +
            "and this person will no longer show in any event's contact list. (Y/N)";
    public static final String CLEAR_CONFIRMATION_MESSAGE = "Are you sure you want to clear all data? (Y/N)";
    public PendingCommandType pendingCommandType;
    public ConfirmPendingCommand(PendingCommandType pendingCommandType) {
        this.pendingCommandType = pendingCommandType;
    }
    @Override
    public CommandResult execute(Model model) {
        if (pendingCommandType == PendingCommandType.DELETE) {
            return new CommandResult(DELETE_CONFIRMATION_MESSAGE, false, false,
                    CommandTabChange.NONE, CommandDetailChange.NONE);
        } else {
            return new CommandResult(CLEAR_CONFIRMATION_MESSAGE, false, false,
                    CommandTabChange.NONE, CommandDetailChange.NONE);
        }
    }

    public enum PendingCommandType {
        DELETE, CLEAR
    }
}
