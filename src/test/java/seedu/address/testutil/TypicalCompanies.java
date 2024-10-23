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
            .withTags("Salary:High", "WLB:low", "Difficulty:low", "Period: SUMMER-2025").build();
    public static final Company META = new CompanyBuilder().withName("Meta")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("meta@example.com").withPhone("00000002")
            .withTags("Salary:High", "WLB:low", "Difficulty:low", "Period: SUMMER-2025").build();
    public static final Company GRAB = new CompanyBuilder().withName("Grab").withPhone("00000003")
            .withEmail("grab@example.com").withAddress("wall street")
            .withTags("Salary:High", "WLB:low", "Difficulty:low", "Period: SUMMER-2025").build();
    public static final Company SHOPEE = new CompanyBuilder().withName("Shopee").withPhone("00000004")
            .withEmail("shopee@example.com").withAddress("10th street").withTags("bigTech")
            .withTags("Salary:High", "WLB:low", "Difficulty:low", "Period: SUMMER-2025").build();
    public static final Company APPLE = new CompanyBuilder().withName("Apple").withPhone("00000005")
            .withEmail("apple@example.com").withAddress("michegan ave").build();
    public static final Company BYTEDANCE = new CompanyBuilder().withName("ByteDance").withPhone("00000006")
            .withEmail("bytedance@example.com").withAddress("little tokyo").build();
    public static final Company AMAZON = new CompanyBuilder().withName("Amazon").withPhone("00000007")
            .withEmail("amazon@example.com").withAddress("4th street").build();

    // Manually added
    public static final Company HOON = new CompanyBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Company IDA = new CompanyBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Company's details found in {@code CommandTestUtil}
    public static final Company TESLA = new CompanyBuilder().withName(VALID_NAME_TESLA).withPhone(VALID_PHONE_TESLA)
            .withEmail(VALID_EMAIL_TESLA).withAddress(VALID_ADDRESS_TESLA).withTags(VALID_TAG_COMPANY).build();
    public static final Company MICROSOFT = new CompanyBuilder().withName(VALID_NAME_MICROSOFT)
            .withPhone(VALID_PHONE_MICROSOFT)
            .withEmail(VALID_EMAIL_MICROSOFT).withAddress(VALID_ADDRESS_MICROSOFT)
            .withTags(VALID_TAG_BIGTECH, VALID_TAG_COMPANY)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCompanies() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical companies.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Company company : getTypicalPersons()) {
            ab.addCompany(company);
        }
        return ab;
    }

    public static List<Company> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(GOOGLE, META, GRAB, SHOPEE, APPLE, BYTEDANCE, AMAZON));
    }
}
