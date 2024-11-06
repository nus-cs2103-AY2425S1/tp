package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;

    private final Listings listings;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Listing> filteredListings;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyListings listings) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and listings " + listings
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.listings = new Listings(listings);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredListings = new FilteredList<>(this.listings.getListingList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new Listings());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getListingsFilePath() {
        return userPrefs.getListingsFilePath();
    }

    @Override
    public void setListingsFilePath(Path listingsFilePath) {
        requireAllNonNull(listingsFilePath);
        userPrefs.setListingsFilePath(listingsFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Listings ================================================================================

    @Override
    public void setListings(ReadOnlyListings listings) {
        this.listings.resetData(listings);
    }

    @Override
    public ReadOnlyListings getListings() {
        return listings;
    }

    @Override
    public boolean hasListing(Listing listing) {
        requireNonNull(listing);
        return listings.hasListing(listing);
    }

    @Override
    public void deleteListing(Listing target) {
        listings.removeListing(target);
    }

    @Override
    public void addListing(Listing listing) {
        listings.addListing(listing);
        updateFilteredListingList(PREDICATE_SHOW_ALL_LISTINGS);
    }

    @Override
    public void setListing(Listing target, Listing editedListing) {
        requireAllNonNull(target, editedListing);

        listings.setListing(target, editedListing);
    }

    /**
     * Returns the listing with the same name as {@code name} exists in Listings.
     */
    @Override
    public Listing getListingByName(Name name) {
        requireNonNull(name);
        return this.getFilteredListingList()
                .stream()
                .filter(listing -> listing.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns true if there exists a listing of the same name as {@code name} in Listings.
     */
    @Override
    public boolean hasListingOfName(Name name) {
        requireNonNull(name);
        return this.getFilteredListingList()
                .stream()
                .anyMatch(listing -> listing.getName().equals(name));
    }

    /**
     * Determines if a listing can be edited without causing duplicate identifiers within the system.
     * Checks if the edited listing's name or address matches any existing listing (excluding the original).
     */
    @Override
    public boolean canEditListing(Listing toEdit, Listing editedListing) {
        requireAllNonNull(toEdit, editedListing);
        return this.getFilteredListingList()
                .stream()
                .filter(listing -> !listing.equals(toEdit))
                .anyMatch(currentListing -> editedListing.getName().equals(currentListing.getName())
                    || editedListing.getAddress().equals(currentListing.getAddress()));
    }

    /**
     * Updates listings associated with a client after the client's details have been edited.
     * If the edited person is a buyer, it replaces the buyer in all relevant listings.
     * If the edited person is a seller, it replaces all listings associated with that seller.
     *
     * @param personToEdit The original person to be edited.
     * @param editedPerson The edited person with updated details.
     */
    @Override
    public void updateListingsAfterClientEdit(Person personToEdit, Person editedPerson) {
        if (this.hasListingsForSeller(personToEdit) || this.hasListingsForBuyer(personToEdit)) {
            if (personToEdit instanceof Buyer buyerToEdit) {

                this.getListings().getListingList()
                        .forEach(listing -> listing.replaceBuyer(buyerToEdit, editedPerson));

            } else if (personToEdit instanceof Seller sellerToEdit) {

                List<Listing> listingsToDelete = this.getListings().getListingList().stream()
                        .filter(listing -> listing.getSeller().equals(sellerToEdit))
                        .toList();

                for (Listing listing : listingsToDelete) {
                    this.deleteListing(listing);
                    this.addListing(listing.modifyListingWithSeller(editedPerson));
                }
            }
        }
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Returns the person with the same name as {@code name} exists in the address book.
     */
    @Override
    public Person getPersonByName(Name name) {
        requireNonNull(name);
        return this.getFilteredPersonList()
                    .stream()
                    .filter(person -> person.getName().equals(name))
                    .findFirst()
                    .orElse(null);
    }

    /**
     * Returns true if the person with the same name as {@code name} exists in the address book.
     */
    @Override
    public boolean hasPersonOfName(Name name) {
        requireNonNull(name);
        return this.getFilteredPersonList()
                .stream()
                .anyMatch(person -> person.getName().equals(name));
    }

    //=========== Filtered Listing List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Listing> getFilteredListingList() {
        return filteredListings;
    }

    @Override
    public void updateFilteredListingList(Predicate<Listing> predicate) {
        requireNonNull(predicate);
        filteredListings.setPredicate(predicate);
    }

    /**
     * Checks if there are any listings associated with the specified {@code seller}.
     *
     * @param seller The seller whose listings are to be checked.
     * @return {@code true} if there is at least one listing associated with the seller;
     *         {@code false} otherwise.
     */
    @Override
    public boolean hasListingsForSeller(Person seller) {
        requireNonNull(seller);
        return listings.getListingList().stream()
                .anyMatch(listing -> listing.getSeller().equals(seller));
    }

    /**
     * Checks if there are any listings associated with the specified {@code buyer}.
     *
     * @param buyer The seller whose listings are to be checked.
     * @return {@code true} if there is at least one listing associated with the buyer;
     *         {@code false} otherwise.
     */
    @Override
    public boolean hasListingsForBuyer(Person buyer) {
        requireNonNull(buyer);
        return listings.getListingList().stream()
                .anyMatch(listing -> listing.getBuyers().contains(buyer));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return  addressBook.equals(otherModelManager.addressBook)
                && listings.equals(otherModelManager.listings)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredListings.equals(otherModelManager.filteredListings);
    }

}
