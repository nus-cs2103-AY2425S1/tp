package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ClientBook;
import seedu.address.model.MeetingBook;
import seedu.address.model.PropertyBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyClientBook;
import seedu.address.model.ReadOnlyMeetingBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Client;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Seller;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new seedu.address.model.person.Name("Alex Yeoh"),
                    new seedu.address.model.person.Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new seedu.address.model.person.Name("Bernice Yu"),
                    new seedu.address.model.person.Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new seedu.address.model.person.Name("Charlotte Oliveiro"),
                    new seedu.address.model.person.Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new seedu.address.model.person.Name("David Li"),
                    new seedu.address.model.person.Phone("87438801"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new seedu.address.model.person.Name("Irfan Ibrahim"),
                    new seedu.address.model.person.Phone("87438804"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new seedu.address.model.person.Name("Roy Balakrishnan"),
                    new seedu.address.model.person.Phone("87438802"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
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

    public static Client[] getSampleClients() {
        return new Client[] {
            new Buyer(new seedu.address.model.client.Name("Alex Yeoh"),
                    new seedu.address.model.client.Phone("87438807"),
                    new seedu.address.model.client.Email("alexyeoh@example.com")),
            new Seller(new seedu.address.model.client.Name("Bernice Yu"),
                    new seedu.address.model.client.Phone("99272758"),
                    new seedu.address.model.client.Email("berniceyu@example.com")),
            new Buyer(new seedu.address.model.client.Name("Charlotte Oliveiro"),
                    new seedu.address.model.client.Phone("93210283"),
                    new seedu.address.model.client.Email("charlotte@example.com")),
            new Seller(new seedu.address.model.client.Name("David Li"),
                    new seedu.address.model.client.Phone("91031282"),
                    new seedu.address.model.client.Email("lidavid@example.com"))
        };
    }
    public static ReadOnlyClientBook getSampleClientBook() {
        ClientBook sampleAb = new ClientBook();
        for (Client sampleClient : getSampleClients()) {
            sampleAb.addClient(sampleClient);
        }
        return sampleAb;
    }

    public static Property[] getSampleProperty() {
        return new Property[] {
            new Property(new PostalCode("123456"), new Unit("11-11"), new Type("HDB"),
                    new Ask("50000"), new Bid("10000")),
            new Property(new PostalCode("123457"), new Unit("00-00"), new Type("landed"),
                    new Ask("50000"), new Bid("10000")),
            new Property(new PostalCode("776688"), new Unit("01-00"), new Type("LANDED"),
                    new Ask("50000"), new Bid("10000")),
            new Property(new PostalCode("567333"), new Unit("01-00"), new Type("CONDO"),
                    new Ask("50000"), new Bid("10000")),
        };
    }

    public static ReadOnlyPropertyBook getSamplePropertyBook() {
        PropertyBook sampleAb = new PropertyBook();
        for (Property sampleProperty : getSampleProperty()) {
            sampleAb.addProperty(sampleProperty);
        }
        return sampleAb;
    }

    public static Meeting[] getSampleMeetings() {
        return new Meeting[] {
            new Meeting(new MeetingTitle("Meeting 1"), new MeetingDate("01-01-2024"), new Phone("91234567"),
                    new Phone("81234567"),
                    new Type("HDB"), new PostalCode("111111")),
            new Meeting(new MeetingTitle("Meeting 2"), new MeetingDate("02-01-2024"), new Phone("91234567"),
                    new Phone("81234567"),
                    new Type("HDB"), new PostalCode("111111")),
            new Meeting(new MeetingTitle("Meeting 3"), new MeetingDate("03-01-2024"),
                    new Phone("81234567"), new Phone("91234567"),
                    new Type("HDB"), new PostalCode("111111")),
        };
    }

    public static ReadOnlyMeetingBook getSampleMeetingBook() {
        MeetingBook sampleMb = new MeetingBook();
        for (Meeting sampleMeeting : getSampleMeetings()) {
            sampleMb.addMeeting(sampleMeeting);
        }
        return sampleMb;
    }
}
