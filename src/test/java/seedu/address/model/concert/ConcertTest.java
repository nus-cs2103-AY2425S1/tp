package seedu.address.model.concert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_GLASTONBURY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_GLASTONBURY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COACHELLA;
import static seedu.address.testutil.TypicalConcerts.COACHELLA;
import static seedu.address.testutil.TypicalConcerts.GLASTONBURY;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ConcertBuilder;

public class ConcertTest {

    @Test
    public void isSameConcert() {
        // same object -> returns true
        assertTrue(COACHELLA.isSameConcert(COACHELLA));

        // null -> returns false
        assertFalse(COACHELLA.isSameConcert(null));

        // same name, all other different -> returns false
        Concert editedCoachella = new ConcertBuilder(COACHELLA)
                .withAddress(VALID_ADDRESS_GLASTONBURY).withDate(VALID_DATE_GLASTONBURY).build();
        assertFalse(COACHELLA.isSameConcert(editedCoachella));

        // all but one attribute different -> returns false
        editedCoachella =
                new ConcertBuilder(COACHELLA).withAddress(VALID_ADDRESS_GLASTONBURY).build();
        assertFalse(COACHELLA.isSameConcert(editedCoachella));

        // name differs in case, all other attributes same -> returns false
        editedCoachella = new ConcertBuilder().withName(VALID_NAME_COACHELLA.toLowerCase()).build();
        assertFalse(COACHELLA.isSameConcert(editedCoachella));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_COACHELLA + " ";
        editedCoachella = new ConcertBuilder(COACHELLA).withName(nameWithTrailingSpaces).build();
        assertFalse(COACHELLA.isSameConcert(editedCoachella));
    }

    @Test
    public void equals() {
        // same return true
        assertTrue(COACHELLA.equals(COACHELLA));

        // different return false
        assertFalse(GLASTONBURY.equals(COACHELLA));
    }

    @Test
    public void toStringMethod() {
        String expected = Concert.class.getCanonicalName() + "{name=" + COACHELLA.getName()
                + ", address=" + COACHELLA.getAddress() + ", date=" + COACHELLA.getDate() + "}";
        assertEquals(COACHELLA.toString(), expected);
    }
}
