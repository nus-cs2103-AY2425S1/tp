package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.BuyerList;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.buyer.Address;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.BuyerType;
import seedu.address.model.buyer.Email;
import seedu.address.model.buyer.Name;
import seedu.address.model.buyer.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ReadOnlyBuyerList} with sample data.
 */
public class SampleBuyerDataUtil {
    public static Buyer[] getSampleBuyers() {
        return new Buyer[] {
            new Buyer(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                new BuyerType("buyer"),
                getTagSet("friends")),
            new Buyer(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new BuyerType("seller"),
                getTagSet("colleagues", "friends")),
            new Buyer(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new BuyerType("buyer"),
                getTagSet("neighbours")),
            new Buyer(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new BuyerType("buyer"),
                getTagSet("family")),
            new Buyer(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                new BuyerType("seller"),
                getTagSet("classmates")),
            new Buyer(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                new BuyerType("buyer"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyBuyerList getSampleBuyerList() {
        BuyerList sampleAb = new BuyerList();
        for (Buyer sampleBuyer : getSampleBuyers()) {
            sampleAb.addBuyer(sampleBuyer);
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
