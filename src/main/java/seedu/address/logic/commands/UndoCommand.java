package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.LogicManager;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Undoes the latest command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undoes the latest command and reverts person list to the state before it.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_COMMAND_SUCCESS = "Undo successful:\n%s";
    public static final String MESSAGE_UNDO_COMMAND_NEUTRAL = "No action to undo:\n%s";
    public static final String MESSAGE_UNDO_ADD = "%s has been removed from SocialBook";
    public static final String MESSAGE_UNDO_EDIT = "Edits to %s has been reverted";
    public static final String MESSAGE_UNDO_DELETE =
            "%s and their appointments have been added back to SocialBook";
    public static final String MESSAGE_UNDO_CLEAR = "Here is the list before clearing";
    public static final String MESSAGE_UNDO_DELETE_APPOINTMENT =
            "Appointment for %s has been added back to appointments";
    public static final String MESSAGE_UNDO_ADD_APPOINTMENT =
            "Appointment for %s has been removed from appointments";

    public static final String MESSAGE_UNDO_ADD_SCHEME = "The following scheme for %s has been removed\n";

    public static final String MESSAGE_UNDO_DELETE_SCHEME = "The following schemes for %s has been added back\n";
    private final CommandHistory pastCommands;
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    /**
     * Loads the past commands into constructor.
     *
     * @param pastCommands all the past commands during this code run.
     */
    public UndoCommand(CommandHistory pastCommands) {
        this.pastCommands = pastCommands;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (pastCommands.getCommandInputHistory().isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_LATEST_COMMAND);
        }
        Command latestCommand = pastCommands.getCommandInputHistory().get(pastCommands.getSize() - 1);
        String latestCommandWord = latestCommand.getCommandWord();
        logger.info("----------------[COMMAND UNDONE][" + latestCommandWord + "]");
        String resultMessage = String.format(
                "No change as command undone (%s) was not an action command", latestCommandWord);
        if (Arrays.asList(ACTION_COMMANDS).contains(latestCommandWord)) {
            resultMessage = latestCommand.undo(model, pastCommands);
            return new CommandResult(String.format(MESSAGE_UNDO_COMMAND_SUCCESS, resultMessage));
        } else {
            pastCommands.remove();
            return new CommandResult(String.format(MESSAGE_UNDO_COMMAND_NEUTRAL, resultMessage));
        }
    }

    public CommandHistory getPastCommands() {
        return pastCommands;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String undo(Model model, CommandHistory pastCommands) {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UndoCommand)) {
            return false;
        }

        UndoCommand otherUndoCommand = (UndoCommand) other;
        return pastCommands.getCommandInputHistory()
                .equals(otherUndoCommand.getPastCommands().getCommandInputHistory());
    }
}
