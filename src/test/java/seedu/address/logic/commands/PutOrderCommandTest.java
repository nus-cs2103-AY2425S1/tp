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
import seedu.address.testutil.TypicalPersons;

class PutOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_putOrder_success() {

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String name = "cake";
        model.addOrder(new Order(name));
        expectedModel.addOrder(new Order(name));
        assertCommandSuccess(new PutOrderCommand(new Order(name), TypicalPersons.ALICE.getName()), model,
                PutOrderCommand.MESSAGE_SUCCESS, expectedModel);
        // temp solution
        TypicalPersons.ALICE.clearOrder();
    }

    @Test
    void execute_orderDoesNotExist_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String name = "cake";
        assertThrows(CommandException.class, () -> new PutOrderCommand(new Order(name),
                TypicalPersons.ALICE.getName()).execute(model));
    }

    @Test
    void equalsMethod() {
        Order order1 = new Order("cake");
        Order order2 = new Order("pizza");
        PutOrderCommand o1 = new PutOrderCommand(order1, TypicalPersons.ALICE.getName());
        PutOrderCommand o2 = new PutOrderCommand(order1, TypicalPersons.ALICE.getName());
        PutOrderCommand o3 = new PutOrderCommand(order2, TypicalPersons.ALICE.getName());
        PutOrderCommand o4 = new PutOrderCommand(order2, TypicalPersons.BENSON.getName());

        assertEquals(o1, o1);
        assertEquals(o1, o2);
        assertNotEquals(o1, null);
        assertNotEquals(o2, o3);
        assertNotEquals(o3, o4);
    }
}
