package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Id;
import seedu.address.model.person.Person;

/**
 * Checks for appointments of an existing patient in the system.
 */
public class CheckAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "checkAppointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Checks the appointments of the doctor identified "
            + "by the doctor ID. "
            + "Parameters: DOCTOR_ID (must be a valid ID) "
            + "LOCAL_DATETIME \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_ID + " 01" + PREFIX_DAY_DATE + " 2023-09-25";

    public static final String MESSAGE_CHECK_APPOINTMENT_SUCCESS = "Checked appointment for Doctor: %1$s on %2$s";
    public static final String MESSAGE_NO_APPOINTMENT_FOUND = "No appointment found for Doctor: %1$s on %2$s";

    public static final String MESSAGE_NO_DATE_TIME = "No date time is given for Doctor appointment: %1$s on %2$s";

    private final int doctorId;
    private final LocalDate date;

    /**
     * @param doctorId of the patient to check the appointment of
     * @param date the specific date and time of the appointment to check (optional)
     */
    public CheckAppointmentCommand(int doctorId, LocalDate date) {
        requireNonNull(doctorId); // Only patientId is mandatory
        this.doctorId = doctorId;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Person> allPersons = model.getFilteredPersonList();
        Person doctorToCheckAppointment = model.getFilteredDoctorById(allPersons, doctorId);
        if (doctorToCheckAppointment == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        LocalDate appointmentDateTime;
        String appointmentDetails;
        throw new CommandException(String.format(MESSAGE_NO_APPOINTMENT_FOUND, doctorToCheckAppointment.getName()));

        // TODO UPDATE
//        if (date != null) {
//            appointmentDateTime = date;
//            appointmentDetails = doctorToCheckAppointment.getOneDayDoctorAppointment(appointmentDateTime, doctorId);
//        } else {
//            throw new CommandException(String.format(MESSAGE_NO_DATE_TIME, doctorToCheckAppointment.getName()));
//        }
//
//        if (appointmentDetails == null || appointmentDetails.isEmpty()) {
//            throw new CommandException(String.format(MESSAGE_NO_APPOINTMENT_FOUND, doctorToCheckAppointment.getName()));
//        }

//        return new CommandResult(appointmentDetails);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CheckAppointmentCommand)) {
            return false;
        }

        // state check
        CheckAppointmentCommand e = (CheckAppointmentCommand) other;
        return doctorId == (e.doctorId)
                && (date == null ? e.date == null : date.equals(e.date));
    }
}
