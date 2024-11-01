package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ClearListingCommandTest {
    private static final Name VALID_LISTING_NAME = new Name("Valid Listing Name");
    private static final Address VALID_ADDRESS = new Address("Valid Address");
    private static final Price VALID_PRICE = new Price("500k",
            new BigDecimal(5000000));
    private static final Area VALID_AREA = new Area(999);
    private static final Region VALID_REGION = Region.WEST;
    private static final Person VALID_SELLER = new PersonBuilder().buildSeller();
    private static final Set<Person> VALID_BUYERS = Set.of(new PersonBuilder().buildBuyer());

    private static final Listing VALID_LISTING = new Listing(VALID_LISTING_NAME,
            VALID_ADDRESS,
            VALID_PRICE,
            VALID_AREA,
            VALID_REGION,
            VALID_SELLER,
            VALID_BUYERS);
    @Test
    public void execute_emptyListing_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearListingCommand(), model,
                ClearListingCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyListing_success() {
        Listings typicalListing = new Listings();
        typicalListing.addListing(VALID_LISTING);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), typicalListing);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());

        assertCommandSuccess(new ClearListingCommand(), model,
                ClearListingCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
