package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
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

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));

        Config sameConfig = new Config();
        assertTrue(defaultConfig.equals(sameConfig));

        Config differentConfig = new Config();
        differentConfig.setLogLevel(Level.WARNING);
        assertFalse(defaultConfig.equals(differentConfig));
    }

    @Test
    public void hashCodeMethod() {
        Config config1 = new Config();
        Config config2 = new Config();

        assertEquals(config1.hashCode(), config2.hashCode());

        config2.setLogLevel(Level.WARNING);
        assertFalse(config1.hashCode() == config2.hashCode());
    }

    @Test
    public void getAndSetLogLevel() {
        Config config = new Config();

        // Default log level should be INFO
        assertEquals(Level.INFO, config.getLogLevel());

        config.setLogLevel(Level.SEVERE);
        assertEquals(Level.SEVERE, config.getLogLevel());
    }

    @Test
    public void getAndSetUserPrefsFilePath() {
        Config config = new Config();

        // Default path should be preferences.json
        assertEquals(Paths.get("preferences.json"), config.getUserPrefsFilePath());

        config.setUserPrefsFilePath(Paths.get("custom_prefs.json"));
        assertEquals(Paths.get("custom_prefs.json"), config.getUserPrefsFilePath());
    }

    @Test
    public void testEqualsWithDifferentObjectTypes() {
        Config config = new Config();
        assertFalse(config.equals(new String("Not a Config object")));
    }

    @Test
    public void testEqualsWithNull() {
        Config config = new Config();
        assertFalse(config.equals(null));
    }

    @Test
    public void setLogLevelWithNull() {
        Config config = new Config();
        config.setLogLevel(null);
        assertEquals(null, config.getLogLevel());
    }

    @Test
    public void setUserPrefsFilePathWithNull() {
        Config config = new Config();
        config.setUserPrefsFilePath(null);
        assertEquals(null, config.getUserPrefsFilePath());
    }
}

