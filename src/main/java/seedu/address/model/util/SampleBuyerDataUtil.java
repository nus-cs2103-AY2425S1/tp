package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.BuyerList;
import seedu.address.model.ReadOnlyBuyerList;
import seedu.address.model.buyer.Budget;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.Email;
import seedu.address.model.buyer.Name;
import seedu.address.model.buyer.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ReadOnlyBuyerList} with sample data.
 */
public class SampleBuyerDataUtil {
    public static Buyer[] getSampleBuyers() {
        return new Buyer[]{
            new Buyer(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Budget("50,000"),
                    getTagSet("urgent", "friend")),
            new Buyer(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Budget("10000"),
                    getTagSet("relocating", "referred")),
            new Buyer(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Budget("35,000"),
                    getTagSet("upgrading")),
            new Buyer(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Budget("6000000"),
                    getTagSet("relative")),
            new Buyer(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Budget("800000"),
                    getTagSet("first-time")),
            new Buyer(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Budget("9123994"),
                    getTagSet("downsizing"))
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
