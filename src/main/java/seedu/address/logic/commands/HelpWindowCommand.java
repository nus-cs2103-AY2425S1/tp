package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Pops up a help window that directs users to the link of the user guide.
 */
public class HelpWindowCommand extends Command {

    public static final String COMMAND_WORD = "helpwindow";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pops up a link to the user guide.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_WINDOW_MESSAGE = "For more information, please head to the user guide.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_WINDOW_MESSAGE, false, true, false);
    }
}
