package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.client.Client;
import seedu.address.model.client.exceptions.ClientNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private AgentAssist historyAgentAssist = null;
    private AgentAssist currentAgentAssist;
    private final UserPrefs userPrefs;
    private final FilteredList<Client> filteredClients;
    private final SimpleObjectProperty<Client> selectedClient = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given agentAssist and userPrefs.
     */
    public ModelManager(ReadOnlyAgentAssist agentAssist, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(agentAssist, userPrefs);

        logger.fine("Initializing with address book: " + agentAssist + " and user prefs " + userPrefs);

        this.currentAgentAssist = new AgentAssist(agentAssist);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredClients = new FilteredList<>(this.currentAgentAssist.getClientList());
    }

    public ModelManager() {
        this(new AgentAssist(), new UserPrefs());
    }

    /**
     * Save the history of the AgentAssist.
     */
    private void saveHistory() {
        historyAgentAssist = currentAgentAssist.getCopy();
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
    public Path getAgentAssistFilePath() {
        return userPrefs.getAgentAssistFilePath();
    }

    @Override
    public void setAgentAssistFilePath(Path agentAssistFilePath) {
        requireNonNull(agentAssistFilePath);
        userPrefs.setAgentAssistFilePath(agentAssistFilePath);
    }

    //=========== AgentAssist ================================================================================

    @Override
    public void setAgentAssist(ReadOnlyAgentAssist agentAssist) {
        saveHistory();
        this.currentAgentAssist.resetData(agentAssist);
    }

    @Override
    public ReadOnlyAgentAssist getAgentAssist() {
        return currentAgentAssist;
    }

    @Override
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return currentAgentAssist.hasClient(client);
    }

    @Override
    public void deleteClient(Client target) {
        saveHistory();
        currentAgentAssist.removeClient(target);
    }

    @Override
    public void addClient(Client client) {
        saveHistory();
        currentAgentAssist.addClient(client);
        updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
    }

    @Override
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);
        saveHistory();
        currentAgentAssist.setClient(target, editedClient);
    }

    @Override
    public void undoCommand() {
        requireNonNull(this.historyAgentAssist);
        this.setAgentAssist(this.historyAgentAssist);
    }

    @Override
    public boolean hasPreviousCommand() {
        return this.historyAgentAssist != null;
    }

    //=========== Selected Client ===========================================================================

    @Override
    public ReadOnlyProperty<Client> selectedClientProperty() {
        return selectedClient;
    }

    @Override
    public Client getSelectedClient() {
        return selectedClient.getValue();
    }

    @Override
    public void setSelectedClient(Client client) {
        requireNonNull(client);
        if (!filteredClients.contains(client)) {
            throw new ClientNotFoundException();
        }
        selectedClient.setValue(client);
    }

    /**
     * Ensures {@code selectedClient} is a valid client in {@code filteredClients}.
     */
    private void ensureSelectedClientIsValid(ListChangeListener.Change<? extends Client> change) {
        while (change.next()) {
            if (this.getSelectedClient() == null) {
                return;
            }

            boolean wasSelectedClientRemoved = change.getRemoved().stream()
                    .anyMatch(removedClient -> selectedClient.getValue().isSameClient(removedClient));
            if (wasSelectedClientRemoved) {
                this.setSelectedClient(change.getFrom() > 0 ? filteredClients.get(change.getFrom() - 1) : null);
            }
        }
    }

    //=========== Filtered Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedAgentAssist}
     */
    @Override
    public ObservableList<Client> getFilteredClientList() {
        return filteredClients;
    }


    @Override
    public void updateFilteredClientList(Predicate<? super Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
    }

    /**
     * Returns the current applied filter if there is any, otherwise, show all clients.
     * @return The current applied filter or all applied filters.
     */
    @Override
    public Predicate<? super Client> getCurrentClientFilter() {
        if (filteredClients.getPredicate() == null) {
            return PREDICATE_SHOW_ALL_CLIENTS;
        }
        return filteredClients.getPredicate();
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
        return currentAgentAssist.equals(otherModelManager.currentAgentAssist)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredClients.equals(otherModelManager.filteredClients);
    }

}
