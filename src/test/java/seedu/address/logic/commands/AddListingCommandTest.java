package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyListings;
import seedu.address.model.ReadOnlyUserPrefs;
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

public class AddListingCommandTest {
    private static final Name VALID_LISTING_NAME = new Name("Valid Listing Name");
    private static final Address VALID_ADDRESS = new Address("Valid Address");
    private static final Price VALID_PRICE = new Price("500k",
            new BigDecimal(5000000));
    private static final Area VALID_AREA = new Area(999);
    private static final Region VALID_REGION_1 = Region.WEST;
    private static final Region VALID_REGION_2 = Region.CENTRAL;
    private static final Seller VALID_SELLER = new PersonBuilder().buildSeller();
    private static final Set<Buyer> VALID_BUYERS =
            Set.of(new PersonBuilder().withName("Jack").buildBuyer());
    private static final Set<Person> VALID_BUYERS_PERSON =
            Set.of(new PersonBuilder().withName("Jack").buildBuyer());
    private static final Set<Person> VALID_BUYERS_AND_SELLERS =
            Set.of(new PersonBuilder().withName("Jack").buildBuyer(), VALID_SELLER);
    private static final Name VALID_SELLER_NAME = new PersonBuilder().buildSeller().getName();
    private static final Set<Name> VALID_BUYERS_NAMES =
            Set.of(new PersonBuilder().withName("Jack").buildBuyer().getName());

    @Test
    public void execute_invalidSeller_throwsCommandException() {
        Seller alice = new PersonBuilder().withName("Alice").buildSeller();
        AddListingCommandTest.ModelStubWithoutListing modelStub =
                new AddListingCommandTest.ModelStubWithoutListing(VALID_BUYERS_AND_SELLERS);
        AddListingCommand addListingCommand =
                new AddListingCommand(VALID_LISTING_NAME,
                        VALID_PRICE,
                        VALID_AREA,
                        VALID_ADDRESS,
                        VALID_REGION_1,
                        alice.getName(),
                        VALID_BUYERS_NAMES);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_INPUT, () -> addListingCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidBuyer_throwsCommandException() {
        Buyer alice = new PersonBuilder().withName("Alice").buildBuyer();
        Set<Name> aliceNameInSet = Set.of(alice.getName());
        AddListingCommandTest.ModelStubWithoutListing modelStub =
                new AddListingCommandTest.ModelStubWithoutListing(VALID_BUYERS_AND_SELLERS);
        AddListingCommand addListingCommand =
                new AddListingCommand(VALID_LISTING_NAME,
                        VALID_PRICE,
                        VALID_AREA,
                        VALID_ADDRESS,
                        VALID_REGION_1,
                        VALID_SELLER_NAME,
                        aliceNameInSet);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_INPUT, () -> addListingCommand.execute(modelStub));
    }

    @Test
    public void execute_addListing_success() throws CommandException {
        AddListingCommandTest.ModelStubWithoutListing modelStub =
                new AddListingCommandTest.ModelStubWithoutListing(VALID_BUYERS_AND_SELLERS);
        CommandResult commandResult =
                new AddListingCommand(VALID_LISTING_NAME,
                        VALID_PRICE,
                        VALID_AREA,
                        VALID_ADDRESS,
                        VALID_REGION_1,
                        VALID_SELLER_NAME,
                        VALID_BUYERS_NAMES)
                        .execute(modelStub);
        Listing listingToBeAdded = new Listing(
                VALID_LISTING_NAME,
                VALID_ADDRESS,
                VALID_PRICE,
                VALID_AREA,
                VALID_REGION_1,
                VALID_SELLER,
                VALID_BUYERS_PERSON);

        assertEquals(String.format(AddListingCommand.MESSAGE_SUCCESS,
                Messages.format(listingToBeAdded)), commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        AddListingCommand addListingCommandOne =
                new AddListingCommand(VALID_LISTING_NAME,
                        VALID_PRICE,
                        VALID_AREA,
                        VALID_ADDRESS,
                        VALID_REGION_1,
                        VALID_SELLER_NAME,
                        VALID_BUYERS_NAMES);
        AddListingCommand addListingCommandTwo =
                new AddListingCommand(VALID_LISTING_NAME,
                        VALID_PRICE,
                        VALID_AREA,
                        VALID_ADDRESS,
                        VALID_REGION_2,
                        VALID_SELLER_NAME,
                        VALID_BUYERS_NAMES);

        // same object -> returns true
        assertTrue(addListingCommandOne.equals(addListingCommandOne));

        // same values -> returns true
        AddListingCommand addListingCommandOneCopy =
                new AddListingCommand(VALID_LISTING_NAME,
                        VALID_PRICE,
                        VALID_AREA,
                        VALID_ADDRESS,
                        VALID_REGION_1,
                        VALID_SELLER_NAME,
                        VALID_BUYERS_NAMES);
        assertTrue(addListingCommandOne.equals(addListingCommandOneCopy));

        // different types -> returns false
        assertFalse(addListingCommandOne.equals(1));

        // null -> returns false
        assertFalse(addListingCommandOne.equals(null));

        // different person -> returns false
        assertFalse(addListingCommandOne.equals(addListingCommandTwo));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getListingsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setListingsFilePath(Path listingsFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setListings(ReadOnlyListings listings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyListings getListings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Person getPersonByName(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasListing(Listing listing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteListing(Listing listing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addListing(Listing listing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setListing(Listing target, Listing editedListing) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Listing getListingByName(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasListingsForSeller(Person seller) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Listing> getFilteredListingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredListingList(Predicate<Listing> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub with no listing.
     */
    private class ModelStubWithoutListing extends AddListingCommandTest.ModelStub {
        final ArrayList<Person> persons = new ArrayList<>();
        private final Listings listings = new Listings();

        ModelStubWithoutListing(Set<Person> persons) {
            requireNonNull(persons);
            this.persons.addAll(persons);
        }
        @Override
        public Person getPersonByName(Name name) {
            requireNonNull(name);
            return persons.stream()
                    .filter(person -> person.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            ObservableList<Person> observablePersons = FXCollections.observableArrayList(persons);
            System.out.println(persons);
            return new FilteredList<>(observablePersons);
        }

        @Override
        public void addListing(Listing listing) {
            listings.addListing(listing);
        }
    }
}
