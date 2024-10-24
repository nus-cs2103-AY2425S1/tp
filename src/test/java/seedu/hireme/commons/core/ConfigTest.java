package seedu.hireme.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Contains tests for the {@code Config} class.
 */
public class ConfigTest {

    /**
     * Tests the {@code toString} method of the {@code Config} class.
     * Ensures that the string representation of the {@code Config} object is as expected.
     */
    @Test
    public void toStringMethod() {
        Config config = new Config();
        String expected = Config.class.getCanonicalName() + "{logLevel=" + config.getLogLevel()
                + ", userPrefsFilePath=" + config.getUserPrefsFilePath() + "}";
        assertEquals(expected, config.toString());
    }

    /**
     * Tests the {@code equals} method of the {@code Config} class.
     * Verifies that a {@code Config} object is equal to itself.
     */
    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));
    }
}
