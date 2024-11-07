package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.FilterCommand.RETURN_TO_HOME;
import static seedu.address.model.filteredappointment.FilteredAppointment.APPOINTMENT_COMPARATOR;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import java.time.LocalDate;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.commandresult.ShowFilteredApptsCommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointmentdatefilter.AppointmentDateFilter;
import seedu.address.model.filteredappointment.FilteredAppointment;
import seedu.address.model.healthservice.HealthService;


public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());

    @Test
    public void equals() {
        LocalDate startDate = LocalDate.parse("2000-10-10");
        LocalDate endDate = LocalDate.parse("2025-10-11");
        HealthService service = new HealthService("Blood Test");
        AppointmentDateFilter dateFilter = new AppointmentDateFilter(startDate, endDate, service);
        AppointmentDateFilter dateFilter2 = new AppointmentDateFilter(null, endDate, null);

        FilterCommand firstFilterCommand = new FilterCommand(dateFilter);
        FilterCommand secondFilterCommand = new FilterCommand(dateFilter2);

        // same object -> returns true
        assertTrue(firstFilterCommand.equals(firstFilterCommand));

        // same values -> returns true
        FilterCommand firstFilterCommandCopy = new FilterCommand(dateFilter);
        assertTrue(firstFilterCommand.equals(firstFilterCommandCopy));

        // different types -> returns false
        assertFalse(firstFilterCommand.equals(1));

        // null -> returns false
        assertFalse(firstFilterCommand.equals(null));

        // different patient -> returns false
        assertFalse(firstFilterCommand.equals(secondFilterCommand));
    }

    @Test
    public void constructor_nullDateFilter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilterCommand(null));
    }

    @Test
    public void execute_singleDateNoHealthService_multiplePatientsFound() {
        LocalDate startDate = LocalDate.parse("2024-12-12");
        LocalDate endDate = LocalDate.parse("2024-12-12");
        HealthService service = null;
        AppointmentDateFilter dateFilter = new AppointmentDateFilter(startDate, endDate, service);
        String expectedMessage = "2 appts found " + dateFilter + RETURN_TO_HOME;

        FilterCommand command = new FilterCommand(dateFilter);

        expectedModel.filterAppts(dateFilter);
        TreeSet<FilteredAppointment> appointments = new TreeSet<>(APPOINTMENT_COMPARATOR);
        appointments.add(new FilteredAppointment(ALICE.getAppts().get(0), ALICE));
        appointments.add(new FilteredAppointment(CARL.getAppts().get(0), CARL));

        CommandResult expectedCommandResult = new ShowFilteredApptsCommandResult(expectedMessage, true);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_rangeDateWithHealthService_multiplePatientsFound() {
        LocalDate startDate = LocalDate.parse("2023-12-12");
        LocalDate endDate = LocalDate.parse("2025-12-12");
        HealthService service = new HealthService("Vaccination");

        AppointmentDateFilter dateFilter = new AppointmentDateFilter(startDate, endDate, service);
        String expectedMessage = "2 appts found " + dateFilter + RETURN_TO_HOME;

        FilterCommand command = new FilterCommand(dateFilter);

        expectedModel.filterAppts(dateFilter);
        TreeSet<FilteredAppointment> appointments = new TreeSet<>(APPOINTMENT_COMPARATOR);
        appointments.add(new FilteredAppointment(BENSON.getAppts().get(0), BENSON));
        appointments.add(new FilteredAppointment(CARL.getAppts().get(0), CARL));

        CommandResult expectedCommandResult = new ShowFilteredApptsCommandResult(expectedMessage, true);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(appointments, model.getFilteredAppts());
    }

    @Test
    public void execute_rangeDateWithHealthService_onePatientFound() {
        LocalDate startDate = LocalDate.parse("2025-11-12");
        LocalDate endDate = LocalDate.parse("2030-12-12");
        HealthService service = new HealthService("Vaccination");
        AppointmentDateFilter dateFilter = new AppointmentDateFilter(startDate, endDate, service);
        String expectedMessage = "1 appt found " + dateFilter + RETURN_TO_HOME;

        FilterCommand command = new FilterCommand(dateFilter);

        expectedModel.filterAppts(dateFilter);
        TreeSet<FilteredAppointment> appointments = new TreeSet<>(APPOINTMENT_COMPARATOR);
        appointments.add(new FilteredAppointment(BENSON.getAppts().get(0), BENSON));

        CommandResult expectedCommandResult = new ShowFilteredApptsCommandResult(expectedMessage, true);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_rangeDateWithHealthService_noPatientFound() {
        LocalDate startDate = LocalDate.parse("2025-11-12");
        LocalDate endDate = LocalDate.parse("2030-12-12");
        HealthService service = new HealthService("Consult");
        AppointmentDateFilter dateFilter = new AppointmentDateFilter(startDate, endDate, service);
        String expectedMessage = "No appts found " + dateFilter + RETURN_TO_HOME;

        FilterCommand command = new FilterCommand(dateFilter);

        expectedModel.filterAppts(dateFilter);
        CommandResult expectedCommandResult = new ShowFilteredApptsCommandResult(expectedMessage, true);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(new TreeSet<>(APPOINTMENT_COMPARATOR), model.getFilteredAppts());
    }
}
