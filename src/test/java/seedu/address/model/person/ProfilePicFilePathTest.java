package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Test
    public void getDefaultProfilePic_returnsCorrectPath() {
        // default profile picture path
        ProfilePicFilePath defaultPath = ProfilePicFilePath.getDefaultProfilePic();
        assertNotNull(defaultPath);
        assertTrue(defaultPath.filePath.toString().replace("\\", "/").endsWith("images/profilepicture.png"));
    }

    @Test
    public void hashCode_sameFilePath_returnsSameHashCode() {
        ProfilePicFilePath path1 = new ProfilePicFilePath(Paths.get("images", "test.png"));
        ProfilePicFilePath path2 = new ProfilePicFilePath(Paths.get("images", "test.png"));

        // same file path -> same hash code
        assertEquals(path1.hashCode(), path2.hashCode());
    }
    @Test
    public void hashCode_differentFilePath_returnsDifferentHashCode() {
        ProfilePicFilePath path1 = new ProfilePicFilePath(Paths.get("images", "test.png"));
        ProfilePicFilePath path2 = new ProfilePicFilePath(Paths.get("images", "different.png"));

        // different file path -> different hash code
        assertNotEquals(path1.hashCode(), path2.hashCode());
    }
}
