package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.ui.HelpWindow;

/**
 * Format full help instructions for every command for display.
 */
public class WelcomeCommand extends Command {

    public static final String COMMAND_WORD = "welcome";
    public static final String USERGUIDE_URL = "https://ay2425s1-cs2103-f12-3.github.io/tp/UserGuide.html";
    public static final String WELCOME_MESSAGE = HelpWindow.HELP_MESSAGE + USERGUIDE_URL;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows welcome message; runs once on app startup.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(WELCOME_MESSAGE, false, false, USERGUIDE_URL);
    }
}
