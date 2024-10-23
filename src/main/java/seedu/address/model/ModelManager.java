package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.assignment.PredefinedAssignmentsData;
import seedu.address.model.assignment.ReadOnlyPredefinedAssignmentsData;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;
    private final PredefinedAssignmentsData predefinedAssignmentsData;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     *
     * @param addressBook               the address book
     * @param userPrefs                 the user prefs
     * @param predefinedAssignmentsData the predefined assignments data
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyUserPrefs userPrefs,
                        ReadOnlyPredefinedAssignmentsData predefinedAssignmentsData) {
        requireAllNonNull(addressBook, userPrefs, predefinedAssignmentsData);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);
        this.predefinedAssignmentsData = new PredefinedAssignmentsData(predefinedAssignmentsData);
    }

    /**
     * Instantiates a new Model manager.
     */
    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new PredefinedAssignmentsData());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
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
    public Optional<Person> getPerson(Name name) {
        for (Person person : addressBook.getPersonList()) {
            if (person.getName().equals(name)) {
                return Optional.of(person); // Return the person if found
            }
        }

        // Return an empty Optional if the person is not found
        return Optional.empty();
    }
    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return sortedPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateSortedPersonList(Comparator<Person> comparator) {
        requireNonNull(comparator);
        sortedPersons.setComparator(comparator);
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

    //=========== Predefined assignments accessors =============================================================

    public boolean hasAssignment(String name) {
        return predefinedAssignmentsData.hasAssignment(name);
    }

    public float maxScore(String assignment) {
        return predefinedAssignmentsData.maxScore(assignment);
    }

    public String getAssignmentName(String name) {
        return predefinedAssignmentsData.getAssignmentName(name);
    }

    public boolean hasName(Name name) {
        return addressBook.hasName(name);
    }

    public Github getGitHubUsername(Name name) {
        return addressBook.getGitHubUsername(name);
    }

    /**
     * Replace all person in currently with new data
     */
    public void replaceAllPersons(List<Person> persons) {
        AddressBook updatedList = new AddressBook();
        updatedList.setPersons(persons);
        addressBook.resetData(updatedList);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
}
