package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index[] targetIndexArray;

    public DeleteCommand(Index[] targetIndexArray) {
        this.targetIndexArray = targetIndexArray;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> personsToDelete = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (Index targetIndex : targetIndexArray) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personsToDelete.add(lastShownList.get(targetIndex.getZeroBased()));
        }

        for (int i = 0; i < personsToDelete.size(); i++) {
            Person personToDelete = personsToDelete.get(i);
            model.deletePerson(personToDelete);
            builder.append(Messages.format(personToDelete));

            if (i < personsToDelete.size() - 1) {
                builder.append("\n");
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, builder));
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
        return Arrays.equals(targetIndexArray, otherDeleteCommand.targetIndexArray);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexArray", Arrays.toString(targetIndexArray))
                .toString();
    }
}
