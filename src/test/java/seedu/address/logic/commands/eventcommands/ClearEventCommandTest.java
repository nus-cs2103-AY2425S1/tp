package seedu.address.logic.commands.eventcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearEventCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearEventCommand clearEventCommand = new ClearEventCommand();

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearEventCommand.setConfirmed(true);
        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_abort() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearEventCommand clearEventCommand = new ClearEventCommand();

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearEventCommand.setConfirmed(false);
        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_ABORTED, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearEventCommand clearEventCommand = new ClearEventCommand();

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearEventCommand.setConfirmed(true);
        expectedModel.setEventList(new AddressBook());

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_abort() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearEventCommand clearEventCommand = new ClearEventCommand();

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearEventCommand.setConfirmed(false);
        expectedModel.setEventList(getTypicalAddressBook());

        assertCommandSuccess(clearEventCommand, model, ClearEventCommand.MESSAGE_ABORTED, expectedModel);
    }

    @Test
    public void equals() {
        ClearEventCommand clearFirstCommand = new ClearEventCommand();
        ClearEventCommand clearSecondCommand = new ClearEventCommand();

        // same object -> returns true
        assertTrue(clearFirstCommand.equals(clearFirstCommand));

        // same command -> returns true
        assertTrue(clearFirstCommand.equals(clearSecondCommand));

        // different types -> returns false
        assertFalse(clearFirstCommand.equals(1));

        // null -> returns false
        assertFalse(clearFirstCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        ClearEventCommand clearCommand = new ClearEventCommand();
        String expected = ClearEventCommand.class.getCanonicalName() + "{isPrompted=false, isConfirmed=false}";
        assertEquals(expected, clearCommand.toString());

        ClearEventCommand.setPrompted(true);
        String secondExpected = ClearEventCommand.class.getCanonicalName() + "{isPrompted=true, isConfirmed=false}";
        assertEquals(secondExpected, clearCommand.toString());

        ClearEventCommand.setConfirmed(true);
        String thirdExpected = ClearEventCommand.class.getCanonicalName() + "{isPrompted=true, isConfirmed=true}";
        assertEquals(thirdExpected, clearCommand.toString());

        ClearEventCommand.setConfirmed(false);
        String fourthExpected = ClearEventCommand.class.getCanonicalName() + "{isPrompted=true, isConfirmed=false}";
        assertEquals(fourthExpected, clearCommand.toString());

        ClearEventCommand.setPrompted(false);
        String fifthExpected = ClearEventCommand.class.getCanonicalName() + "{isPrompted=false, isConfirmed=false}";
        assertEquals(fifthExpected, clearCommand.toString());
    }
}
