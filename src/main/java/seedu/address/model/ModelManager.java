package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddLogCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.log.Log;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

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
     * Returns the full list of persons in the address book.
     */
    @Override
    public ObservableList<Person> getPersonList() {
        return addressBook.getPersonList();
    }

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

    //=========== Session Log ================================================================================

    @Override
    public ObservableList<Log> getSessionLog(int personIndex) {
        return addressBook.getSessionLog(personIndex);
    }

    @Override
    public void addLog(Person personToUpdate, Log log) throws CommandException {
        requireAllNonNull(personToUpdate, log);
        // Create a new Set of logs that includes the new log
        Set<Log> updatedLogs = new HashSet<>(personToUpdate.getLogs());

        if (updatedLogs.contains(log)) {
            throw new CommandException(AddLogCommand.MESSAGE_DUPLICATE_LOG);
        }

        updatedLogs.add(log);

        // Create a new updated person with the additional log
        Person updatedPerson = new Person(
                personToUpdate.getName(),
                personToUpdate.getIdentityNumber(),
                personToUpdate.getPhone(),
                personToUpdate.getEmail(),
                personToUpdate.getAddress(),
                personToUpdate.getStatus(),
                updatedLogs // Updated logs set
        );

        // Update the model with the new person (with the added log)
        this.setPerson(personToUpdate, updatedPerson);
    }

    //========== Util Methods ================================================================================


    //===============Saved Commands=====================================================================================

    /**
     * Sets the saved command in the model.
     *
     * @param command The command to be saved.
     */
    @Override
    public void setSavedCommand(Command command) {
        this.savedCommand = command;
    }

    @Override
    public boolean hasSavedCommand() {
        return this.savedCommand != null;
    }


    @Override
    public void clearSavedCommand() {
        this.savedCommand = null;
    }

    @Override
    public Command getSavedCommand() {
        return this.savedCommand;
    }

    @Override
    public CommandResult executeSavedCommand() throws CommandException {
        if (!hasSavedCommand()) {
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
