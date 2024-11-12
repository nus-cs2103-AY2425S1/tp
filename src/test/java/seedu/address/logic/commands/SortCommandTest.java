package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.ASC;
import static seedu.address.logic.commands.SortCommand.DESC;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;



/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }
    @Test
    public void constructor_invalidOrder_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new SortCommand("Invalid", true));
    }

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(ASC, null));
    }
    @Test
    public void execute_sortAscendingNameSuccess() {
        SortCommand sortCommand = new SortCommand(ASC, false);
        expectedModel.sortFilteredPersonList(ASC, false);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void execute_sortAscendingScheduleSuccess() {
        SortCommand sortCommand = new SortCommand(ASC, true);
        expectedModel.sortFilteredPersonList(ASC, true);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortDescendingNameSuccess() {
        SortCommand sortCommand = new SortCommand(DESC, false);
        expectedModel.sortFilteredPersonList(DESC, false);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void execute_sortDescendingScheduleSuccess() {
        SortCommand sortCommand = new SortCommand(DESC, true);
        expectedModel.sortFilteredPersonList(DESC, true);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }


    @Test
    public void equals() {
        SortCommand sortFirstCommand = new SortCommand(ASC, true);
        SortCommand sortSecondCommand = new SortCommand(DESC, true);
        SortCommand sortThirdCommand = new SortCommand(ASC, false);

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand(ASC, true);
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
        assertFalse(sortFirstCommand.equals(sortThirdCommand));
    }


}

