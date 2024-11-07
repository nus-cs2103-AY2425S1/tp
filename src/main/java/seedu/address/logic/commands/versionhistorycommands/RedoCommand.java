package seedu.address.logic.commands.versionhistorycommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.exceptions.RedoLimitException;

/**
 * Reverses the previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Reverses the most recent undo instruction.\n"
        + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redo successful";

    /**
     * Creates an RedoCommand to undo the previous instruction
     */
    public RedoCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setStateStudents();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        try {
            versionHistory.redoVersion();
        } catch (RedoLimitException e) {
            throw new CommandException(e.getMessage());
        }
        return versionHistory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }
}
