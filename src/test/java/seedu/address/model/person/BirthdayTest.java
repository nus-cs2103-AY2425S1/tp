package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BirthdayTest {

    @Test
    public void equals() {
        Birthday birthday = new Birthday("Hello");

        // same object -> returns true
        assertTrue(birthday.equals(birthday));

        // same values -> returns true
        Birthday birthdayCopy = new Birthday(birthday.value);
        assertTrue(birthday.equals(birthdayCopy));

        // different types -> returns false
        assertFalse(birthday.equals(1));

        // null -> returns false
        assertFalse(birthday.equals(null));

        // different birthday -> returns false
        Birthday differentBirthday = new Birthday("Bye");
        assertFalse(birthday.equals(differentBirthday));
    }
}
