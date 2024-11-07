package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;


public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(true), model, ClearCommand.MESSAGE_PERSONS_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
        // Clear address book
        expectedModel.setAddressBook(new AddressBook());
        // Clear attendees from events in expected model
        ObservableList<Event> allEvents = expectedModel.getEventList();
        for (int i = 0; i < allEvents.size(); i++) {
            Event currEvent = allEvents.get(i);
            Event updatedEvent = new Event(
                    currEvent.getEventName(),
                    currEvent.getStartDate(),
                    currEvent.getEndDate(),
                    currEvent.getLocation(),
                    new HashSet<>()
            );
            expectedModel.updateEvent(updatedEvent, i);
        }

        assertCommandSuccess(new ClearCommand(true), model, ClearCommand.MESSAGE_PERSONS_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyEventBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        assertCommandSuccess(new ClearCommand(false), model, ClearCommand.MESSAGE_EVENTS_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEventBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
        expectedModel.setEventBook(new EventBook());
        assertCommandSuccess(new ClearCommand(false), model, ClearCommand.MESSAGE_EVENTS_SUCCESS, expectedModel);
    }

}
