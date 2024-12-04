package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.ui.StatusBarFooter;

/**
 * Represents a command that opens the chat window.
 */
public class ChatWindowCommand extends Command {
    public static final String COMMAND_WORD = "chatbot";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the chat bot window.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_CHAT_BOT_MESSAGE = "Opened chat bot window.";

    @Override
    public CommandResult execute(Model model) {
        StatusBarFooter.handleChatButtonAction();
        return new CommandResult(SHOWING_CHAT_BOT_MESSAGE);
    }
}
