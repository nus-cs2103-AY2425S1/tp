package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a Car belonging to a person already present in the MATER address book.
 */
public class DeleteCarCommand extends Command {

    public static final String COMMAND_WORD = "delete-car";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the car currently belonging to the client of the index provided.\n"
            + "User must already have a car. The index must be a positive integer.\n"
            + "User's car cannot be currently checked-in in order to be deleted.\n"
            + "Example: " + COMMAND_WORD  + " 1 ";

    public static final String MESSAGE_USER_IS_CHECKED_IN = "This person's car is currently checked in.";
    public static final String MESSAGE_DELETE_CAR_SUCCESS = "Car successfully deleted: %s";
    public static final String MESSAGE_USER_HAS_NO_CAR = "This user has no car to delete.";

    private final Index index;

    public DeleteCarCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Valid index check
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Set up the person the method will be interacting with
        Person personToDeleteCarFrom = lastShownList.get(index.getZeroBased());

        // Check-in status check
        if (personToDeleteCarFrom.isServicing()) {
            throw new CommandException(MESSAGE_USER_IS_CHECKED_IN);
        }

        // Check whether person has a car to delete in the first place
        if (personToDeleteCarFrom.getCar() == null) {
            throw new CommandException(MESSAGE_USER_HAS_NO_CAR);
        }

        // As Person is immutable, we must create a new Person object.
        Person updatedPerson = new Person(
                personToDeleteCarFrom.getName(),
                personToDeleteCarFrom.getPhone(),
                personToDeleteCarFrom.getEmail(),
                personToDeleteCarFrom.getAddress(),
                personToDeleteCarFrom.getIssues()
        );

        // This method call effectively replaces the old user with the new user with a car.
        model.setPerson(personToDeleteCarFrom, updatedPerson);

        return new CommandResult(String.format(MESSAGE_DELETE_CAR_SUCCESS, index));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCarCommand)) {
            return false;
        }

        DeleteCarCommand otherCommand = (DeleteCarCommand) other;
        return index.equals(otherCommand.index);
    }

}
