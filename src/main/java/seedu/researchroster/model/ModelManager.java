package seedu.researchroster.model;

import static java.util.Objects.requireNonNull;
import static seedu.researchroster.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.researchroster.commons.core.GuiSettings;
import seedu.researchroster.commons.core.LogsCenter;
import seedu.researchroster.model.person.Person;

/**
 * Represents the in-memory model of the researchroster book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ResearchRoster researchRoster;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given researchRoster and userPrefs.
     */
    public ModelManager(ReadOnlyResearchRoster addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with researchroster book: " + addressBook + " and user prefs " + userPrefs);

        this.researchRoster = new ResearchRoster(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.researchRoster.getPersonList());
    }

    public ModelManager() {
        this(new ResearchRoster(), new UserPrefs());
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

    //=========== ResearchRoster ================================================================================

    @Override
    public void setAddressBook(ReadOnlyResearchRoster addressBook) {
        this.researchRoster.resetData(addressBook);
    }

    @Override
    public ReadOnlyResearchRoster getAddressBook() {
        return researchRoster;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return researchRoster.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        researchRoster.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        researchRoster.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        researchRoster.setPerson(target, editedPerson);
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
        return researchRoster.equals(otherModelManager.researchRoster)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
