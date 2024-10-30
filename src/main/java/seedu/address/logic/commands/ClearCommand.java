package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.InspectWindow;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS_CONTACTS = "Contacts have been cleared!";
    public static final String MESSAGE_SUCCESS_DELIVERIES = "Deliveries have been cleared!";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!AddressBookParser.getInspect()) {
            requireNonNull(model);
            model.setAddressBook(new AddressBook());
            return new CommandResult(MESSAGE_SUCCESS_CONTACTS);
        } else {
            Person inspectedPerson = InspectWindow.getInspectedPerson();
            inspectedPerson.setDeliveryList(new ArrayList<>());
            return new CommandResult(MESSAGE_SUCCESS_DELIVERIES);
        }
    }
}
