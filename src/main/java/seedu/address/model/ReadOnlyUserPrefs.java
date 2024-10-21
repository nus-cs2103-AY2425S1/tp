package seedu.address.model;

import java.nio.file.Path;
import java.util.HashSet;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.profile.Profile;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    HashSet<Profile> getProfiles();
}
