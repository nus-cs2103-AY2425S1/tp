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
    private Path buyerListFilePath = Paths.get("data" , "buyerlist.json");
    private Path meetUpListFilePath = Paths.get("data", "meetuplist.json");
    private Path propertyListFilePath = Paths.get("data", "propertylist.json");

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
        setBuyerListFilePath(newUserPrefs.getBuyerListFilePath());
        setMeetUpListFilePath(newUserPrefs.getMeetUpListFilePath());
        setPropertyListFilePath(newUserPrefs.getPropertyListFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getBuyerListFilePath() {
        return buyerListFilePath;
    }

    public void setBuyerListFilePath(Path buyerListFilePath) {
        requireNonNull(buyerListFilePath);
        this.buyerListFilePath = buyerListFilePath;
    }

    public Path getMeetUpListFilePath() {
        return meetUpListFilePath;
    }

    public void setMeetUpListFilePath(Path meetUpListFilePath) {
        requireNonNull(meetUpListFilePath);
        this.meetUpListFilePath = meetUpListFilePath;
    }

    public Path getPropertyListFilePath() {
        return propertyListFilePath;
    }

    public void setPropertyListFilePath(Path propertyListFilePath) {
        requireNonNull(propertyListFilePath);
        this.propertyListFilePath = propertyListFilePath;
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
                && buyerListFilePath.equals(otherUserPrefs.buyerListFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, buyerListFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + buyerListFilePath);
        return sb.toString();
    }

}
