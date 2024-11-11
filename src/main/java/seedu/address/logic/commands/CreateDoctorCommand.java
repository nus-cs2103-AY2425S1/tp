package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Objects;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Id;
import seedu.address.model.person.Person;

/**
 * Creates a new Doctor profile
 *
 * Code is adapted from @@author sandyk0105
 */
public class CreateDoctorCommand extends Command {
    public static final String COMMAND_WORD = "createD";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new doctor. \n"
            + COMMAND_WORD + " "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "Successfully created a new doctor of ID: #%d : \n"
            + "%2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This doctor already exists.";
    public static final String MESSAGE_OVERLAPPING_PATIENT = "This person already exists as a patient\n"
            + "Please check the details you have entered!";

    private final Person toAdd;

    /**
     * Creates an CreateDoctorCommand to add the specified {@code doctor}
     */
    public CreateDoctorCommand(Person doctor) {
        requireNonNull(doctor);
        toAdd = doctor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            if (model.getPersonRole(toAdd).equals("DOCTOR")) {
                if (toAdd.getId() == Id.getCurrentDoctorIdCounter() - 2) {
                    Id.reduceCurrentDoctorIdCounter();
                }
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }
            if (model.getPersonRole(toAdd).equals("PATIENT")) {
                if (toAdd.getId() == Id.getCurrentDoctorIdCounter() - 2) {
                    Id.reduceCurrentDoctorIdCounter();
                }
                throw new CommandException(MESSAGE_OVERLAPPING_PATIENT);
            }
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getId(), Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CreateDoctorCommand)) {
            return false;
        }

        CreateDoctorCommand otherCmd = (CreateDoctorCommand) other;
        return otherCmd.toAdd.equals(this.toAdd);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(toAdd);
    }

}
