package seedu.address.logic.commands;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.log.Log;
import seedu.address.model.log.LogEntry;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

import java.util.List;

import static seedu.address.logic.commands.AddLogCommand.MESSAGE_PERSON_NOT_FOUND;

public class AddLogEntryCommand extends Command {

    public static final String COMMAND_WORD = "add log entry";
    private final IdentityNumber identityNumber;
    private final AppointmentDate appointmentDate;

    public AddLogEntryCommand(IdentityNumber identityNumber, AppointmentDate appointmentDate) {
        this.identityNumber = identityNumber;
        this.appointmentDate = appointmentDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException("This method is not meant to be called.");
    }

    public CommandResult execute(Model model, Logic logic) throws CommandException {
        // Validate identity number and appointment date
        Person personToUpdate = getPerson(model);
        Boolean isValidAppointmentDate = AppointmentDate.isValidDateString(appointmentDate.toString());

        if (!isValidAppointmentDate) {
            throw new CommandException(AppointmentDate.MESSAGE_CONSTRAINTS);
        }

        if (personToUpdate == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, identityNumber));
        }

        // Use Logic to prompt for the log entry input
        String logEntryText = logic.promptForLogEntryInput();

        if (logEntryText == null || logEntryText.trim().isEmpty()) {
            throw new CommandException("No log entry provided.");
        }

        // Construct the Log and AddLogCommand
        LogEntry logEntry = new LogEntry(logEntryText);
        Log log = new Log(appointmentDate, logEntry);
        AddLogCommand addLogCommand = new AddLogCommand(identityNumber, log);

        // Execute AddLogCommand and return result
        return addLogCommand.execute(model);
    }

    private Person getPerson(Model model) throws CommandException {
        List<Person> lastShownList = model.getPersonList();
        Person personToUpdate = null;

        // Find the person by identity number
        for (Person person : lastShownList) {
            if (person.getIdentityNumber().equals(identityNumber)) {
                personToUpdate = person;
                break;
            }
        }

        // If person was not found, throw an exception
        if (personToUpdate == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, identityNumber));
        }
        return personToUpdate;
    }
}
