package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;
import seedu.address.model.client.UniqueClientList;
import seedu.address.model.client.exceptions.ClientNotFoundException;
import seedu.address.model.client.exceptions.DuplicateClientException;

/**
 * Wraps all data at the client-book level.
 * Duplicates are not allowed (by .isSameClient comparison).
 */
public class ClientBook implements ReadOnlyClientBook {

    private static final Logger logger = LogsCenter.getLogger(ClientBook.class);

    private final UniqueClientList clients;

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
        requireNonNull(toBeCopied, "toBeCopied cannot be null.");
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the client list with {@code clients}.
     * {@code clients} must not contain duplicate clients and must not be null.
     *
     * @param clients The new list of clients.
     * @throws DuplicateClientException if {@code clients} contains duplicate clients.
     */
    public void setClients(List<Client> clients) {
        requireNonNull(clients, "Client list cannot be null.");
        for (Client client : clients) {
            requireNonNull(client, "Client in the list cannot be null.");
        }

        try {
            this.clients.setClients(clients);
        } catch (DuplicateClientException e) {
            logger.log(Level.WARNING, "Attempted to set client list with duplicates.", e);
            throw e;
        }

        assert this.clients.asUnmodifiableObservableList().equals(clients) : "Client list was not set correctly!";
    }

    /**
     * Resets the existing data of this {@code ClientBook} with {@code newData}.
     *
     * @param newData The new data to replace the current client book data.
     */
    public void resetData(ReadOnlyClientBook newData) {
        requireNonNull(newData, "New data cannot be null.");
        setClients(newData.getClientList());
        assert getClientList().equals(newData.getClientList()) : "Client data was not reset correctly!";
    }

    //// client-level operations

    /**
     * Returns true if a client with the same identity as {@code client} exists in the client book.
     *
     * @param clientToCheck The client to check for existence.
     * @return True if the client exists, false otherwise.
     */
    public boolean hasClient(Client clientToCheck) {
        requireNonNull(clientToCheck, "Client cannot be null.");
        boolean exists = clients.contains(clientToCheck);
        assert exists == clients.contains(clientToCheck) : "Client existence check failed!";
        return exists;
    }

    /**
     * Returns true if {@code clientToCheck} is a Buyer and a Buyer with the same email as {@code client}
     * exists in the client book or if {@code clientToCheck} is a Seller and a Seller with the same
     * email as {@code clientToCheck} exists in the client book, false otherwise.
     *
     * @param clientToCheck The client to check for duplicate email.
     * @return True if {@code clientToCheck} has the same email as an existing client of the same, false otherwise.
     */
    public boolean sameEmailExists(Client clientToCheck) {
        requireNonNull(clientToCheck, "Client cannot be null.");
        boolean exists = clients.containsEmail(clientToCheck);
        assert exists == clients.containsEmail(clientToCheck) : "Duplicate email check failed!";
        return exists;
    }

    /**
     * Adds a client to the client book.
     * The client must not already exist in the client book.
     *
     * @param client The client to add.
     * @throws DuplicateClientException if the client already exists in the client book.
     */
    public void addClient(Client client) {
        requireNonNull(client, "Client to add cannot be null.");

        try {
            clients.add(client);
        } catch (DuplicateClientException e) {
            logger.log(Level.WARNING, "Attempted to add a duplicate client: " + client, e);
            throw e;
        }

        assert hasClient(client) : "Client was not added successfully!";
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the client book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the client book.
     *
     * @param target The client to replace.
     * @param editedClient The new client details to replace with.
     * @throws ClientNotFoundException if {@code target} does not exist in the client book.
     * @throws DuplicateClientException if the {@code editedClient} conflicts with another client's identity.
     */
    public void setClient(Client target, Client editedClient) {
        requireNonNull(target, "Target client cannot be null.");
        requireNonNull(editedClient, "Edited client cannot be null.");

        try {
            clients.setClient(target, editedClient);
        } catch (ClientNotFoundException e) {
            logger.log(Level.SEVERE, "Attempted to replace a non-existent client: " + target, e);
            throw e;
        } catch (DuplicateClientException e) {
            logger.log(Level.WARNING, "Attempted to replace with a duplicate client: " + editedClient, e);
            throw e;
        }

        assert clients.contains(editedClient) : "Client replacement failed!";
    }

    /**
     * Removes {@code key} from this {@code ClientBook}.
     * {@code key} must exist in the client book.
     *
     * @param key The client to remove.
     * @throws ClientNotFoundException if {@code key} does not exist in the client book.
     */
    public void removeClient(Client key) {
        requireNonNull(key, "Client to remove cannot be null.");

        try {
            clients.remove(key);
        } catch (ClientNotFoundException e) {
            logger.log(Level.SEVERE, "Attempted to remove a non-existent client: " + key, e);
            throw e;
        }

        assert !hasClient(key) : "Client was not removed successfully!";
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

        if (!(other instanceof ClientBook)) {
            return false;
        }

        ClientBook otherClientBook = (ClientBook) other;
        boolean isEqual = clients.equals(otherClientBook.clients);
        assert isEqual == clients.equals(otherClientBook.clients) : "Equality check failed!";
        return isEqual;
    }

    @Override
    public int hashCode() {
        return clients.hashCode();
    }
}
