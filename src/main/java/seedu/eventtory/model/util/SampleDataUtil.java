package seedu.eventtory.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.eventtory.model.AddressBook;
import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.ReadOnlyAddressBook;
import seedu.eventtory.model.ReadOnlyEventTory;
import seedu.eventtory.model.commons.name.Name;
import seedu.eventtory.model.commons.tag.Tag;
import seedu.eventtory.model.vendor.Description;
import seedu.eventtory.model.vendor.Phone;
import seedu.eventtory.model.vendor.Vendor;

/**
 * Contains utility methods for populating {@code EventTory} with sample data.
 */
public class SampleDataUtil {
    public static Vendor[] getSampleVendors() {
        return new Vendor[] {
            new Vendor(new Name("Alex Yeoh"), new Phone("87438807"),
                new Description("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Vendor(new Name("Bernice Yu"), new Phone("99272758"),
                new Description("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Vendor(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Description("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Vendor(new Name("David Li"), new Phone("91031282"),
                new Description("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Vendor(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Description("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Vendor(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Description("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyEventTory getSampleEventTory() {
        EventTory sampleEt = new EventTory();
        for (Vendor sampleVendor : getSampleVendors()) {
            sampleEt.addVendor(sampleVendor);
        }
        return sampleEt;
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
