package seedu.address.model.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingName;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final Person ALEX = new Person(new PersonId("969d5233-5ae8-4c56-97b7-b25737afa01a"),
            new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
            new Address("Blk 30 Geylang Street 29, #06-40"),
            getTagSet("photographer", "videographer"));
    public static final Person BERNICE = new Person(new PersonId("e44ad460-6609-47b6-a96f-1fc90725afe9"),
            new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            getTagSet("caterer"));
    public static final Person CHARLOTTE = new Person(new PersonId("4c744692-3879-4d6d-a770-71ed8d8077b3"),
            new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
            new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
            getTagSet("musician"));
    public static final Person DAVID = new Person(new PersonId("81e742e0-05d5-40ba-8b62-6e419c8773a8"),
            new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
            new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            getTagSet("florist"));
    public static final Person IRFAN = new Person(new PersonId("1ac39835-5204-4e42-8710-494955702541"),
            new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
            new Address("Blk 47 Tampines Street 20, #17-35"),
            getTagSet("musician"));
    public static final Person ROY = new Person(new PersonId("2626be32-8db1-429c-a375-76336055ae6a"),
            new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
            new Address("Blk 45 Aljunied Street 85, #11-31"),
            getTagSet("photographer", "videographer"));
    public static final Wedding WEDDING1 = new Wedding(new WeddingName("John and Jane Wedding"),
            new WeddingDate(LocalDate.parse("12/12/2025", FORMATTER)),
            new ArrayList<>(List.of(ALEX.getId(), BERNICE.getId(), CHARLOTTE.getId(), DAVID.getId())));
    public static final Wedding WEDDING2 = new Wedding(new WeddingName("Sean and Emma Wedding"),
            new WeddingDate(LocalDate.parse("14/01/2025", FORMATTER)),
            new ArrayList<>(List.of(BERNICE.getId(), DAVID.getId(), IRFAN.getId(), ROY.getId())));
    public static Person[] getSamplePersons() {
        return new Person[] {
            ALEX, BERNICE, CHARLOTTE, DAVID, IRFAN, ROY
        };
    }
    public static Wedding[] getSampleWeddings() {
        return new Wedding[] {
            WEDDING1, WEDDING2
        };
    }
    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Wedding sampleWedding : getSampleWeddings()) {
            sampleAb.addWedding(sampleWedding);
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

}
