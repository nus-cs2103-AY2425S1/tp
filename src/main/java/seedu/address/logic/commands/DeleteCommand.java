package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.LINE_BREAK;
import static seedu.address.logic.Messages.WHITESPACE;
import static seedu.address.logic.Messages.styleCommand;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

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

    public static final String MESSAGE_FUNCTION = COMMAND_WORD
            + ": Deletes the contact identified by the displayed index number or "
            + "by Full name.";



    public static final String MESSAGE_COMMAND_FORMAT = "3 possible formats as follow:" + LINE_BREAK
            + "1. " + styleCommand(COMMAND_WORD + WHITESPACE + "INDEX (positive integer)") + LINE_BREAK
            + "2. " + styleCommand(COMMAND_WORD + WHITESPACE + "FULL_NAME_OF_CONTACT_TO_DELETE") + LINE_BREAK
            + "3. " + styleCommand(COMMAND_WORD + WHITESPACE + PREFIX_NAME
            + "FULL_NAME_OF_CONTACT_TO_DELETE");
    public static final String MESSAGE_COMMAND_EXAMPLE = "Example One:" + WHITESPACE
            + COMMAND_WORD + WHITESPACE + "1" + LINE_BREAK
            + "Example Two: " + COMMAND_WORD + WHITESPACE + "John Doe" + LINE_BREAK
            + "Example Three: " + COMMAND_WORD + WHITESPACE + PREFIX_NAME + WHITESPACE + "John Doe";

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
        List<Contact> lastShownFilteredList = model.getFilteredContactList();
        List<Contact> allContactList = model.getAllContactList();

        if (targetIndex == null) {
            screenDuplicate(allContactList);
            setTargetIndex(lastShownFilteredList);
        }

        assert(targetIndex != null);

        if (targetIndex.getZeroBased() >= lastShownFilteredList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToDelete = lastShownFilteredList.get(targetIndex.getZeroBased());
        model.deleteContact(contactToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS, Messages.format(contactToDelete)));
    }

    // DRY not adhered (compare to edit)
    private void screenDuplicate(List<Contact> lastShownList) throws CommandException {
        long nameCount = lastShownList.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(targetName))
                .count();
        boolean hasDuplicate = nameCount > 1;
        if (hasDuplicate) {
            throw new CommandException(
                    String.format(
                            Messages.MESSAGE_DUPLICATE_NAME,
                            COMMAND_WORD, targetName.fullName));
        }
    }

    private void setTargetIndex(List<Contact> lastShownList) throws CommandException {
        int temp = lastShownList.stream()
                .filter(contact -> contact.getName().equalsIgnoreCase(targetName))
                .map(lastShownList::indexOf)
                .reduce(invalidTargetIndex, (x, y) -> y);
        if (temp == invalidTargetIndex) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_CONTACT_NOT_IN_ADDRESS_BOOK, targetName.fullName));
        }
        this.targetIndex = Index.fromZeroBased(temp);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand otherDeleteCommand)) {
            return false;
        }

        if (targetIndex == null) {
            if (otherDeleteCommand.targetIndex == null) {
                if (targetName == null) {
                    return otherDeleteCommand.targetName == null;
                }
                return targetName.equalsIgnoreCase(otherDeleteCommand.targetName);
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
