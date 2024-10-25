package seedu.ddd.testutil;

import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_PHONE_AMY;

import java.util.HashSet;
import java.util.Set;

import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.event.common.EventId;
import seedu.ddd.model.tag.Tag;
import seedu.ddd.model.util.SampleDataUtil;

/**
 * A utility class to help with building Client objects.
 */
public class ClientBuilder {

    public static final String DEFAULT_NAME = VALID_NAME_AMY;
    public static final String DEFAULT_PHONE = VALID_PHONE_AMY;
    public static final String DEFAULT_EMAIL = VALID_EMAIL_AMY;
    public static final String DEFAULT_ADDRESS = VALID_ADDRESS_AMY;
    public static final String DEFAULT_ID = VALID_ID_AMY;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private ContactId contactId;
    private Set<EventId> eventIds;

    /**
     * Creates a {@code ClientBuilder} with the default details.
     */
    public ClientBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        contactId = new ContactId(DEFAULT_ID);
        eventIds = new HashSet<>();
    }

    /**
     * Initializes the ClientBuilder with the data of {@code clientToCopy}.
     */
    public ClientBuilder(Client clientToCopy) {
        name = clientToCopy.getName();
        phone = clientToCopy.getPhone();
        email = clientToCopy.getEmail();
        address = clientToCopy.getAddress();
        tags = new HashSet<>(clientToCopy.getTags());
        contactId = clientToCopy.getId();
        eventIds = new HashSet<>(clientToCopy.getEventIds());
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
     * Sets the {@code EventIds} associated to the {@code Client} that we are building.
     */
    public ClientBuilder withEventIds(int ... eventIds) {
        this.eventIds = SampleDataUtil.getEventIdSet(eventIds);
        return this;
    }

    public Client build() {
        return new Client(name, phone, email, address, tags, contactId);
    }

}
