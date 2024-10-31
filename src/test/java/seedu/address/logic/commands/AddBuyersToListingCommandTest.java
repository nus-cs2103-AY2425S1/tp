package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Listings;
import seedu.address.model.ModelStub;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.testutil.PersonBuilder;

public class AddBuyersToListingCommandTest {
    private static final Name VALID_LISTING_NAME = new Name("Valid Listing Name");
    private static final Name INVALID_LISTING_NAME = new Name("Invalid Listing Name");
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
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBuyersToListingCommand(VALID_LISTING_NAME,
                null));
    }

    /*
    @Test
    public void constructor_nullListing_throwsCommandException() throws CommandException {
        Buyer alice = new PersonBuilder().withName("Alice").buildBuyer();
        Set<Name> validBuyerSet = Set.of(alice.getName());
        AddBuyersToListingCommandTest.ModelStubWithPersonWithoutListing modelStub =
                new AddBuyersToListingCommandTest.ModelStubWithPersonWithoutListing(alice, VALID_LISTING);
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(INVALID_LISTING_NAME, validBuyerSet);
        System.out.println(addBuyersToListingCommand.execute(modelStub).getFeedbackToUser());
        assertThrows(CommandException.class, AddBuyersToListingCommand
                .MESSAGE_LISTING_NOT_FOUND, () -> addBuyersToListingCommand.execute(modelStub));
    }
    */

    @Test
    public void constructor_buyerIsASeller_throwsCommandException() {
        Seller invalidBuyer = new PersonBuilder().buildSeller();
        AddBuyersToListingCommandTest.ModelStubWithPersonWithoutListing modelStub =
                new AddBuyersToListingCommandTest.ModelStubWithPersonWithoutListing(invalidBuyer, VALID_LISTING);
        Set<Name> invalidBuyerSet = Set.of(invalidBuyer.getName());
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, invalidBuyerSet);
        assertThrows(CommandException.class, "The specified person "
                + invalidBuyer.getName() + " is not a buyer.", () -> addBuyersToListingCommand.execute(modelStub));
    }

    @Test
    public void execute_buyerAddedToListing_success() throws Exception {
        Buyer alice = new PersonBuilder().withName("Alice").buildBuyer();
        AddBuyersToListingCommandTest.ModelStubWithPersonWithoutListing modelStub =
                new AddBuyersToListingCommandTest.ModelStubWithPersonWithoutListing(alice, VALID_LISTING);
        Set<Name> validBuyerSet = Set.of(alice.getName());

        CommandResult commandResult =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, validBuyerSet).execute(modelStub);

        assertEquals(String.format(AddBuyersToListingCommand.MESSAGE_ADD_BUYERS_SUCCESS,
                VALID_LISTING), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_existingBuyerOfListing_throwsCommandException() {
        Buyer validBuyer = new PersonBuilder().buildBuyer();
        Set<Name> validBuyerSet = Set.of(validBuyer.getName());
        AddBuyersToListingCommand addBuyersToListingCommand =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, validBuyerSet);
        ModelStub modelStub = new ModelStubWithPersonWithoutListing(validBuyer, VALID_LISTING);
        assertThrows(CommandException.class,
                AddBuyersToListingCommand.MESSAGE_DUPLICATE_BUYERS, () -> addBuyersToListingCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Buyer alice = new PersonBuilder().withName("Alice").buildBuyer();
        Buyer bob = new PersonBuilder().withName("Bob").buildBuyer();
        Set<Name> setBuyer1 = Set.of(alice.getName());
        Set<Name> setBuyer2 = Set.of(bob.getName());
        AddBuyersToListingCommand addAliceToListingCommand =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, setBuyer1);
        AddBuyersToListingCommand addBobToListingCommand =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, setBuyer2);

        // same object -> returns true
        assertTrue(addAliceToListingCommand.equals(addAliceToListingCommand));

        // same values -> returns true
        AddBuyersToListingCommand addAliceToListingCommandCopy =
                new AddBuyersToListingCommand(VALID_LISTING_NAME, setBuyer1);
        assertTrue(addAliceToListingCommand.equals(addAliceToListingCommandCopy));

        // different types -> returns false
        assertFalse(addAliceToListingCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceToListingCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceToListingCommand.equals(addBobToListingCommand));
    }

    /**
     * A Model stub that contains a single person with no listing.
     */
    private class ModelStubWithPersonWithoutListing extends ModelStub {
        private final Person person;
        private final Listing listing;
        private final Listings listings = new Listings();

        ModelStubWithPersonWithoutListing(Person person, Listing listing) {
            requireNonNull(person);
            this.person = person;
            this.listing = listing;
            listings.addListing(listing);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        @Override
        public boolean hasListing(Listing listing) {
            return false;
        }
        @Override
        public Listing getListingByName(Name name) {
            requireNonNull(name);
            return this.listing;
        }

        @Override
        public Person getPersonByName(Name name) {
            requireNonNull(name);
            return this.person;
        }

        @Override
        public void setListing(Listing target, Listing editedListing) {
            requireAllNonNull(target, editedListing);

            listings.setListing(target, editedListing);
        }
    }
}
