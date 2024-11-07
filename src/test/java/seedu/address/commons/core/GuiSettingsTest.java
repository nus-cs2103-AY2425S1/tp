package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.Point;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {

    @Test
    public void defaultConstructor_initializesToDefaultValues() {
        GuiSettings guiSettings = new GuiSettings();
        assertEquals(740, guiSettings.getWindowWidth(), "Default window width should be 740");
        assertEquals(600, guiSettings.getWindowHeight(), "Default window height should be 600");
        assertNull(guiSettings.getWindowCoordinates(), "Default window coordinates should be null");
    }

    @Test
    public void parameterizedConstructor_initializesToSpecifiedValues() {
        GuiSettings guiSettings = new GuiSettings(800, 600, 100, 150);
        assertEquals(800, guiSettings.getWindowWidth(), "Window width should be 800");
        assertEquals(600, guiSettings.getWindowHeight(), "Window height should be 600");
        assertEquals(new Point(100, 150), guiSettings.getWindowCoordinates(),
                "Window coordinates should be (100, 150)");
    }

    @Test
    public void getWindowCoordinates_returnsNewPointInstance() {
        GuiSettings guiSettings = new GuiSettings(800, 600, 100, 150);
        Point coordinates = guiSettings.getWindowCoordinates();

        // Ensure the coordinates are as expected
        assertEquals(new Point(100, 150), coordinates, "Window coordinates should be (100, 150)");

        // Modify the returned coordinates and check that original coordinates remain unchanged (immutability)
        coordinates.setLocation(200, 250);
        assertEquals(new Point(100, 150), guiSettings.getWindowCoordinates(),
                "Original window coordinates should remain unchanged");
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        GuiSettings guiSettings1 = new GuiSettings(800, 600, 100, 150);
        GuiSettings guiSettings2 = new GuiSettings(800, 600, 100, 150);
        assertEquals(guiSettings1, guiSettings2, "GuiSettings instances with the same values should be equal");
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        GuiSettings guiSettings1 = new GuiSettings(800, 600, 100, 150);
        GuiSettings guiSettings2 = new GuiSettings(1024, 768, 200, 300);

        assertNotEquals(guiSettings1, guiSettings2,
                            "GuiSettings instances with different values should not be equal");
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        GuiSettings guiSettings = new GuiSettings();
        assertNotEquals(guiSettings, "someString",
                "GuiSettings should not be equal to an instance of a different type");
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        GuiSettings guiSettings1 = new GuiSettings(800, 600, 100, 150);
        GuiSettings guiSettings2 = new GuiSettings(800, 600, 100, 150);
        assertEquals(guiSettings1.hashCode(), guiSettings2.hashCode(),
                "Equal GuiSettings instances should have the same hash code");
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHashCode() {
        GuiSettings guiSettings1 = new GuiSettings(800, 600, 100, 150);
        GuiSettings guiSettings2 = new GuiSettings(1024, 768, 200, 300);

        assertNotEquals(guiSettings1.hashCode(), guiSettings2.hashCode(),
                "GuiSettings instances with different values should have different hash codes");
    }

    @Test
    public void toStringMethod() {
        GuiSettings guiSettings = new GuiSettings();
        String expected = GuiSettings.class.getCanonicalName() + "{windowWidth=" + guiSettings.getWindowWidth()
                + ", windowHeight=" + guiSettings.getWindowHeight() + ", windowCoordinates="
                + guiSettings.getWindowCoordinates() + "}";
        assertEquals(expected, guiSettings.toString());
    }
}
