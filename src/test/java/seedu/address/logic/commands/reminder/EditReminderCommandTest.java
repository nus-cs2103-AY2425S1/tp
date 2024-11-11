package seedu.address.logic.commands.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.reminder.EditReminderCommand.MESSAGE_REMINDER_NOT_EDITED;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;
import seedu.address.model.util.SampleDataUtil;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditReminderCommand.
 */
public class EditReminderCommandTest {

    private Model model;
    private Reminder originalReminder;
    private Reminder editedReminder;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(SampleDataUtil.getSampleClientHub(), new UserPrefs());
        originalReminder = new Reminder("Jeremy Sim", LocalDateTime.of(2022, 1, 1, 0, 0),
                new ReminderDescription("Initial reminder"));
        model.addReminder(originalReminder);

        editedReminder = new Reminder("Jeremy Sim", LocalDateTime.of(2022, 1, 2, 12, 0),
                new ReminderDescription("Edited reminder"));
    }

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        EditReminderCommand.EditReminderFields editReminderFields = new EditReminderCommand.EditReminderFields();
        editReminderFields.setDateTime(editedReminder.getDateTime());
        editReminderFields.setDescription(editedReminder.getDescription());

        EditReminderCommand command = new EditReminderCommand(Index.fromZeroBased(0), editReminderFields);
        command.execute(model);
        assertEquals(editedReminder, model.getDisplayReminders().get(0));
    }


    @Test
    public void execute_noFieldsSpecified_throwsCommandException() {
        // Create EditReminderFields with no fields specified
        EditReminderCommand.EditReminderFields editReminderFields = new EditReminderCommand.EditReminderFields();

        // Create an EditReminderCommand with an index and the empty EditReminderFields
        EditReminderCommand command = new EditReminderCommand(Index.fromZeroBased(0), editReminderFields);

        // Execute command and expect CommandException due to no fields being edited
        assertThrows(CommandException.class, () -> command.execute(model),
                EditReminderCommand.MESSAGE_REMINDER_NOT_EDITED);
    }


    @Test
    public void execute_duplicateReminder_throwsCommandException() {
        model.addReminder(editedReminder);
        EditReminderCommand.EditReminderFields editReminderFields = new EditReminderCommand.EditReminderFields();
        editReminderFields.setDateTime(editedReminder.getDateTime());
        editReminderFields.setDescription(editedReminder.getDescription());

        EditReminderCommand command = new EditReminderCommand(Index.fromZeroBased(1), editReminderFields);

        assertThrows(CommandException.class, () -> command.execute(model), MESSAGE_REMINDER_NOT_EDITED);
    }

    @Test
    public void execute_invalidReminderIndex_throwsCommandException() {
        EditReminderCommand.EditReminderFields editReminderFields = new EditReminderCommand.EditReminderFields();
        editReminderFields.setDateTime(editedReminder.getDateTime());
        editReminderFields.setDescription(editedReminder.getDescription());

        EditReminderCommand command = new EditReminderCommand(Index.fromZeroBased(10), editReminderFields);

        assertThrows(CommandException.class, () -> command.execute(model), "Invalid reminder index.");
    }
}
