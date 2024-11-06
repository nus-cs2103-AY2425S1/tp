package seedu.address.logic.commands;

import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    private static Set<String> personActionCommands;
    private static Set<String> appointmentActionCommands;

    public static Set<String> getPersonActionCommands() {
        if (personActionCommands == null) {
            personActionCommands = Set.of(
                AddCommand.COMMAND_WORD,
                EditCommand.COMMAND_WORD,
                DeleteCommand.COMMAND_WORD,
                ClearCommand.COMMAND_WORD,
                ArchiveCommand.COMMAND_WORD_ARCHIVE,
                ArchiveCommand.COMMAND_WORD_UNARCHIVE);
        }
        return personActionCommands;
    }

    public static Set<String> getAppointmentActionCommands() {
        if (appointmentActionCommands == null) {
            appointmentActionCommands = Set.of(
                AddAppointmentCommand.COMMAND_WORD,
                EditAppointmentCommand.COMMAND_WORD,
                DeleteAppointmentCommand.COMMAND_WORD);
        }
        return appointmentActionCommands;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    public abstract String getCommandWord();

    /**
     * Reverts the effects of this command, if applicable. This method is intended to be overridden
     * by commands that support undo functionality, such as action commands that alter the model
     * state (e.g., adding, deleting, or editing entities).
     * <p>
     * By default, this method returns {@code null}, indicating that the command does not support undo.
     * Override this method in subclasses to provide specific undo behavior.
     *
     * @param model The {@code Model} on which to perform the undo operation.
     * @param pastCommands The {@code CommandHistory} to remove the latest command after undoing.
     * @return A feedback message indicating the result of the undo operation, or {@code null} if undo
     *         is not supported.
     */
    public String undo(Model model, CommandHistory pastCommands) {
        return null;
    }
}
