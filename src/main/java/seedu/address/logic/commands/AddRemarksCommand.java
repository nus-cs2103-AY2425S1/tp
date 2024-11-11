package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;


/**
 * Adds remarks to a Patient's remarks
 */
public class AddRemarksCommand extends Command {

    public static final String COMMAND_WORD = "addR";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds remarks to the patient. "
            + "Existing remarks will be concatenated by the input.\n"
            + COMMAND_WORD + " "
            + PREFIX_ID + "PATIENT_ID "
            + PREFIX_REMARK + "REMARK\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1234 "
            + PREFIX_REMARK + "Much better than previous appointment.";

    public static final String MESSAGE_ADD_REMARKS_SUCCESS = "Successfully "
            + "added remarks: '%s' to patient of ID: %d.";
    public static final String MESSAGE_ADD_REMARKS_FAILURE = "Unable to "
            + "add remarks! Check the id entered!";
    public static final String MESSAGE_WRONG_ROLE = "Unable to add remarks for a doctor.\n"
            + " Please check the id of the person you are adding remarks to!";
    private final int patientId;
    private final Remark additionalRemarks;

    /**
     * Adds notes to a Patient's remarks
     * @param patientId patient id
     * @param remarks additional remarks to be added
     */
    public AddRemarksCommand(int patientId, Remark remarks) {
        requireAllNonNull(remarks);
        this.patientId = patientId;
        this.additionalRemarks = remarks;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        ObservableList<Person> allPersons = model.getAllPersons();
        Person patientToAddRemarks = model.getFilteredPatientById(allPersons, patientId);

        checkForCorrectPatient(patientToAddRemarks);

        Person editedPatient = createUpdatedRemarksPatient(patientToAddRemarks);

        model.setPerson(patientToAddRemarks, editedPatient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADD_REMARKS_SUCCESS,
                additionalRemarks, patientId
        ));
    }

    /**
     * Checks for valid patient input
     */
    public void checkForCorrectPatient(Person patient) throws CommandException {
        if (patient == null) {
            throw new CommandException(MESSAGE_ADD_REMARKS_FAILURE);
        }
        if (patient.getRole().equals("DOCTOR")) {
            throw new CommandException(MESSAGE_WRONG_ROLE);
        }
    }

    /**
     * Creates a copy of the original patient with remarks being updated
     */
    public Person createUpdatedRemarksPatient(Person patientToAddRemarks) {
        return new Person(patientToAddRemarks.getName(),
                patientToAddRemarks.getId(), patientToAddRemarks.getRole(), patientToAddRemarks.getPhone(),
                patientToAddRemarks.getEmail(), patientToAddRemarks.getAddress(),
                patientToAddRemarks.addRemarks(additionalRemarks.getValue()),
                patientToAddRemarks.getAppointments());
    }
    @Override
    public boolean equals(Object other) {
        // Shortcuts: reference equality
        if (other == this) {
            return true;
        }

        // instanceof check and cast
        if (!(other instanceof AddRemarksCommand)) {
            return false;
        }

        AddRemarksCommand otherCommand = (AddRemarksCommand) other;

        // Compare patientId and additionalNotes
        return patientId == otherCommand.patientId
                && additionalRemarks.equals(otherCommand.additionalRemarks);
    }
}
