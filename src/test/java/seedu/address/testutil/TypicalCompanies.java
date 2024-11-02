package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_GOOGLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_NUS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_COMPANY_BARISTA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.company.Company;


/**
 * A utility class containing a list of {@code Company} objects to be used in tests.
 */
public class TypicalCompanies {

    // Manually defined sample companies
    public static final Company GOOGLE = new CompanyBuilder()
            .withName(VALID_COMPANY_NAME_GOOGLE)
            .withAddress("1600 Amphitheatre Parkway, Mountain View, CA 94043")
            .withBillingDate("10")
            .withPhone("6502530000")
            .build();

    public static final Company MICROSOFT = new CompanyBuilder()
            .withName("Microsoft Corporation")
            .withAddress("One Microsoft Way, Redmond, WA 98052")
            .withBillingDate("15")
            .withPhone("4258828080")
            .build();

    public static final Company AMAZON = new CompanyBuilder()
            .withName("Amazon.com, Inc.")
            .withAddress("410 Terry Avenue North, Seattle, WA 98109")
            .withBillingDate("25")
            .withPhone("2062661000")
            .build();

    public static final Company NUS = new CompanyBuilder()
            .withName(VALID_COMPANY_NAME_NUS)
            .withAddress("21 Lower Kent Ridge Rd, Singapore 119077")
            .withBillingDate("5")
            .withPhone("65166666")
            .build();

    public static final Company APPLE = new CompanyBuilder()
            .withName("Apple Inc.")
            .withAddress("1 Apple Park Way, Cupertino, CA 95014")
            .withBillingDate("07")
            .withPhone("4089961010")
            .build();

    public static final Company TESLA = new CompanyBuilder()
            .withName("Tesla, Inc.")
            .withAddress("3500 Deer Creek Rd, Palo Alto, CA 94304")
            .withBillingDate("12")
            .withPhone("6506815000")
            .build();

    public static final Company FACEBOOK = new CompanyBuilder()
            .withName("Meta Platforms, Inc.")
            .withAddress("1 Hacker Way, Menlo Park, CA 94025")
            .withBillingDate("28")
            .withPhone("6505434800")
            .build();

    public static final Company STARBUCKS = new CompanyBuilder()
            .withName(VALID_JOB_COMPANY_BARISTA)
            .withAddress("9 N Buona Vista Dr, #01-13/14 The Metropolis, Singapore 138588")
            .withBillingDate("1")
            .withPhone("6569101200")
            .build();

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

    /**
     * Returns an {@code AddressBook} with all the typical companies.
     */
    public static List<Company> getTypicalCompanies() {
        return new ArrayList<>(Arrays.asList(GOOGLE, MICROSOFT, AMAZON, NUS, APPLE, TESLA, FACEBOOK, STARBUCKS));
    }
}
