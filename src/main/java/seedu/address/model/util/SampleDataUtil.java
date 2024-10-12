package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ClientBook;
import seedu.address.model.PropertyBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyClientBook;
import seedu.address.model.ReadOnlyPropertyBook;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Client;
import seedu.address.model.client.Seller;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Unit;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
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

    public static Client[] getSampleProperties() {
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
        for (Client sampleClient : getSampleProperties()) {
            sampleAb.addClient(sampleClient);
        }
        return sampleAb;
    }

    public static Property[] getSampleProperty() {
        return new Property[] {
                new Property(new PostalCode("123456"), new Unit("11-11")),
                new Property(new PostalCode("123457"), new Unit("00-00")),
        };
    }

    public static ReadOnlyPropertyBook getSamplePropertyBook() {
        PropertyBook sampleAb = new PropertyBook();
        for (Property sampleProperty : getSampleProperty()) {
            sampleAb.addProperty(sampleProperty);
        }
        return sampleAb;
    }
}
