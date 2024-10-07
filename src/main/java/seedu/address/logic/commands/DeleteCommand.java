package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Set<Index> targetIndices;

    public DeleteCommand(Set<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Index item: targetIndices) {
            if (item.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_INDEX_SHOWN, item.getOneBased()));
            }
        }


        List<Person> deletedPeople = targetIndices.stream()
                .map(targetIndex -> lastShownList.get(targetIndex.getZeroBased()))
                .toList();

        targetIndices.stream().forEach(targetIndex -> {
            Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deletePerson(personToDelete);
        });

        String formattedDeletedPeople = deletedPeople.stream()
                .map(Messages::format) // Format each person using Messages::format
                .collect(Collectors.joining(", ")); // Join them with a separator (comma in this case)


        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, formattedDeletedPeople));
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
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
