package seedu.address.testutil.testdata;

import seedu.address.model.person.Volunteer;
import seedu.address.testutil.VolunteerBuilder;

/**
 * A utility class containing a list of {@code Volunteer} objects to be used in tests.
 */
public class TypicalVolunteers {
    public static final Volunteer VOLUNTEER_BEN_10HOURS = new VolunteerBuilder().withName("Ben Ten")
            .withHours("10").build();
    public static final Volunteer VOLUNTEER_ALICE_5HOURS = new VolunteerBuilder().withName("Alice Volunteer")
            .withHours("5").build();
    public static final Volunteer VOLUNTEER_CHARLIE_15HOURS = new VolunteerBuilder().withName("Charlie")
            .withHours("15").build();
}
