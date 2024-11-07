package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalWeddings.AMY_WEDDING;
import static seedu.address.testutil.TypicalWeddings.BOB_WEDDING;
import static seedu.address.testutil.TypicalWeddings.VALID_WEDDING_NAME_AMY_WEDDING;
import static seedu.address.testutil.TypicalWeddings.VALID_WEDDING_NAME_BOB_WEDDING;

import org.junit.jupiter.api.Test;

public class WeddingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Wedding(null));
    }

    @Test
    public void constructor_invalidWeddingName_throwsIllegalArgumentException() {
        String invalidWeddingName = "";
        assertThrows(IllegalArgumentException.class, () -> new Wedding(new WeddingName(invalidWeddingName)));
    }

    @Test
    public void isValidWeddingName() {
        // null Wedding name
        assertThrows(NullPointerException.class, () -> Wedding.isValidWeddingName(null));
    }

    @Test
    public void isValidWeddingName_validWeddingName_returnsTrue() {
        // Valid Wedding names
        assertTrue(Wedding.isValidWeddingName("friend"));
        assertTrue(Wedding.isValidWeddingName("work"));
        assertTrue(Wedding.isValidWeddingName("123"));
        assertTrue(Wedding.isValidWeddingName("friend 123")); // Alphanumeric with spaces
    }

    @Test
    public void isValidWeddingName_invalidWeddingName_returnsFalse() {
        // Invalid Wedding names
        assertFalse(Wedding.isValidWeddingName("")); // Empty string
        assertFalse(Wedding.isValidWeddingName(" ")); // Spaces only
        assertFalse(Wedding.isValidWeddingName("@home")); // Special character not allowed
        assertFalse(Wedding.isValidWeddingName("friend!")); // Special character not allowed
    }

    @Test
    public void isSameWedding_sameWedding_returnsTrue() {
        Wedding amyWedding = AMY_WEDDING;
        assertTrue(amyWedding.isSameWedding(amyWedding));
    }

    @Test
    public void isSameWedding_identicalWeddingName_returnsTrue() {
        Wedding amyWedding1 = AMY_WEDDING;
        Wedding amyWedding2 = AMY_WEDDING;
        assertTrue(amyWedding1.isSameWedding(amyWedding2));
    }

    @Test
    public void isSameWedding_differentWeddingName_returnsFalse() {
        Wedding amyWedding = AMY_WEDDING;
        Wedding bobWeding = BOB_WEDDING;
        assertFalse(amyWedding.isSameWedding(bobWeding));
    }

    @Test
    public void equals_identicalWeddingName_returnsTrue() {
        Wedding wedding1 = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        Wedding wedding2 = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        assertTrue(wedding1.equals(wedding2));
    }

    @Test
    public void equals_differentWeddingName_returnsFalse() {
        Wedding wedding1 = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        Wedding wedding2 = new Wedding(VALID_WEDDING_NAME_BOB_WEDDING);
        assertFalse(wedding1.equals(wedding2));
    }

    @Test
    public void toString_validWedding_returnsExpectedFormat() {
        Wedding amyWedding = AMY_WEDDING;
        assertTrue(amyWedding.toString().equals("[Amy's Wedding]"));
    }

    @Test
    public void noPersonsWeddinggedCheck() {
        Wedding amyWedding = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        assertEquals(0, amyWedding.getNumPersonsForWedding());
    }

    @Test
    public void setPartner1() {
        Wedding amyWedding = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        amyWedding.setPartner1(ALICE);
        assertEquals(ALICE, amyWedding.getPartner1());
    }

    @Test
    public void setPartner2() {
        Wedding amyWedding = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        amyWedding.setPartner2(ALICE);
        assertEquals(ALICE, amyWedding.getPartner2());
    }

    @Test
    public void increasePeopleCount() {
        Wedding amyWedding = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        assertEquals(1, amyWedding.getNumPersonsForWedding());
    }

    @Test
    public void decrementPeopleCount() {
        Wedding amyWedding = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        assertEquals(0, amyWedding.getNumPersonsForWedding());
    }

    @Test
    public void canBeDeleted() {
        Wedding amyWedding = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
        assertTrue(amyWedding.canBeDeleted());
    }
}
