package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAREGIVER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATIENT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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
            + PREFIX_PATIENT + "S1234567A "
            + PREFIX_CAREGIVER + "S7654321B";

    public static final String MESSAGE_SUCCESS = "Linked %1$s and %2$s";

    public static final String MESSAGE_DUPLICATE_LINK = "This link already exists in the address book";

    private final String patientNric;
    private final String caregiverNric;

    /**
     * Creates a LinkCommand to link the specified {@code Patient} and {@code Caregiver}
     */
    public LinkCommand(String patientNric, String caregiverNric) {
        this.patientNric = patientNric;
        this.caregiverNric = caregiverNric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasLink(patientNric, caregiverNric)) {
            throw new CommandException(MESSAGE_DUPLICATE_LINK);
        }

        model.addLink(patientNric, caregiverNric);
        return new CommandResult(String.format(MESSAGE_SUCCESS, patientNric, caregiverNric));
    }

}
