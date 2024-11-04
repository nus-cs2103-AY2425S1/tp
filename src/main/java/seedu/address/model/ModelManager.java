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
import seedu.address.model.client.Client;

/**
 * Represents the in-memory model of Prudy's data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Prudy prudy;
    private final UserPrefs userPrefs;
    private final FilteredList<Client> filteredClients;

    /**
     * Initializes a ModelManager with the given prudy and userPrefs.
     */
    public ModelManager(ReadOnlyPrudy prudy, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(prudy, userPrefs);

        logger.fine("Initializing with Prudy: " + prudy + " and user prefs " + userPrefs);

        this.prudy = new Prudy(prudy);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredClients = new FilteredList<>(this.prudy.getClientList());

    }

    public ModelManager() {
        this(new Prudy(), new UserPrefs());
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
    public Path getPrudyFilePath() {
        return userPrefs.getPrudyFilePath();
    }

    @Override
    public void setPrudyFilePath(Path prudyFilePath) {
        requireNonNull(prudyFilePath);
        userPrefs.setPrudyFilePath(prudyFilePath);
    }

    //=========== Prudy ================================================================================

    @Override
    public void setPrudy(ReadOnlyPrudy prudy) {
        this.prudy.resetData(prudy);
    }

    @Override
    public ReadOnlyPrudy getPrudy() {
        return prudy;
    }

    @Override
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return prudy.hasClient(client);
    }

    @Override
    public void deleteClient(Client target) {
        prudy.removeClient(target);
    }

    @Override
    public void addClient(Client client) {
        prudy.addClient(client);
        updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
    }

    @Override
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);

        prudy.setClient(target, editedClient);
    }

    //=========== Filtered Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedPrudy}
     */
    @Override
    public ObservableList<Client> getFilteredClientList() {
        return filteredClients;
    }

    @Override
    public void updateFilteredClientList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
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
        return prudy.equals(otherModelManager.prudy)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredClients.equals(otherModelManager.filteredClients);
    }

}
