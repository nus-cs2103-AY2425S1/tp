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
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ClientHub clientHub;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given clientHub and userPrefs.
     */
    public ModelManager(ReadOnlyClientHub clientHub, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(clientHub, userPrefs);

        logger.fine("Initializing with clienthub: " + clientHub + " and user prefs " + userPrefs);

        this.clientHub = new ClientHub(clientHub);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.clientHub.getPersonList());
    }

    public ModelManager() {
        this(new ClientHub(), new UserPrefs());
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
    public Path getClientHubFilePath() {
        return userPrefs.getClientHubFilePath();
    }

    @Override
    public void setClientHubFilePath(Path clientHubFilePath) {
        requireNonNull(clientHubFilePath);
        userPrefs.setClientHubFilePath(clientHubFilePath);
    }

    //=========== ClientHub ================================================================================

    @Override
    public void setClientHub(ReadOnlyClientHub clientHub) {
        this.clientHub.resetData(clientHub);
    }

    @Override
    public ReadOnlyClientHub getClientHub() {
        return clientHub;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return clientHub.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        clientHub.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        clientHub.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        clientHub.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedClientHub}
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
        return clientHub.equals(otherModelManager.clientHub)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
