package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SUPPLIER;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListSupplierCommand.
 */
public class ListSupplierCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListSupplierCommand(), model, ListSupplierCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_supplierlistIsFiltered_showsEverything() {
        showSupplierAtIndex(model, INDEX_FIRST_SUPPLIER);
        assertCommandSuccess(new ListSupplierCommand(), model, ListSupplierCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
