package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.MedCon;

/**
 * Command to assign a medical condition to a patient in the address book.
 */
public class AddMedConCommand extends Command {

    public static final String COMMAND_WORD = "addMedCon";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns "
            + "a medical condition to a patient in the address book.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + "c/MedCon\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "T0123456F "
            + "c/Diabetes";

    public static final String MESSAGE_ARGUMENTS = "NRIC: %1$d, MedCon: %2$s";

    private final Nric nric;
    private final MedCon medCon;

    /**
     * @param nric of the patient to assign the medical condition to
     * @param medCon of the patient
     */
    public AddMedConCommand(Nric nric, MedCon medCon) {
        requireAllNonNull(nric, medCon);

        this.nric = nric;
        this.medCon = medCon;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, nric, medCon));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMedConCommand)) {
            return false;
        }

        AddMedConCommand c = (AddMedConCommand) other;
        return nric.equals(c.nric)
                && medCon.equals(c.medCon);
    }
}