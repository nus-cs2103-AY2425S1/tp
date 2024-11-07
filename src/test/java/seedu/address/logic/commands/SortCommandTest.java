package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
    public void execute_listIsAlreadySorted_showsSameList() {
        //Note: list is sorted by latest by default
        assertCommandSuccess(new SortCommand("latest"), model, SortCommand.MESSAGE_SUCCESS_LATEST, expectedModel);
        assertEquals(model, expectedModel);
    }

    @Test
    public void execute_listIsSortedMultipleTimes_showsSameList() {
        //Note: list is sorted by latest by default
        //If we sort by another comparator, then sort by the initial comparator, the list should still be the same
        assertCommandSuccess(new SortCommand("name"), model, SortCommand.MESSAGE_SUCCESS_NAME, expectedModel);
        assertCommandSuccess(new SortCommand("latest"), model, SortCommand.MESSAGE_SUCCESS_LATEST, expectedModel);
        assertEquals(model, expectedModel);
    }
}
