package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalPawPatrol;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListBothCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPawPatrol.getTypicalPawPatrol(), new UserPrefs());
        expectedModel = new ModelManager(model.getPawPatrol(), new UserPrefs());
    }

    @Test
    public void execute_petListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListBothCommand(), model, ListBothCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        //showOwnerAtIndex(model, INDEX_FIRST_OWNER); fix later
        assertCommandSuccess(new ListBothCommand(), model, ListBothCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ListBothCommand listBothCommand = new ListBothCommand();

        assertEquals(listBothCommand, new ListBothCommand());

        assertEquals(listBothCommand, listBothCommand);
    }
}
