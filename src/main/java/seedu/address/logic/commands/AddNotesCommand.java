package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Id;
import seedu.address.model.person.Person;



/**
 * Adds notes to a Patient's remarks
 */
public class AddNotesCommand extends Command {

    public static final String COMMAND_WORD = "addNotes";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds notes to the remarks of a Patient."
            + "Existing remarks will be concatenated by the input.\n"
            + COMMAND_WORD + " "
            + PREFIX_ID + "[PATIENT_ID] "
            + PREFIX_REMARK + "[ADDITIONAL REMARKS]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ID + "1234 "
            + PREFIX_REMARK + "Much better than previous appointment.";

    public static final String MESSAGE_ADD_NOTES_SUCCESS = "Successfully "
            + "added notes: %s to patient of ID: %d.";
    private final int patientId;
    private final String additionalNotes;

    /**
     * Adds notes to a Patient's remarks
     * @param patientId patient id
     * @param additionalNotes notes to be added
     */
    public AddNotesCommand(int patientId, String additionalNotes) {
        requireAllNonNull(patientId, additionalNotes);

        this.patientId = patientId;
        this.additionalNotes = additionalNotes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        ObservableList<Person> allPersons = model.getFilteredPersonList();
        Person patientToAddNotes = model.getFilteredPatientById(allPersons, patientId);
        patientToAddNotes.addNotes(additionalNotes);

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADD_NOTES_SUCCESS, additionalNotes, patientId
        ));
    }


}
