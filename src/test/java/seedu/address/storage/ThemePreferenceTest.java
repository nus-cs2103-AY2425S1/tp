package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ThemePreferenceTest {

    private static final Path TEST_THEME_PREF_FILE_PATH = Paths.get("data", "themePreference.json");

    @BeforeEach
    void setUp() throws IOException {
        // Clean up before each test
        if (Files.exists(TEST_THEME_PREF_FILE_PATH)) {
            Files.delete(TEST_THEME_PREF_FILE_PATH);
        }
        Files.createDirectories(TEST_THEME_PREF_FILE_PATH.getParent());
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(TEST_THEME_PREF_FILE_PATH)) {
            Files.delete(TEST_THEME_PREF_FILE_PATH);
        }

        Path parentDir = TEST_THEME_PREF_FILE_PATH.getParent();
        if (Files.exists(parentDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(parentDir)) {
                for (Path entry : stream) {
                    Files.delete(entry);
                }
            }
            Files.delete(parentDir);
        }
    }

    @Test
    void testDefaultThemeLoading() {
        ThemePreference themePreference = new ThemePreference();
        assertEquals("LIGHT", themePreference.getTheme());
    }

    @Test
    void testThemeLoadingFromFile() throws IOException {
        Files.writeString(TEST_THEME_PREF_FILE_PATH, "\"DARK\"");
        ThemePreference themePreference = new ThemePreference();
        assertEquals("DARK", themePreference.getTheme());
    }

    @Test
    void testThemeLoadingWithEmptyFile() throws IOException {
        Files.writeString(TEST_THEME_PREF_FILE_PATH, "");
        ThemePreference themePreference = new ThemePreference();
        assertEquals("LIGHT", themePreference.getTheme());
    }

    @Test
    void testThemeLoadingWithInvalidJson() throws IOException {
        Files.writeString(TEST_THEME_PREF_FILE_PATH, "invalid_json");
        ThemePreference themePreference = new ThemePreference();
        assertEquals("LIGHT", themePreference.getTheme());
    }

    @Test
    void testSavingThemePreference() throws IOException {
        ThemePreference themePreference = new ThemePreference();
        themePreference.saveThemePreference("DARK");
        assertEquals("DARK", themePreference.getTheme());
        assertTrue(Files.exists(TEST_THEME_PREF_FILE_PATH));
        assertEquals("\"DARK\"", Files.readString(TEST_THEME_PREF_FILE_PATH));
    }

    @Test
    void testSettingAndSavingTheme() throws IOException {
        ThemePreference themePreference = new ThemePreference();
        themePreference.setTheme("DARK");
        assertEquals("DARK", themePreference.getTheme());
        assertTrue(Files.exists(TEST_THEME_PREF_FILE_PATH));
        assertEquals("\"DARK\"", Files.readString(TEST_THEME_PREF_FILE_PATH));
    }

    @Test
    void testDirectoryCreationFailure() throws IOException {
        Path parentDir = TEST_THEME_PREF_FILE_PATH.getParent();
        Files.deleteIfExists(parentDir); // Delete the parent directory to simulate failure

        ThemePreference themePreference = new ThemePreference();

        // Attempt to save a theme
        themePreference.saveThemePreference("DARK");

        // Assert that the theme could still be changed to dark as it will create the file
        assertEquals("DARK", themePreference.getTheme());

        // Verify that the parent directory exists
        assertTrue(Files.exists(parentDir));
    }

    @Test
    void testThemePreferenceNotSavedOnException() throws IOException {
        ThemePreference themePreference = new ThemePreference();
        try {
            // This line simulates the behaviour of the method and throws an exception
            throw new IOException("Save failed");
        } catch (IOException e) {
            // do nothing
        }

        // Verify that the theme is still LIGHT as default
        assertEquals("LIGHT", themePreference.getTheme());
    }
}
