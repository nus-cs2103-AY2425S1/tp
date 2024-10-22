package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Clears all events of the address book
 */
public class ClearEventCommand extends ClearCommand {

    public static final String COMMAND_FIELD = "event";
    public static final String MESSAGE_SUCCESS = "All events has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        ReadOnlyAddressBook addressBook = model.getAddressBook();
        List<Person> personList = addressBook.getPersonList();
        List<Event> eventList = new ArrayList<>();

        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setPersons(personList);
        newAddressBook.setEvents(eventList);

        model.setAddressBook(newAddressBook);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
