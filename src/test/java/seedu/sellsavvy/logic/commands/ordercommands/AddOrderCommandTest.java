package seedu.sellsavvy.logic.commands.ordercommands;

import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.sellsavvy.testutil.TypicalOrders.ABACUS;
import static seedu.sellsavvy.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.person.Person;

public class AddOrderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_newOrder_success() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, ABACUS);

        Model expectedModel = new ModelManager(getTypicalAddressBook().createCopy(), new UserPrefs());
        Person personToAddUnder = expectedModel.getFilteredPersonList().get(0);
        String expectedMessage = String.format(AddOrderCommand.MESSAGE_SUCCESS,
                personToAddUnder.getName(), Messages.format(ABACUS));
        personToAddUnder.getOrderList().add(ABACUS);

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }
}
