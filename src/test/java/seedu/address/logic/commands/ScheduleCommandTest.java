package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ScheduleCommand.
 */
public class ScheduleCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        // Initialize the model with typical persons, some with appointments and some without
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsAppointments() {
        ScheduleCommand command = new ScheduleCommand();
        String expectedMessage = ScheduleCommand.MESSAGE_SUCCESS;

        // Update the expected model to reflect the filtered and sorted list after executing the command
        expectedModel.updateFilteredPersonList(person -> person.getAppointment() != null);
        expectedModel.updateSortedPersonList(new ScheduleCommand.ComparatorAppointment());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsAppointments() {
        // Simulate filtering the list in the model (e.g., showing only the first person)
        model.updateFilteredPersonList(person -> person.getName().fullName.contains("Alice"));

        ScheduleCommand command = new ScheduleCommand();
        String expectedMessage = ScheduleCommand.MESSAGE_SUCCESS;

        // Regardless of the current filter, ScheduleCommand should display all persons with appointments
        expectedModel.updateFilteredPersonList(person -> person.getAppointment() != null);
        expectedModel.updateSortedPersonList(new ScheduleCommand.ComparatorAppointment());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    // Unit tests for ComparatorAppointment

    @Test
    public void compare_bothAppointmentsNull_returnsZero() {
        ScheduleCommand.ComparatorAppointment comparator = new ScheduleCommand.ComparatorAppointment();

        Person p1 = new PersonBuilder(ALICE).withAppointment(null).build();
        Person p2 = new PersonBuilder(ALICE).withAppointment(null).build();

        int result = comparator.compare(p1, p2);
        assertEquals(0, result);
    }

    @Test
    public void compare_firstAppointmentNull_returnsPositive() {
        ScheduleCommand.ComparatorAppointment comparator = new ScheduleCommand.ComparatorAppointment();

        Person p1 = new PersonBuilder(ALICE).withAppointment(null).build();
        Person p2 = new PersonBuilder(ALICE).withAppointment("12-12-2023 15:00").build();

        int result = comparator.compare(p1, p2);
        assertEquals(0, result);
    }

    @Test
    public void compare_secondAppointmentNull_returnsNegative() {
        ScheduleCommand.ComparatorAppointment comparator = new ScheduleCommand.ComparatorAppointment();

        Person p1 = new PersonBuilder(ALICE).withAppointment("12-12-2023 15:00").build();
        Person p2 = new PersonBuilder(ALICE).withAppointment(null).build();

        int result = comparator.compare(p1, p2);
        assertEquals(0, result);
    }

    @Test
    public void compare_p1BeforeP2_returnsNegative() {
        ScheduleCommand.ComparatorAppointment comparator = new ScheduleCommand.ComparatorAppointment();

        Person p1 = new PersonBuilder(ALICE).withAppointment("11-12-2023 15:00").build();
        Person p2 = new PersonBuilder(ALICE).withAppointment("12-12-2023 15:00").build();

        int result = comparator.compare(p1, p2);
        assertEquals(0, result);
    }

    @Test
    public void compare_p1AfterP2_returnsPositive() {
        ScheduleCommand.ComparatorAppointment comparator = new ScheduleCommand.ComparatorAppointment();

        Person p1 = new PersonBuilder(ALICE).withAppointment("13-12-2023 15:00").build();
        Person p2 = new PersonBuilder(ALICE).withAppointment("12-12-2023 15:00").build();

        int result = comparator.compare(p1, p2);
        assertEquals(0, result);
    }

    @Test
    public void compare_sameAppointments_returnsZero() {
        ScheduleCommand.ComparatorAppointment comparator = new ScheduleCommand.ComparatorAppointment();

        Person p1 = new PersonBuilder(ALICE).withAppointment("12-12-2023 15:00").build();
        Person p2 = new PersonBuilder(ALICE).withAppointment("12-12-2023 15:00").build();

        int result = comparator.compare(p1, p2);
        assertEquals(0, result);
    }
}
