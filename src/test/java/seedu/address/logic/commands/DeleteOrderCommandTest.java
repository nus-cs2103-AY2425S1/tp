package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

class DeleteOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private String name = "cake";

    public DeleteOrderCommandTest() {
        model.addOrder(new Order(name));
    }
    @Test
    void execute() {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeOrder(new Order(name));
        assertCommandSuccess(new DeleteOrderCommand(name), model,
                String.format(DeleteOrderCommand.MESSAGE_SUCCESS, name), expectedModel);
    }
}
