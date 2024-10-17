package seedu.address.model.concert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalConcerts.COACHELLA;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

public class ConcertContactTest {
    @Test
    void createConcertContact() {
        //checks if ConcertContact object can be created
        ConcertContact contactTest1 = new ConcertContact(ALICE, COACHELLA);

        assertEquals(COACHELLA, contactTest1.getConcert());
        assertEquals(ALICE, contactTest1.getPerson());
    }

    @Test
    void equalsTest() {
        ConcertContact contactTest1 = new ConcertContact(ALICE, COACHELLA);
        ConcertContact contactTest2 = new ConcertContact(BOB, COACHELLA);

        assertTrue(contactTest1.equals(contactTest1)); //should return true since same contact
        assertFalse(contactTest1.equals(contactTest2)); //should return false since different contact
    }

}
