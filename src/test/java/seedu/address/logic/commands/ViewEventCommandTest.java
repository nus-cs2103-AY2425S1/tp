package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventManager;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.event.commands.ViewEventCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.PersonInEventPredicate;
import seedu.address.testutil.TypicalEvents;


public class ViewEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventManager(), new UserPrefs());

    @Test
    public void execute_eventExists_success() {
        // Inputs
        Event inputEvent = TypicalEvents.SPORTS_FESTIVAL;
        ViewEventCommand command = new ViewEventCommand(inputEvent);

        // Expected outputs
        String expectedMessage = String.format(ViewEventCommand.MESSAGE_SUCCESS, inputEvent.getName());
        PersonInEventPredicate predicate = new PersonInEventPredicate(inputEvent);
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_eventDoesNotExist_throwsCommandException() {
        // Inputs
        Event inputEvent = TypicalEvents.TEST_EVENT;
        ViewEventCommand command = new ViewEventCommand(inputEvent);

        // Expected outputs
        String expectedMessage = ViewEventCommand.EVENT_DOES_NOT_EXIST;

        assertCommandFailure(command, model, expectedMessage);
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Event inputEvent1 = TypicalEvents.TEST_EVENT;
        Event inputEvent2 = TypicalEvents.TEST_EVENT;

        ViewEventCommand command1 = new ViewEventCommand(inputEvent1);
        ViewEventCommand command2 = new ViewEventCommand(inputEvent2);

        assertEquals(command1, command2);
        assertEquals(command1, command1);
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        Event inputEvent = TypicalEvents.TEST_EVENT;
        Event notInputEvent = TypicalEvents.TEST_EVENT_2;

        ViewEventCommand commandInputEvent = new ViewEventCommand(inputEvent);
        ViewEventCommand commandNotInputEvent = new ViewEventCommand(notInputEvent);

        assertNotEquals(commandInputEvent, commandNotInputEvent);
    }

    @Test
    public void toString_returnsCorrectString() {
        Event testEvent = TypicalEvents.SPORTS_FESTIVAL;
        ViewEventCommand viewCommand = new ViewEventCommand(testEvent);
        String expected = ViewEventCommand.class.getCanonicalName() + "{eventToView=" + testEvent + "}";
        assertEquals(expected, viewCommand.toString());
        System.out.println(testEvent.toString());
    }
}
