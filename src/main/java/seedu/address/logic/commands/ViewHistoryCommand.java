package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.time.LocalDateTime;

import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Views the medical history of an existing patient in the system.
 */
public class ViewHistoryCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the medical history of a patient/doctor "
            + "based on ID provided. \n"
            + COMMAND_WORD + " "
            + PREFIX_ID + "PATIENT_ID "
            + "[" + PREFIX_DATE + "DATE_TIME] \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ID + "01 " + PREFIX_DATE + "2023-09-25 10:15";
    public static final String MESSAGE_NO_HISTORY_FOUND = "No history found for the person";

    private final int personId;
    private final LocalDateTime dateTime;

    /**
     * @param personId of the patient to view the history of
     * @param dateTime the specific date and time of the history to view (optional)
     */
    public ViewHistoryCommand(int personId, LocalDateTime dateTime) {
        requireNonNull(personId); // Only patientId is mandatory
        this.personId = personId;
        this.dateTime = dateTime;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> allPersons = model.getAllPersons();
        Person personToView = model.getFilteredPersonById(allPersons, personId);
        if (personToView == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_ID);
        }

        LocalDateTime historyDateTime;
        String history;
        if (dateTime != null) {
            historyDateTime = dateTime;
            history = personToView.getOneHistory(historyDateTime, personId);
        } else {
            history = personToView.getStringAppointments();
        }

        if (history == null || history.isEmpty()) {
            throw new CommandException(MESSAGE_NO_HISTORY_FOUND);
        }

        return new CommandResult(history);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewHistoryCommand)) {
            return false;
        }

        // state check
        ViewHistoryCommand e = (ViewHistoryCommand) other;
        return personId == (e.personId)
                && (dateTime == null
                ? dateTime == e.dateTime
                : dateTime.equals(e.dateTime));
    }
}
