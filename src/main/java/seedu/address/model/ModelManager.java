package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.history.HistoryCommand;
import seedu.address.model.history.HistoryCommandList;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final HistoryCommandList historyCommandList;
    private final VersionedAddressBook versionedAddressBook;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        historyCommandList = new HistoryCommandList();
        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.versionedAddressBook.getPersonList());
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
        this.versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /**
     * Gets the AddressBook in previous state at specific index for testing purpose.
     */
    @Override
    public AddressBook getVersionedAddressBook(int index) {
        return new AddressBook(versionedAddressBook.getAddressBookStateList().get(index));
    }

    /**
     * Saves the getCurrentAddressBook() AddressBook state in the history.
     */
    @Override
    public void commitAddressBook() {
        versionedAddressBook.commitAddressBook();
    }

    /**
     * Reverses the AddressBook to the previous state.
     */
    @Override
    public void undoAddressBook() throws CommandException {
        versionedAddressBook.undoAddressBook();
    }

    /**
     * Reverses the AddressBook to the next state.
     */
    @Override
    public void redoAddressBook() throws CommandException {
        versionedAddressBook.redoAddressBook();
    }

    /**
     * Discards the unsaved changes in the current state.
     */
    public void discardUnsavedChanges() {
        versionedAddressBook.discardUnsavedChanges();
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasPhone(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPhone(person);
    }

    @Override
    public boolean hasEmail(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasEmail(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.setPerson(target, editedPerson);
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
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return versionedAddressBook.getCurrentAddressBook()
                .equals(otherModelManager.versionedAddressBook.getCurrentAddressBook())
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    //=========== History Command List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Command} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<HistoryCommand> getHistoryCommandList() {
        return FXCollections.unmodifiableObservableList(historyCommandList.getHistoryCommands());
    }

    /**
     * Adds command to the history command list.
     */
    @Override
    public void addHistoryCommand(Command toAdd, String originalCommandText) {
        historyCommandList.add(toAdd, originalCommandText);
    }
}
