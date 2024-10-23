package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAREGIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Link a caregiver and a patient by their nric and update person model accordingly.
 */
public class LinkCommand extends Command {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Links a caregiver and a patient by their NRICs.\n"
            + "Parameters: "
            + PREFIX_PATIENT + "PATIENT_NRIC "
            + PREFIX_CAREGIVER + "CAREGIVER_NRIC\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATIENT + "S8484131E "
            + PREFIX_CAREGIVER + "S7654321B";

    public static final String MESSAGE_SUCCESS = "Linked %1$s and %2$s";

    public static final String MESSAGE_DUPLICATE_LINK = "This link already exists in CareLink";

    public static final String SAME_PERSON = "Cannot link same people";

    public static final String PERSON_NOT_FOUND = "Incorrect NRIC. Person not found";

    public static final String ROLE_NOT_MATCH = "Incorrect roles. The patient NRIC must correspond to a patient, "
            + "and the caregiver NRIC must correspond to a caregiver.";

    private final Nric patientNric;
    private final Nric caregiverNric;

    /**
     * Creates a LinkCommand to link the specified {@code Patient} and {@code Caregiver}
     */
    public LinkCommand(Nric patientNric, Nric caregiverNric) {
        this.patientNric = patientNric;
        this.caregiverNric = caregiverNric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (this.patientNric == this.caregiverNric) {
            throw new CommandException(SAME_PERSON);
        }

        Person patient = model.getPerson(patientNric);
        Person caregiver = model.getPerson(caregiverNric);
        if (patient == null || caregiver == null) {
            throw new CommandException(PERSON_NOT_FOUND);
        }

        if (!patient.getRoles().contains(Role.PATIENT)
                || !caregiver.getRoles().contains(Role.CAREGIVER)) {
            throw new CommandException(ROLE_NOT_MATCH);
        }

        if (model.hasLink(patient, caregiver)) {
            throw new CommandException(MESSAGE_DUPLICATE_LINK);
        }

        model.addLink(patient, caregiver);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(patient), Messages.format(caregiver)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LinkCommand)) {
            return false;
        }

        LinkCommand otherLinkCommand = (LinkCommand) other;
        return patientNric.equals(otherLinkCommand.patientNric)
                && caregiverNric.equals(otherLinkCommand.caregiverNric);
    }

}
