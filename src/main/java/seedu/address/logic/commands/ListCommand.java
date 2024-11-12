package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_FUNCTION = COMMAND_WORD + ": Lists out the entire address book.";
    public static final String MESSAGE_SUCCESS = "Listed all persons";
    public static final String MESSAGE_EMPTY = " Your address book is empty";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(model));
    }

    /**
     * Returns {@code String} message result of ListCommand executed on {@code Model}.
     */
    public String generateSuccessMessage(Model model) {
        int numberOfPersons = model.getAddressBook().getPersonList().size();

        if (numberOfPersons == 0) {
            return MESSAGE_EMPTY;
        }

        String numberOfContacts = ", there are currently " + numberOfPersons + " contacts in your address book";
        return MESSAGE_SUCCESS + numberOfContacts;
    }
}
