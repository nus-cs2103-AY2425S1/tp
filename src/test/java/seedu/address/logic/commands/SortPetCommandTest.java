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
public class SortPetCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalPawPatrol.getTypicalPawPatrol(), new UserPrefs());
        expectedModel = new ModelManager(model.getPawPatrol(), new UserPrefs());
        expectedModel.sortPets();
    }

    @Test
    public void create_sortUnfilteredPetList_success() {
        assertCommandSuccess(new SortPetCommand(), model, SortPetCommand.MESSAGE_SORT_PET_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortUnfilteredPetList_success() {
        SortPetCommand sortPetCommand = new SortPetCommand();

        String expectedMessage = String.format(SortPetCommand.MESSAGE_SORT_PET_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getPawPatrol(), new UserPrefs());
        expectedModel.sortPets();

        assertCommandSuccess(sortPetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        //showPetAtIndex(model, INDEX_FIRST_Pet); fix later
        assertCommandSuccess(new SortPetCommand(), model, SortPetCommand.MESSAGE_SORT_PET_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        SortPetCommand sortPetCommand = new SortPetCommand();

        assertEquals(sortPetCommand, new SortPetCommand());

        assertNotEquals(sortPetCommand, new SortOwnerCommand());

        assertEquals(sortPetCommand, sortPetCommand);
    }
}
