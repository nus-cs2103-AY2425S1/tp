package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.company.Address;
import seedu.address.model.company.ApplicationStatus;
import seedu.address.model.company.Bookmark;
import seedu.address.model.company.CareerPageUrl;
import seedu.address.model.company.Company;
import seedu.address.model.company.Email;
import seedu.address.model.company.Name;
import seedu.address.model.company.Phone;
import seedu.address.model.company.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagBuilder;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Bookmark DEFAULT_BOOKMARK = new Bookmark(false);
    public static final ApplicationStatus DEFAULT_APPLICATION_STATUS = new ApplicationStatus("");
    public static final Remark DEFAULT_REMARK = new Remark("remark");
    public static Company[] getSampleCompanies() {
        return new Company[] {
            new Company(new Name("Google"), new Phone("00000001"), new Email("google@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new CareerPageUrl("www.google-careers.com"),
                    DEFAULT_APPLICATION_STATUS, getTagSet("Salary: Low", "WLB: High"),
                    DEFAULT_BOOKMARK, DEFAULT_REMARK),
            new Company(new Name("Grab"), new Phone("00000002"), new Email("grab@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new CareerPageUrl("www.grab-jobs.com"),
                    DEFAULT_APPLICATION_STATUS, getTagSet("bigTech", "transport"), DEFAULT_BOOKMARK, DEFAULT_REMARK),
            new Company(new Name("Shopee"), new Phone("00000003"), new Email("shopee@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new CareerPageUrl("www.shopee-jobs.com"),
                    DEFAULT_APPLICATION_STATUS, getTagSet("shopping"), DEFAULT_BOOKMARK, DEFAULT_REMARK),
            new Company(new Name("Meta"), new Phone("00000004"), new Email("meta@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new CareerPageUrl("www.meta-jobs.com"),
                    DEFAULT_APPLICATION_STATUS, getTagSet("bigTech"), DEFAULT_BOOKMARK, DEFAULT_REMARK),
            new Company(new Name("Apple"), new Phone("00000005"), new Email("apple@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new CareerPageUrl("www.apple-careers.com"),
                    DEFAULT_APPLICATION_STATUS, getTagSet("iphone"), DEFAULT_BOOKMARK, DEFAULT_REMARK),
            new Company(new Name("ByteDance"), new Phone("00000006"), new Email("bytedance@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new CareerPageUrl("www.bytedance-jobs.com"),
                    DEFAULT_APPLICATION_STATUS, getTagSet("douyin"), DEFAULT_BOOKMARK, DEFAULT_REMARK)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Company sampleCompany : getSampleCompanies()) {
            sampleAb.addCompany(sampleCompany);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(tag -> new TagBuilder().build(tagFormatter(tag))) // Use TagBuilder
                .collect(Collectors.toSet());
    }

    /**
     * Helps Formats the Strings in getTagSet to a buildable string input
     */
    private static String tagFormatter(String tagValue) {
        String[] parts = tagValue.split(":");
        String key = parts[0].trim();
        //Remove the empty space at index 0, and replace empty space with _ to simulate the tagging command
        String value = parts.length > 1 ? "_" + parts[1].stripLeading().replace("-", "_") : "";
        return key + value;
    }
}
