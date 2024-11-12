package seedu.address.testutil;

import seedu.address.model.person.Date;
import seedu.address.model.person.Partner;

/**
 * A utility class to help with building Partner objects.
 */
public class PartnerBuilder extends PersonBuilder<PartnerBuilder> {

    public static final String DEFAULT_END_DATE = "2020-10-20";

    private Date partnershipEndDate;

    /**
     * Creates a {@code PartnerBuilder} with the default details.
     */
    public PartnerBuilder() {
        super();
        this.partnershipEndDate = new Date(DEFAULT_END_DATE);
    }

    /**
     * Sets the {@code partnershipEndDate} of the {@code Partner} that we are building.
     */
    public PartnerBuilder withPartnershipEndDate(String partnershipEndDate) {
        this.partnershipEndDate = new Date(partnershipEndDate);
        return this;
    }

    @Override
    public Partner build() {
        return new Partner(name, phone, email, address, tags, partnershipEndDate);
    }
}
