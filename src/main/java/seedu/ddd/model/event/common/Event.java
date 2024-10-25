package seedu.ddd.model.event.common;

import static seedu.ddd.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.ddd.commons.util.AppUtil;
import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.Displayable;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.event.common.Date;
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

    // Identity fields
    private final Name name;
    private final Description description;
    private final Date date;
    private final EventId eventId;

    // References
    private final List<Client> clients;
    private final List<Vendor> vendors;

    /**
     * Constructs a {@code Event}.
     *
     * @param clients A list of client.
     * @param vendors A list of vendors.
     */
    public Event(
        Name name,
        Description description,
        Date date,
        List<Client> clients,
        List<Vendor> vendors,
        EventId eventId
    ) {
        requireAllNonNull(name, description, date, clients, vendors, eventId);
        AppUtil.checkArgument(isValidEvent(clients), MESSAGE_CONSTRAINTS);
        assert !clients.isEmpty();

        this.name = name;
        this.description = description;
        this.date = date;
        this.clients = new ArrayList<>(clients);
        this.vendors = new ArrayList<>(vendors);
        this.eventId = eventId;
    }

    /**
     * Constructs a {@code Event}.
     * 
     * Alternative constructor that defers the loading of clients and vendors.
     */
    public Event(Name name, Description description, Date date, EventId eventId) {
        requireAllNonNull(name, description, date, eventId);

        this.name = name;
        this.date = date;
        this.description = description;
        this.clients = new ArrayList<>();
        this.vendors = new ArrayList<>();
        this.eventId = eventId;
    }

    /**
     * Returns true if it is a valid event, which means there must
     * be at least one {@code Client} in clients list.
     * @param testList The {@code ArrayList} of {@code Client} at the constructor.
     */
    public static boolean isValidEvent(Collection<Client> testList) {
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
     * Adds a {@code Client} to an {@code Event}
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
     * Adds a {@code Vendor} to an {@code Event}
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
     * Returns the event name.
     * @return A {@code Name} which represents the name of the event.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Returns the event description.
     * @return A {@code Description} which represents the description of the event.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Returns the event date.
     * @return A {@code Date} which represents the date of the event.
     */
    public Date getDate() {
        return this.date;
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
     * Returns the unique event ID.
     */
    public EventId getEventId() {
        return this.eventId;
    }

    /**
     * Return if the two events are the same.
     * 
     * Two events are considered the same if they have the same client and date.
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
