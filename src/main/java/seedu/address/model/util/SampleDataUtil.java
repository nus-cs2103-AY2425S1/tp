package seedu.address.model.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ClientHub;
import seedu.address.model.ReadOnlyClientHub;
import seedu.address.model.clienttype.ClientType;
import seedu.address.model.person.Address;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.reminder.Reminder;

/**
 * Contains utility methods for populating {@code ClientHub} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Ahmad Syuaib"), new Phone("87438807"), new Email("ahmadsyuaib@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getClientTypeSet("Investment"), new Description("Only free on weekends"), Collections.EMPTY_SET),
            new Person(new Name("Jeremy Sim"), new Phone("99272758"), new Email("jeremysim@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getClientTypeSet("Investment", "Healthcare"), new Description("Likes to play basketball"),
                    Collections.EMPTY_SET),
            new Person(new Name("Liu Rui"), new Phone("93210283"), new Email("liurui@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getClientTypeSet("Insurance Plan", "Savings"), new Description("Plans to retire early"),
                    Collections.EMPTY_SET),
            new Person(new Name("Rubin Lin"), new Phone("91031282"), new Email("rubinlin@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getClientTypeSet("Healthcare", "Savings"), new Description("Started a podcast"),
                    Collections.EMPTY_SET),
            new Person(new Name("Harith Nurhisham"), new Phone("92492021"), new Email("harithnurhisham@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getClientTypeSet("Insurance Plan", "Investment"), new Description("Does not like to travel "
                    + "out of the east"), Collections.EMPTY_SET),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getClientTypeSet("Investment"), new Description("Recently married"), Collections.EMPTY_SET),
            new Person(new Name("Sarah Lee"), new Phone("89732145"), new Email("sarahlee@example.com"),
                    new Address("Blk 38 Boon Lay Street 43, #32-03"),
                    getClientTypeSet("Healthcare", "Insurance Plan"),
                    new Description("Takes care of old parents"), Collections.EMPTY_SET),
        };
    }

    public static ReadOnlyClientHub getSampleClientHub() {
        ClientHub sampleCh = new ClientHub();
        for (Person samplePerson : getSamplePersons()) {
            sampleCh.addPerson(samplePerson);
        }
        return sampleCh;
    }

    /**
     * Returns a client type set containing the list of strings given.
     */
    public static Set<ClientType> getClientTypeSet(String... strings) {
        return Arrays.stream(strings)
                .map(ClientType::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a client type set containing the list of reminders given.
     */
    public static Set<Reminder> getReminderSet(Reminder... reminders) {
        return Arrays.stream(reminders)
                .collect(Collectors.toSet());
    }
}
