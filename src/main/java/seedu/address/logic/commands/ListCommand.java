package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL;

import seedu.address.model.Model;
import seedu.address.model.internshipapplication.InternshipApplication;

/**
 * Lists all internship applications in hire me to the user.
 */
public class ListCommand extends Command<InternshipApplication> {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all internship applications";

    @Override
    public CommandResult execute(Model<InternshipApplication> model) {
        requireNonNull(model);
        model.updateFilteredList(PREDICATE_SHOW_ALL);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
