package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONCERT_CONTACTS;

import seedu.address.model.Model;

/**
 * Lists all concert contacts in the address book to the user.
 */
public class ListConcertContactCommand extends Command {

    public static final String COMMAND_WORD = "listcc";

    public static final String MESSAGE_SUCCESS = "Listed all concert contacts";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredConcertContactList(PREDICATE_SHOW_ALL_CONCERT_CONTACTS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
