package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

class ListOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    public ListOrderCommandTest() {
        model.addOrder(new Order("cake"));
    }

    @Test
    void executeTest() {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(new ListOrderCommand(), model,
                ListOrderCommand.MESSAGE_SUCCESS + expectedModel.getOrderList().toString(), expectedModel);
    }

}
