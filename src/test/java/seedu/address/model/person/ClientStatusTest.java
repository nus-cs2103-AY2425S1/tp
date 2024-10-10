package seedu.address.model.person;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ClientStatusTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClientStatus(null));
    }

    @Test
    public void constructor_invalidClientStatus_throwsIllegalArgumentException() {
        String invalidStatus = "referral";
        assertThrows(IllegalArgumentException.class, () -> new ClientStatus(invalidStatus));
    }

    @Test
    public void isValidClientStatus() {
        // null status
        assertThrows(NullPointerException.class, () -> ClientStatus.isValidClientStatus(null));

        // invalid client status
        assertFalse(ClientStatus.isValidClientStatus("")); // empty string
        assertFalse(ClientStatus.isValidClientStatus(" ")); // spaces only
        assertFalse(ClientStatus.isValidClientStatus("referral")); // should be 'potential'
        assertFalse(ClientStatus.isValidClientStatus("olf")); // typo for old
        assertFalse(ClientStatus.isValidClientStatus("ACTIVE")); // Incorrect upper case
        assertFalse(ClientStatus.isValidClientStatus("OLD")); // Incorrect upper case
        assertFalse(ClientStatus.isValidClientStatus("POTENTIAL")); // Incorrect upper case
        assertFalse(ClientStatus.isValidClientStatus("UNRESPONSIVE")); // Incorrect upper case

        // valid client status
        assertTrue(ClientStatus.isValidClientStatus("active")); // correct lower case
        assertTrue(ClientStatus.isValidClientStatus("old")); // correct lower case
        assertTrue(ClientStatus.isValidClientStatus("potential")); // correct lower case
        assertTrue(ClientStatus.isValidClientStatus("unresponsive")); // correct lower case
    }

    @Test
    public void toString_validStatus_returnsCorrectString() {

        ClientStatus activeClientStatus = new ClientStatus("active");
        assertTrue(activeClientStatus.toString().equals("active"));

        ClientStatus oldClientStatus = new ClientStatus("old");
        assertTrue(oldClientStatus.toString().equals("old"));

        ClientStatus potentialClientStatus = new ClientStatus("potential");
        assertTrue(potentialClientStatus.toString().equals("potential"));

        ClientStatus unresponsiveClientStatus = new ClientStatus("unresponsive");
        assertTrue(unresponsiveClientStatus.toString().equals("unresponsive"));
    }

    @Test
    public void equals() {
        ClientStatus activeClientStatus = new ClientStatus("active");
        ClientStatus oldClientStatus = new ClientStatus("old");
        ClientStatus potentialClientStatus = new ClientStatus("potential");
        ClientStatus unresponsiveClientStatus = new ClientStatus("unresponsive");

        // same values -> returns true
        assertTrue(activeClientStatus.equals(new ClientStatus("active")));
        assertTrue(oldClientStatus.equals(new ClientStatus("old")));
        assertTrue(potentialClientStatus.equals(new ClientStatus("potential")));
        assertTrue(unresponsiveClientStatus.equals(new ClientStatus("unresponsive")));

        // same object -> returns true
        assertTrue(activeClientStatus.equals(activeClientStatus));

        // null -> returns false
        assertFalse(activeClientStatus.equals(null));

        // different types -> returns false
        assertFalse(activeClientStatus.equals("String"));

        // different values -> returns false
        assertFalse(activeClientStatus.equals(potentialClientStatus));
    }

    @Test
    public void hashCode_sameStatus_sameHashCode() {

        ClientStatus activeClientStatus = new ClientStatus("active");
        ClientStatus activeClientStatus2 = new ClientStatus("active");
        assertTrue(activeClientStatus.hashCode() == activeClientStatus2.hashCode());

        ClientStatus oldClientStatus = new ClientStatus("active");
        ClientStatus oldClientStatus2 = new ClientStatus("active");
        assertTrue(oldClientStatus.hashCode() == oldClientStatus2.hashCode());

    }
}
