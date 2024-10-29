package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
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
    public void execute_invalidOrderName_throwsCommandException() {
        assertThrows(CommandException.class, () -> new DeleteOrderCommand(" cake").execute(model));
        assertThrows(CommandException.class, () -> new DeleteOrderCommand("c.ke").execute(model));
        assertThrows(CommandException.class, () -> new DeleteOrderCommand("c/ke").execute(model));
    }

    @Test
    public void execute_validOrder_success() {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeOrder(new Order(name));
        assertCommandSuccess(new DeleteOrderCommand(name), model,
                String.format(DeleteOrderCommand.MESSAGE_SUCCESS, name), expectedModel);
    }

    @Test
    void equalsMethodTest() {
        String firstOrderName = "cake";
        String secondOrderName = "pizza";
        DeleteOrderCommand first = new DeleteOrderCommand(firstOrderName);
        DeleteOrderCommand second = new DeleteOrderCommand(secondOrderName);

        // same object -> returns true
        assertTrue(first.equals(first));

        // same values -> returns true
        DeleteOrderCommand firstCopy = new DeleteOrderCommand(firstOrderName);
        assertTrue(first.equals(firstCopy));

        // different types -> returns false
        assertFalse(first.equals(1));

        // null -> returns false
        assertFalse(first.equals(null));

        // different person -> returns false
        assertFalse(first.equals(second));
    }
}
