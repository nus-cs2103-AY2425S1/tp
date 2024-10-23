package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
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
            + "Parameters: (INDEX)+ (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person(s):\n%1$s";


    private final Index[] targetIndexArray;

    public DeleteCommand(Index[] targetIndexArray) {
        this.targetIndexArray = targetIndexArray;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> personsToDelete = new ArrayList<>();

        for (Index targetIndex : targetIndexArray) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + ": "
                        + targetIndex.getOneBased());
            }
            personsToDelete.add(lastShownList.get(targetIndex.getZeroBased()));
        }

        String resultMessage = personsToDelete.stream()
                .peek(model::deletePerson)
                .map(Messages::format)
                .collect(Collectors.joining("\n"));

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, resultMessage));
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
