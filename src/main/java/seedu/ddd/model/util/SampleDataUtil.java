package seedu.ddd.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.ddd.model.AddressBook;
import seedu.ddd.model.ReadOnlyAddressBook;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    // Client fields
    public static final String SAMPLE_CLIENT_NAME = "Amy Bee";
    public static final String SAMPLE_CLIENT_PHONE = "12345678";
    public static final String SAMPLE_CLIENT_EMAIL = "amy@example.com";
    public static final String SAMPLE_CLIENT_ADDRESS = "Block 312, Amy Street 1";
    public static final String SAMPLE_CLIENT_ID = "0";

    // Vendor fields
    public static final String SAMPLE_VENDOR_NAME = "Bob Choo";
    public static final String SAMPLE_VENDOR_PHONE = "87654321";
    public static final String SAMPLE_VENDOR_EMAIL = "bob@example.com";
    public static final String SAMPLE_VENDOR_ADDRESS = "Block 123, Bobby Street 3";
    public static final String SAMPLE_VENDOR_SERVICE = "Catering";
    public static final String SAMPLE_VENDOR_ID = "1";

    // Common contact fields
    public static final String SAMPLE_TAG_1 = "budget";
    public static final String SAMPLE_TAG_2 = "vegetarian";

    // Event fields
    public static final String SAMPLE_EVENT_NAME = "Sample Wedding";
    public static final String SAMPLE_EVENT_DESCRIPTION = "Wedding reception";
    public static final String SAMPLE_EVENT_DATE = "2000-01-01";
    public static final String SAMPLE_EVENT_ID = "0";

    private static final int SAMPLE_NEXT_CONTACT_ID = 2;
    private static final int SAMPLE_NEXT_EVENT_ID = 1;

    public static ReadOnlyAddressBook getSampleAddressBook() {
        Client sampleClient = new Client(
            new Name(SAMPLE_CLIENT_NAME),
            new Phone(SAMPLE_CLIENT_PHONE),
            new Email(SAMPLE_CLIENT_EMAIL),
            new Address(SAMPLE_CLIENT_ADDRESS),
            getTagSet(SAMPLE_TAG_1, SAMPLE_TAG_2),
            new ContactId(SAMPLE_CLIENT_ID)
        );
        Vendor sampleVendor = new Vendor(
            new Name(SAMPLE_VENDOR_NAME),
            new Phone(SAMPLE_VENDOR_PHONE),
            new Email(SAMPLE_VENDOR_EMAIL),
            new Address(SAMPLE_VENDOR_ADDRESS),
            new Service(SAMPLE_VENDOR_SERVICE),
            getTagSet(SAMPLE_TAG_1),
            new ContactId(SAMPLE_VENDOR_ID)
        );

        Event sampleEvent = new Event(
            new Name(SAMPLE_EVENT_NAME),
            new Description(SAMPLE_EVENT_DESCRIPTION),
            new Date(SAMPLE_EVENT_DATE),
            List.of(sampleClient),
            List.of(sampleVendor),
            new EventId(SAMPLE_EVENT_ID)
        );

        AddressBook sampleAb = new AddressBook();
        sampleAb.addEvent(sampleEvent);

        // contacts added only after event has been created
        List<Contact> contacts = List.of(sampleClient, sampleVendor);
        for (Contact contact : contacts) {
            contact.addEvent(sampleEvent);
            sampleAb.addContact(contact);
        }

        AddressBook.setNextContactId(SAMPLE_NEXT_CONTACT_ID);
        AddressBook.setNextEventId(SAMPLE_NEXT_EVENT_ID);
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Set<EventId> getEventIdSet(int[] eventIds) {
        return Arrays.stream(eventIds)
                .mapToObj(EventId::new)
                .collect(Collectors.toSet());
    }
}
