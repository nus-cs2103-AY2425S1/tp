package seedu.address.testutil;

import seedu.address.model.person.DonatedAmount;
import seedu.address.model.person.Donor;

/**
 * A utility class to help with building Donor objects.
 */
public class DonorBuilder extends PersonBuilder<DonorBuilder> {

    public static final String DEFAULT_DONATED_AMOUNT = "10";

    private DonatedAmount donatedAmount;

    /**
     * Creates a {@code DonorBuilder} with the default details.
     */
    public DonorBuilder() {
        super();
        this.donatedAmount = new DonatedAmount(DEFAULT_DONATED_AMOUNT);
    }

    /**
     * Sets the {@code donatedAmount} of the {@code Donor} that we are building.
     */
    public DonorBuilder withDonatedAmount(String donatedAmount) {
        this.donatedAmount = new DonatedAmount(donatedAmount);
        return this;
    }

    @Override
    public Donor build() {
        return new Donor(name, phone, email, address, tags, donatedAmount);
    }
}
