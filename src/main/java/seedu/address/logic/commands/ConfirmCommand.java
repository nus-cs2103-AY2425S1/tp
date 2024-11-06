package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Permanently removes the listed participants from the address book.
 */
public class ConfirmCommand extends Command {

    public static final String COMMAND_WORD = "confirm";
    public static final String MESSAGE_SUCCESS_FULL_CLEAR = "Address book has been cleared";
    public static final String MESSAGE_SUCCESS_FILTERED_CLEAR = "Filtered participants have been deleted";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!ClearCommand.getIsClear()) {
            throw new CommandException(MESSAGE_UNKNOWN_COMMAND);
        }

        requireNonNull(model);
        ObservableList<Person> participantList = model.getFilteredPersonList();
        ObservableList<Person> unfilteredList = model.getAddressBook().getPersonList();

        if (participantList.equals(unfilteredList)) {
            model.setAddressBook(new AddressBook());
            ClearCommand.setIsClear(false);
            return new CommandResult(MESSAGE_SUCCESS_FULL_CLEAR);
        } else {
            while (!participantList.isEmpty()) {
                Person personToDelete = participantList.get(0);
                model.deletePerson(personToDelete);
            }
            ClearCommand.setIsClear(false);
            return new CommandResult(MESSAGE_SUCCESS_FILTERED_CLEAR);
        }
    }
}
