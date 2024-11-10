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
    private Path goodsFilePath = Paths.get("data", "goods.csv");

    private Path filterGoodsFilePath = Paths.get("data", "filteredGoods.csv");

    private Boolean exportFilterGoods = false;

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
        setGoodsFilePath(newUserPrefs.getGoodsFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Boolean getExportFilterGoods() {
        return exportFilterGoods;
    }

    public void setExportFilterGoodsToTrue() {
        this.exportFilterGoods = true;
    };

    public void setExportFilterGoodsToFalse() {
        this.exportFilterGoods = false;
    };

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public Path getGoodsFilePath() {
        return goodsFilePath;
    }

    public Path getFilterGoodsFilePath() {
        return filterGoodsFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    public void setFilterGoodsFilePath(Path goodsFilePath) {
        requireNonNull(goodsFilePath);
        this.goodsFilePath = goodsFilePath;
    }
    public void setGoodsFilePath(Path goodsFilePath) {
        requireNonNull(goodsFilePath);
        this.goodsFilePath = goodsFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UserPrefs otherUserPrefs)) {
            return false;
        }

        return guiSettings.equals(otherUserPrefs.guiSettings)
                && addressBookFilePath.equals(otherUserPrefs.addressBookFilePath);
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
        return sb.toString();
    }

}
