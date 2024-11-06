package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
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
    public boolean hasPersonOfName(Name name) {
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
    public void updateListingsAfterClientEdit(Person personToEdit, Person editedPerson) {
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
    public boolean hasListingOfName(Name name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canEditListing(Listing toEdit, Listing editedListing) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasListingsForSeller(Person seller) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasListingsForBuyer(Person buyer) {
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
