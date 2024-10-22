package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Generates an error message for an invalid command, suggesting a correct command.
     *
     * @param invalidCommand The invalid command entered by the user.
     * @param commandWord    The valid command word to suggest.
     * @return A string indicating the invalid command and suggesting a valid one.
     */
    public static String generateInvalidVariantMessage(String invalidCommand, String commandWord) {
        return invalidCommand + " is an invalid command. Perhaps you meant " + commandWord + " ?";
    }

    /**
     * Generates an error message for an invalid command, suggesting two possible valid commands.
     *
     * @param invalidCommand   The invalid command entered by the user.
     * @param shortCommandWord The short form of the valid command.
     * @param longCommandWord  The long form of the valid command.
     * @return A string indicating the invalid command and suggesting two possible valid commands.
     */
    public static String generateInvalidVariantMessage(String invalidCommand, String shortCommandWord,
                                                       String longCommandWord) {
        return invalidCommand + " is an invalid command. Perhaps you meant " + shortCommandWord + " or "
                + longCommandWord + " ?";
    }

}
