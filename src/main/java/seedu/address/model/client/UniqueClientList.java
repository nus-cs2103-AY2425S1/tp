package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.client.exceptions.ClientNotFoundException;
import seedu.address.model.client.exceptions.DuplicateClientException;

/**
 * A list of clients that enforces uniqueness between its elements and does not allow nulls.
 * A client is considered unique by comparing using {@code Client#isSameClient(Client)}. As such, adding and updating of
 * clients uses Client#isSameClient(Client) for equality so as to ensure that the client being added or updated is
 * unique in terms of identity in the UniqueClientList. However, the removal of a client uses Client#equals(Object) so
 * as to ensure that the client with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Client#isSameClient(Client)
 */
public class UniqueClientList implements Iterable<Client> {

    private static final Logger logger = LogsCenter.getLogger(UniqueClientList.class);

    private final ObservableList<Client> internalList = FXCollections.observableArrayList();
    private final ObservableList<Client> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent client as the given argument.
     */
    public boolean contains(Client toCheck) {
        requireNonNull(toCheck, "Client to check cannot be null.");
        boolean isSameClient = internalList.stream().anyMatch(toCheck::isSameClient);
        assert isSameClient == internalList.stream().anyMatch(toCheck::isSameClient)
                : "Client existence check failed!";
        return isSameClient;
    }

    /**
     * Returns true if the list contains a client with the same client type and email.
     */
    public boolean containsEmail(Client toCheck) {
        requireNonNull(toCheck, "Client to check cannot be null.");
        boolean isDuplicateEmail = internalList.stream().anyMatch(toCheck::isDuplicateEmail);
        return isDuplicateEmail;
    }

    /**
     * Adds a client to the list.
     * The client must not already exist in the list.
     */
    public void add(Client toAdd) {
        requireNonNull(toAdd, "Client to add cannot be null.");
        if (contains(toAdd)) {
            logger.log(Level.WARNING, "Attempted to add duplicate client: " + toAdd);
            throw new DuplicateClientException();
        }
        internalList.add(toAdd);
        assert internalList.contains(toAdd) : "Client was not added successfully!";
        logger.log(Level.INFO, "Client added: " + toAdd);
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the list.
     * The client identity of {@code editedClient} must not be the same as another existing client in the list.
     */
    public void setClient(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            logger.log(Level.SEVERE, "Client not found: " + target);
            throw new ClientNotFoundException();
        }

        if (!target.isSameClient(editedClient) && contains(editedClient)) {
            logger.log(Level.WARNING, "Attempted to replace with a duplicate client: " + editedClient);
            throw new DuplicateClientException();
        }

        internalList.set(index, editedClient);
        assert internalList.get(index).equals(editedClient) : "Client was not replaced successfully!";
        logger.log(Level.INFO, "Client replaced: " + target + " with " + editedClient);
    }

    /**
     * Removes the equivalent client from the list.
     * The client must exist in the list.
     */
    public void remove(Client toRemove) {
        requireNonNull(toRemove, "Client to remove cannot be null.");
        if (!internalList.remove(toRemove)) {
            logger.log(Level.SEVERE, "Client to remove not found: " + toRemove);
            throw new ClientNotFoundException();
        }
        assert !internalList.contains(toRemove) : "Client was not removed successfully!";
        logger.log(Level.INFO, "Client removed: " + toRemove);
    }

    /**
     * Replaces the contents of this list with the provided {@code newClientList}.
     *
     * @param newClientList The new UniqueClientList to replace the current list.
     */
    public void setClients(UniqueClientList newClientList) {
        requireNonNull(newClientList, "New client list cannot be null.");
        internalList.setAll(newClientList.internalList);
        assert internalList.equals(newClientList.internalList) : "Clients were not set successfully!";
        logger.log(Level.INFO, "Client list replaced with new client list.");
    }

    /**
     * Replaces the contents of this list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        requireAllNonNull(clients);
        if (!clientsAreUnique(clients)) {
            logger.log(Level.WARNING, "Attempted to set clients with duplicate entries.");
            throw new DuplicateClientException();
        }

        internalList.setAll(clients);
        assert internalList.equals(clients) : "Client list was not replaced successfully!";
        logger.log(Level.INFO, "Client list replaced with provided list of clients.");
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Client> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Client> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueClientList)) {
            return false;
        }

        UniqueClientList otherUniqueClientList = (UniqueClientList) other;
        boolean isEqual = internalList.equals(otherUniqueClientList.internalList);
        assert isEqual == internalList.equals(otherUniqueClientList.internalList) : "Equality check failed!";
        return isEqual;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code clients} contains only unique clients.
     */
    public boolean clientsAreUnique(List<Client> clients) {
        for (int i = 0; i < clients.size() - 1; i++) {
            for (int j = i + 1; j < clients.size(); j++) {
                if (clients.get(i).isSameClient(clients.get(j))) {
                    logger.log(Level.WARNING, "Duplicate client detected during uniqueness check: "
                            + clients.get(i));
                    return false;
                }
            }
        }
        return true;
    }
}
