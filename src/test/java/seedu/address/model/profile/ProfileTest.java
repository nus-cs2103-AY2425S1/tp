package seedu.address.model.profile;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.address.model.profile.exceptions.IllegalProfileNameException;
import seedu.address.model.profile.exceptions.IllegalProfilePathException;

class ProfileTest {


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Profile(null));
    }

    @Test
    void constructor_defaultProfileName_success() {
        Profile profile = new Profile();
        assertEquals("addressbook", profile.toString(),
                "Default profile name should be 'addressbook'");
    }

    @Test
    void constructor_validProfileName_success() {
        Profile profile = new Profile("validProfile1");
        assertEquals("validProfile1", profile.toString(),
                "Profile name should match the provided valid name.");
    }

    @Test
    void constructor_invalidProfileName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Profile("invalid name")); // white space
    }

    @Test
    void isValidProfileName() {
        //  Null case
        assertThrows(NullPointerException.class, () -> Profile.isValidProfileName(null));

        // Positive test cases (valid names)
        assertTrue(Profile.isValidProfileName("valid_Name-123"),
                "Should return true for names with '-' and '_'");
        assertTrue(Profile.isValidProfileName("a"), "Should return true for single-character name");
        assertTrue(Profile.isValidProfileName("validProfileName12345678901234"),
                "Boundary case: Should return true for exactly 30 characters");

        // Negative test cases (invalid names)
        assertFalse(Profile.isValidProfileName("invalid name"), "Should return false for name with spaces");
        assertFalse(Profile.isValidProfileName("invalid@name"),
                "Should return false for name with special characters");
        assertFalse(Profile.isValidProfileName("profileNameWithThirtyOneChars31"),
                "Boundary case: Should return false for name with 31 characters");
        assertFalse(Profile.isValidProfileName("aVeryLongProfileNameThatExceedsThirtyCharacters"),
                "Should return false for name exceeding 30 characters");
    }

    @Test
    void isValidProfileFromPath() {
        Path validPath = Paths.get("data", "validProfile.json");
        Path invalidPathWithInvalidName = Paths.get("data", "invalid name.json");
        Path invalidPathWithSubfolder = Paths.get("data", "data", "invalidProfile.json");
        Path invalidFileType = Paths.get("data", "name.txt");

        assertTrue(Profile.isValidProfileFromPath(validPath),
                "Should return true for valid profile path format and name");
        assertFalse(Profile.isValidProfileFromPath(invalidPathWithInvalidName),
                "Should return false for path with space");
        assertFalse(Profile.isValidProfileFromPath(invalidPathWithSubfolder),
                "Should return false for nested path structure");
        assertFalse(Profile.isValidProfileFromPath(invalidFileType),
                "Should return false for non .json files");
    }

    @Test
    void extractProfileNameFromPathOrThrow_validPath_returnsProfileName() {
        Path validPath = Paths.get("data", "validProfile.json");
        assertEquals("validProfile", Profile.extractProfileNameFromPathOrThrow(validPath),
                "Should return 'validProfile' as profile name from valid path");
    }

    @Test
    void extractProfileNameFromPathOrThrow_invalidPath_throwsException() {
        Path invalidPathWithSpecialChars = Paths.get("data", "invalid@profile.json");
        Path invalidPathWithSubfolder = Paths.get("data", "subfolder", "profile.json");

        assertThrows(IllegalProfileNameException.class,
                IllegalProfileNameException.ERR_MSG, () ->
                        Profile.extractProfileNameFromPathOrThrow(invalidPathWithSpecialChars)
        );
        assertThrows(IllegalProfilePathException.class,
                IllegalProfilePathException.ERR_MSG, () ->
                        Profile.extractProfileNameFromPathOrThrow(invalidPathWithSubfolder)
        );
    }

    @Test
    void extractNameFromPathOrIgnore_validPath_returnsProfileNameStream() {
        Path validPath = Paths.get("data", "validProfile.json");
        String extractedName = Profile.extractNameFromPathOrIgnore(validPath)
                .findFirst()
                .orElse(null);

        assertNotNull(extractedName, "Stream should contain a profile name for valid path");
        assertEquals("validProfile", extractedName, "Profile name should match the extracted name");
    }

    @Test
    void extractNameFromPathOrIgnore_invalidPath_returnsEmptyStream() {
        // Invalid path with subfolder (invalid structure)
        Path invalidPathWithSubfolder = Paths.get("data", "subfolder", "profile.json");

        // Path with invalid name (contains space)
        Path invalidPathWithInvalidName = Paths.get("data", "invalid name.json");

        Stream<String> resultStream1 = Profile.extractNameFromPathOrIgnore(invalidPathWithSubfolder);
        Stream<String> resultStream2 = Profile.extractNameFromPathOrIgnore(invalidPathWithInvalidName);

        assertTrue(resultStream1.findFirst().isEmpty(),
                "Stream should be empty for an invalid path structure");
        assertTrue(resultStream2.findFirst().isEmpty(),
                "Stream should be empty for an invalid profile name in path");
    }

    @Test
    void extractNameFromPathOrIgnore_emptyPath_returnsEmptyStream() {
        // Path to an empty string (invalid)
        Path emptyPath = Paths.get("");
        Stream<String> resultStream = Profile.extractNameFromPathOrIgnore(emptyPath);
        assertTrue(resultStream.findFirst().isEmpty(), "Stream should be empty for an empty path");
    }

    @Test
    void toPath_correctFormatting() {
        Profile profile = new Profile("customProfile");
        Path expectedPath = Paths.get("data", "customProfile.json");
        assertEquals(expectedPath, profile.toPath(), "The profile path should match the expected format");
    }

    @Test
    void profileStringToPath() {
        Path pathForString = Profile.profileStringToPath("a_valid_path");
        assertEquals(Paths.get("data", "a_valid_path.json"), pathForString,
                "Path should be 'data/a_valid_path.json'");

    }

    // Test EmptyProfile singleton instance
    @Test
    void getEmptyProfile_returnsSameInstance() {
        Profile.EmptyProfile instanceOne = Profile.getEmptyProfile();
        Profile.EmptyProfile instanceTwo = Profile.getEmptyProfile();
        assertSame(instanceOne, instanceTwo, "EmptyProfile should return the same instance each time");
    }
}
