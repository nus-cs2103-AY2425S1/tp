package seedu.address.logic.commands.clientcommands;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Listings;
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
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());
    }

    @Test
    public void execute_newBuyer_success() {
        Buyer validBuyer = new PersonBuilder().buildBuyer();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
        expectedModel.addPerson(validBuyer);

        assertCommandSuccess(new AddBuyerProfileCommand(validBuyer), model,
                String.format(AddBuyerProfileCommand.MESSAGE_SUCCESS, Messages.format(validBuyer)),
                expectedModel);
    }

    @Test
    public void execute_newSeller_success() {
        Seller validSeller = new PersonBuilder().buildSeller();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
        expectedModel.addPerson(validSeller);

        assertCommandSuccess(new AddSellerProfileCommand(validSeller), model,
                String.format(AddSellerProfileCommand.MESSAGE_SUCCESS, Messages.format(validSeller)),
                expectedModel);
    }

    @Test
    public void execute_duplicateBuyer_throwsCommandException() {
        Buyer buyerInList = (Buyer) model.getAddressBook().getPersonList().get(3);
        assertCommandFailure(new AddBuyerProfileCommand(buyerInList), model,
                AddBuyerProfileCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateSeller_throwsCommandException() {
        Seller sellerInList = null;

        // Find the first seller in the list
        for (Person person : model.getAddressBook().getPersonList()) {
            if (person instanceof Seller) {
                sellerInList = (Seller) person;
                break; // Exit the loop once we find a seller
            }
        }

        // Ensure that a seller was found
        if (sellerInList != null) {
            assertCommandFailure(new AddSellerProfileCommand(sellerInList), model,
                    AddSellerProfileCommand.MESSAGE_DUPLICATE_PERSON);
        } else {
            fail("No seller found in the list to perform the duplicate test.");
        }
    }

}
