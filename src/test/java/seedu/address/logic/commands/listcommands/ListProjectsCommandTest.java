package seedu.address.logic.commands.listcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalProjects.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ListProjectsCommand}.
 */
public class ListProjectsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook());
        expectedModel = new ModelManager(model.getAddressBook(), model.getCommandTextHistory(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        int numShown = expectedModel.getFilteredProjectList().size();
        assertCommandSuccess(new ListProjectsCommand(), model, String.format(ListProjectsCommand.MESSAGE_SUCCESS,
                numShown), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);
        int numShown = expectedModel.getFilteredProjectList().size();
        assertCommandSuccess(new ListProjectsCommand(), model, String.format(ListProjectsCommand.MESSAGE_SUCCESS,
                numShown), expectedModel);
    }
}
