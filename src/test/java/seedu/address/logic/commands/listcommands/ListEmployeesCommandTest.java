package seedu.address.logic.commands.listcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEmployeeAtIndex;
import static seedu.address.testutil.TypicalEmployees.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.DisplayType;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListEmployeesCommand.
 */
public class ListEmployeesCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook());
        expectedModel = new ModelManager(model.getAddressBook(), model.getCommandTextHistory(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        int numShown = expectedModel.getFilteredEmployeeList().size();
        CommandResult expectedCommandResult = new CommandResult(String.format(ListEmployeesCommand.MESSAGE_SUCCESS,
                numShown), DisplayType.EMPLOYEE_LIST, false, false);
        assertCommandSuccess(new ListEmployeesCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);
        int numShown = expectedModel.getFilteredEmployeeList().size();
        CommandResult expectedCommandResult = new CommandResult(String.format(ListEmployeesCommand.MESSAGE_SUCCESS,
                numShown), DisplayType.EMPLOYEE_LIST, false, false);
        assertCommandSuccess(new ListEmployeesCommand(), model, expectedCommandResult, expectedModel);
    }
}
