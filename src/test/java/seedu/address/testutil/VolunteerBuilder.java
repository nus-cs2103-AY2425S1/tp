package seedu.address.testutil;

import seedu.address.model.person.Hours;
import seedu.address.model.person.Volunteer;

/**
 * A utility class to help with building Volunteer objects.
 */
public class VolunteerBuilder extends PersonBuilder<VolunteerBuilder> {

    public static final String DEFAULT_HOURS = "10";

    private Hours hours;

    /**
     * Creates a {@code VolunteerBuilder} with the default details.
     */
    public VolunteerBuilder() {
        super();
        this.hours = new Hours(DEFAULT_HOURS);
    }

    /**
     * Sets the {@code Hours} of the {@code Volunteer} that we are building.
     */
    public VolunteerBuilder withHours(String hours) {
        this.hours = new Hours(hours);
        return this;
    }

    @Override
    public Volunteer build() {
        return new Volunteer(name, phone, email, address, tags, hours);
    }
}
