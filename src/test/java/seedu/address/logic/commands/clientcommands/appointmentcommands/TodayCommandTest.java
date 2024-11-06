package seedu.address.logic.commands.clientcommands.appointmentcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_TODAY_APPOINTMENTS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.HasAppointmentTodayPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for TodayCommand.
 */
public class TodayCommandTest {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yy");
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
    }

    @Test
    public void execute_noAppointmentsToday_throwsCommandException() {
        TodayCommand todayCommand = new TodayCommand();

        // Ensure model is set to a state with no appointments today
        model.updateFilteredPersonList(p -> false);

        assertCommandFailure(todayCommand, model, TodayCommand.MESSAGE_NO_APPOINTMENTS_TODAY);
    }

    @Test
    public void execute_appointmentsToday_showsList() {
        // Get today's date formatted as "dd-MM-yy"
        String todayDate = LocalDate.now().format(DATE_FORMATTER);

        // Add a person (Buyer) with an appointment today to the model
        Person buyerWithAppointmentToday = new PersonBuilder()
                .withName("Alice Buyer")
                .withAppointment(todayDate, "0800", "0900")
                .buildBuyer();

        model.addPerson(buyerWithAppointmentToday);
        expectedModel.addPerson(buyerWithAppointmentToday);

        HasAppointmentTodayPredicate predicate = new HasAppointmentTodayPredicate();
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_TODAY_APPOINTMENTS,
                                    expectedModel.getFilteredPersonList().size());

        TodayCommand todayCommand = new TodayCommand();

        assertCommandSuccess(todayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleAppointmentsToday_showsList() {
        // Get today's date formatted as "dd-MM-yy"
        String todayDate = LocalDate.now().format(DATE_FORMATTER);

        // Add multiple persons (Buyer and Seller) with appointments today to the model
        Person buyerWithAppointmentToday = new PersonBuilder()
                .withName("Alice Buyer")
                .withAppointment(todayDate, "0800", "0900")
                .buildBuyer();

        Person sellerWithAppointmentToday = new PersonBuilder()
                .withName("Bob Seller")
                .withAppointment(todayDate, "1000", "1100")
                .buildSeller();

        model.addPerson(buyerWithAppointmentToday);
        model.addPerson(sellerWithAppointmentToday);

        expectedModel.addPerson(buyerWithAppointmentToday);
        expectedModel.addPerson(sellerWithAppointmentToday);

        HasAppointmentTodayPredicate predicate = new HasAppointmentTodayPredicate();
        expectedModel.updateFilteredPersonList(predicate);

        String expectedMessage = String.format(MESSAGE_TODAY_APPOINTMENTS,
                                    expectedModel.getFilteredPersonList().size());

        TodayCommand todayCommand = new TodayCommand();

        assertCommandSuccess(todayCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        TodayCommand todayCommand = new TodayCommand();
        TodayCommand anotherTodayCommand = new TodayCommand();

        // same object -> returns true
        assertTrue(todayCommand.equals(todayCommand));

        // same values -> returns true
        assertTrue(todayCommand.equals(anotherTodayCommand));

        // different types -> returns false
        assertFalse(todayCommand.equals(1));

        // null -> returns false
        assertFalse(todayCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        TodayCommand todayCommand = new TodayCommand();
        String expected = TodayCommand.class.getCanonicalName()
                + "{predicate=" + new HasAppointmentTodayPredicate() + "}";
        assertEquals(expected, todayCommand.toString());
    }
}
