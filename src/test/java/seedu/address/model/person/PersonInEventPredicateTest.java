package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEvents.ART_EXHIBITION;
import static seedu.address.testutil.TypicalEvents.SPORTS_FESTIVAL;
import static seedu.address.testutil.TypicalEvents.getPersonSet;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;


public class PersonInEventPredicateTest {

    @Test
    public void equals() {
        PersonInEventPredicate firstPredicate = new PersonInEventPredicate(SPORTS_FESTIVAL);
        PersonInEventPredicate secondPredicate = new PersonInEventPredicate(SPORTS_FESTIVAL);
        PersonInEventPredicate thirdPredicate = new PersonInEventPredicate(ART_EXHIBITION);

        // Same Value -> equals
        assertEquals(firstPredicate, secondPredicate);

        // Same predicate object -> equals
        assertEquals(firstPredicate, firstPredicate);

        // Different event value -> not equals
        assertNotEquals(firstPredicate, thirdPredicate);

        // null -> not equals
        assertNotEquals(null, firstPredicate);

        // different type -> not equals
        assertNotEquals(1, firstPredicate);
    }

    @Test
    public void test_personInEvent_returnsTrue() {
        Event testEvent = new EventBuilder().withName("Test Event")
                .withAttendees(getPersonSet(ALICE, AMY)).build();
        Event testEvent2 = new EventBuilder().withName("Test Event")
                .withAttendees(getPersonSet(ALICE, AMY))
                .withSponsors(getPersonSet(ALICE)).build();

        PersonInEventPredicate predicate = new PersonInEventPredicate(testEvent);
        assertTrue(predicate.test(ALICE));
        assertTrue(predicate.test(AMY));

        PersonInEventPredicate predicate2 = new PersonInEventPredicate(testEvent2);
        assertTrue(predicate2.test(ALICE));
        assertTrue(predicate2.test(AMY));
    }

    @Test
    public void test_personInEvent_returnsFalse() {
        Event testEvent = new EventBuilder().withName("Test Event")
                .withAttendees(getPersonSet(ALICE, AMY)).build();
        Event testEvent2 = new EventBuilder().withName("Test Event")
                .withAttendees(getPersonSet(ALICE, AMY))
                .withSponsors(getPersonSet(ALICE)).build();

        PersonInEventPredicate predicate = new PersonInEventPredicate(testEvent);
        assertFalse(predicate.test(BOB));

        PersonInEventPredicate predicate2 = new PersonInEventPredicate(testEvent2);
        assertFalse(predicate.test(BOB));
    }

    @Test
    public void toStringMethod() {
        PersonInEventPredicate predicate = new PersonInEventPredicate(SPORTS_FESTIVAL);

        String expected = PersonInEventPredicate.class.getCanonicalName() + "{Event=" + SPORTS_FESTIVAL + "}";
        assertEquals(expected, predicate.toString());
    }
}
