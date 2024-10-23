package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
public class SortOwnerCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPawPatrol.getTypicalPawPatrol(), new UserPrefs());
        expectedModel = new ModelManager(model.getPawPatrol(), new UserPrefs());
        expectedModel.sortOwners();
    }

    @Test
    public void create_sortUnfilteredOwnerList_success() {
        assertCommandSuccess(new SortOwnerCommand(), model, SortOwnerCommand.MESSAGE_SORT_OWNER_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortUnfilteredOwnerList_success() {
        SortOwnerCommand sortOwnerCommand = new SortOwnerCommand();

        String expectedMessage = String.format(SortOwnerCommand.MESSAGE_SORT_OWNER_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getPawPatrol(), new UserPrefs());
        expectedModel.sortOwners();

        assertCommandSuccess(sortOwnerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        //showOwnerAtIndex(model, INDEX_FIRST_OWNER); fix later
        assertCommandSuccess(new ListBothCommand(), model, ListBothCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        SortOwnerCommand sortOwnerCommand = new SortOwnerCommand();

        assertEquals(sortOwnerCommand, new SortOwnerCommand());

        assertNotEquals(sortOwnerCommand, new SortPetCommand());

        assertEquals(sortOwnerCommand, sortOwnerCommand);
    }
}
