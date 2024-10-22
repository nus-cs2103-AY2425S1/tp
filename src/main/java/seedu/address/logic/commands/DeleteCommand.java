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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME (must start with an alphabet)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " john doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private Index targetIndex;
    private Name targetName;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public DeleteCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Maybe can split this class into DeleteByIndexCommand and DeleteByNameCommand
        requireNonNull(model);
        Person personToDelete;
        if (this.targetIndex == null) {
            // Delete by name
            List<Person> personList = model.findPersonsWithName(targetName);
            if (personList.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
            } else if (personList.size() > 1) {
                throw new CommandException(Messages.MESSAGE_MORE_THAN_ONE_PERSON_DISPLAYED_NAME);
            }
            personToDelete = personList.get(0);
        } else {
            // Delete by index
            List<Person> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            personToDelete = lastShownList.get(targetIndex.getZeroBased());
        }

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

        if (this.targetIndex == null && otherDeleteCommand.targetIndex == null) {
            return targetName.equals(otherDeleteCommand.targetName);
        } else if (this.targetName == null && otherDeleteCommand.targetName == null) {
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        }

        return false;
    }

    @Override
    public String toString() {
        if (targetIndex == null) {
            return new ToStringBuilder(this)
                    .add("targetName", targetName)
                    .toString();
        }
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
