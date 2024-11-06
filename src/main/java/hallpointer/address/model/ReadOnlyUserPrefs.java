package hallpointer.address.model;

import java.nio.file.Path;

import hallpointer.address.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getHallPointerFilePath();

}
