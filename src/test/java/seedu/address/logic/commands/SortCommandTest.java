package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.ASCENDING;
import static seedu.address.logic.commands.SortCommand.DESCENDING;
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
    public void execute_sortAscendingNameSuccess() {
        SortCommand sortCommand = new SortCommand(ASCENDING, false);
        expectedModel.sortFilteredPersonList(ASCENDING, false);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void execute_sortAscendingScheduleSuccess() {
        SortCommand sortCommand = new SortCommand(ASCENDING, true);
        expectedModel.sortFilteredPersonList(ASCENDING, true);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortDescendingNameSuccess() {
        SortCommand sortCommand = new SortCommand(DESCENDING, false);
        expectedModel.sortFilteredPersonList(DESCENDING, false);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
    @Test
    public void execute_sortDescendingScheduleSuccess() {
        SortCommand sortCommand = new SortCommand(DESCENDING, true);
        expectedModel.sortFilteredPersonList(DESCENDING, true);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }


    @Test
    public void equals() {
        SortCommand sortFirstCommand = new SortCommand(ASCENDING, true);
        SortCommand sortSecondCommand = new SortCommand(DESCENDING, true);
        SortCommand sortThirdCommand = new SortCommand(ASCENDING, false);

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand(ASCENDING, true);
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

