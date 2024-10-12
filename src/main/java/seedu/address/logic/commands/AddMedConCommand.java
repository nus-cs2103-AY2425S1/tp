package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.MedCon;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

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

    public static final String MESSAGE_ADD_MEDCON_SUCCESS = "Added medical conditon to Person: %1$s";
    public static final String MESSAGE_DELETE_MEDCON_SUCCESS = "Removed medical conditon from Person: %1$s";
    public static final String PATIENT_DOES_NOT_EXIST = "Patient does not exist in contact list";

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
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Person person : lastShownList) {
            if (person.getNric().equals(this.nric)) {
                Person editedPerson = new Person(
                        person.getName(), person.getPhone(), person.getEmail(),
                        person.getNric(), person.getAddress(), person.getDateOfBirth(),
                        person.getGender(), person.getTags(), person.getPriority(), person.getAppointments(),
                        medCon);
                model.setPerson(person, editedPerson);
                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(generateSuccessMessage(editedPerson));
            }
        }
        throw new CommandException(PATIENT_DOES_NOT_EXIST);
    }

    /**
     * Generates a command execution success message based on whether
     * the medical condition is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !medCon.value.isEmpty() ? MESSAGE_ADD_MEDCON_SUCCESS : MESSAGE_DELETE_MEDCON_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
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
