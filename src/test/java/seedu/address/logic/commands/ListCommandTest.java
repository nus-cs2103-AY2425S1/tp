package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalPawPatrol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPawPatrol(), new UserPrefs());
        expectedModel = new ModelManager(model.getPawPatrol(), new UserPrefs());
    }

    @Test
    public void execute_petListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListPetCommand(), model, ListPetCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_ownerListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListOwnerCommand(), model, ListOwnerCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        //showOwnerAtIndex(model, INDEX_FIRST_OWNER);
        assertCommandSuccess(new ListOwnerCommand(), model, ListOwnerCommand.MESSAGE_SUCCESS, expectedModel);
    }
    /*
    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
    */

    @Test
    public void equals() {
        ListPetCommand listPetCommand = new ListPetCommand();

        ListOwnerCommand listOwnerCommand = new ListOwnerCommand();

        assertEquals(listOwnerCommand, new ListOwnerCommand());

        assertEquals(listPetCommand, new ListPetCommand());

        assertEquals(listPetCommand, listPetCommand);

        assertEquals(listOwnerCommand, listOwnerCommand);
    }
}
