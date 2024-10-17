package seedu.hireme.logic.commands;

import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command<InternshipApplication> {

    public static final String COMMAND_WORD = "/help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model<InternshipApplication> model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
