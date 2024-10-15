package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

class AddOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void executeTest() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String name = "cake";
        expectedModel.addOrder(new Order(name));
        assertCommandSuccess(new AddOrderCommand(name), model,
                String.format(AddOrderCommand.MESSAGE_SUCCESS, name), expectedModel);
    }

    @Test
    void testEquals() {
        AddOrderCommand o1 = new AddOrderCommand("cake");
        AddOrderCommand o2 = new AddOrderCommand("cAke");
        AddOrderCommand o3 = new AddOrderCommand("test");
        assertEquals(o1, o2);
        assertNotEquals(o2, o3);
    }
}
