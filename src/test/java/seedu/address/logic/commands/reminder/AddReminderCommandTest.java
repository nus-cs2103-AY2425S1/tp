package seedu.address.logic.commands.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalReminders.getTypicalClientHub;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.ClientHub;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;
import seedu.address.testutil.PersonBuilder;


public class AddReminderCommandTest {
    private ClientHub clientHub = getTypicalClientHub();
    private Model model = new ModelManager(clientHub, new UserPrefs());

    @Test
    public void execute_validPerson_success() {
        // Setup a valid person and reminder
        Person personToAddReminder = model.getClientHub().getPersonList().get(Index.fromZeroBased(0).getZeroBased());
        ReminderDescription reminderDescription = new ReminderDescription("New Year's Eve");

        Reminder reminderToAdd = new Reminder(personToAddReminder.getName().fullName,
                LocalDateTime.of(2024, 11, 5, 14, 0), reminderDescription);

        // Create AddReminderCommand with the valid reminder
        AddReminderCommand addReminderCommand = new AddReminderCommand(reminderToAdd);

        // Expected success message after the reminder is added
        String expectedMessage = String.format(AddReminderCommand.MESSAGE_SUCCESS,
                Messages.format(reminderToAdd));

        // Create expected model and perform the add operation
        Model expectedModel = new ModelManager(model.getClientHub(), new UserPrefs());
        Person personCopy = expectedModel.getClientHub().getPersonList().get(Index.fromZeroBased(0).getZeroBased());
        personCopy.addReminder(reminderToAdd);

        // Verify the expected model and actual model match after executing the command
        assertCommandSuccess(addReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentPerson_throwsCommandException() {
        // Setup a reminder with a nonexistent person name
        Reminder nonexistentReminder = new Reminder("Nonexistent Person",
                LocalDateTime.of(2024, 11, 5, 14, 0),
                new ReminderDescription("New Year's Eve"));

        // Create AddReminderCommand with the invalid reminder
        AddReminderCommand addReminderCommand = new AddReminderCommand(nonexistentReminder);

        // Execute the command and expect failure
        assertCommandFailure(addReminderCommand, model, AddReminderCommand.MESSAGE_NONEXISTENT_PERSON);
    }

    @Test
    public void execute_multipleMatchingPersons_throwsCommandException() {
        // Initialize the model with an empty ClientHub and UserPrefs
        Model model = new ModelManager(new ClientHub(), new UserPrefs());

        // Create two persons with similar names
        Person alice = new PersonBuilder().withName("Alice").build();
        Person alice2 = new PersonBuilder().withName("Alicee").build();

        // Add both persons to the model
        model.addPerson(alice);
        model.addPerson(alice2);

        // Setup a reminder with an ambiguous name that matches multiple persons
        Reminder ambiguousReminder = new Reminder("Alice",
                LocalDateTime.of(2024, 11, 5, 14, 0),
                new ReminderDescription("New Year's Eve"));

        // Create AddReminderCommand with the ambiguous reminder
        AddReminderCommand addReminderCommand = new AddReminderCommand(ambiguousReminder);

        // Execute the command and expect failure due to multiple matches
        assertCommandFailure(addReminderCommand, model, AddReminderCommand.MESSAGE_MORE_THAN_ONE_PERSON);
    }

    @Test
    public void equals_sameObjectAndValues_returnsTrue() {
        // Arrange: Create identical reminders and commands
        Reminder firstReminder = new Reminder("John Doe",
                LocalDateTime.of(2024, 12, 31, 23, 59),
                new ReminderDescription("New Year's Eve"));
        AddReminderCommand addFirstCommand = new AddReminderCommand(firstReminder);

        // Positive test case: same object -> returns true
        assertTrue(addFirstCommand.equals(addFirstCommand));

        // Positive test case: different object, same values -> returns true
        AddReminderCommand addFirstCommandCopy = new AddReminderCommand(firstReminder);
        assertTrue(addFirstCommand.equals(addFirstCommandCopy));
    }
    @Test
    public void toString_validAddReminderCommand_correctStringRepresentation() {
        // Create a Reminder object with sample data
        Reminder reminder = new Reminder("John Doe",
                LocalDateTime.of(2021, 12, 31, 23, 59),
                new ReminderDescription("New Year's Eve"));

        // Create an AddReminderCommand with the reminder
        AddReminderCommand addReminderCommand = new AddReminderCommand(reminder);

        // Define the expected string representation
        String expectedString = "AddReminderCommand {toAdd=" + reminder + "}";

        // Verify that the toString output matches the expected string
        assertEquals(expectedString, addReminderCommand.toString());
    }
}
