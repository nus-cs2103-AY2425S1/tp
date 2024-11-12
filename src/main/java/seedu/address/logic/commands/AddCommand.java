package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.ShowPatientInfoCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * Adds a patient to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " usage: add n|[NAME] i|[NRIC] s|[SEX] d|[DATE OF BIRTH] p|[PHONE NO.]\n"
            + "Input \"help " + COMMAND_WORD + "\" for detailed description and usage of this command";

    public static final String MESSAGE_SUCCESS = "New patient added.\n"
            + "%1$s\n"
            + "Input \"home\" to return to home page";

    public static final String MESSAGE_DUPLICATE_PATIENT = "This patient already exists in the ClinicConnect system";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    private final Patient toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Patient}
     */
    public AddCommand(Patient patient) {
        requireNonNull(patient);
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
        return new ShowPatientInfoCommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)),
                toAdd, true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
