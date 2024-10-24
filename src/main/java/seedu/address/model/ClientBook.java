package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;
import seedu.address.model.client.UniqueClientList;

/**
 * Wraps all data at the client-book level
 * Duplicates are not allowed (by .isSameClient comparison)
 */
public class ClientBook implements ReadOnlyClientBook {

    private final UniqueClientList clients;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        clients = new UniqueClientList();
    }

    public ClientBook() {}

    /**
     * Creates a ClientBook using the Clients in the {@code toBeCopied}.
     *
     * @param toBeCopied The data to be copied into this ClientBook.
     */
    public ClientBook(ReadOnlyClientBook toBeCopied) {
        this();
        requireNonNull(toBeCopied, "The data to be copied cannot be null.");
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the client list with {@code clients}.
     * {@code clients} must not contain duplicate clients and must not be null.
     *
     * @param clients The new list of clients.
     */
    public void setClients(List<Client> clients) {
        requireNonNull(clients, "Client list cannot be null.");
        // Ensure the list does not contain null elements
        for (Client client : clients) {
            requireNonNull(client, "Client in the list cannot be null.");
        }
        this.clients.setClients(clients);
    }

    /**
     * Resets the existing data of this {@code ClientBook} with {@code newData}.
     *
     * @param newData The new data to replace the current client book data.
     */
    public void resetData(ReadOnlyClientBook newData) {
        requireNonNull(newData, "New data cannot be null.");
        setClients(newData.getClientList());
    }

    //// client-level operations

    /**
     * Returns true if a client with the same identity as {@code client} exists in the client book.
     *
     * @param client The client to check for existence.
     * @return True if the client exists, false otherwise.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client, "Client cannot be null.");
        return clients.contains(client);
    }

    /**
     * Adds a client to the client book.
     * The client must not already exist in the client book.
     *
     * @param p The client to add.
     */
    public void addClient(Client p) {
        requireNonNull(p, "Client to add cannot be null.");
        clients.add(p);
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the client book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the client book.
     *
     * @param target The client to replace.
     * @param editedClient The new client details to replace with.
     */
    public void setClient(Client target, Client editedClient) {
        requireNonNull(target, "Target client cannot be null.");
        requireNonNull(editedClient, "Edited client cannot be null.");
        clients.setClient(target, editedClient);
    }

    /**
     * Removes {@code key} from this {@code ClientBook}.
     * {@code key} must exist in the client book.
     *
     * @param key The client to remove.
     */
    public void removeClient(Client key) {
        requireNonNull(key, "Client to remove cannot be null.");
        clients.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clients", clients)
                .toString();
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientBook)) {
            return false;
        }

        ClientBook otherClientBook = (ClientBook) other;
        return clients.equals(otherClientBook.clients);
    }

    @Override
    public int hashCode() {
        return clients.hashCode();
    }
}
