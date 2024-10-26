package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.model.contactrecord.ContactRecordList;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final ObservableList<ContactRecord> displayedCallHistory;
    private final CommandTextHistory commandTextHistory;
    private boolean historyView = false;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.displayedCallHistory = FXCollections.observableArrayList();
        this.commandTextHistory = new CommandTextHistory();
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
    public boolean hasSimilarPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasSimilarPerson(person);
    }

    @Override
    public boolean hasSimilarPerson(Person person, Person exclude) {
        requireAllNonNull(person, exclude);
        return addressBook.hasSimilarPerson(person, exclude);
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
    public void markAsContacted(Person target, ContactRecord contactRecord) {
        requireNonNull(target);
        target.markAsContacted(contactRecord);
    }

    @Override
    public Person getPersonByNric(Nric nric) {
        for (Person person : filteredPersons) {
            if (person.getNric().equals(nric)) {
                return person;
            }
        }
        return null;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public SortedList<Person> getSortedFilteredPersonList() {
        return new SortedList<>(filteredPersons).sorted();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        setHistoryView(false);
    }

    //=========== Call History ================================================================================
    @Override
    public ContactRecordList getCallHistory(Person target) {
        requireNonNull(target);
        return target.getContactRecords();
    }

    @Override
    public void updateDisplayedList(ContactRecordList callHistory) {
        requireNonNull(callHistory);
        displayedCallHistory.clear();
        for (int i = callHistory.size() - 1; i >= 0; i--) {
            displayedCallHistory.add(callHistory.get(i));
        }
        setHistoryView(true);
    }

    @Override
    public boolean isHistoryView() {
        return historyView;
    }

    @Override
    public void setHistoryView(boolean historyView) {
        this.historyView = historyView;
    }

    /**
     * Returns an unmodifiable view of the list of {@code ContactRecords}
     */
    @Override
    public ObservableList<ContactRecord> getDisplayedCallHistory() {
        return displayedCallHistory;
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
                && displayedCallHistory.equals(otherModelManager.displayedCallHistory);
    }

    //=========== CommandTextHistory =========================================================================

    @Override
    public void addCommandTextToHistory(String commandText) {
        commandTextHistory.addCommandToHistory(commandText);
    }

    @Override
    public String getPreviousCommandTextFromHistory() {
        return commandTextHistory.getPreviousCommand();
    }

    @Override
    public String getNextCommandTextFromHistory() {
        return commandTextHistory.getNextCommand();
    }
}
