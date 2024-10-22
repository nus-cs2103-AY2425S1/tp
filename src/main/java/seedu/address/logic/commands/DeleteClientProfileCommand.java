package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Deletes a client identified using it's displayed index from the address book.
 */
public class DeleteClientProfileCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the client profile corresponding to the client's name.\n"
            + "Parameters: CLIENT_NAME (case-insensitive)\n"
            + "Example: " + COMMAND_WORD + " n/Tan Wen Xuan";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Successfully deleted %1$s "
            + "with the number: %2$s " + "and email: %3$s!";
    private final Name targetName;

    public DeleteClientProfileCommand(Name targetName) {
        this.targetName = targetName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToDelete = model.getPersonByName(targetName);
        if (!lastShownList.contains(personToDelete)) {
            String closestMatch = findClosestMatch(targetName.toString(), lastShownList);

            if (closestMatch != null) {
                throw new CommandException(String.format(Messages.MESSAGE_SUGGESTION, closestMatch));
            } else {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INPUT);
            }
        }

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName(), personToDelete.getPhone(), personToDelete.getEmail()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteClientProfileCommand)) {
            return false;
        }

        DeleteClientProfileCommand otherDeleteCommand = (DeleteClientProfileCommand) other;
        return targetName.equals(otherDeleteCommand.targetName);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetName", targetName)
                .toString();
    }
}
