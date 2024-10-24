package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Concert> filteredConcerts;
    private final FilteredList<ConcertContact> filteredConcertContacts;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredConcerts = new FilteredList<>(this.addressBook.getConcertList());
        filteredConcertContacts = new FilteredList<>(this.addressBook.getConcertContactList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
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
    public boolean hasConcert(Concert concert) {
        requireNonNull(concert);
        return addressBook.hasConcert(concert);
    }

    @Override
    public boolean hasConcertContact(ConcertContact concertContact) {
        requireNonNull(concertContact);
        return addressBook.hasConcertContact(concertContact);
    }

    @Override
    public boolean hasConcertContact(Person person, Concert concert) {
        requireAllNonNull(person, concert);
        return addressBook.hasConcertContact(person, concert);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    public void deleteConcertContact(Person targetPerson, Concert targetConcert) {
        addressBook.removeConcertContact(targetPerson, targetConcert);
    }

    @Override
    public void deleteConcertContact(Concert target) {
        addressBook.removeConcertContact(target);
    }

    @Override
    public void deleteConcertContact(Person target) {
        addressBook.removeConcertContact(target);
    }

    @Override
    public void deleteConcert(Concert target) {
        addressBook.removeConcert(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addConcert(Concert concert) {
        addressBook.addConcert(concert);
        updateFilteredConcertList(PREDICATE_SHOW_ALL_CONCERTS);
    }

    @Override
    public void addConcertContact(ConcertContact concertContact) {
        addressBook.addConcertContact(concertContact);
        updateFilteredConcertContactList(PREDICATE_SHOW_ALL_CONCERT_CONTACTS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setConcert(Concert target, Concert editedConcert) {
        requireAllNonNull(target, editedConcert);

        addressBook.setConcert(target, editedConcert);
    }

    @Override
    public void setConcertContact(ConcertContact target, ConcertContact editedConcertContact) {
        requireAllNonNull(target, editedConcertContact);

        addressBook.setConcertContact(target, editedConcertContact);
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

    //=========== Filtered Concert List Accessors =============================================================
    @Override
    public ObservableList<Concert> getFilteredConcertList() {
        return filteredConcerts;
    }

    @Override
    public void updateFilteredConcertList(Predicate<Concert> predicate) {
        requireNonNull(predicate);
        filteredConcerts.setPredicate(predicate);
    }

    //=========== Filtered ConcertContact List Accessors =============================================================
    @Override
    public ObservableList<ConcertContact> getFilteredConcertContactList() {
        return filteredConcertContacts;
    }

    @Override
    public void updateFilteredConcertContactList(Predicate<ConcertContact> predicate) {
        requireNonNull(predicate);
        filteredConcertContacts.setPredicate(predicate);
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
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredConcerts.equals(otherModelManager.filteredConcerts)
                && filteredConcertContacts.equals(otherModelManager.filteredConcertContacts);
    }
}
