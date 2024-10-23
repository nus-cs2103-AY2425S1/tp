package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_DATE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_PHONE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class containing a list of {@code Volunteer} objects to be used in tests.
 */
public class TypicalVolunteers {

    public static final Volunteer ALICE = new VolunteerBuilder().withName(VALID_VOLUNTEER_NAME_ALICE)
            .withPhone(VALID_VOLUNTEER_PHONE_ALICE).withEmail(VALID_VOLUNTEER_EMAIL_ALICE)
            .withAvailableDate(VALID_VOLUNTEER_DATE_ALICE).build();

    public static final Volunteer BOB = new VolunteerBuilder().withName(VALID_VOLUNTEER_NAME_BOB)
            .withPhone(VALID_VOLUNTEER_PHONE_BOB).withEmail(VALID_VOLUNTEER_EMAIL_BOB)
            .withAvailableDate(VALID_VOLUNTEER_DATE_BOB).build();

    public static final Volunteer CHARLIE = new VolunteerBuilder().withName("Charlie Lee")
            .withPhone("92345678").withEmail("charlielee@example.com")
            .withAvailableDate("2023-07-22")
            .build();

    public static final Volunteer DAVE = new VolunteerBuilder().withName("Dave Lim")
            .withPhone("93456789").withEmail("davelim@example.com")
            .withAvailableDate("2023-08-12")
            .build();

    // Manually added
    public static final Volunteer EVE = new VolunteerBuilder().withName("Eve Tan")
            .withPhone("91234512").withEmail("evetan@example.com")
            .withAvailableDate("2023-09-14")
            .build();

    public static final Volunteer FAYE = new VolunteerBuilder().withName("Faye Koh")
            .withPhone("93451245").withEmail("fayekoh@example.com")
            .withAvailableDate("2023-10-22")
            .build();

    // Keyword for testing search
    public static final String KEYWORD_MATCHING_VOLUNTEER = "Charlie"; // A keyword that matches CHARLIE

    private TypicalVolunteers() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical volunteers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Volunteer volunteer : getTypicalVolunteers()) {
            ab.addVolunteer(volunteer);
        }
        return ab;
    }

    public static List<Volunteer> getTypicalVolunteers() {
        return new ArrayList<>(Arrays.asList(ALICE, BOB, CHARLIE, DAVE, EVE, FAYE));
    }
}
