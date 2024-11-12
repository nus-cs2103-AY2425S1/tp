package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.ui.DisplayType;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, DisplayType.SAME_AS_PREVIOUS, false, true);
    }

}
