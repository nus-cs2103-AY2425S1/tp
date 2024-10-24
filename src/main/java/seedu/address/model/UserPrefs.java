package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    // Default sort by priority
    private String sortPreference = "default";
    private Path reminderAddressBookFilePath = Paths.get("data", "reminderaddressbook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        setSortPreference(newUserPrefs.getSortPreference());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    /**
     * Returns the sort preference
     *
     * @return The sort preference as a {@code String}
     */
    public String getSortPreference() {
        return sortPreference;
    }

    /**
     * Sets the sort preference
     *
     * @param sortPreference The sort preference to set.
     */
    public void setSortPreference(String sortPreference) {
        requireNonNull(sortPreference);
        this.sortPreference = sortPreference;
    }


    public Path getReminderAddressBookFilePath() {
        return reminderAddressBookFilePath;
    }

    public void setReminderAddressBookFilePath(Path reminderAddressBookFilePath) {
        requireNonNull(reminderAddressBookFilePath);
        this.reminderAddressBookFilePath = reminderAddressBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserPrefs)) {
            return false;
        }

        UserPrefs otherUserPrefs = (UserPrefs) other;
        return guiSettings.equals(otherUserPrefs.guiSettings)
                && addressBookFilePath.equals(otherUserPrefs.addressBookFilePath)
                && sortPreference.equals(otherUserPrefs.sortPreference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nSorting Preference : " + sortPreference);
        return sb.toString();
    }

}
