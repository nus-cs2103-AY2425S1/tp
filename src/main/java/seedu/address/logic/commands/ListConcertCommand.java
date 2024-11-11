package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONCERTS;

import seedu.address.model.Model;

/**
 * Lists all concerts in the address book to the user.
 */
public class ListConcertCommand extends Command {

    public static final String COMMAND_WORD = "listc";

    public static final String MESSAGE_SUCCESS = "Listed all concerts";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredConcertList(PREDICATE_SHOW_ALL_CONCERTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
