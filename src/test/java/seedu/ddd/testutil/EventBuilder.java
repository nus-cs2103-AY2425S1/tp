package seedu.ddd.testutil;

import static seedu.ddd.logic.commands.CommandTestUtil.VALID_DESCRIPTION_WEDDING_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EVENT_ID_WEDDING_AMY;

import java.util.ArrayList;
import java.util.List;

import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final String DEFAULT_DESCRIPTION = VALID_DESCRIPTION_WEDDING_AMY;
    public static final String DEFAULT_EVENT_ID = VALID_EVENT_ID_WEDDING_AMY;
    private Description description;
    private EventId eventId;
    private List<Client> clients;
    private List<Vendor> vendors;

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        eventId = new EventId(DEFAULT_EVENT_ID);
        clients = new ArrayList<>();
        clients.add(new ClientBuilder().build());
        vendors = new ArrayList<>();
        vendors.add(new VendorBuilder().build());
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        description = eventToCopy.getDescription();
        eventId = eventToCopy.getEventId();
        clients = new ArrayList<>();
        clients.addAll(eventToCopy.getClients());
        vendors = new ArrayList<>();
        vendors.addAll(eventToCopy.getVendors());
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
        this.clients = clients;
        return this;
    }

    /**
     * Sets the {@code Vendors} of the {@code Event} that we are building.
     */
    public EventBuilder withVendors(List<Vendor> vendors) {
        this.vendors = vendors;
        return this;
    }
    public Event build() {
        return new Event(clients, vendors, description, eventId);
    }
}
