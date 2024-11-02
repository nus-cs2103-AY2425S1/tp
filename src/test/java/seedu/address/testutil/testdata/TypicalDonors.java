package seedu.address.testutil.testdata;

import seedu.address.model.person.Donor;
import seedu.address.testutil.DonorBuilder;

/**
 * A utility class containing a list of {@code Donor} objects to be used in tests.
 */
public class TypicalDonors {
    public static final Donor DONOR_ALEX_20 = new DonorBuilder().withName("Alex Johnson")
            .withDonatedAmount("20").build();
    public static final Donor DONOR_BEN_50 = new DonorBuilder().withName("Ben Cooper")
            .withDonatedAmount("50").build();
    public static final Donor DONOR_CHARLIE_100 = new DonorBuilder().withName("Charlie Brown")
            .withDonatedAmount("100").build();
    public static final Donor DONOR_DANA_5 = new DonorBuilder().withName("Dana Smith")
            .withDonatedAmount("5").build();
}
