package seedu.hireme.logic.commands;

import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.InternshipApplication;

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
