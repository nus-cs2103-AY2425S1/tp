package seedu.address.model.meetup;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AddedBuyerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddedBuyer(null));
    }

    @Test
    public void constructor_invalidAddedBuyerName_throwsIllegalArgumentException() {
        String invalidAddedBuyerName = "H!";
        assertThrows(IllegalArgumentException.class, () -> new AddedBuyer(invalidAddedBuyerName));
    }

    @Test
    public void isValidAddedBuyerName() {
        // null addedBuyer name
        assertThrows(NullPointerException.class, () -> AddedBuyer.isValidBuyerName(null));
    }
}
