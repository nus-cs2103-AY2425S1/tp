package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Listing> PREDICATE_SHOW_ALL_LISTINGS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the user prefs' listings file path.
     */
    Path getListingsFilePath();

    /**
     * Sets the user prefs' listings file path.
     */
    void setListingsFilePath(Path listingsFilePath);

    /**
     * Replaces listingds data with the data in {@code addressBook}.
     */
    void setListings(ReadOnlyListings listings);

    /** Returns the Listings */
    ReadOnlyListings getListings();

    // Methods for clients

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns the person with the same name as {@code name} exists in the address book.
     */
    Person getPersonByName(Name name);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    //Methods for listings

    /**
     * Returns true if a listing with the same identity as {@code listing} exists in the address book.
     */
    boolean hasListing(Listing listing);

    /**
     * Deletes the given listing.
     * The listing must exist in the address book.
     */
    void deleteListing(Listing listing);

    /**
     * Adds the given listing.
     * {@code listing} must not already exist in the address book.
     */
    void addListing(Listing listing);

    /**
     * Replaces the given person {@code target} with {@code editedListing}.
     * {@code listing} must exist in the address book.
     * The person identity of {@code editedListing} must not be the same as another existing person in the address book.
     */
    void setListing(Listing target, Listing editedListing);


    // Returns the listing with the same name as {@code listing} exists in the address book.
    //Person getPersonByName(Name name);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Listing> getFilteredListingList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered listing list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredListingList(Predicate<Listing> predicate);
}
