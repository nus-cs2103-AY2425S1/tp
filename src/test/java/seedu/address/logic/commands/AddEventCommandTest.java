package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Venue;
import seedu.address.model.person.Person;
import seedu.address.model.person.role.athlete.SportString;
import seedu.address.testutil.TypicalAddressBook;


public class AddEventCommandTest {

    @Test
    void execute_eventAcceptedByModel_addSuccessful() throws Exception {
        Model modelStub = new ModelManager();
        Set<Person> participants = new HashSet<>();
        Event validEvent = new Event(new EventName("IFG"), new SportString("Soccer"), new Venue("Stadium"),
                participants);

        CommandResult commandResult = new AddEventCommand(validEvent).execute(modelStub);

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.getFeedbackToUser());
        assertTrue(modelStub.hasEvent(validEvent));
    }

    @Test
    void execute_duplicateEvent_throwsCommandException() {
        Set<Person> participants = new HashSet<>();
        Event validEvent = new Event(new EventName("IFG"), new SportString("Chess"), new Venue("Stadium"),
                participants);
        AddEventCommand addEventCommand = new AddEventCommand(validEvent);
        Model modelStub = new ModelManager(TypicalAddressBook.getTypicalAddressBook(), new UserPrefs());

        assertThrows(CommandException.class, () -> addEventCommand.execute(modelStub),
                AddEventCommand.MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        Set<Person> participants = new HashSet<>();
        Event event = new Event(new EventName("IFG"), new SportString("Chess"), new Venue("Stadium"), participants);
        AddEventCommand addEventCommand = new AddEventCommand(event);
        assertEquals(addEventCommand, addEventCommand);
    }

    @Test
    void equals_differentTypes_returnsFalse() {
        Set<Person> participants = new HashSet<>();
        Event event = new Event(new EventName("IFG"), new SportString("Chess"), new Venue("Stadium"), participants);
        AddEventCommand addEventCommand = new AddEventCommand(event);
        assertNotEquals(1, addEventCommand);
    }

    @Test
    void equals_null_returnsFalse() {
        Set<Person> participants = new HashSet<>();
        Event event = new Event(new EventName("IFG"), new SportString("Chess"), new Venue("Stadium"), participants);
        AddEventCommand addEventCommand = new AddEventCommand(event);
        assertNotEquals(null, addEventCommand);
    }

    @Test
    void equals_differentEvent_returnsFalse() {
        Set<Person> participants1 = new HashSet<>();
        Set<Person> participants2 = new HashSet<>();
        AddEventCommand addEventCommand1 = new AddEventCommand(new Event(new EventName("IFG"),
                new SportString("Chess"), new Venue("Stadium"), participants1));
        AddEventCommand addEventCommand2 = new AddEventCommand(new Event(new EventName("SUNIG"),
                new SportString("Chess"), new Venue("Arena"), participants2));
        assertNotEquals(addEventCommand1, addEventCommand2);
    }
}
