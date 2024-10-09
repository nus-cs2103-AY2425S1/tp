package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Reminder;
import seedu.address.model.person.Schedule;
import seedu.address.testutil.PersonBuilder;

public class ReminderCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validReminder_success() {
        Person personToRemind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule validSchedule = new Schedule("2024-10-04 1000");
        Reminder validReminder = new Reminder("2024-10-04 1000", "1 day");

        Person scheduledPerson = new PersonBuilder(personToRemind)
                .withSchedule(validSchedule.dateTime).build();
        model.setPerson(personToRemind, scheduledPerson);

        ReminderCommand command = new ReminderCommand(personToRemind.getName().toString(),
                validSchedule.dateTime, "1 day");

        Person personWithReminder = new PersonBuilder(scheduledPerson)
                .withReminder(validSchedule.dateTime, validReminder.getReminderTime())
                .build();

        String expectedMessage = String.format(ReminderCommand.MESSAGE_SUCCESS,
                personToRemind.getName(), validSchedule.dateTime, "1 day");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToRemind, personWithReminder);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidReminderTime_throwsCommandException() {
        Person personToRemind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule validSchedule = new Schedule("2024-10-04 1000");
        Reminder invalidReminder = new Reminder("2024-10-04 1000", "1 cycle"); // Invalid time

        // Set the schedule first
        Person personWithSchedule = new PersonBuilder(personToRemind).withSchedule(validSchedule.dateTime).build();
        model.setPerson(personToRemind, personWithSchedule);

        // Create reminder command
        ReminderCommand command = new ReminderCommand(personWithSchedule.getName().toString(),
                validSchedule.dateTime, invalidReminder.getReminderTime());

        assertCommandFailure(command, model, ReminderCommand.MESSAGE_INVALID_REMINDER_TIME);
    }

    @Test
    public void execute_reminderAlreadyExists_throwsCommandException() throws CommandException {
        Person personToRemind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule validSchedule = new Schedule("2024-10-04 1000");
        Reminder existingReminder = new Reminder(validSchedule.dateTime, "1 day");

        // Set the schedule and reminder
        Person personWithReminder = new PersonBuilder(personToRemind)
                .withSchedule(validSchedule.dateTime)
                .withReminder(validSchedule.dateTime, existingReminder.getReminderTime())
                .build();
        model.setPerson(personToRemind, personWithReminder);

        // Try to set the same reminder again
        ReminderCommand command = new ReminderCommand(personWithReminder.getName().toString(),
                validSchedule.dateTime, existingReminder.getReminderTime());

        assertCommandFailure(command, model, ReminderCommand.MESSAGE_REMINDER_EXISTS);
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        ReminderCommand command = new ReminderCommand("Unknown Person", "2024-10-05 1000", "1 day");

        assertCommandFailure(command, model, ReminderCommand.MESSAGE_INVALID_NAME);
    }

    @Test
    public void execute_appointmentNotFound_throwsCommandException() {
        Person personToRemind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Reminder reminder = new Reminder("2024-10-05 1000", "1 day");

        // Person doesn't have the appointment
        ReminderCommand command = new ReminderCommand(personToRemind.getName().toString(),
                reminder.getAppointmentDateTime(), reminder.getReminderTime());

        assertCommandFailure(command, model, ReminderCommand.MESSAGE_INVALID_APPOINTMENT);
    }

    @Test
    public void equals_sameObject_returnsFalse() {
        ReminderCommand command = new ReminderCommand("John Doe", "2024-10-05 1000", "1 day");
        assertFalse(command.equals(command));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        ReminderCommand command = new ReminderCommand("John Doe", "2024-10-05 1000", "1 day");
        assertFalse(command.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        ReminderCommand command = new ReminderCommand("John Doe", "2024-10-05 1000", "1 day");
        assertFalse(command.equals("String Object"));
    }

    @Test
    public void equals_differentName_returnsFalse() {
        ReminderCommand command1 = new ReminderCommand("John Doe", "2024-10-05 1000", "1 day");
        ReminderCommand command2 = new ReminderCommand("Jane Doe", "2024-10-05 1000", "1 day");
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentAppointmentDateTime_returnsFalse() {
        ReminderCommand command1 = new ReminderCommand("John Doe", "2024-10-05 1000", "1 day");
        ReminderCommand command2 = new ReminderCommand("John Doe", "2024-11-05 1000", "1 day");
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_differentReminderTime_returnsFalse() {
        ReminderCommand command1 = new ReminderCommand("John Doe", "2024-10-05 1000", "1 day");
        ReminderCommand command2 = new ReminderCommand("John Doe", "2024-10-05 1000", "2 days");
        assertFalse(command1.equals(command2));
    }

    @Test
    public void equals_identicalValues_returnsTrue() {
        ReminderCommand command1 = new ReminderCommand("John Doe", "2024-10-05 1000", "1 day");
        ReminderCommand command2 = new ReminderCommand("John Doe", "2024-10-05 1000", "1 day");
        assertTrue(command1.equals(command2));
    }
}
