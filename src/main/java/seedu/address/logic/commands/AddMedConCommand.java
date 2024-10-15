package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            + "one or more medical conditions to a patient in the address book.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + "c/CONDITION...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "T0123456F "
            + "c/Diabetes c/Hypertension";

    public static final String MESSAGE_ARGUMENTS = "NRIC: %1$d, MedCon: %2$s";

    public static final String MESSAGE_ADD_MEDCON_SUCCESS = "Added medical condition to Person: %1$s";
    public static final String MESSAGE_DELETE_MEDCON_SUCCESS = "Removed medical condition from Person: %1$s";
    public static final String PATIENT_DOES_NOT_EXIST = "Patient does not exist in contact list";
    public static final String MESSAGE_DUPLICATE_MEDCON = "Condition already assigned: %1$s";

    private final Nric nric;
    private final Set<MedCon> medCons;

    /**
     * @param nric of the patient to assign the medical conditions to
     * @param medCons set of medical conditions to be assigned.
     */
    public AddMedConCommand(Nric nric, Set<MedCon> medCons) {
        requireAllNonNull(nric, medCons);

        this.nric = nric;
        this.medCons = medCons;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Person person : lastShownList) {
            if (person.getNric().equals(this.nric)) {
                Set<MedCon> updatedMedConSet = new HashSet<>(person.getMedCons());
                // check for duplicates
                for (MedCon medCon : medCons) {
                    if (!updatedMedConSet.add(medCon)) {
                        throw new CommandException(String.format(MESSAGE_DUPLICATE_MEDCON, medCon.value));
                    }
                }
                Person editedPerson = new Person(
                        person.getName(), person.getPhone(), person.getEmail(),
                        person.getNric(), person.getAddress(), person.getDateOfBirth(),
                        person.getGender(), person.getTags(), person.getPriority(), person.getAppointments(),
                        updatedMedConSet);
                model.setPerson(person, editedPerson);
                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
                return new CommandResult(generateSuccessMessage(editedPerson));
            }
        }
        throw new CommandException(PATIENT_DOES_NOT_EXIST);
    }

    /**
     * Generates a command execution success message based on the added medical conditions.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_ADD_MEDCON_SUCCESS, Messages.format(personToEdit));
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
                && medCons.equals(c.medCons);
    }
}
