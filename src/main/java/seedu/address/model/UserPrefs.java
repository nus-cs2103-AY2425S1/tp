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
    private Path addressBookSaveFilePath = Paths.get("data" , "addressbook.json");
    private Path addressBookExportFilePath = Paths.get("data", "addressbook.csv");

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
        setAddressBookSaveFilePath(newUserPrefs.getAddressBookSaveFilePath());
        setAddressBookExportFilePath(newUserPrefs.getAddressBookExportFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookSaveFilePath() {
        return addressBookSaveFilePath;
    }

    public void setAddressBookSaveFilePath(Path addressBookSaveFilePath) {
        requireNonNull(addressBookSaveFilePath);
        this.addressBookSaveFilePath = addressBookSaveFilePath;
    }

    public Path getAddressBookExportFilePath() {
        return addressBookExportFilePath;
    }

    public void setAddressBookExportFilePath(Path addressBookExportFilePath) {
        requireNonNull(addressBookExportFilePath);
        this.addressBookExportFilePath = addressBookExportFilePath;
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
                && addressBookSaveFilePath.equals(otherUserPrefs.addressBookSaveFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookSaveFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookSaveFilePath);
        return sb.toString();
    }

}
