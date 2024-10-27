package seedu.ddd.testutil.event;

import static seedu.ddd.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_CLIENT_LIST;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DATE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DESCRIPTION;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_NAME;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_VENDOR_LIST;

import java.util.ArrayList;
import java.util.List;

import seedu.ddd.model.common.Name;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    private Name name;
    private Description description;
    private Date date;
    private List<Client> clients;
    private List<Vendor> vendors;
    private EventId eventId;

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = new Name(eventToCopy.getName().fullName);
        description = new Description(eventToCopy.getDescription().description);
        date = new Date(eventToCopy.getDate().toString());
        clients = new ArrayList<>(eventToCopy.getClients());
        vendors = new ArrayList<>(eventToCopy.getVendors());
        eventId = new EventId(eventToCopy.getEventId().eventId);
    }

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        name = DEFAULT_EVENT_NAME;
        description = DEFAULT_EVENT_DESCRIPTION;
        date = DEFAULT_EVENT_DATE;
        clients = new ArrayList<>(DEFAULT_EVENT_CLIENT_LIST);
        vendors = new ArrayList<>(DEFAULT_EVENT_VENDOR_LIST);
        eventId = DEFAULT_EVENT_ID;
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code EventId} of the {@code Event} that we are building.
     */
    public EventBuilder withEventId(int eventId) {
        this.eventId = new EventId(eventId);
        return this;
    }

    /**
     * Sets the {@code Clients} of the {@code Event} that we are building.
     */
    public EventBuilder withClients(List<Client> clients) {
        this.clients = new ArrayList<>(clients);
        return this;
    }

    /**
     * Sets the {@code Clients} of the {@code Event} that we are building.
     */
    public EventBuilder withClients(Client ... clients) {
        assert clients.length > 0;
        this.clients = List.of(clients);
        return this;
    }

    /**
     * Sets the {@code Vendors} of the {@code Event} that we are building.
     */
    public EventBuilder withVendors(List<Vendor> vendors) {
        this.vendors = new ArrayList<>(vendors);
        return this;
    }

    /**
     * Sets the {@code Vendors} of the {@code Event} that we are building.
     */
    public EventBuilder withVendors(Vendor ... vendors) {
        assert vendors.length > 0;
        this.vendors = List.of(vendors);
        return this;
    }

    /**
     * Creates an {@code Event} from the current fields;
     */
    public Event build() {
        requireAllNonNull(name, description, date, clients, vendors, eventId);
        return new Event(name, description, date, clients, vendors, eventId);
    }
}
