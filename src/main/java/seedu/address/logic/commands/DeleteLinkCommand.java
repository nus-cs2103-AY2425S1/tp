package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAREGIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Deletes a link between a patient and a caregiver by their nric and update person model accordingly.
 */
public class DeleteLinkCommand extends Command {

    public static final String COMMAND_WORD = "deletelink";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the link identified by the NRIC number of the patient and the caregiver.\n"
            + "Parameters: "
            + PREFIX_PATIENT + "PATIENT_NRIC "
            + PREFIX_CAREGIVER + "CAREGIVER_NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT + "S1234567A "
            + PREFIX_CAREGIVER + "S1234567B";

    public static final String MESSAGE_DELETE_LINK_SUCCESS = "Deleted Link between %1$s and %2$s";

    public static final String MESSAGE_NO_LINK = "This link does not exist in CareLink";

    public static final String PERSON_NOT_FOUND = "Incorrect NRIC. Person not found";

    private final Nric patientNric;
    private final Nric caregiverNric;

    /**
     * @param patientNric The Nric of the patient
     * @param caregiverNric The Nric of the caregiver
     */

    public DeleteLinkCommand(Nric patientNric, Nric caregiverNric) {
        this.patientNric = patientNric;
        this.caregiverNric = caregiverNric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person patient = model.getPerson(patientNric);
        Person caregiver = model.getPerson(caregiverNric);
        if (patient == null || caregiver == null) {
            throw new CommandException(PERSON_NOT_FOUND);
        }

        if (!model.hasLink(patient, caregiver)) {
            throw new CommandException(MESSAGE_NO_LINK);
        }

        model.deleteLink(patient, caregiver);
        return new CommandResult(
                String.format(MESSAGE_DELETE_LINK_SUCCESS, Messages.format(patient), Messages.format(caregiver)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteLinkCommand)) {
            return false;
        }

        DeleteLinkCommand otherLinkCommand = (DeleteLinkCommand) other;
        return patientNric.equals(otherLinkCommand.patientNric)
                && caregiverNric.equals(otherLinkCommand.caregiverNric);
    }

}
