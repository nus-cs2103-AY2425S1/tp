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
import seedu.address.model.person.Name;
import seedu.address.testutil.TypicalPersons;

class ShowOrderHistoryCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_emptyHistory_success() {

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(new ShowOrderHistoryCommand(TypicalPersons.ALICE.getName()), model,
                ShowOrderHistoryCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_showHistory_success() {

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String name = "cake";
        model.addOrder(new Order(name));
        PutOrderCommand command = new PutOrderCommand(new Order(name), TypicalPersons.ALICE.getName());
        try {
            command.execute(model);
            assertCommandSuccess(new ShowOrderHistoryCommand(TypicalPersons.ALICE.getName()), model,
                    ShowOrderHistoryCommand.MESSAGE_SUCCESS
                            + TypicalPersons.ALICE.getOrderTracker() , model);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            TypicalPersons.ALICE.clearOrder();
        }

    }

    @Test
    void execute_personDoesNotExist_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        String name = "Invalid Name";
        assertThrows(CommandException.class, () -> new ShowOrderHistoryCommand(new Name(name)).execute(model));
    }

    @Test
    void equalsMethod() {
        ShowOrderHistoryCommand o1 = new ShowOrderHistoryCommand(TypicalPersons.ALICE.getName());
        ShowOrderHistoryCommand o2 = new ShowOrderHistoryCommand(TypicalPersons.BENSON.getName());

        assertEquals(o1, o1);
        assertNotEquals(o1, o2);
        assertNotEquals(o1, null);
    }
}
