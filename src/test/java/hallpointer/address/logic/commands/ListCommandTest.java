package hallpointer.address.logic.commands;

import static hallpointer.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hallpointer.address.logic.commands.CommandTestUtil.showMemberAtIndex;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalMembers.getTypicalHallPointer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hallpointer.address.model.Model;
import hallpointer.address.model.ModelManager;
import hallpointer.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalHallPointer(), new UserPrefs());
        expectedModel = new ModelManager(model.getHallPointer(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
