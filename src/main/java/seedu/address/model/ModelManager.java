package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.EventManager;
import seedu.address.model.event.ReadOnlyEventManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final EventManager eventManager;
    private final FilteredList<Person> filteredPersons;

    private ObservableList<Person> contactListForFindEvent;

    /**
     * The set of persons that are excluded from the search. Reset upon exiting searchmode or clear command
     */
    private final Set<Person> excludedPersons = new HashSet<>();

    private BooleanProperty searchMode = new SimpleBooleanProperty(false);
    private BooleanProperty isFindEvent = new SimpleBooleanProperty(false);
    private Predicate<Person> lastPredicate = PREDICATE_SHOW_ALL_PERSONS;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyEventManager eventManager,
                        ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, eventManager, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                    + " and event manager " + eventManager
                    + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.eventManager = new EventManager(eventManager);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new EventManager(), new UserPrefs());
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
    public int countSamePersons(Person person) {
        requireNonNull(person);
        return addressBook.countSamePersons(person);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasPhone(Person person) {
        requireNonNull(person);
        return addressBook.hasPhone(person);
    }

    @Override
    public boolean hasEmail(Person person) {
        requireNonNull(person);
        return addressBook.hasEmail(person);
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
    //=========== Event Manager ==============================================================================

    @Override
    public void setEventManager(ReadOnlyEventManager eventManager) {
        this.eventManager.resetData(eventManager);
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
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
        lastPredicate = predicate;
    }

    public Predicate<Person> getLastPredicate() {
        return lastPredicate;
    }

    /**
     * Checks the search mode of the model.
     */
    public boolean getSearchMode() {
        return searchMode.get();
    }

    /**
     * Sets the search mode of the model.
     */
    public void setSearchMode(boolean searchMode) {
        this.searchMode.set(searchMode);
    }

    /**
     * Returns the search mode object.
     */
    public BooleanProperty searchModeProperty() {
        return searchMode;
    }

    public BooleanProperty getIsFindEvent() {
        return isFindEvent;
    }

    public void setIsFindEvent(boolean isFindEvent) {
        this.isFindEvent.set(isFindEvent);
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
                && eventManager.equals(otherModelManager.eventManager)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }


    @Override
    public ObservableList<Person> getAllPersons() {
        return addressBook.getPersonList();
    }


    /**
     * Gets the set of persons that are excluded from the search.
     * @return copy of excludedPersons
     */
    @Override
    public Set<Person> getExcludedPersons() {
        return new HashSet<>(excludedPersons);
    }

    /**
     * Excludes a Person from the search.
     * @param person to be excluded
     */
    @Override
    public void excludePerson(Person person) throws PersonNotFoundException {
        if (!addressBook.hasPerson(person)) {

            throw new PersonNotFoundException();
        }
        excludedPersons.add(person);
    }

    @Override
    public void clearExcludedPersons() {
        excludedPersons.clear();
    }

    /**
     * Updates the filtered list of persons with the excluded persons.
     */
    @Override
    public void updateFilteredListWithExclusions(Predicate<Person> predicate) {

        Predicate<Person> excludeRemovedPersons = person -> !excludedPersons.contains(person);
        if (predicate != null) {
            lastPredicate = lastPredicate.or(predicate);
        }
        updateFilteredPersonList(excludeRemovedPersons.and(lastPredicate));
    }

    @Override
    public String[] findSameField(Person person) {
        requireNonNull(person);
        return addressBook.findSameField(person);
    }

    @Override
    public void setContactListForFindEvent(ArrayList<Person> persons) {
        this.contactListForFindEvent = FXCollections.observableList(persons);
    }

    @Override
    public ObservableList<Person> getContactListForFindEvent() {
        return this.contactListForFindEvent;
    }
}
