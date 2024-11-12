package seedu.internbuddy.testutil;

import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_EMAIL_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_EMAIL_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_NAME_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_PHONE_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_STATUS_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_STATUS_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_TAG_SOFTWARE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.internbuddy.testutil.TypicalApplications.DS_APPLICATION;
import static seedu.internbuddy.testutil.TypicalApplications.SWE_APPLICATION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.internbuddy.model.AddressBook;
import seedu.internbuddy.model.company.Company;

/**
 * A utility class containing a list of {@code Company} objects to be used in tests.
 */
public class TypicalCompanies {

    public static final Company APPLE_FAVOURITED = new CompanyBuilder().withName("Apple").withPhone("4089961010")
            .withEmail("contact@apple.com").withAddress("1 Apple Park Way, Cupertino, CA")
            .withIsFavourite(true).build();
    public static final Company AMAZON = new CompanyBuilder().withName("Amazon").withPhone("2062661000")
            .withEmail("contact@amazon.com").withAddress("410 Terry Ave N, Seattle, WA")
            .withTags("ecommerce", "cloud")
            .withStatus("INTERESTED")
            .withApplications(SWE_APPLICATION, DS_APPLICATION)
            .build();
    public static final Company FACEBOOK = new CompanyBuilder().withName("Facebook").withPhone("6505434800")
            .withEmail("contact@facebook.com").withAddress("1 Hacker Way, Menlo Park, CA").build();
    public static final Company TESLA = new CompanyBuilder().withName("Tesla").withPhone("6506815000")
            .withEmail("contact@tesla.com").withAddress("3500 Deer Creek Road, Palo Alto, CA").build();
    public static final Company NETFLIX = new CompanyBuilder().withName("Netflix").withPhone("4085403700")
            .withEmail("contact@netflix.com").withAddress("100 Winchester Circle, Los Gatos, CA").build();
    public static final Company SAP = new CompanyBuilder().withName("SAP").withPhone("6503407500")
            .withEmail("SAP@career.com").withAddress("3410 Hillview Ave, Palo Alto, CA")
            .withStatus("CLOSED").build();

    // Manually added
    public static final Company SPOTIFY = new CompanyBuilder().withName("Spotify").withPhone("9175653894")
            .withEmail("contact@spotify.com").withAddress("150 Greenwich Street, New York, NY").build();
    public static final Company TWITTER = new CompanyBuilder().withName("Twitter").withPhone("4152229670")
            .withEmail("contact@twitter.com").withAddress("1355 Market Street, San Francisco, CA").build();

    // Manually added - Company's details found in {@code CommandTestUtil}
    public static final Company GOOGLE = new CompanyBuilder().withName(VALID_NAME_GOOGLE).withPhone(VALID_PHONE_GOOGLE)
            .withEmail(VALID_EMAIL_GOOGLE).withAddress(VALID_ADDRESS_GOOGLE).withTags(VALID_TAG_TECH)
            .withStatus(VALID_STATUS_GOOGLE).withApplications(SWE_APPLICATION).build();
    public static final Company MICROSOFT = new CompanyBuilder().withName(VALID_NAME_MICROSOFT)
            .withPhone(VALID_PHONE_MICROSOFT).withEmail(VALID_EMAIL_MICROSOFT)
            .withAddress(VALID_ADDRESS_MICROSOFT).withTags(VALID_TAG_SOFTWARE, VALID_TAG_TECH)
            .withStatus(VALID_STATUS_MICROSOFT).build();

    public static final String KEYWORD_MATCHING_SPOTIFY = "Spotify"; // A keyword that matches SPOTIFY

    private TypicalCompanies() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical companies.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Company company : getTypicalCompanies()) {
            ab.addCompany(company);
        }
        return ab;
    }

    public static List<Company> getTypicalCompanies() {
        return new ArrayList<>(Arrays.asList(APPLE_FAVOURITED, AMAZON, FACEBOOK, TESLA, NETFLIX, SAP));
    }
}
