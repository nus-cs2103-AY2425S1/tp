package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventParticipatedByVolunteerPredicateTest {

    @Test
    public void equals() {
        String firstVolunteerName = "John Doe";
        String secondVolunteerName = "Jane Doe";

        EventParticipatedByVolunteerPredicate firstPredicate =
                new EventParticipatedByVolunteerPredicate(firstVolunteerName);
        EventParticipatedByVolunteerPredicate secondPredicate =
                new EventParticipatedByVolunteerPredicate(secondVolunteerName);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventParticipatedByVolunteerPredicate firstPredicateCopy =
                new EventParticipatedByVolunteerPredicate(firstVolunteerName);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different volunteer name -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventContainsVolunteer_returnsTrue() {
        // Volunteer is assigned to the event
        EventParticipatedByVolunteerPredicate predicate = new EventParticipatedByVolunteerPredicate("John Doe");
        Event eventWithVolunteer = new EventBuilder().withVolunteers("John Doe", "Jane Doe").build();
        assertTrue(predicate.test(eventWithVolunteer));
    }

    @Test
    public void test_eventDoesNotContainVolunteer_returnsFalse() {
        // Volunteer is not assigned to the event
        EventParticipatedByVolunteerPredicate predicate = new EventParticipatedByVolunteerPredicate("John Doe");
        Event eventWithoutVolunteer = new EventBuilder().withVolunteers("Jane Doe").build();
        assertFalse(predicate.test(eventWithoutVolunteer));

        // Event has no volunteers
        Event eventWithNoVolunteers = new EventBuilder().withVolunteers().build();
        assertFalse(predicate.test(eventWithNoVolunteers));
    }
}
