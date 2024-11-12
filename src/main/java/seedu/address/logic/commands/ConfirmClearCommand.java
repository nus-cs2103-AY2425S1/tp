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
public class ConfirmClearCommand extends Command {

    public static final String COMMAND_WORD = "confirm";
    public static final String MESSAGE_SUCCESS_FULL_CLEAR = "Address book has been cleared.";
    public static final String MESSAGE_SUCCESS_FILTERED_CLEAR = "Filtered participants have been deleted.";

    /**
     * Executes the clear command, which removes either
     * all entries in the address book
     * or filtered entries,
     * based on the current filtered list of participants.
     *
     * @param model the {@code Model} that the command should operate on.
     * @return a {@code CommandResult} with a message indicating the success of the operation.
     * @throws CommandException if the clear command is not enabled or known.
     */
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
