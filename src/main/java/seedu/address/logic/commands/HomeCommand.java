package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.DefaultCommandResult;
import seedu.address.model.Model;

/**
 * Lists all patients in the address book to the user.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";
    public static final String MESSAGE_SUCCESS = "Returned to home page" + "\n"
            + MESSAGE_PATIENTS_LISTED_OVERVIEW;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        String msgSuccess = successMessageCreator(model);

        return new DefaultCommandResult(msgSuccess);
    }

    /**
     * creates the success message based on the size of the patient size in model
     */
    public static String successMessageCreator(Model model) {
        int size = model.getPatientSize();
        String isOrAre;
        String patientOrPatients;
        if (size == 1) {
            isOrAre = "is";
            patientOrPatients = "patient";
        } else {
            isOrAre = "are";
            patientOrPatients = "patients";
        }
        return String.format(MESSAGE_SUCCESS, isOrAre, size, patientOrPatients);
    }
}
