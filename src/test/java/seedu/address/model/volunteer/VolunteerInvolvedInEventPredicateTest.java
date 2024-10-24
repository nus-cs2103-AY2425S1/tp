package seedu.address.model.volunteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VolunteerBuilder;

public class VolunteerInvolvedInEventPredicateTest {

    @Test
    public void equals() {
        String firstEventName = "EventA";
        String secondEventName = "EventB";

        VolunteerInvolvedInEventPredicate firstPredicate = new VolunteerInvolvedInEventPredicate(firstEventName);
        VolunteerInvolvedInEventPredicate secondPredicate = new VolunteerInvolvedInEventPredicate(secondEventName);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        VolunteerInvolvedInEventPredicate firstPredicateCopy = new VolunteerInvolvedInEventPredicate(firstEventName);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different event name -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_volunteerIsInvolvedInEvent_returnsTrue() {
        // Matching event name
        VolunteerInvolvedInEventPredicate predicate = new VolunteerInvolvedInEventPredicate("Charity Run");
        assertTrue(predicate.test(new VolunteerBuilder().withEvents("Charity Run").build()));

        // Volunteer involved in multiple events, one matches
        predicate = new VolunteerInvolvedInEventPredicate("Food Drive");
        assertTrue(predicate.test(new VolunteerBuilder().withEvents("Charity Run", "Food Drive").build()));
    }

    @Test
    public void test_volunteerIsNotInvolvedInEvent_returnsFalse() {
        // Non-matching event name
        VolunteerInvolvedInEventPredicate predicate = new VolunteerInvolvedInEventPredicate("Food Drive");
        assertFalse(predicate.test(new VolunteerBuilder().withEvents("Charity Run").build()));

        // Volunteer has no events
        predicate = new VolunteerInvolvedInEventPredicate("Charity Run");
        assertFalse(predicate.test(new VolunteerBuilder().build()));
    }

    @Test
    public void toStringMethod() {
        String eventName = "EventA";
        VolunteerInvolvedInEventPredicate predicate = new VolunteerInvolvedInEventPredicate(eventName);

        String expected = VolunteerInvolvedInEventPredicate.class.getCanonicalName() + "{event name=" + eventName + "}";
        assertEquals(expected, predicate.toString());
    }
}
