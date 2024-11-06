package seedu.address.model.volunteer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.VolunteerBuilder;

public class VolunteerNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        String firstEventName = "Community Service";
        String secondEventName = "Charity Run";

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
        VolunteerInvolvedInEventPredicate predicate = new VolunteerInvolvedInEventPredicate("Community Service");
        assertTrue(predicate.test(new VolunteerBuilder().withEvents("Community Service", "Charity Run").build()));

        // Matching event name in a larger list
        predicate = new VolunteerInvolvedInEventPredicate("Charity Run");
        assertTrue(predicate.test(new VolunteerBuilder().withEvents("Community Service", "Charity Run").build()));
    }

    @Test
    public void test_volunteerIsNotInvolvedInEvent_returnsFalse() {
        // No matching event name
        VolunteerInvolvedInEventPredicate predicate = new VolunteerInvolvedInEventPredicate("Fundraiser");
        assertFalse(predicate.test(new VolunteerBuilder().withEvents("Community Service", "Charity Run").build()));

        // Empty involved events list
        predicate = new VolunteerInvolvedInEventPredicate("Community Service");
        assertFalse(predicate.test(new VolunteerBuilder().withEvents().build()));
    }

    @Test
    public void toStringMethod() {
        VolunteerInvolvedInEventPredicate predicate = new VolunteerInvolvedInEventPredicate("Community Service");

        String expected = VolunteerInvolvedInEventPredicate.class.getCanonicalName() + "{event name=Community Service}";

        assertEquals(expected, predicate.toString());
    }
}
