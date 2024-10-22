package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OWNER;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.owner.Owner;
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
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void create_sortUnfilteredOwnerList_success() {
        assertCommandSuccess(new SortOwnerCommand(), model, SortOwnerCommand.MESSAGE_SORT_OWNER_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortUnfilteredOwnerList_success() {
        SortOwnerCommand sortOwnerCommand = new SortOwnerCommand();

        String expectedMessage = String.format(SortOwnerCommand.MESSAGE_SORT_OWNER_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
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
        ListBothCommand listBothCommand = new ListBothCommand();

        assertEquals(listBothCommand, new ListBothCommand());

        assertEquals(listBothCommand, listBothCommand);
    }
}
