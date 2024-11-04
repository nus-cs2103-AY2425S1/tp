package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GithubTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Github(null));
    }

    @Test
    public void constructor_invalidGithub_throwsIllegalArgumentException() {
        String emptyUsername = "";
        String usernameWithWhitespaces = "john asd";
        String usernameWithRestrictedSpecialCharacters = "john@asd";
        assertThrows(IllegalArgumentException.class, () -> new Github(emptyUsername));
        assertThrows(IllegalArgumentException.class, () -> new Github(usernameWithWhitespaces));
        assertThrows(IllegalArgumentException.class, () -> new Github(usernameWithRestrictedSpecialCharacters));
    }

    @Test
    public void isValidUsername() {
        // null username
        assertThrows(NullPointerException.class, () -> Github.isValidGithubUsername(null));

        // invalid username
        assertFalse(Github.isValidGithubUsername("")); // empty string
        assertFalse(Github.isValidGithubUsername(" ")); // whitespace only
        assertFalse(Github.isValidGithubUsername("-John")); // Starts with hypen only
        assertFalse(Github.isValidGithubUsername("John-")); // Ends with hypen only
        assertFalse(Github.isValidGithubUsername("-John-Doe")); // Starts with hypen and hyphen in between
        assertFalse(Github.isValidGithubUsername("John--Doe")); //  consecutive hypens only
        assertFalse(Github.isValidGithubUsername("-")); // hypen only
        // invalid long username (>39 characters)
        assertFalse(Github.isValidGithubUsername("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ-beepo"));
        assertFalse(Github.isValidGithubUsername("@")); // special character only
        assertFalse(Github.isValidGithubUsername("John Doe")); // whitespace between name

        // valid username
        assertTrue(Github.isValidGithubUsername("John-Doe"));
        assertTrue(Github.isValidGithubUsername("J")); // one character
        assertTrue(Github.isValidGithubUsername("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ-beep")); // 39 characters
    }

    @Test
    public void equals() {
        Github github = new Github("Valid-Username");

        // same values -> returns true
        assertTrue(github.equals(new Github("Valid-Username")));

        // same object -> returns true
        assertTrue(github.equals(github));

        // null -> returns false
        assertFalse(github.equals(null));

        // different types -> returns false
        assertFalse(github.equals(5.0f));

        // different values -> returns false
        assertFalse(github.equals(new Github("Other-Valid-Username")));
    }

    @Test
    public void compareTo() {
        Github github = new Github("Alice");
        Github otherGithub = new Github("Bob");

        // null input
        assertThrows(NullPointerException.class, () -> github.compareTo(null));

        // valid input
        assertTrue(github.compareTo(otherGithub) < 0);
        assertTrue(otherGithub.compareTo(github) > 0);

        // same input
        assertEquals(0, github.compareTo(github));
    }
}
