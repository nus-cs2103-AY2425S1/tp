package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BirthdayTest {

    @Test
    public void equals_differentBirthday() {
        Birthday birthday = new Birthday("1990-05-20");

        // same object -> returns true
        assertTrue(birthday.equals(birthday));

        // same values -> returns true
        Birthday birthdayCopy = new Birthday(birthday.value.toString());
        assertTrue(birthday.equals(birthdayCopy));

        // different types -> returns false
        assertFalse(birthday.equals(1));

        // null -> returns false
        assertFalse(birthday.equals(null));

        // different birthday -> returns false
        Birthday differentBirthday = new Birthday("1991-06-21");
        assertFalse(birthday.equals(differentBirthday));
    }

    @Test
    public void equals_emptyBirthday() {
        Birthday emptyBirthday = new Birthday("");
        Birthday anotherEmptyBirthday = new Birthday("");

        // Same empty birthday -> returns true
        assertTrue(emptyBirthday.equals(anotherEmptyBirthday));
    }
}
