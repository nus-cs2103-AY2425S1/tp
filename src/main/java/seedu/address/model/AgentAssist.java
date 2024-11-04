package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.Client;
import seedu.address.model.client.UniqueClientList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameClient comparison)
 */
public class AgentAssist implements ReadOnlyAgentAssist {

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

    public AgentAssist() {}

    /**
     * Creates an AgentAssist using the Clients in the {@code toBeCopied}
     */
    public AgentAssist(ReadOnlyAgentAssist toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the client list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setClients(clients);
    }

    /**
     * Resets the existing data of this {@code AgentAssist} with {@code newData}.
     */
    public void resetData(ReadOnlyAgentAssist newData) {
        requireNonNull(newData);

        setClients(newData.getClientList());
    }

    //// client-level operations

    /**
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    /**
     * Adds a client to the address book.
     * The client must not already exist in the address book.
     */
    public void addClient(Client p) {
        clients.add(p);
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the address book.
     */
    public void setClient(Client target, Client editedClient) {
        requireNonNull(editedClient);

        clients.setClient(target, editedClient);
    }

    /**
     * Removes {@code key} from this {@code AgentAssist}.
     * {@code key} must exist in the address book.
     */
    public void removeClient(Client key) {
        clients.remove(key);
    }

    /**
     * Gets a copy of the current AgentAssist.
     */
    public AgentAssist getCopy() {
        AgentAssist newAgentAssist = new AgentAssist();
        newAgentAssist.setClients(this.getClientList());
        return newAgentAssist;
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
        if (!(other instanceof AgentAssist)) {
            return false;
        }

        AgentAssist otherAgentAssist = (AgentAssist) other;
        return clients.equals(otherAgentAssist.clients);
    }

    @Override
    public int hashCode() {
        return clients.hashCode();
    }
}
