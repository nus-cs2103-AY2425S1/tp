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
import seedu.address.model.person.JobContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingNameContainsKeywordsPredicate;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final WeddingBook weddingBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Wedding> filteredWeddings;

    /**
     * Initializes a ModelManager with the given addressBook, weddingBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlyWeddingBook weddingBook) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.weddingBook = new WeddingBook(weddingBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredWeddings = new FilteredList<>(this.weddingBook.getWeddingList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new WeddingBook());
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
    public Path getWeddingBookFilePath() {
        return userPrefs.getWeddingBookFilePath();
    }

    @Override
    public void setWeddingBookFilePath(Path weddingBookFilePath) {
        requireNonNull(weddingBookFilePath);
        userPrefs.setWeddingBookFilePath(weddingBookFilePath);
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

    @Override
    public void updateFilteredPersonList(JobContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== WeddingBook ================================================================================

    @Override
    public void setWeddingBook(ReadOnlyWeddingBook weddingBook) {
        this.weddingBook.resetData(weddingBook);
    }

    @Override
    public ReadOnlyWeddingBook getWeddingBook() {
        return weddingBook;
    }

    @Override
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return weddingBook.hasWedding(wedding);
    }

    @Override
    public void deleteWedding(Wedding target) {
        weddingBook.removeWedding(target);
    }

    @Override
    public void addWedding(Wedding wedding) {
        weddingBook.addWedding(wedding);
        updateFilteredWeddingList(PREDICATE_SHOW_ALL_WEDDINGS);
    }

    @Override
    public void setWedding(Wedding target, Wedding editedWedding) {
        requireAllNonNull(target, editedWedding);

        weddingBook.setWedding(target, editedWedding);
    }

    //=========== Filtered Wedding List Accessors ============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Wedding} backed by the internal list of
     * {@code versionedWeddingBook}
     */
    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        return filteredWeddings;
    }

    @Override
    public void updateFilteredWeddingList(Predicate<Wedding> predicate) {
        requireNonNull(predicate);
        filteredWeddings.setPredicate(predicate);
    }

    @Override
    public void updateFilteredWeddingList(WeddingNameContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        filteredWeddings.setPredicate(predicate);
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
                && weddingBook.equals(otherModelManager.weddingBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredWeddings.equals(otherModelManager.filteredWeddings);
    }

}
