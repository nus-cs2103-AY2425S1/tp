package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PersonIdTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PersonId((String) null));
    }

    @Test
    public void equals() {
        PersonId id = new PersonId("969d5233-5ae8-4c56-97b7-b25737afa01f");

        // same values -> returns true
        assertTrue(id.equals(new PersonId("969d5233-5ae8-4c56-97b7-b25737afa01f")));

        // same object -> returns true
        assertTrue(id.equals(id));

        // null -> returns false
        assertFalse(id.equals(null));

        // different types -> returns false
        assertFalse(id.equals(5.0f));

        // different values -> returns false
        assertFalse(id.equals(new PersonId("e44ad470-6609-47b6-a96f-1fc90725afe9")));
    }
}
