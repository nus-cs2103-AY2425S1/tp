package seedu.ddd.model.event;

import static seedu.ddd.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * Represents a Event in the address book.
 * Guarantees: to be determined.
 */
public class Event {

    private ArrayList<Client> clients;
    private ArrayList<Vendor> vendors;

    /**
     * Constructs a {@code Event}.
     *
     * @param clients A list of client.
     * @param vendors A list of vendors.
     */
    public Event(ArrayList<Client> clients, ArrayList<Vendor> vendors) {
        requireAllNonNull(clients, vendors);
        this.clients = clients;
        this.vendors = vendors;
    }

    /**
     * Returns a copy
     * @return
     */
    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Vendor> getVendors() {
        return vendors;
    }

    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;

        Set<Client> thisClients = new HashSet<>(this.getClients());
        Set<Vendor> thisVendors = new HashSet<>(this.getVendors());
        Set<Client> otherClients = new HashSet<>(otherEvent.getClients());
        Set<Vendor> otherVendors = new HashSet<>(otherEvent.getVendors());

        return thisClients.equals(otherClients) && thisVendors.equals(otherVendors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClients(), this.getVendors());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clients", this.getClients())
                .add("vendors", this.getVendors())
                .toString();
    }
}