// src/main/java/seedu/address/logic/commands/DeleteYCommand.java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.StaticContext;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.WeddingBook;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;

/**
 * Confirms the deletion of a person from the address book or a wedding from the wedding book.
 */
public class DeleteYCommand extends Command {

    public static final String COMMAND_WORD = "y";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_WEDDING_SUCCESS =
            "Deleted Wedding:\nWedding name: %1$s\nVenue: %2$s\nDate: %3$s";
    public static final String MESSAGE_DELETE_ADDRESS_BOOK_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_DELETE_WEDDING_BOOK_SUCCESS = "Wedding book has been cleared!";
    public static final String MESSAGE_NO_PENDING_OPERATION = "No pending delete operation.";
    private final Person personToDelete;
    private final Wedding weddingToDelete;

    /**
     * Creates a DeleteYCommand to delete the specified {@code Person} once the confirmation is given.
     * @param personToDelete
     */
    public DeleteYCommand(Person personToDelete) {
        this.personToDelete = personToDelete;
        this.weddingToDelete = null;
    }

    /**
     * Creates a DeleteYCommand to delete the specified {@code Wedding} once the confirmation is given.
     * @param weddingToDelete
     */
    public DeleteYCommand(Wedding weddingToDelete) {
        this.weddingToDelete = weddingToDelete;
        this.personToDelete = null;
    }

    /**
     * Creates a DeleteYCommand to clear the address book or wedding book once the confirmation is given.
     * Initializes personToDelete and weddingToDelete to null.
     */
    public DeleteYCommand() {
        this.personToDelete = null;
        this.weddingToDelete = null;
    }

    /**
     * Executes the DeleteYCommand.
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} that describes the result of executing the command.
     * @throws CommandException if there is no pending delete operation.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (StaticContext.isClearAddressBookPending()) {
            model.clearAllPersonTags();
            model.setAddressBook(new AddressBook());
            StaticContext.setClearAddressBookPending(false);
            return new CommandResult(MESSAGE_DELETE_ADDRESS_BOOK_SUCCESS);
        }

        if (StaticContext.isClearWeddingBookPending()) {
            model.clearAllWeddingParticipants();
            model.setWeddingBook(new WeddingBook());
            StaticContext.setClearWeddingBookPending(false);
            return new CommandResult(MESSAGE_DELETE_WEDDING_BOOK_SUCCESS);
        }

        if (!(weddingToDelete == null)) {
            model.deleteTagsWithWedding(weddingToDelete);
            model.deleteWedding(weddingToDelete);
            // Clear history of weddingToDelete from StaticContext once delete operation is done
            StaticContext.setWeddingToDelete(null);
            return new CommandResult(String.format(MESSAGE_DELETE_WEDDING_SUCCESS,
                    weddingToDelete.getWeddingName(), weddingToDelete.getVenue(), weddingToDelete.getDate()));
        }

        if (!(personToDelete == null)) {
            Person personToDeleteWithNoTag = model.personWithAllTagsRemoved(personToDelete);
            model.deletePerson(personToDeleteWithNoTag);
            // Clear history of personToDelete from StaticContext once delete operation is done
            StaticContext.setPersonToDelete(null);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        }

        return new CommandResult(MESSAGE_NO_PENDING_OPERATION);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteYCommand)) {
            return false;
        }

        DeleteYCommand otherDeleteYCommand = (DeleteYCommand) other;

        if (weddingToDelete != null) {
            return weddingToDelete.equals(otherDeleteYCommand.weddingToDelete);
        }
        return personToDelete.equals(otherDeleteYCommand.personToDelete);
    }
}
