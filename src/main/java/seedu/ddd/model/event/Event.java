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

    public static final String MESSAGE_CONSTRAINTS =
            "There must be at least one client in a specific event,";
    private final ArrayList<Client> clients;
    private final ArrayList<Vendor> vendors;
    private final Description description;

    /**
     * Constructs a {@code Event}.
     *
     * @param clients A list of client.
     * @param vendors A list of vendors.
     */
    public Event(ArrayList<Client> clients, ArrayList<Vendor> vendors, Description description) {
        requireAllNonNull(clients, vendors);
        // another test to check
        this.clients = clients;
        this.vendors = vendors;
        this.description = description;
    }

    /**
     * Returns the clients list.
     * @return An {@code ArrayList} of {@code Client}.
     */
    public ArrayList<Client> getClients() {
        return clients;
    }

    /**
     * Returns the vendors list.
     * @return An {@code ArrayList} of {@code Vendor}.
     */
    public ArrayList<Vendor> getVendors() {
        return vendors;
    }

    /**
     * Returns the event description.
     * @return A {@code String} which represents the description of the event.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Return if the two events are the same.
     * @param otherEvent Another event.
     * @return A boolean value which represents the result.
     */
    public boolean isSameEvent(Event otherEvent) {
        return this.equals(otherEvent);
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

        //two event is equal if have same clients,
        //same vendors and have same description.
        Set<Client> thisClients = new HashSet<>(this.getClients());
        Set<Vendor> thisVendors = new HashSet<>(this.getVendors());
        Description thisDescription = this.getDescription();
        Set<Client> otherClients = new HashSet<>(otherEvent.getClients());
        Set<Vendor> otherVendors = new HashSet<>(otherEvent.getVendors());
        Description otherDescription = otherEvent.getDescription();

        return thisClients.equals(otherClients)
                && thisVendors.equals(otherVendors)
                && thisDescription.equals(otherDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClients(), this.getVendors(), this.getDescription());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clients", this.getClients())
                .add("vendors", this.getVendors())
                .add("description", this.getDescription())
                .toString();
    }
}
