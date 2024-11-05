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



/**
 * Adds notes to a Patient's remarks
 */
public class AddRemarksCommand extends Command {

    public static final String COMMAND_WORD = "addR";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds remarks to the patient."
            + "Existing remarks will be concatenated by the input.\n"
            + COMMAND_WORD + " "
            + PREFIX_ID + "[PATIENT_ID] "
            + PREFIX_REMARK + "[ADDITIONAL REMARKS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1234 "
            + PREFIX_REMARK + "Much better than previous appointment.";

    public static final String MESSAGE_ADD_REMARKS_SUCCESS = "Successfully "
            + "added remarks: '%s' to patient of ID: %d.";
    public static final String MESSAGE_ADD_NOTES_FAILURE = "Unable to "
            + "add remarks! Check the id entered!";
    private final int patientId;
    private final String additionalNotes;

    /**
     * Adds notes to a Patient's remarks
     * @param patientId patient id
     * @param additionalNotes notes to be added
     */
    public AddRemarksCommand(int patientId, String additionalNotes) {
        requireAllNonNull(patientId, additionalNotes);

        this.patientId = patientId;
        this.additionalNotes = additionalNotes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        ObservableList<Person> allPersons = model.getFilteredPersonList();
        Person patientToAddRemarks = model.getFilteredPatientById(allPersons, patientId);
        if (patientToAddRemarks == null) {
            throw new CommandException(MESSAGE_ADD_NOTES_FAILURE);
        }
        Person editedPatient = new Person(patientToAddRemarks.getName(),
                patientToAddRemarks.getId(), patientToAddRemarks.getRole(), patientToAddRemarks.getPhone(),
                patientToAddRemarks.getEmail(), patientToAddRemarks.getAddress(),
                patientToAddRemarks.addRemarks(additionalNotes),
                patientToAddRemarks.getAppointments(), patientToAddRemarks.getTags());

        model.setPerson(patientToAddRemarks, editedPatient);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADD_REMARKS_SUCCESS, additionalNotes, patientId
        ));
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
                && additionalNotes.equals(otherCommand.additionalNotes);
    }
}
