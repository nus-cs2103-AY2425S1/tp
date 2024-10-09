package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using their name from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name used in the address book.\n"
            + "Parameters: NAME (must match exactly one person)\n"
            + "Example: " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person index provided is invalid"; // Updated message

    private final Name targetName;

    public DeleteCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Optional<Person> personToDelete = lastShownList.stream()
                .filter(person -> person.getName().equals(targetName))
                .findFirst();

        if (personToDelete.isEmpty()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND); // Updated exception message
        }

        model.deletePerson(personToDelete.get());
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.get().getName()));
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
        return targetName.equals(otherDeleteCommand.targetName);
    }

    @Override
    public String toString() {
        return String.format("DeleteCommand[targetName=%s]", targetName);
    }
}
