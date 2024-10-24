package seedu.ddd.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.ddd.model.AddressBook;
import seedu.ddd.model.ReadOnlyAddressBook;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.client.Date;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.ContactId;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Name;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.model.contact.vendor.Service;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.Event;
import seedu.ddd.model.event.common.EventId;
import seedu.ddd.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static final Client sampleCLient = new Client(new Name("A"), new Phone("12345678"), new Email("a@a.com"),
            new Address("A"), new Date("01 Jan 2000"), getTagSet("another"), new ContactId(0));
    private static final Vendor sampleVendor = new Vendor(new Name("B"), new Phone("12345678"), new Email("b@b.com"),
            new Address("B"), new Service("Catering"), getTagSet("test"), new ContactId(1));
    private static final Event sampleEvent = new Event(new Description("Sample Event"), new EventId(0));
    private static final Contact[] sampleContacts = new Contact[] {
        sampleCLient, sampleVendor
    };
    private static final int sampleNextContactId = sampleContacts.length;
    private static final int sampleNextEventId = 1;

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : sampleContacts) {
            sampleAb.addContact(sampleContact);
        }
        sampleAb.addEvent(sampleEvent);
        sampleEvent.addVendor(sampleVendor);
        sampleEvent.addClient(sampleCLient);
        AddressBook.setNextContactId(sampleNextContactId);
        AddressBook.setNextEventId(sampleNextEventId);
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
