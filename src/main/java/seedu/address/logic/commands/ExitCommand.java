package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.internshipapplication.InternshipApplication;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command<InternshipApplication> {

    public static final String COMMAND_WORD = "/exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting HireMe as requested ...";

    @Override
    public CommandResult execute(Model<InternshipApplication> model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
