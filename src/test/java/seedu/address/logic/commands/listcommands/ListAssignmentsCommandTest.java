package seedu.address.logic.commands.listcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAssignmentAtIndex;
import static seedu.address.testutil.TypicalAssignments.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.DisplayType;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAssignmentsCommand.
 */
public class ListAssignmentsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(ListAssignmentsCommand.MESSAGE_SUCCESS,
                DisplayType.ASSIGNMENT_LIST, false, false);
        assertCommandSuccess(new ListAssignmentsCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showAssignmentAtIndex(model, INDEX_FIRST_PERSON);
        CommandResult expectedCommandResult = new CommandResult(ListAssignmentsCommand.MESSAGE_SUCCESS,
                DisplayType.ASSIGNMENT_LIST, false, false);
        assertCommandSuccess(new ListAssignmentsCommand(), model, expectedCommandResult, expectedModel);
    }
}
