package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

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
     * Returns the user prefs' Prudy file path.
     */
    Path getPrudyFilePath();

    /**
     * Sets the user prefs' Prudy file path.
     */
    void setPrudyFilePath(Path prudyFilePath);

    /**
     * Replaces Prudy data with the data in {@code prudy}.
     */
    void setPrudy(ReadOnlyPrudy prudy);

    /** Returns Prudy */
    ReadOnlyPrudy getPrudy();

    /**
     * Returns true if a client with the same identity as {@code client} exists in Prudy.
     */
    boolean hasClient(Client client);

    /**
     * Deletes the given client.
     * The client must exist in Prudy.
     */
    void deleteClient(Client target);

    /**
     * Adds the given client.
     * {@code client} must not already exist in Prudy.
     */
    void addClient(Client client);

    /**
     * Replaces the given client {@code target} with {@code editedClient}.
     * {@code target} must exist in Prudy.
     * The client identity of {@code editedClient} must not be the same as another existing client in Prudy.
     */
    void setClient(Client target, Client editedClient);

    /** Returns an unmodifiable view of the filtered client list */
    ObservableList<Client> getFilteredClientList();

    /**
     * Updates the filter of the filtered client list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClientList(Predicate<Client> predicate);
}
