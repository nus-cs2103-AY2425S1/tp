package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Client;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Client> PREDICATE_SHOW_ALL_CLIENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAgentAssistFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAgentAssistFilePath(Path agentAssistFilePath);

    /**
     * Replaces address book data with the data in {@code agentAssist}.
     */
    void setAgentAssist(ReadOnlyAgentAssist agentAssist);

    /** Returns the AgentAssist */
    ReadOnlyAgentAssist getAgentAssist();

    /**
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    boolean hasClient(Client client);

    /**
     * Deletes the given client.
     * The client must exist in the address book.
     */
    void deleteClient(Client target);

    /**
     * Adds the given client.
     * {@code client} must not already exist in the address book.
     */
    void addClient(Client client);

    /**
     * Replaces the given client {@code target} with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the address book.
     */
    void setClient(Client target, Client editedClient);

    /**
     * Undos previous command by setting currentAddressBook to historyAddressBook.
     */
    void undoCommand();

    /**
     * Returns true if a command has been executed before.
     */
    boolean hasPreviousCommand();

    /** Returns an unmodifiable view of the filtered client list */
    ObservableList<Client> getFilteredClientList();

    /**
     * Updates the filter of the filtered client list to filter by the given {@code predicates}.
     * @throws NullPointerException if {@code predicates} is null.
     */
    void updateFilteredClientList(Predicate<? super Client> predicate);

    /**
     * Selected client in the filtered client list.
     * null if no client is selected.
     */
    ReadOnlyProperty<Client> selectedClientProperty();

    /**
     * Returns the selected client in the filtered client list.
     * null if no client is selected.
     */
    Client getSelectedClient();

    /**
     * Sets the selected client in the filtered client list.
     */
    void setSelectedClient(Client client);

    /**
     * @return The current applied filter of the filtered client list
     */
    Predicate<? super Client> getCurrentClientFilter();
}
