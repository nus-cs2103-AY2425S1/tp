package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidAddress));
    }

    @Test
    public void isValidStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid status
        assertFalse(Status.isValidStatus(""));
        assertFalse(Status.isValidStatus(" "));
        assertFalse(Status.isValidStatus("no"));

        //valid status
        assertTrue(Status.isValidStatus("delivered"));
        assertTrue(Status.isValidStatus("delivering"));
        assertTrue(Status.isValidStatus("not delivered"));
    }

    @Test
    public void getValue_validStatus_returnsCorrectString() {
        Status deliveredStatus = new Status("delivered");
        Status deliveringStatus = new Status("delivering");
        Status notDeliveredStatus = new Status("not delivered");
        assertEquals("delivered", deliveredStatus.getValue());
        assertEquals("delivering", deliveringStatus.getValue());
        assertEquals("not delivered", notDeliveredStatus.getValue());
    }
}
