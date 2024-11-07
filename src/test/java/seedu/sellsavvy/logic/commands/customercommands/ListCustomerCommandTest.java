package seedu.sellsavvy.logic.commands.customercommands;

import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.showCustomerAtIndex;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCustomerCommand.
 */
public class ListCustomerCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCustomerCommand(), model, ListCustomerCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showCustomerAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCustomerCommand(), model, ListCustomerCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
