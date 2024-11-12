package seedu.address.model.meetup;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddedBuyerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddedBuyer(null));
    }

    @Test
    public void constructor_invalidAddedBuyerName_throwsIllegalArgumentException() {
        String invalidName = "H!";
        assertThrows(IllegalArgumentException.class, () -> new AddedBuyer(invalidName));
    }

    @Test
    public void equals() {
        AddedBuyer addedBuyer = new AddedBuyer("ValidAddedBuyerName");

        // same values -> returns true
        assertTrue(addedBuyer.equals(new AddedBuyer("ValidAddedBuyerName")));

        // same object -> returns true
        assertTrue(addedBuyer.equals(addedBuyer));

        // null -> returns false
        assertFalse(addedBuyer.equals(null));

        // different types -> returns false
        assertFalse(addedBuyer.equals(5.0f));

        // different values -> returns false
        assertFalse(addedBuyer.equals(new AddedBuyer("OtherValidAddedBuyerName")));
    }
}
