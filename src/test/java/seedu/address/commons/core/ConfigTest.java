package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void toStringMethod() {
        Config config = new Config();
        String expected = Config.class.getCanonicalName() + "{logLevel=" + config.getLogLevel()
                + ", userPrefsFilePath=" + config.getUserPrefsFilePath() + "}";
        assertEquals(expected, config.toString());
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void equalsMethod() {
        Config config = new Config();
        // same values -> returns true
        Config configCopy = new Config();
        assertTrue(config.equals(configCopy));
        assertEquals(config.hashCode(), configCopy.hashCode());
        // same object -> returns true
        assertTrue(config.equals(config));
        // null -> returns false
        assertTrue(!config.equals(null));
        // different type -> returns false
        assertTrue(!config.equals(5));
        // different log level -> returns false
        Config differentConfig = new Config();
        differentConfig.setLogLevel(Level.SEVERE);
        assertFalse(config.equals(differentConfig));
        // different user prefs file path -> returns false
        differentConfig = new Config();
        differentConfig.setUserPrefsFilePath(Path.of("different"));
        assertFalse(config.equals(differentConfig));
    }

    @Test
    public void getScheduleStorageFilePath() {
        Config config = new Config();
        assertEquals(Path.of("schedule.json"), config.getScheduleStorageFilePath());
    }
}
