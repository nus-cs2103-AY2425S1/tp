package seedu.address.logic.commands.event.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.event.commands.AddEventCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;

public class AddEventCommandTest {

    private EventManager eventManager;
    private Event validEvent;
    private AddEventCommand addEventCommand;

    @BeforeEach
    public void setUp() {
        validEvent = new Event("Festival");
        eventManager = new EventManager();
        eventManager.addEvent(validEvent);
        addEventCommand = new AddEventCommand(validEvent);
    }
    @Test
    public void execute_nullInput_throwsException() {
        assertThrows(NullPointerException.class, () -> addEventCommand.execute(new ModelManager(), null));
    }

    @Test
    public void execute_duplicateEvent_throwsCommandException() {
        assertThrows(CommandException.class, () -> addEventCommand.execute(new ModelManager(),
                new EventManagerStubWithEvent()));
    }

    @Test
    public void execute_newEvent_success() throws CommandException {
        EventManagerStubWithoutEvent eventManagerStubWithoutEvent = new EventManagerStubWithoutEvent();
        Assertions.assertEquals(new CommandResult(String.format("New event added: %1$s", "Festival")),
                addEventCommand.execute(new ModelManager(), eventManagerStubWithoutEvent));
        assertTrue(eventManagerStubWithoutEvent.getIsAddEventCalled());
    }

    private class EventManagerStubWithEvent extends EventManager {
        @Override
        public boolean hasEvent(Event event) {
            return true;
        }
    }

    private class EventManagerStubWithoutEvent extends EventManager {
        private boolean isAddEventCalled = false;
        @Override
        public boolean hasEvent(Event event) {
            return false;
        }
        @Override
        public void addEvent(Event event) {
            this.isAddEventCalled = true;
        }

        public boolean getIsAddEventCalled() {
            return isAddEventCalled;
        }
    }

    @Test
    public void equals() {
        Event eventA = new Event("Event A");
        Event eventB = new Event("Event B");
        AddEventCommand addEventACommand = new AddEventCommand(eventA);
        AddEventCommand addEventBCommand = new AddEventCommand(eventB);


        // same values -> returns true
        AddEventCommand addEventACommandCopy = new AddEventCommand(eventA);
        assertEquals(addEventACommand, addEventACommandCopy);

        // different types -> returns false
        assertNotEquals(1, addEventACommand);

        // null -> returns false
        assertNotEquals(null, addEventACommand);

        // different event -> returns false
        assertNotEquals(addEventACommand, addEventBCommand);
    }

    @Test
    public void toStringMethod() {
        AddEventCommand addEventCommand = new AddEventCommand(validEvent);
        String expected = AddEventCommand.class.getCanonicalName() + "{eventToBeAdded=" + validEvent + "}";
        assertEquals(expected, addEventCommand.toString());
    }
}
