package seedu.address.model.util;

import seedu.address.model.ClientBook;
import seedu.address.model.MeetingBook;
import seedu.address.model.PropertyBook;
import seedu.address.model.ReadOnlyClientBook;
import seedu.address.model.ReadOnlyMeetingBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Seller;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingDate;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Client[] getSampleClients() {
        return new Client[] {
            new Buyer(new Name("Alex Yeoh"),
                    new Phone("87438807"),
                    new Email("alexyeoh@example.com")),
            new Seller(new Name("Bernice Yu"),
                    new Phone("99272758"),
                    new Email("berniceyu@example.com")),
            new Buyer(new Name("Charlotte Oliveiro"),
                    new Phone("93210283"),
                    new Email("charlotte@example.com")),
            new Seller(new Name("David Li"),
                    new Phone("91031282"),
                    new Email("lidavid@example.com"))
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
