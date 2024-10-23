package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
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
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex == null) {
            setTargetIndex(lastShownList);
        }

        assert(targetIndex != null);

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    private void setTargetIndex(List<Person> lastShownList) throws CommandException {
        int temp = lastShownList.stream()
                .filter(person -> person.getName().equalsIgnoreCase(targetName))
                .map(lastShownList::indexOf)
                .reduce(invalidTargetIndex, (x, y) -> y);
        if (temp == invalidTargetIndex) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_IN_ADDRESS_BOOK);
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
