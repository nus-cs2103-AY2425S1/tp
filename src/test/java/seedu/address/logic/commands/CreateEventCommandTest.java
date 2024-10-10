package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVendors.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;

public class CreateEventCommandTest {
    private Event excursionEvent = new Event(new Name("Zoo Excursion"), new Date("2024-10-10"));
    private Event partyEvent = new Event(new Name("Birthday Party"), new Date("2023-12-30"));
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateEventCommand(null));
    }

    @Test
    public void execute_commandUnimplemented_throwsCommandException() {
        assertThrows(CommandException.class, () -> new CreateEventCommand(excursionEvent).execute(model));
    }

    @Test
    public void equals() {
        CreateEventCommand addExcursionCommand = new CreateEventCommand(excursionEvent);
        CreateEventCommand addPartyCommand = new CreateEventCommand(partyEvent);

        // same object -> returns true
        assertTrue(addExcursionCommand.equals(addExcursionCommand));

        // same values -> returns true
        CreateEventCommand addPartyCommandCopy = new CreateEventCommand(partyEvent);
        assertTrue(addPartyCommand.equals(addPartyCommandCopy));

        // different types -> returns false
        assertFalse(addExcursionCommand.equals(1));

        // null -> returns false
        assertFalse(addExcursionCommand.equals(null));

        // different vendor -> returns false
        assertFalse(addExcursionCommand.equals(addPartyCommand));
    }

    @Test
    public void toStringMethod() {
        CreateEventCommand createEventCommand = new CreateEventCommand(excursionEvent);
        String expected = CreateEventCommand.class.getCanonicalName() + "{toAdd=" + excursionEvent + "}";
        assertEquals(expected, createEventCommand.toString());
    }
}
