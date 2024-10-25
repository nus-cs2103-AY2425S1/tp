package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class ProfilePicFilePathTest {

    @Test
    public void equals() {
        ProfilePicFilePath path = new ProfilePicFilePath(Paths.get("images", "x.png"));

        // same object -> returns true
        assertTrue(path.equals(path));

        // same values -> returns true
        ProfilePicFilePath pathCopy = new ProfilePicFilePath(path.filePath);
        assertTrue(path.equals(pathCopy));

        // different types -> returns false
        assertFalse(path.equals(1));

        // null -> returns false
        assertFalse(path.equals(null));

        // different file path -> returns false
        ProfilePicFilePath differentPath = new ProfilePicFilePath(Paths.get("images", "y.png"));
        assertFalse(path.equals(differentPath));
    }
}
