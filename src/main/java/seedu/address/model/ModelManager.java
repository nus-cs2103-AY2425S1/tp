package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.CommandGetterResult;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final HistoricalAddressBook historicalAddressBook;
    private final UserPrefs userPrefs;
    private final CommandHistory commandHistory;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs, and commandHistory.
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyUserPrefs userPrefs, ReadOnlyCommandHistory commandHistory) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs
                + " and command history " + commandHistory);

        this.historicalAddressBook = new HistoricalAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.commandHistory = new CommandHistory(commandHistory);
        filteredPersons = new FilteredList<>(this.historicalAddressBook.getPersonList());
    }

    public ModelManager() {
        this(new HistoricalAddressBook(new AddressBook()), new UserPrefs(), new CommandHistory());
    }


    //=========== CommandHistory Related =====================================================================

    @Override
    public void setCommandHistory(ReadOnlyCommandHistory commandHistory) {
        requireNonNull(commandHistory);
        this.commandHistory.resetData(commandHistory);
    }

    @Override
    public ReadOnlyCommandHistory getCommandHistory() {
        return commandHistory;
    }

    @Override
    public void addCommand(String commandString) {
        commandHistory.addCommand(commandString);
    }

    @Override
    public CommandGetterResult getEarlierCommandGetterResult(CommandGetterResult commandGetterResult) {
        return commandHistory.getEarlierCommandGetterResult(commandGetterResult);
    }

    @Override
    public CommandGetterResult getLaterCommandGetterResult(CommandGetterResult commandGetterResult) {
        return commandHistory.getLaterCommandGetterResult(commandGetterResult);
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
        this.historicalAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return historicalAddressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return historicalAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        historicalAddressBook.removePerson(target);
        historicalAddressBook.save();
    }

    @Override
    public void addPerson(Person person) {
        historicalAddressBook.addPerson(person);
        historicalAddressBook.save();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        historicalAddressBook.setPerson(target, editedPerson);
        historicalAddressBook.save();
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

    //=========== Undo/Redo ==================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return historicalAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return historicalAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        historicalAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        historicalAddressBook.redo();
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
        return historicalAddressBook.equals(otherModelManager.historicalAddressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && commandHistory.equals(otherModelManager.commandHistory)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("logger", logger)
                .add("historicalAddressBook", historicalAddressBook)
                .add("userPrefs", userPrefs)
                .add("commandHistory", commandHistory)
                .add("filteredPersons", filteredPersons)
                .toString();
    }
}
