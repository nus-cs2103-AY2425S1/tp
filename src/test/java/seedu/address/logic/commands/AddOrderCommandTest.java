package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

class AddOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidOrderName_throwsCommandException() {
        assertThrows(CommandException.class, () -> new AddOrderCommand(" cake").execute(model));
        assertThrows(CommandException.class, () -> new AddOrderCommand("c.ke").execute(model));
        assertThrows(CommandException.class, () -> new AddOrderCommand("c/ke").execute(model));
    }

    @Test
    public void execute_newOrder_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String name = "cake";
        expectedModel.addOrder(new Order(name));
        assertCommandSuccess(new AddOrderCommand(name), model,
                String.format(AddOrderCommand.MESSAGE_SUCCESS, name), expectedModel);
    }

    @Test
    public void execute_duplicateOrder_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String name = "cake";
        Order order = new Order(name);
        model.addOrder(order);
        assertThrows(CommandException.class, () -> new AddOrderCommand(name).execute(model));
    }

    @Test
    public void equalsMethod() {
        AddOrderCommand o1 = new AddOrderCommand("cake");
        AddOrderCommand o2 = new AddOrderCommand("cake");
        AddOrderCommand o3 = new AddOrderCommand("test");
        assertEquals(o1, o1);
        assertNotEquals(o1, null);
        assertEquals(o1, o2);
        assertNotEquals(o2, o3);
    }
}
