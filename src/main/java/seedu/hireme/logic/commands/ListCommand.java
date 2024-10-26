package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hireme.model.Model;

/**
 * Lists all internship applications in hire me to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "/list";

    public static final String MESSAGE_SUCCESS = "Listed all internship applications";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredList(Model.PREDICATE_SHOW_ALL);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
