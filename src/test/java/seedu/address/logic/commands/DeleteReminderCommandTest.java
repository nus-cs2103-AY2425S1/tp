package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Reminder;
import seedu.address.testutil.PersonBuilder;

public class DeleteReminderCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        Person personWithReminder = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(
                personWithReminder.getName());

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                Messages.formatReminder(personWithReminder,
                        personWithReminder.getReminder().getReminderTime()));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deleteReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        Name name = new Name("John");
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(name);

        assertCommandFailure(deleteReminderCommand, model, Messages.MESSAGE_INVALID_NAME_DISPLAYED);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Reminder validReminder = new Reminder("1 day");
        Person personWithReminderToDelete = new PersonBuilder(person)
                .withReminder(validReminder.getReminderTime()).build();
        model.setPerson(person, personWithReminderToDelete);

        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(
                personWithReminderToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(person, personWithReminderToDelete);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                Messages.formatReminder(personWithReminderToDelete,
                        personWithReminderToDelete.getReminder().toString()));

        assertCommandSuccess(deleteReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        Name name = new Name("John");
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(name);

        assertCommandFailure(deleteReminderCommand, model, Messages.MESSAGE_INVALID_NAME_DISPLAYED);
    }

    @Test
    public void execute_noReminder_throwsCommandException() {
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(GEORGE.getName());
        assertCommandFailure(deleteReminderCommand, model, Messages.MESSAGE_NO_REMINDER);
    }

    @Test
    public void toStringMethod() {
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(
                new Name("John"));
        String expected = DeleteReminderCommand.class.getCanonicalName() + "{toDeleteReminderFor=John}";
        assertEquals(deleteReminderCommand.toString(), expected);
    }
}
