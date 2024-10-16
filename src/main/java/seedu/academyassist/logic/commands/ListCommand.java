package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.model.Model;

/**
 * Lists all student in the management system to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_SUCCESS = "Listed all students details";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
