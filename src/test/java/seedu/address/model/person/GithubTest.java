package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
        assertFalse(Github.isValidGithubUsername(" ")); // spaces only
        assertFalse(Github.isValidGithubUsername("-John")); // Starts with hypen only
        assertFalse(Github.isValidGithubUsername("John-")); // Ends with hypen only
        assertFalse(Github.isValidGithubUsername("-John-Doe")); // Starts with hypen and hyphen in between
        assertFalse(Github.isValidGithubUsername("John--Doe")); //  consecutive hypens only
        assertFalse(Github.isValidGithubUsername("-")); // hypen only
        assertFalse(Github.isValidGithubUsername("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ-beepo")); // long username (>39 characters)

        // valid username
        assertTrue(Github.isValidGithubUsername("John-Doe"));
        assertTrue(Github.isValidGithubUsername("J")); // one character
        assertTrue(Github.isValidGithubUsername("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ-beep")); // long username
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
}
