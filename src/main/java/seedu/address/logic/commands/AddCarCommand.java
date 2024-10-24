package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAKE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VRN;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.car.Car;
import seedu.address.model.person.Person;

/**
 * Adds a Car to a Client in the MATER address book.
 */
public class AddCarCommand extends Command {

    public static final String COMMAND_WORD = "add-car";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a Car to the indexed Client, who must not be associated to a Car.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_VRN + " VEHICLE REGISTRATION NUMBER "
            + PREFIX_VIN + " VEHICLE IDENTIFICATION NUMBER "
            + PREFIX_MAKE + " VEHICLE MAKE "
            + PREFIX_MODEL + " VEHICLE MODEL\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_VRN + "SJH9514P "
            + PREFIX_VIN + "KMHGH4JH3EU073801 "
            + PREFIX_MAKE + "Toyota "
            + PREFIX_MODEL + "Corolla ";

    public static final String MESSAGE_USER_ALREADY_HAS_CAR = "Client is already associated to a Car.";
    public static final String MESSAGE_ADD_CAR_SUCCESS = "Car successfully added to Client: %s (VRN: %s).";
    public static final String MESSAGE_SAME_CAR_ALREADY_EXISTS = "Car already exists in MATER.";

    private final Index index;
    private final Car carToAdd;


    /**
     * Creates an AddCarCommand to add the specified {@code Car} to the {@code Person}
     */
    public AddCarCommand(Index index, Car carToAdd) {
        requireNonNull(index);
        requireNonNull(carToAdd);
        this.index = index;
        this.carToAdd = carToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddCarTo = lastShownList.get(index.getZeroBased());

        if (personToAddCarTo.getCar() != null) {
            throw new CommandException(MESSAGE_USER_ALREADY_HAS_CAR);
        }

        // Check whether the car is already present
        if (model.hasCar(carToAdd)) {
            throw new CommandException(MESSAGE_SAME_CAR_ALREADY_EXISTS);
        }

        // As Person is immutable, we must create a new Person object.
        Person updatedPerson = new Person(
                personToAddCarTo.getName(),
                personToAddCarTo.getPhone(),
                personToAddCarTo.getEmail(),
                personToAddCarTo.getAddress(),
                personToAddCarTo.getIssues(),
                carToAdd
        );

        // This method call effectively replaces the old user with the new user with a car.
        model.setPerson(personToAddCarTo, updatedPerson);

        return new CommandResult(String.format(MESSAGE_ADD_CAR_SUCCESS, updatedPerson.getName(), carToAdd.getVrn()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCarCommand)) {
            return false;
        }

        AddCarCommand otherCommand = (AddCarCommand) other;
        return index.equals(otherCommand.index) && carToAdd.equals(otherCommand.carToAdd);
    }

}

