package spleetwaise.commons.model;

import java.nio.file.Path;

import spleetwaise.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    Path getTransactionBookFilePath();

}
