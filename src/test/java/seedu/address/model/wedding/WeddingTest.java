package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_TWO;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.WeddingBuilder;

public class WeddingTest {

    @Test
    public void isSameWedding() {
        // same object -> returns true
        assertTrue(WEDDING_ONE.isSameWedding(WEDDING_ONE));

        // null -> returns false
        assertFalse(WEDDING_ONE.isSameWedding(null));

        // same wedding name, different venue and datetime -> returns true
        Wedding editedWeddingOne = new WeddingBuilder(WEDDING_ONE).withVenue("Botanic Gardens")
                .withDate("15/03/2024").build();
        assertTrue(WEDDING_ONE.isSameWedding(editedWeddingOne));

        // different wedding name, all other attributes same -> returns false
        editedWeddingOne = new WeddingBuilder(WEDDING_ONE).withWeddingName("James Tan & Emily Koh").build();
        assertFalse(WEDDING_ONE.isSameWedding(editedWeddingOne));

        // wedding name differs in case, all other attributes same -> returns false
        Wedding editedWeddingTwo = new WeddingBuilder(WEDDING_TWO).withWeddingName("DAVID LEE & CLARA WONG").build();
        assertFalse(WEDDING_TWO.isSameWedding(editedWeddingTwo));

        // wedding name has trailing spaces, all other attributes same -> returns false
        String weddingNameWithTrailingSpaces = WEDDING_TWO.getWeddingName().toString() + " ";
        editedWeddingTwo = new WeddingBuilder(WEDDING_TWO).withWeddingName(weddingNameWithTrailingSpaces).build();
        assertFalse(WEDDING_TWO.isSameWedding(editedWeddingTwo));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Wedding weddingCopy = new WeddingBuilder(WEDDING_ONE).build();
        assertTrue(WEDDING_ONE.equals(weddingCopy));

        // same object -> returns true
        assertTrue(WEDDING_ONE.equals(WEDDING_ONE));

        // null -> returns false
        assertFalse(WEDDING_ONE.equals(null));

        // different type -> returns false
        assertFalse(WEDDING_ONE.equals(5));

        // different wedding -> returns false
        assertFalse(WEDDING_ONE.equals(WEDDING_TWO));

        // different wedding name -> returns false
        Wedding editedWeddingOne = new WeddingBuilder(WEDDING_ONE).withWeddingName("James Tan & Emily Koh").build();
        assertFalse(WEDDING_ONE.equals(editedWeddingOne));

        // different venue -> returns false
        editedWeddingOne = new WeddingBuilder(WEDDING_ONE).withVenue("Botanic Gardens").build();
        assertFalse(WEDDING_ONE.equals(editedWeddingOne));

        // different datetime -> returns false
        editedWeddingOne = new WeddingBuilder(WEDDING_ONE).withDate("15/03/2024").build();
        assertFalse(WEDDING_ONE.equals(editedWeddingOne));
    }

    @Test
    public void toStringMethod() {
        String expected = Wedding.class.getCanonicalName() + "{weddingName=" + WEDDING_ONE.getWeddingName()
                + ", venue=" + WEDDING_ONE.getVenue() + ", date=" + WEDDING_ONE.getDate() + "}";
        assertTrue(WEDDING_ONE.toString().equals(expected));
    }
}
