package seedu.address.testutil;

import seedu.address.model.company.Company;

/**
 * A utility class containing a list of {@code Company} objects to be used in tests.
 */
public class TypicalCompanies {
    public static final Company NUS = new CompanyBuilder().build();
    private TypicalCompanies() {} // prevents instantiation
}
