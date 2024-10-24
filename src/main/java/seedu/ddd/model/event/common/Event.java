package seedu.ddd.model.event.common;

import static seedu.ddd.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.ddd.commons.util.AppUtil;
import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.Displayable;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * Represents a Event in the address book.
 * Guarantees: to be determined.
 */
public class Event implements Displayable {

    public static final String MESSAGE_CONSTRAINTS =
            "There must be at least one client in a specific event.";
    private final List<Client> clients;
    private final List<Vendor> vendors;
    private final Description description;
    private final EventId eventId;

    /**
     * Constructs a {@code Event}.
     *
     * @param clients A list of client.
     * @param vendors A list of vendors.
     */
    public Event(List<Client> clients, List<Vendor> vendors, Description description, EventId eventId) {
        requireAllNonNull(clients, vendors, description);
        AppUtil.checkArgument(isValidEvent(clients), MESSAGE_CONSTRAINTS);
        assert !clients.isEmpty();
        this.clients = new ArrayList<>();
        this.clients.addAll(clients);
        this.vendors = new ArrayList<>();
        this.vendors.addAll(vendors);
        this.description = description;
        this.eventId = eventId;
    }

    /**
     * Alternative constructor that defers the loading of clients and vendors
     */
    public Event(Description description, EventId eventId) {
        this.description = description;
        this.eventId = eventId;
        this.clients = new ArrayList<>();
        this.vendors = new ArrayList<>();
    }

    /**
     * Returns true if it is a valid event, which means there must
     * be at least one {@code Client} in clients list.
     * @param testList The {@code ArrayList} of {@code Client} at the constructor.
     */
    public static boolean isValidEvent(List<Client> testList) {
        return !testList.isEmpty();
    }

    /**
     * Returns the contact list.
     * @return A {@code List} of {@code Contacts}.
     */
    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.addAll(clients);
        contacts.addAll(vendors);
        return Collections.unmodifiableList(contacts);
    }

    /**
     * Adds a{@code Client} to an {@code Event}
     * If the current event is not stored in the client's set of events,
     * it will add the association
     */
    public void addClient(Client client) {
        clients.add(client);
        Set<Event> events = client.getEvents();
        if (!events.contains(this)) {
            client.addEvent(this);
        }
    }

    /**
     * Adds a{@code Vendor} to an {@code Event}
     * If the current event is not stored in the vendor's set of events,
     * it will add the association
     */
    public void addVendor(Vendor vendor) {
        vendors.add(vendor);
        Set<Event> events = vendor.getEvents();
        if (!events.contains(this)) {
            vendor.addEvent(this);
        }
    }

    /**
     * Returns the client list.
     * @return A {@code List} of {@code Clients}.
     */
    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    /**
     * Returns the vendor list.
     * @return A {@code List} of {@code Vendors}.
     */
    public List<Vendor> getVendors() {
        return Collections.unmodifiableList(vendors);
    }

    /**
     * Returns the list of client ids.
     * @return A {@code List} of {@code ContactId}.
     */
    public List<ContactId> getClientIds() {
        return clients.stream().map(Contact::getId).toList();
    }

    /**
     * Returns the list of vendor ids.
     * @return A {@code List} of {@code ContactId}.
     */
    public List<ContactId> getVendorIds() {
        return vendors.stream().map(Contact::getId).toList();
    }
    /**
     * Returns the event description.
     * @return A {@code String} which represents the description of the event.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Returns the unique event ID.
     */
    public EventId getEventId() {
        return this.eventId;
    }

    /**
     * Return if the two events are the same.
     * @param otherEvent Another event.
     * @return A boolean value which represents the result.
     */
    public boolean isSameEvent(Event otherEvent) {
        Set<Client> thisClients = new HashSet<>(this.getClients());
        Set<Client> otherClients = new HashSet<>(otherEvent.getClients());
        Description thisDescription = this.getDescription();
        Description otherDescription = otherEvent.getDescription();
        return thisClients.equals(otherClients)
                && thisDescription.equals(otherDescription);
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
        return Objects.hash(this.getClients(), this.getVendors(),
                this.getDescription(), this.getEventId());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("clients", this.getClients())
                .add("vendors", this.getVendors())
                .add("description", this.getDescription())
                .add("event id", eventId)
                .toString();
    }
}
