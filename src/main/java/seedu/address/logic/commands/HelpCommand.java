package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Provides a link popup to a help guide.\n"
            + "There should be no parameters!\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Success. Opened help window" + "\n";

    private String message;

    /**
     * Creates a HelpCommand .
     */
    public HelpCommand() {
        this.message = "";
    }

    /**
     * Creates a HelpCommand with an additional warning message to display to the user.
     *
     * @param message Warning message to be displayed to the user
     */
    public HelpCommand(String message) {
        this.message = message;
    }
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS + message, true, false);
    }
}
