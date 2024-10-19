package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddClientProfile}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newBuyer_success() {
        Buyer validBuyer = (Buyer) new PersonBuilder().buildBuyer();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validBuyer);

        assertCommandSuccess(new AddBuyerProfile(validBuyer), model,
                String.format(AddBuyerProfile.MESSAGE_SUCCESS, Messages.format(validBuyer)),
                expectedModel);
    }

    @Test
    public void execute_newSeller_success() {
        Seller validSeller = (Seller) new PersonBuilder().buildSeller();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validSeller);

        assertCommandSuccess(new AddSellerProfile(validSeller), model,
                String.format(AddSellerProfile.MESSAGE_SUCCESS, Messages.format(validSeller)),
                expectedModel);
    }

    @Test
    public void execute_duplicateBuyer_throwsCommandException() {
        Buyer buyerInList = (Buyer) model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddBuyerProfile(buyerInList), model,
                AddBuyerProfile.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateSeller_throwsCommandException() {
        Seller sellerInList = (Seller) model.getAddressBook().getPersonList().get(1); // Assuming 1 is a seller
        assertCommandFailure(new AddSellerProfile(sellerInList), model,
                AddSellerProfile.MESSAGE_DUPLICATE_PERSON);
    }

}
