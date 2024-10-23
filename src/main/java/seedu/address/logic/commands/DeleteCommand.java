package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
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
            + "Parameters: INDEX_1 [INDEX_2]... (must be positive integer(s))\n"
            + "Example: " + COMMAND_WORD + " 1 3 5";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted successfully!\n"
            + "Deleted participant(s): %1$s";

    private final List<Index> targetIndices;

    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Sort indices in reverse order so that users can delete from the end without
        // affecting the indices of remaining persons
        List<Index> sortedIndices = targetIndices.stream()
                .sorted((i1, i2) -> Integer.compare(i2.getZeroBased(), i1.getZeroBased()))
                .distinct()
                .collect(Collectors.toList());

        StringBuilder successMessage = new StringBuilder();

        for (Index index : sortedIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(
                        String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, lastShownList.size()));
            }
            Person personToDelete = lastShownList.get(index.getZeroBased());
            model.deletePerson(personToDelete);
            successMessage.append(Messages.format(personToDelete)).append("\n");
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, successMessage.toString().trim()));
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
