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
     * Creates a HelpCommand.
     */
    public HelpCommand() {
        this.message = "";
    }

    /**
     * Constructs a {@code HelpCommand} object with the specified message.
     *
     * <p>This constructor creates a {@code HelpCommand} with a custom message, which can be used to
     * provide additional information or warnings when executing the command.
     * The constructor uses an assertion to ensure that the provided message is not {@code null}.</p>
     *
     * @param message The message to be associated with this {@code HelpCommand}. Must not be {@code null}.
     * @throws AssertionError If the provided message is {@code null}.
     */
    public HelpCommand(String message) {
        assert message != null;

        this.message = message;
    }
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS + message, true, false);
    }
}
