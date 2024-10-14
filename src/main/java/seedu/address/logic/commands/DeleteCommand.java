package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using their name or index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name or index in the address book.\n"
            + "Parameters: NAME or INDEX (must match exactly one person or be a valid index)\n"
            + "Example: " + COMMAND_WORD + " John Doe\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person's name provided is invalid";
    public static final String MESSAGE_INVALID_INDEX = "The person index provided is invalid";

    private final Name targetName;
    private final Index targetIndex;

    /**
     * @param targetName of the person to be deleted in the list
     */
    public DeleteCommand(Name targetName) {
        this.targetName = targetName;
        this.targetIndex = null;
    }

    /**
     * @param targetIndex of the index of the person to be deleted in the list
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.targetName = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToDelete;

        if (targetName != null) {
            Optional<Person> personOptional = lastShownList.stream()
                    .filter(person -> person.getName().equals(targetName))
                    .findFirst();

            if (personOptional.isEmpty()) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
            }
            personToDelete = personOptional.get();
        } else {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(MESSAGE_INVALID_INDEX);
            }
            personToDelete = lastShownList.get(targetIndex.getZeroBased());
        }

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName()));
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


        return (targetName != null && targetName.equals(otherDeleteCommand.targetName))
                || (targetIndex != null && targetIndex.equals(otherDeleteCommand.targetIndex));
    }

    @Override
    public String toString() {
        if (targetName != null) {
            return String.format("DeleteCommand[targetName=%s]", targetName);
        } else {
            return String.format("DeleteCommand[targetIndex=%d]", targetIndex.getOneBased());
        }
    }
}
