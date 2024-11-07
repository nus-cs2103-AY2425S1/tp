package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.ShowPatientInfoCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;



/**
 * Adds a patient to the address book with optional fields.
 */
public class AddFCommand extends Command {

    public static final String COMMAND_WORD = "addf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new patient record "
            + "(with additional information) into the system\n"
            + "Input \"help " + COMMAND_WORD + "\" for description and usage of this command";

    public static final String MESSAGE_SUCCESS = "New patient added.\n"
            + "%1$s\n"
            + "Input \"home\" to return to home page";

    public static final String MESSAGE_DUPLICATE_PATIENT = "This patient already exists in the Clinic Connect system";
    private static Logger logger = Logger.getLogger("Foo");

    private final Patient toAdd;


    /**
     * Creates an AddFCommand to add the specified {@code Patient}
     */
    public AddFCommand(Patient patient) {
        requireNonNull(patient);
        assert patient != null : "Patient to add should not be null";
        toAdd = patient;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPatient(toAdd)) {
            logger.log(Level.WARNING, "Attempted to add a duplicate patient");
            throw new CommandException(MESSAGE_DUPLICATE_PATIENT);
        }

        model.addPatient(toAdd);
        logger.log(Level.INFO, "New patient (" + toAdd.getNric() + ") has been added to the system");
        return new ShowPatientInfoCommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)),
                toAdd, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddFCommand)) {
            return false;
        }

        AddFCommand otherAddFCommand = (AddFCommand) other;
        return toAdd.equals(otherAddFCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
