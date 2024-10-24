package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;



/**
 * Test class for RoomNumberPredicate.
 */
class RoomNumberPredicateTest {
    private Person personWithRoom010101 = new PersonBuilder().withRoomNumber("01-0101").build();
    private Person personWithRoom010102 = new PersonBuilder().withRoomNumber("01-0102").build();


    /**
     * Test that the predicate returns true when the person's room number matches the predicate's room number.
     */
    @Test
    void test_roomNumberMatch_returnsTrue() {
        RoomNumberPredicate predicate = new RoomNumberPredicate("01-0101");
        assertTrue(predicate.test(personWithRoom010101));
    }

    /**
     * Test that the predicate returns false when the person's room number does not match the predicate's room number.
     */
    @Test
    void test_roomNumberNoMatch_returnsFalse() {
        RoomNumberPredicate predicate = new RoomNumberPredicate("01-0101");
        assertFalse(predicate.test(personWithRoom010102));
    }

    /**
     * Test the equality of two RoomNumberPredicate objects with the same room number.
     */
    @Test
    void equals_sameRoomNumber_returnsTrue() {
        RoomNumberPredicate predicate1 = new RoomNumberPredicate("01-0101");
        RoomNumberPredicate predicate2 = new RoomNumberPredicate("01-0101");
        assertEquals(predicate1, predicate2, "Two predicates with the same room number should be equal");
    }

    /**
     * Test the equality of two RoomNumberPredicate objects with different room numbers.
     */
    @Test
    void equals_differentRoomNumber_returnsFalse() {
        RoomNumberPredicate predicate1 = new RoomNumberPredicate("01-0101");
        RoomNumberPredicate predicate2 = new RoomNumberPredicate("01-0102");
        assertNotEquals(predicate1, predicate2, "Two predicates with different room numbers should not be equal");
    }
}
