package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using its displayed index or name from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number or the full name used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME (must match an existing person name exactly)\n"
            + "Example: " + COMMAND_WORD + " 1 or " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with the name: %1$s";

    private final Index targetIndex;
    private final String targetName;

    /**
     * Creates a DeleteCommand for deleting by index.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    /**
     * Creates a DeleteCommand for deleting by name.
     */
    public DeleteCommand(String targetName) {
        this.targetName = targetName;
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToDelete;
        if (targetIndex != null) {
            // Deletion by index
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToDelete = lastShownList.get(targetIndex.getZeroBased());
        } else {
            // Deletion by name
            Optional<Person> personOptional = lastShownList.stream()
                    .filter(person -> person.getName().fullName.equalsIgnoreCase(targetName))
                    .findFirst();

            if (personOptional.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, targetName));
            }
            personToDelete = personOptional.get();
        }

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return (targetIndex != null && targetIndex.equals(otherDeleteCommand.targetIndex))
                || (targetName != null && targetName.equals(otherDeleteCommand.targetName));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("targetName", targetName)
                .toString();
    }
}
