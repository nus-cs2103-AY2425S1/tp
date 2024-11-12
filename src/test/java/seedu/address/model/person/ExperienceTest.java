package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ExperienceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Experience(null));
    }

    @Test
    public void constructor_invalidExperience_throwsIllegalArgumentException() {
        String invalidExperience = " "; // invalid experience (blank)
        assertThrows(IllegalArgumentException.class, () -> new Experience(invalidExperience));
    }

    @Test
    public void isValidExperience() {
        // null experience
        assertThrows(NullPointerException.class, () -> Experience.isValidExperience(null));

        // invalid experience
        assertFalse(Experience.isValidExperience("")); // empty string
        assertFalse(Experience.isValidExperience(" ")); // spaces only
        assertFalse(Experience.isValidExperience("    ")); // multiple spaces

        // valid experience
        assertTrue(Experience.isValidExperience("CTO of Facebook from 2000-2022")); // typical experience description
        assertTrue(Experience.isValidExperience("Software Engineer")); // simple description
        assertTrue(Experience.isValidExperience("Intern at ABC Inc.")); // contains spaces and alphanumeric
        assertTrue(Experience.isValidExperience("-")); // single character
    }
}
