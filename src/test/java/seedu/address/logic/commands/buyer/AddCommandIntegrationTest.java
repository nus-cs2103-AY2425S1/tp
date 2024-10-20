package seedu.address.logic.commands.buyer;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.TypicalBuyers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.buyer.Buyer;
import seedu.address.testutil.BuyerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalMeetUpList());
    }

    @Test
    public void execute_newPerson_success() {
        Buyer validBuyer = new BuyerBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), getTypicalMeetUpList());
        expectedModel.addBuyer(validBuyer);

        assertCommandSuccess(new AddCommand(validBuyer), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validBuyer)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Buyer buyerInList = model.getAddressBook().getBuyerList().get(0);
        assertCommandFailure(new AddCommand(buyerInList), model,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
