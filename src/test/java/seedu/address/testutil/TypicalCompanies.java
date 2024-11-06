package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BIGTECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPANY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.company.Company;

/**
 * A utility class containing a list of {@code Company} objects to be used in
 * tests.
 */
public class TypicalCompanies {

    public static final Company GOOGLE = new CompanyBuilder().withName("Google")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("google@example.com")
            .withPhone("00000001")
            .withCareerPageUrl("www.google-careers.com").withRemark("Leading tech company")
            .withTags("Salary:High", "WLB:low", "Difficulty:low", "Period: SUMMER-2025").build();
    public static final Company META = new CompanyBuilder().withName("Meta")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("meta@example.com").withPhone("00000002")
            .withCareerPageUrl("www.meta-careers.com").withRemark("Social media giant")
            .withTags("Salary:High", "WLB:low", "Difficulty:low", "Period: SUMMER-2025").build();
    public static final Company GRAB = new CompanyBuilder().withName("Grab").withPhone("00000003")
            .withEmail("grab@example.com").withAddress("wall street")
            .withCareerPageUrl("www.grab-jobs.com").withRemark("Leading ride-hailing service in Southeast Asia")
            .withTags("Salary:High", "WLB:low", "Difficulty:low", "Period: SUMMER-2025").build();
    public static final Company SHOPEE = new CompanyBuilder().withName("Shopee").withPhone("00000004")
            .withEmail("shopee@example.com").withAddress("10th street").withTags("bigTech")
            .withCareerPageUrl("www.shopee-careers.com").withRemark("E-commerce platform")
            .withTags("Salary:High", "WLB:low", "Difficulty:low", "Period: SUMMER-2025").build();
    public static final Company APPLE = new CompanyBuilder().withName("Apple").withPhone("00000005")
            .withEmail("apple@example.com").withAddress("Michigan Ave")
            .withCareerPageUrl("www.apple-careers.com").withRemark("Creators of the iPhone")
            .build();

    public static final Company BYTEDANCE = new CompanyBuilder().withName("ByteDance").withPhone("00000006")
            .withEmail("bytedance@example.com").withAddress("Little Tokyo")
            .withCareerPageUrl("www.bytedance-jobs.com").withRemark("TikTok parent company")
            .build();

    public static final Company AMAZON = new CompanyBuilder().withName("Amazon").withPhone("00000007")
            .withEmail("amazon@example.com").withAddress("4th Street")
            .withCareerPageUrl("www.amazon-jobs.com").withRemark("E-commerce and cloud computing leader")
            .build();

    // Manually added
    public static final Company HOON = new CompanyBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("Little India")
            .withCareerPageUrl("www.hoon-careers.com").withRemark("Custom remark for Hoon")
            .build();

    public static final Company IDA = new CompanyBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("Chicago Ave")
            .withCareerPageUrl("www.ida-careers.com").withRemark("Custom remark for Ida")
            .build();

    // Manually added - Company's details found in {@code CommandTestUtil}
    public static final Company TESLA = new CompanyBuilder().withName(VALID_NAME_TESLA)
            .withPhone(VALID_PHONE_TESLA).withEmail(VALID_EMAIL_TESLA)
            .withAddress(VALID_ADDRESS_TESLA).withTags(VALID_TAG_COMPANY)
            .withCareerPageUrl("www.tesla-careers.com").withRemark("Electric vehicle leader")
            .build();

    public static final Company MICROSOFT = new CompanyBuilder().withName(VALID_NAME_MICROSOFT)
            .withPhone(VALID_PHONE_MICROSOFT).withEmail(VALID_EMAIL_MICROSOFT)
            .withAddress(VALID_ADDRESS_MICROSOFT).withTags(VALID_TAG_BIGTECH, VALID_TAG_COMPANY)
            .withCareerPageUrl("www.microsoft-careers.com").withRemark("Software giant")
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCompanies() {
    } // prevents instantiation

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
        return new ArrayList<>(Arrays.asList(GOOGLE, META, GRAB, SHOPEE, APPLE, BYTEDANCE, AMAZON));
    }
}
