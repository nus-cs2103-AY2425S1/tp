package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Time;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.shared.Date;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.PatientBuilder;

/**
 * Unit tests for {@code DeleteAppointmentCommand}.
 */
public class DeleteAppointmentCommandTest {

    private Model model;
    private Appointment appointmentToDelete;
    private Doctor doctor;
    private Patient patient;

    /**
     * Sets up the testing environment with an appointment, doctor, and patient.
     */
    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        doctor = new DoctorBuilder().build();
        patient = new PatientBuilder().build();

        appointmentToDelete = new Appointment(doctor, patient, new Date("23-04-2023"), new Time("1100"));
    }

    /**
     * Tests the successful execution of a valid delete appointment command.
     * Ensures the correct success message is returned.
     *
     * @throws Exception if the appointment cannot be deleted.
     */
    @Test
    public void execute_validAppointment_success() throws Exception {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(appointmentToDelete);

        CommandResult result = deleteAppointmentCommand.execute(model);

        // Checking the success message after the deletion
        assertEquals(String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                appointmentToDelete.getPatient().getName(),
                appointmentToDelete.getDoctor().getName(),
                appointmentToDelete.getDate(),
                appointmentToDelete.getTime()), result.getFeedbackToUser());
    }

    /**
     * Tests the behavior when attempting to delete an invalid appointment.
     * Expects a {@code CommandException} to be thrown.
     */
    @Test
    public void execute_invalidAppointment_throwsCommandException() {
        // Creating an invalid appointment
        Appointment invalidAppointment = new Appointment(new DoctorBuilder().build(),
                new PatientBuilder().build(), new Date("12-12-2023"), new Time("0900"));

        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(invalidAppointment);

        // Asserting that a CommandException is thrown with the expected message
        assertThrows(CommandException.class, () -> deleteAppointmentCommand.execute(model),
                DeleteAppointmentCommand.MESSAGE_INVALID_APPOINTMENT_ID);
    }

    /**
     * Tests the equality of {@code DeleteAppointmentCommand} objects.
     */
    @Test
    public void equals() {
        // Creating another appointment for comparison
        Appointment otherAppointment = new Appointment(new DoctorBuilder().build(),
                new PatientBuilder().build(), new Date("23-05-2023"), new Time("1200"));

        DeleteAppointmentCommand deleteFirstAppointmentCommand = new DeleteAppointmentCommand(appointmentToDelete);
        DeleteAppointmentCommand deleteSecondAppointmentCommand = new DeleteAppointmentCommand(otherAppointment);

        // same object -> returns true
        assertEquals(deleteFirstAppointmentCommand, deleteFirstAppointmentCommand);

        // same appointment -> returns true
        DeleteAppointmentCommand deleteFirstAppointmentCommandCopy = new DeleteAppointmentCommand(appointmentToDelete);
        assertEquals(deleteFirstAppointmentCommand, deleteFirstAppointmentCommandCopy);

        // different appointment -> returns false
        assertEquals(false, deleteFirstAppointmentCommand.equals(deleteSecondAppointmentCommand));

        // different type -> returns false
        assertEquals(false, deleteFirstAppointmentCommand.equals(1));
    }
}
