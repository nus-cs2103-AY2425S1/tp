package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointmentdatefilter.AppointmentDateFilter;
import seedu.address.model.healthservice.HealthService;


public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());

    @Test
    public void equals() {
        LocalDate startDate = LocalDate.parse("2000-10-10");
        LocalDate endDate = LocalDate.parse("2000-10-11");
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
    public void constructor_nullStartDate_throwsNullPointerException() {
        int a = 1;
    }
}
