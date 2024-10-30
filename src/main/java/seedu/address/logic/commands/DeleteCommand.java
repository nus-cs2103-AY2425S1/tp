package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Name;

/**
 * Deletes a contact identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the contact identified by the index number used in the displayed contact list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static final int invalidTargetIndex = -1;

    private Index targetIndex;
    private final Name targetName;

    /**
     * Constructor method that takes in an Index Object
     * @param targetIndex index from the addressbook whereby is expected to be deleted.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Constructor method that takes in a Name Object
     * @param name the name which is expected to be deleted
     */
    public DeleteCommand(Name name) {
        this.targetIndex = null;
        this.targetName = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (targetIndex == null) {
            setTargetIndex(lastShownList);
        }

        assert(targetIndex != null);

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteContact(contactToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS, Messages.format(contactToDelete)));
    }

    private void setTargetIndex(List<Contact> lastShownList) throws CommandException {
        int temp = lastShownList.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(targetName))
                .map(lastShownList::indexOf)
                .reduce(invalidTargetIndex, (x, y) -> y);
        if (temp == invalidTargetIndex) {
            throw new CommandException(Messages.MESSAGE_CONTACT_NOT_IN_ADDRESS_BOOK);
        }
        this.targetIndex = Index.fromZeroBased(temp);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;

        if (targetIndex == null) {
            if (otherDeleteCommand.targetIndex == null) {
                if (targetName == null) {
                    return otherDeleteCommand.targetName == null;
                }
                return targetName.equals(otherDeleteCommand.targetName);
            }
            return false;
        }

        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
