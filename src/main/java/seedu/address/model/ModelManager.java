package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.tag.ActiveTags;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Wedding> filteredWeddings;
    private ActiveTags activeTags; //Stores all currently used tags
    private WeddingName currentWeddingName; //Stores currently viewed wedding, null if not in wedding view
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredWeddings = new FilteredList<>(this.addressBook.getWeddingList());
        activeTags = new ActiveTags(this.addressBook.findTagOccurrences(), userPrefs.getTagColors());
        currentWeddingName = null;
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

    @Override
    public ObservableMap<String, String> getTagColorMap() {
        return activeTags.getTagColorMap();
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

    @Override
    public void addWedding(Wedding wedding) {
        requireNonNull(wedding);

        addressBook.addWedding(wedding);
        System.out.println(Arrays.toString(addressBook.getWeddingList().toArray()));
    }

    @Override
    public boolean hasWedding(Wedding wedding) {
        requireNonNull(wedding);
        return addressBook.hasWedding(wedding);
    }

    @Override
    public void removeWedding(Wedding wedding) {
        requireNonNull(wedding);

        addressBook.removeWedding(wedding);
    }

    @Override
    public void setWedding(Wedding wedding, Wedding editedWedding) {
        requireAllNonNull(wedding, editedWedding);

        addressBook.setWedding(wedding, editedWedding);
    }

    @Override
    public void assignPerson(Wedding wedding, Person person) {
        requireAllNonNull(wedding, person);

        addressBook.assignPerson(wedding, person);
    }

    @Override
    public void unassignPerson(Wedding wedding, Person person) {
        requireAllNonNull(wedding, person);

        addressBook.unassignPerson(wedding, person);
    }

    @Override
    public ObservableList<Wedding> getFilteredWeddingList() {
        return filteredWeddings;
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
    public void sortPersonList(Comparator<Person> comparator) {
        requireNonNull(comparator);
        addressBook.sortPersons(comparator);
    }

    @Override
    public ActiveTags getActiveTags() {
        return activeTags;
    }

    @Override
    public WeddingName getCurrentWeddingName() {
        return currentWeddingName;
    }

    @Override
    public void setCurrentWeddingName(WeddingName currentWeddingName) {
        this.currentWeddingName = currentWeddingName;
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
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
