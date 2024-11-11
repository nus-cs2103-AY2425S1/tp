package seedu.address.logic.commands.personcommands;

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

public class ClearPersonCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearPersonCommand clearPersonCommand = new ClearPersonCommand();

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearPersonCommand.setConfirmed(true);
        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_abort() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearPersonCommand clearPersonCommand = new ClearPersonCommand();

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearPersonCommand.setConfirmed(false);
        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_ABORTED, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearPersonCommand clearPersonCommand = new ClearPersonCommand();

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearPersonCommand.setConfirmed(true);
        expectedModel.setPersonList(new AddressBook());

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_abort() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearPersonCommand clearPersonCommand = new ClearPersonCommand();

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_CONFIRMATION, expectedModel);

        clearPersonCommand.setConfirmed(false);
        expectedModel.setPersonList(getTypicalAddressBook());

        assertCommandSuccess(clearPersonCommand, model, ClearPersonCommand.MESSAGE_ABORTED, expectedModel);
    }

    @Test
    public void equals() {
        ClearPersonCommand clearFirstCommand = new ClearPersonCommand();
        ClearPersonCommand clearSecondCommand = new ClearPersonCommand();

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
        ClearPersonCommand clearCommand = new ClearPersonCommand();
        String expected = ClearPersonCommand.class.getCanonicalName() + "{isPrompted=false, isConfirmed=false}";
        assertEquals(expected, clearCommand.toString());

        ClearPersonCommand.setPrompted(true);
        String secondExpected = ClearPersonCommand.class.getCanonicalName() + "{isPrompted=true, isConfirmed=false}";
        assertEquals(secondExpected, clearCommand.toString());

        ClearPersonCommand.setConfirmed(true);
        String thirdExpected = ClearPersonCommand.class.getCanonicalName() + "{isPrompted=true, isConfirmed=true}";
        assertEquals(thirdExpected, clearCommand.toString());

        ClearPersonCommand.setConfirmed(false);
        String fourthExpected = ClearPersonCommand.class.getCanonicalName() + "{isPrompted=true, isConfirmed=false}";
        assertEquals(fourthExpected, clearCommand.toString());

        ClearPersonCommand.setPrompted(false);
        String fifthExpected = ClearPersonCommand.class.getCanonicalName() + "{isPrompted=false, isConfirmed=false}";
        assertEquals(fifthExpected, clearCommand.toString());
    }
}
