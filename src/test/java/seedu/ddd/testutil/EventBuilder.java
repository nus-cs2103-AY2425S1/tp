package seedu.ddd.testutil;

import static seedu.ddd.logic.commands.CommandTestUtil.VALID_DESCRIPTION_WEDDING_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EVENT_ID_WEDDING_AMY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final Description DEFAULT_DESCRIPTION = new Description(VALID_DESCRIPTION_WEDDING_AMY);
    public static final Description DEFAULT_DESCRIPTION_TWO = new Description(VALID_DESCRIPTION_WEDDING_AMY);
    public static final EventId DEFAULT_EVENT_ID = new EventId(VALID_EVENT_ID_WEDDING_AMY);
    public static final List<Client> DEFAULT_CLIENT_LIST =
            new ArrayList<>(Collections.singletonList(new ClientBuilder().build()));
    public static final List<Vendor> DEFAULT_VENDOR_LIST =
            new ArrayList<>(Collections.singletonList(new VendorBuilder().build()));
    public static final Set<Client> DEFAULT_CLIENT_SET =
            new HashSet<>(Collections.singletonList(new ClientBuilder().build()));

    public static final Set<Vendor> DEFAULT_VENDOR_SET =
            new HashSet<>(Collections.singletonList(new VendorBuilder().build()));
    public static final Set<ContactId> DEFAULT_CLIENT_CONTACT_ID_SET =
            new HashSet<>(Collections.singletonList(new ContactId(ClientBuilder.DEFAULT_ID)));

    public static final Set<ContactId> DEFAULT_VENDOR_CONTACT_ID_SET =
            new HashSet<>(Collections.singletonList(new ContactId(VendorBuilder.DEFAULT_ID)));
    private Description description = DEFAULT_DESCRIPTION;
    private EventId eventId = DEFAULT_EVENT_ID;
    private List<Client> clients = new ArrayList<>(DEFAULT_CLIENT_LIST);
    private List<Vendor> vendors = new ArrayList<>(DEFAULT_VENDOR_LIST);

    /**
     * Creates a {@code EventBuilder} with the default details.
     */
    public EventBuilder() {
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
        this.clients = new ArrayList<>();
        this.clients.addAll(clients);
        return this;
    }

    /**
     * Sets the {@code Vendors} of the {@code Event} that we are building.
     */
    public EventBuilder withVendors(List<Vendor> vendors) {
        this.vendors = new ArrayList<>();
        this.vendors.addAll(vendors);
        return this;
    }
    public Event build() {
        return new Event(clients, vendors, description, eventId);
    }
}
