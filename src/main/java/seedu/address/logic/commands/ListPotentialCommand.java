package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POTENTIAL_HIRES;

import seedu.address.model.Model;

/**
 * Lists all potential hires in the address book to the user.
 */
public class ListPotentialCommand extends ListCommand {

    public static final String ARGUMENT_WORD = "ph";

    public static final String MESSAGE_SUCCESS = "Listed all potential employees";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_POTENTIAL_HIRES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
