package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_EMPTY_LIST = "There are no persons in the address book.";

    public static final String MESSAGE_SUCCESS = "Listed all %d person(s)";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        int addressBookSize = model.getAddressBook().getPersonList().size();
        if (addressBookSize == 0) {
            return new CommandResult(MESSAGE_EMPTY_LIST);
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, addressBookSize));
        }
    }
}
