package seedu.hireme.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Contains tests for the {@code GuiSettings} class.
 */
public class GuiSettingsTest {

    /**
     * Tests the {@code toString} method of the {@code GuiSettings} class.
     * Ensures that the string representation of the {@code GuiSettings} object is as expected.
     */
    @Test
    public void toStringMethod() {
        GuiSettings guiSettings = new GuiSettings();
        String expected = GuiSettings.class.getCanonicalName() + "{windowWidth=" + guiSettings.getWindowWidth()
                + ", windowHeight=" + guiSettings.getWindowHeight() + ", windowCoordinates="
                + guiSettings.getWindowCoordinates() + "}";
        assertEquals(expected, guiSettings.toString());
    }
}
