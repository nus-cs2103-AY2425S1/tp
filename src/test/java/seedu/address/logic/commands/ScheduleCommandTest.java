package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

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
        expectedModel.updateSortedPersonList(new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                // Handle null appointments and null values
                if (p1.getAppointment() == null && p2.getAppointment() == null) {
                    return 0;
                } else if (p1.getAppointment() == null) {
                    return 1;
                } else if (p2.getAppointment() == null) {
                    return -1;
                } else if (p1.getAppointment().value == null && p2.getAppointment().value == null) {
                    return 0;
                } else if (p1.getAppointment().value == null) {
                    return 1;
                } else if (p2.getAppointment().value == null) {
                    return -1;
                } else {
                    // Compare appointment times
                    return p1.getAppointment().value.compareTo(p2.getAppointment().value);
                }
            }
        });

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
        expectedModel.updateSortedPersonList(new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                // Handle null appointments and null values
                if (p1.getAppointment() == null && p2.getAppointment() == null) {
                    return 0;
                } else if (p1.getAppointment() == null) {
                    return 1;
                } else if (p2.getAppointment() == null) {
                    return -1;
                } else if (p1.getAppointment().value == null && p2.getAppointment().value == null) {
                    return 0;
                } else if (p1.getAppointment().value == null) {
                    return 1;
                } else if (p2.getAppointment().value == null) {
                    return -1;
                } else {
                    // Compare appointment times
                    return p1.getAppointment().value.compareTo(p2.getAppointment().value);
                }
            }
        });

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
