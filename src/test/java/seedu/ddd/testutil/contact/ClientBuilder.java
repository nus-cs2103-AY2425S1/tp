package seedu.ddd.testutil.contact;

import static seedu.ddd.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_CLIENT_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_CLIENT_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_CLIENT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_CLIENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_CLIENT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_CLIENT_TAGS;

import java.util.HashSet;
import java.util.Set;

import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.event.common.EventId;
import seedu.ddd.model.util.SampleDataUtil;

/**
 * A utility class to help with building Client objects.
 */
public class ClientBuilder {

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private ContactId contactId;
    private Set<EventId> eventIds;

    /**
     * Initializes the ClientBuilder with the data of {@code clientToCopy}.
     */
    public ClientBuilder(Client clientToCopy) {
        name = new Name(clientToCopy.getName().fullName);
        phone = new Phone(clientToCopy.getPhone().value);
        email = new Email(clientToCopy.getEmail().value);
        address = new Address(clientToCopy.getAddress().value);
        tags = new HashSet<>(clientToCopy.getTags());
        contactId = new ContactId(clientToCopy.getId().contactId);
    }

    /**
     * Creates a {@code ClientBuilder} with the default details.
     */
    public ClientBuilder() {
        name = DEFAULT_CLIENT_NAME;
        phone = DEFAULT_CLIENT_PHONE;
        email = DEFAULT_CLIENT_EMAIL;
        address = DEFAULT_CLIENT_ADDRESS;
        tags = new HashSet<>(DEFAULT_CLIENT_TAGS);
        contactId = DEFAULT_CLIENT_ID;
        eventIds = new HashSet<>();
    }

    /**
     * Sets the {@code Name} of the {@code Client} that we are building.
     */
    public ClientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Client} that we are building.
     */
    public ClientBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Client} that we are building.
     */
    public ClientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Client} that we are building.
     */
    public ClientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Client} that we are building.
     */
    public ClientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code ContactId} of the {@code Client} that we are building.
     */
    public ClientBuilder withId(int id) {
        this.contactId = new ContactId(id);
        return this;
    }

    /**
     * Sets the {@code ContactId} of the {@code Client} that we are building.
     */
    public ClientBuilder withId(String id) {
        this.contactId = new ContactId(id);
        return this;
    }

    /**
     * Sets the {@code EventIds} associated to the {@code Client} that we are building.
     */
    public ClientBuilder withEventIds(int ... eventIds) {
        this.eventIds = SampleDataUtil.getEventIdSet(eventIds);
        return this;
    }

    /**
     * Creates a {@code Client} from the current fields;
     */
    public Client build() {
        requireAllNonNull(name, phone, email, address, tags, contactId);
        return new Client(name, phone, email, address, tags, contactId);
    }

}
