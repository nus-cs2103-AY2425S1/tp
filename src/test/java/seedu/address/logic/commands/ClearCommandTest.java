package seedu.address.logic.commands;

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


public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearCommand clearCommand = new ClearCommand();

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearCommand.setConfirmed(true);
        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_abort() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearCommand clearCommand = new ClearCommand();

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearCommand.setConfirmed(false);
        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_ABORTED, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearCommand clearCommand = new ClearCommand();

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearCommand.setConfirmed(true);
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_abort() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearCommand clearCommand = new ClearCommand();

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearCommand.setConfirmed(false);
        expectedModel.setAddressBook(getTypicalAddressBook());

        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_ABORTED, expectedModel);
    }

    @Test
    public void equals() {
        ClearCommand clearFirstCommand = new ClearCommand();
        ClearCommand clearSecondCommand = new ClearCommand();

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
        ClearCommand clearCommand = new ClearCommand();
        String expected = ClearCommand.class.getCanonicalName() + "{isPrompted=false, isConfirmed=false}";
        assertEquals(expected, clearCommand.toString());

        ClearCommand.setPrompted(true);
        String secondExpected = ClearCommand.class.getCanonicalName() + "{isPrompted=true, isConfirmed=false}";
        assertEquals(secondExpected, clearCommand.toString());

        ClearCommand.setConfirmed(true);
        String thirdExpected = ClearCommand.class.getCanonicalName() + "{isPrompted=true, isConfirmed=true}";
        assertEquals(thirdExpected, clearCommand.toString());

        ClearCommand.setConfirmed(false);
        String fourthExpected = ClearCommand.class.getCanonicalName() + "{isPrompted=true, isConfirmed=false}";
        assertEquals(fourthExpected, clearCommand.toString());

        ClearCommand.setPrompted(false);
        String fifthExpected = ClearCommand.class.getCanonicalName() + "{isPrompted=false, isConfirmed=false}";
        assertEquals(fifthExpected, clearCommand.toString());
    }
}
