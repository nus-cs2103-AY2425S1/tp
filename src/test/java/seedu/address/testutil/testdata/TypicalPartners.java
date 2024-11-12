package seedu.address.testutil.testdata;

import seedu.address.model.person.Partner;
import seedu.address.testutil.PartnerBuilder;

/**
 * A utility class containing a list of {@code Partner} objects to be used in tests.
 */
public class TypicalPartners {
    public static final Partner PARTNER_JOHN_UNTIL_20231231 = new PartnerBuilder()
            .withName("John Smith")
            .withPartnershipEndDate("2023-12-31")
            .withTags("AlphaCorp")
            .build();

    public static final Partner PARTNER_JANE_UNTIL_20220630 = new PartnerBuilder()
            .withName("Jane Doe")
            .withPartnershipEndDate("2022-06-30")
            .withTags("GammaPower")
            .build();

    public static final Partner PARTNER_BILL_UNTIL_20240315 = new PartnerBuilder()
            .withName("Bill Turner")
            .withPartnershipEndDate("2024-03-15")
            .withTags("GothamIndustries")
            .build();
}
