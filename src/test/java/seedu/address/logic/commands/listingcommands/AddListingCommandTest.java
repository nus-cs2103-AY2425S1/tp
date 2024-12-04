package seedu.address.logic.commands.listingcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalListings.PASIR_RIS;
import static seedu.address.testutil.TypicalListings.TAMPINES;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Listings;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyListings;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Region;
import seedu.address.model.name.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.ListingBuilder;

public class AddListingCommandTest {

    @Test
    public void constructor_nulllistingName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(null, PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON))));
    }

    @Test
    public void constructor_nullPrice_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(PASIR_RIS.getName(), null,
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON))));
    }

    @Test
    public void constructor_nullArea_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                null, PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON))));
    }

    @Test
    public void constructor_nullAddress_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), null, PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON))));
    }

    @Test
    public void constructor_nullRegion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), null, INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON))));
    }

    @Test
    public void constructor_nullSeller_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), null,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON))));
    }

    @Test
    public void constructor_noBuyers_success() throws CommandException {
        ModelStubAcceptingListingAdded modelStub = new ModelStubAcceptingListingAdded();
        modelStub.addPerson(ALICE);

        CommandResult commandResult = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>()).execute(modelStub);
        Listing listingWithNoBuyers = new ListingBuilder(PASIR_RIS).withBuyers().build();

        assertEquals(String.format(AddListingCommand.MESSAGE_SUCCESS, Messages.format(listingWithNoBuyers)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(PASIR_RIS), modelStub.listingsAdded);
    }

    @Test
    public void execute_listingAcceptedByModel_addSuccessful() throws CommandException {
        ModelStubAcceptingListingAdded modelStub = new ModelStubAcceptingListingAdded();
        modelStub.addPerson(ALICE);
        modelStub.addPerson(DANIEL);
        modelStub.addPerson(GEORGE);

        CommandResult commandResult = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON))).execute(modelStub);

        assertEquals(String.format(AddListingCommand.MESSAGE_SUCCESS, Messages.format(PASIR_RIS)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(PASIR_RIS), modelStub.listingsAdded);
    }

    @Test
    public void execute_duplicateListing_throwsCommandException() {
        AddListingCommand addListingCommand = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON)));

        ModelStub modelStub = new ModelStubWithListing(PASIR_RIS);
        modelStub.addPerson(ALICE);
        modelStub.addPerson(DANIEL);
        modelStub.addPerson(GEORGE);

        assertThrows(CommandException.class,
                AddListingCommand.MESSAGE_DUPLICATE_LISTING, () -> addListingCommand.execute(modelStub));
    }

    @Test
    public void execute_sellerNotOfTypeSeller_throwsCommandException() {
        AddListingCommand addListingCommand = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON)));

        // FIONA is a buyer
        ModelStub modelStub = new ModelStubWithListing(PASIR_RIS);
        modelStub.addPerson(FIONA);
        modelStub.addPerson(DANIEL);
        modelStub.addPerson(GEORGE);

        assertThrows(CommandException.class,
                String.format(AddListingCommand.MESSAGE_NOT_SELLER, INDEX_FIRST_PERSON.getOneBased(),
                        FIONA.getName()), () -> addListingCommand.execute(modelStub));
    }

    @Test
    public void execute_sellerIndexOutOfBounds_throwsCommandException() {
        AddListingCommand addListingCommand = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_THIRD_PERSON,
                new HashSet<>(List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON)));

        // Only 2 people in the stub
        ModelStub modelStub = new ModelStubWithListing(PASIR_RIS);
        modelStub.addPerson(DANIEL);
        modelStub.addPerson(GEORGE);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_SELLER_INDEX, () -> addListingCommand.execute(modelStub));
    }

    @Test
    public void execute_buyerIndexOutOfBounds_throwsCommandException() {
        AddListingCommand addListingCommand = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON)));

        // Only 2 people in the stub
        ModelStub modelStub = new ModelStubWithListing(PASIR_RIS);
        modelStub.addPerson(ALICE);
        modelStub.addPerson(GEORGE);

        assertThrows(CommandException.class,
                String.format(Messages.MESSAGE_INVALID_BUYER_INDEX,
                        INDEX_THIRD_PERSON.getOneBased()), () -> addListingCommand.execute(modelStub));
    }

    @Test
    public void execute_buyerNotOfTypeBuyer_throwsCommandException() {
        AddListingCommand addListingCommand = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_SECOND_PERSON)));

        // BENSON is a seller
        ModelStub modelStub = new ModelStubAcceptingListingAdded();
        modelStub.addPerson(ALICE);
        modelStub.addPerson(BENSON);

        assertThrows(CommandException.class,
                String.format(AddListingCommand.MESSAGE_NOT_BUYER, INDEX_SECOND_PERSON.getOneBased(),
                        BENSON.getName()), () -> addListingCommand.execute(modelStub));
    }

    // equals and toString methods
    @Test
    public void equals() {
        AddListingCommand addPasirRisCommand = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON)));

        AddListingCommand addTampinesCommand = new AddListingCommand(TAMPINES.getName(), TAMPINES.getPrice(),
                TAMPINES.getArea(), TAMPINES.getAddress(), TAMPINES.getRegion(), INDEX_SECOND_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_FIFTH_PERSON)));

        // same object -> return true
        assertTrue(addPasirRisCommand.equals(addPasirRisCommand));

        // same values -> returns true
        AddListingCommand addPasirRisCommandCopy = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON)));
        assertTrue(addPasirRisCommand.equals(addPasirRisCommandCopy));

        // different values -> return false
        assertFalse(addPasirRisCommand.equals(addTampinesCommand));

        // different types -> return false
        assertFalse(addPasirRisCommand.equals(1));

        // null -> return false
        assertFalse(addPasirRisCommand.equals(null));

        // any one value is different -> return false
        AddListingCommand different = new AddListingCommand(PASIR_RIS.getName(), TAMPINES.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON)));
        assertFalse(addPasirRisCommand.equals(different));

        different = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                new Area("50"), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON)));
        assertFalse(addPasirRisCommand.equals(different));

        different = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), TAMPINES.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON)));
        assertFalse(addPasirRisCommand.equals(different));

        different = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), Region.WEST, INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON)));
        assertFalse(addPasirRisCommand.equals(different));

        different = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_SECOND_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON)));
        assertFalse(addPasirRisCommand.equals(different));

        different = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON)));
        assertFalse(addPasirRisCommand.equals(different));
    }

    @Test
    public void toStringMethod() {
        AddListingCommand addListingCommand = new AddListingCommand(PASIR_RIS.getName(), PASIR_RIS.getPrice(),
                PASIR_RIS.getArea(), PASIR_RIS.getAddress(), PASIR_RIS.getRegion(), INDEX_FIRST_PERSON,
                new HashSet<>(List.of(INDEX_FOURTH_PERSON, INDEX_SIXTH_PERSON)));
        String expected = AddListingCommand.class.getCanonicalName()
                + "{toAdd=" + PASIR_RIS.getName()
                + ", address=" + PASIR_RIS.getAddress()
                + "}";
        assertEquals(expected, addListingCommand.toString());
    }

    /**
     * A Model stub that contains a single listing.
     */
    private class ModelStubWithListing extends ModelStub {
        private final Listing listing;
        private final ArrayList<Listing> listingsAdded = new ArrayList<>();
        private final ArrayList<Person> persons = new ArrayList<>();

        ModelStubWithListing(Listing listing) {
            requireNonNull(listing);
            this.listing = listing;
        }

        @Override
        public void addListing(Listing listing) {
            requireNonNull(listing);
            listingsAdded.add(listing);
        }

        @Override
        public boolean hasListing(Listing listing) {
            requireNonNull(listing);
            return this.listing.isSameListing(listing);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            persons.add(person);
        }

        @Override
        public ObservableList<Listing> getFilteredListingList() {
            return javafx.collections.FXCollections.observableList(listingsAdded);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableList(persons);
        }

        @Override
        public Person getPersonByName(Name name) {
            requireNonNull(name);
            return this.getFilteredPersonList()
                    .stream()
                    .filter(person -> person.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }

    }

    /**
     * A Model stub that always accept the listing being added.
     */
    private class ModelStubAcceptingListingAdded extends ModelStub {
        private final ArrayList<Listing> listingsAdded = new ArrayList<>();
        private final ArrayList<Person> persons = new ArrayList<>();

        @Override
        public boolean hasListing(Listing listing) {
            requireNonNull(listing);
            return listingsAdded.stream().anyMatch(listing::isSameListing);
        }

        @Override
        public void addListing(Listing listing) {
            requireNonNull(listing);
            listingsAdded.add(listing);
        }

        @Override
        public ReadOnlyListings getListings() {
            return new Listings();
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            persons.add(person);
        }

        @Override
        public ObservableList<Listing> getFilteredListingList() {
            return javafx.collections.FXCollections.observableList(listingsAdded);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableList(persons);
        }

        @Override
        public Person getPersonByName(Name name) {
            requireNonNull(name);
            return this.getFilteredPersonList()
                    .stream()
                    .filter(person -> person.getName().equals(name))
                    .findFirst()
                    .orElse(null);
        }
    }

}
