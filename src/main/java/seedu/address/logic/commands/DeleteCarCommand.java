package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a Car belonging to a person already present in the MATER address book.
 */
public class DeleteCarCommand extends Command {

    public static final String COMMAND_WORD = "delete-car";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the Car of the indexed Client, who must be associated to a Car.\n"
            + "Car must not be Checked In.\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_USER_IS_CHECKED_IN = "Car is currently Checked In.";
    public static final String MESSAGE_DELETE_CAR_SUCCESS = "Car successfully deleted from Client";
    public static final String MESSAGE_USER_HAS_NO_CAR = "Client is not associated to a Car.";

    private final Index index;

    /**
     * Creates a DeleteCarCommand to delete a {@code Car} from the {@code Person}
     */
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

        return new CommandResult(Messages.formatSuccessMessage(updatedPerson, MESSAGE_DELETE_CAR_SUCCESS));
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
