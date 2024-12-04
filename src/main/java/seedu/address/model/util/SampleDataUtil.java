package seedu.address.model.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.Listings;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyListings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.name.Name;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Seller;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Appointment EMPTY_APPOINTMENT = Appointment.EMPTY_APPOINTMENT;
    public static final Person ALEX = new Buyer(new Name("Alex Yeoh"), new Phone("87438807"),
            new Email("alexyeoh@example.com"), getTagSet("friends"), EMPTY_APPOINTMENT);
    public static final Person BERNICE = new Seller(new Name("Bernice Yu"), new Phone("99272758"),
            new Email("berniceyu@example.com"), getTagSet("colleagues", "friends"), EMPTY_APPOINTMENT);
    public static final Person CHARLOTTE = new Buyer(new Name("Charlotte Oliveiro"), new Phone("93210283"),
            new Email("charlotte@example.com"), getTagSet("neighbours"), EMPTY_APPOINTMENT);
    public static final Person DAVID = new Seller(new Name("David Li"), new Phone("91031282"),
            new Email("lidavid@example.com"), getTagSet("family"), EMPTY_APPOINTMENT);
    public static final Person IRFAN = new Buyer(new Name("Irfan Ibrahim"), new Phone("92492021"),
            new Email("irfan@example.com"), getTagSet("classmates"), EMPTY_APPOINTMENT);
    public static final Person ROY = new Seller(new Name("Roy Balakrishnan"), new Phone("92624417"),
            new Email("royb@example.com"), getTagSet("colleagues"), EMPTY_APPOINTMENT);

    public static Person[] getSamplePersons() {
        return new Person[] {
            ALEX, BERNICE, CHARLOTTE, DAVID, IRFAN, ROY
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
     * Generates an array of sample {@code Listing} objects.
     *
     * @return An array of {@code Listing} objects with sample data.
     */
    public static Listing[] sampleListings() {
        return new Listing[] {
            new Listing(new Name("RC4"), new Address("134 Clementi Ave"),
                    new Price("200000", new BigDecimal("2000")), new Area("100"),
                            Region.WEST, BERNICE, new HashSet<>()),
        };
    }

    public static ReadOnlyListings getSampleListings() {
        Listings sampleListings = new Listings();
        for (Listing sampleListing : sampleListings()) {
            sampleListings.addListing(sampleListing);
        }
        return sampleListings;
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
