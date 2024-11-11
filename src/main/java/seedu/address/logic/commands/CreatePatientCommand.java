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
 * Creates a new Patient profile
 */
public class CreatePatientCommand extends Command {
    public static final String COMMAND_WORD = "createP";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new patient. \n"
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

    public static final String MESSAGE_SUCCESS = "Successfully created a new patient of ID: #%d : \n"
            + "%2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This patient already exists.";
    public static final String MESSAGE_OVERLAPPING_DOCTOR = "This person already exists as a doctor!\n"
            + "Please check the details you have entered!";

    private final Person toAdd;

    /**
     * Creates an CreatePatientCommand to add the specified {@code Patient}
     */
    public CreatePatientCommand(Person patient) {
        requireNonNull(patient);
        toAdd = patient;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            if (model.getPersonRole(toAdd).equals("PATIENT")) {
                if (toAdd.getId() == Id.getCurrentPatientIdCounter() - 2) {
                    Id.reduceCurrentPatientIdCounter();
                }
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            }
            if (model.getPersonRole(toAdd).equals("DOCTOR")) {
                if (toAdd.getId() == Id.getCurrentPatientIdCounter() - 2) {
                    Id.reduceCurrentPatientIdCounter();
                }
                throw new CommandException(MESSAGE_OVERLAPPING_DOCTOR);
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

        if (!(other instanceof CreatePatientCommand)) {
            return false;
        }

        CreatePatientCommand otherCmd = (CreatePatientCommand) other;
        return otherCmd.toAdd.equals(this.toAdd);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(toAdd);
    }

}
