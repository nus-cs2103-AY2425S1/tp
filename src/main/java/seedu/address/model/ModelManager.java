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
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.log.Log;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    // FilteredLogs should be final? Note that it is not initalised,
    // may cause run time error. TODO: Improve on stability
    private FilteredList<Log> filteredLogs;

    // Dangerous, find a better way to implement this.
    private Command savedCommand = null;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());;
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

    /**
     * Returns an unmodifiable view of the list of {@code Log} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Log> getFilteredLogList() {
        return filteredLogs;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
    @Override
    public void updateFilteredLogList(Predicate<Log> predicate) {
        requireNonNull(predicate);
        filteredLogs.setPredicate(predicate);
    }

    /**
     * Updates the filter of the logs list to filter by the given {@code identityNumber}.
     *
     * @param identityNumber
     */
    @Override
    public void updateFilteredPersonListById(IdentityNumber identityNumber) {
        requireNonNull(identityNumber);
        Predicate<Person> predicate = person -> person.getIdentityNumber().equals(identityNumber);
        updateFilteredPersonList(predicate);
    }

    /**
     * Updates logs list to show logs identified by the given {@code identityNumber}.
     *
     * @param identityNumber
     */
    public void updateFilteredLogListById(IdentityNumber identityNumber) {
        requireNonNull(identityNumber);

        // Get the person with matching identity number
        updateFilteredPersonListById(identityNumber);
        Person targetPerson = filteredPersons.get(0);

        // Convert Set<Log> to an ObservableList<Log>
        ObservableList<Log> loglist = FXCollections.observableArrayList(targetPerson.getLogs());

        // Update the FilteredLogs to the logs of the targetPerson
        filteredLogs = new FilteredList<>(loglist);
    }

    //===============Saved Commands=====================================================================================

    /**
     * Sets the saved command in the model.
     *
     * @param command The command to be saved.
     */
    @Override
    public void setSaveCommand(Command command) {
        this.savedCommand = command;
    }

    /**
     * Returns true if there is a saved command in the model.
     */
    @Override
    public boolean hasSavedCommand() {
        return this.savedCommand != null;
    }

    /**
     * Clears the saved command in the model.
     */
    @Override
    public void clearSavedCommand() {
        this.savedCommand = null;
    }

    /**
     * Executes the saved command in the model.
     *
     * @return The result of the command execution.
     * @throws CommandException If saved command does not exist or error occurs during command execution.
     */
    @Override
    public CommandResult executeSavedCommand() throws CommandException {
        if (!hasSavedCommand()) {
            clearSavedCommand();
            throw new CommandException("No command to confirm.");
        }
        CommandResult result = this.savedCommand.execute(this);
        clearSavedCommand();
        return result;
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
