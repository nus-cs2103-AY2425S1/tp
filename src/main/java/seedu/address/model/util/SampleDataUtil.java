package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Restaurant[] getSampleRestaurants() {
        return new Restaurant[] {
            new Restaurant(new Name("Swenswen"), new Phone("87438807"), new Email("sowhen@idk.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Rating(null),
                getTagSet("friends", "$$")),
            new Restaurant(new Name("Hai shang lao"), new Phone("99272758"), new Email("ilovexjp@ccpno1.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Rating(1),
                getTagSet("colleagues", "friends", "$$$$")),
            new Restaurant(new Name("Mala Hideaway"), new Phone("93210283"), new Email("buyaola@buyaoma.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Rating(2),
                getTagSet("neighbours", "$")),
            new Restaurant(new Name("Heavens Kitchen"), new Phone("91031282"), new Email("idiotsandwich@gr.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Rating(3),
                getTagSet("family", "$$$")),
            new Restaurant(new Name("McDonkey"), new Phone("92492021"), new Email("parapapapa@mc.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Rating(4),
                getTagSet("classmates")),
            new Restaurant(new Name("Mookantang"), new Phone("92624417"), new Email("moomoo@kt.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Rating(5),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Restaurant sampleRestaurant : getSampleRestaurants()) {
            sampleAb.addRestaurant(sampleRestaurant);
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
