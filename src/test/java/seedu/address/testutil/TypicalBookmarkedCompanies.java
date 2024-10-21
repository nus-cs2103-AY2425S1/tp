package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.company.Company;

/**
 * A utility class containing a list of bookmarked {@code Company} objects to be
 * used in tests for bookmarking commands.
 */
public class TypicalBookmarkedCompanies {

    public static final Company GOOGLE_BM = new CompanyBuilder().withName("Google")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("google@example.com")
            .withPhone("00000001")
            .withTags("google").withIsBookmark(true).build();
    public static final Company META_BM = new CompanyBuilder().withName("Meta")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("meta@example.com").withPhone("00000002")
            .withTags("bigTech", "facebook").withIsBookmark(true).build();
    public static final Company GRAB_BM = new CompanyBuilder().withName("Grab").withPhone("00000003")
            .withEmail("grab@example.com").withAddress("wall street").withIsBookmark(true).build();
    public static final Company SHOPEE_BM = new CompanyBuilder().withName("Shopee").withPhone("00000004")
            .withEmail("shopee@example.com").withAddress("10th street").withTags("bigTech").withIsBookmark(true)
            .build();
    public static final Company APPLE_BM = new CompanyBuilder().withName("Apple").withPhone("00000005")
            .withEmail("apple@example.com").withAddress("michegan ave").withIsBookmark(true).build();
    public static final Company BYTEDANCE_BM = new CompanyBuilder().withName("ByteDance").withPhone("00000006")
            .withEmail("bytedance@example.com").withAddress("little tokyo").withIsBookmark(true).build();
    public static final Company AMAZON_BM = new CompanyBuilder().withName("Amazon").withPhone("00000007")
            .withEmail("amazon@example.com").withAddress("4th street").withIsBookmark(true).build();

    private TypicalBookmarkedCompanies() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical bookmarked companies.
     */
    public static AddressBook getTypicalBookmarkedAddressBook() {
        AddressBook ab = new AddressBook();
        for (Company company : getTypicalBookmarkedCompanies()) {
            ab.addCompany(company);
        }
        return ab;
    }

    public static List<Company> getTypicalBookmarkedCompanies() {
        return new ArrayList<>(Arrays.asList(GOOGLE_BM, META_BM, GRAB_BM, SHOPEE_BM, APPLE_BM, BYTEDANCE_BM,
                AMAZON_BM));
    }
}
