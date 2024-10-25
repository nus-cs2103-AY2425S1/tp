package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS_FULL_CLEAR = "Address book has been cleared";
    public static final String MESSAGE_SUCCESS_FILTERED_CLEAR = "Filtered participants have been deleted";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Person> participantList = model.getFilteredPersonList();
        if (participantList.equals(model.getAddressBook().getPersonList())) {
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS_FULL_CLEAR);
        } else {
            while (!participantList.isEmpty()) {
                Person personToDelete = participantList.get(0);
                model.deletePerson(personToDelete);
            }
            return new CommandResult(MESSAGE_SUCCESS_FILTERED_CLEAR);
        }
    }
}
