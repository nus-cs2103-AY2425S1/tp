package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE =
            "Hi it looks like your trying to use the delete command, but your not formatting it quite right!\n"
            + "Ensure the Parameter: INDEX (must be a positive integer, no decimals!)\n"
            + "For Example: " + COMMAND_WORD + " 1\n"
            + "This will delete the person identified by the index number used in the displayed person list.\n";


    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "You have deleted a person: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        assert lastShownList != null;

        // validation check on list and index
        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_DELETE_EMPTY_ERROR);
        }
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_DELETE_UPPERBOUND_ERROR);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        assert personToDelete != null;

        model.deletePerson(personToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
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
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
