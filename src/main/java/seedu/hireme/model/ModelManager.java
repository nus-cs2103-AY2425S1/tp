package seedu.hireme.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.hireme.commons.core.GuiSettings;
import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.commons.util.CollectionUtil;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Status;

/**
 * Represents the in-memory model of the address book data.
 *
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<InternshipApplication> filtered;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     *
     * @param addressBook The address book containing the application data.
     * @param userPrefs   The user preferences.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.info("Initializing ModelManager with AddressBook and UserPrefs");
        logger.fine("AddressBook: " + addressBook + ", UserPrefs: " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filtered = new FilteredList<>(this.addressBook.getList());

        logger.fine("Filtered list initialized with " + filtered.size() + " items.");
    }

    /**
     * Initializes a ModelManager with default values.
     */
    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
        logger.info("Initialized default ModelManager");
    }

    //=========== UserPrefs ==================================================================================

    /**
     * Sets the user preferences.
     *
     * @param userPrefs The new user preferences to be saved.
     */
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    /**
     * Gets the user preferences.
     *
     * @return the {@code ReadOnlyUserPrefs} object.
     */
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    /**
     * Gets the GUI settings for the application.
     *
     * @return the current {@code GuiSettings}.
     */
    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    /**
     * Sets the GUI settings for the application.
     *
     * @param guiSettings the new {@code GuiSettings} to be saved.
     */
    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    /**
     * Gets the file path to the HireMe data file.
     *
     * @return the {@code Path} to the HireMe data file.
     */
    @Override
    public Path getHireMeFilePath() {
        return userPrefs.getHireMeFilePath();
    }

    /**
     * Sets the file path to the HireMe data file.
     *
     * @param hireMeFilePath The new file path to be saved.
     */
    @Override
    public void setHireMeFilePath(Path hireMeFilePath) {
        requireNonNull(hireMeFilePath);
        userPrefs.setHireMeFilePath(hireMeFilePath);
    }

    //=========== AddressBook ================================================================================

    /**
     * Replaces the address book data with the provided {@code ReadOnlyAddressBook}.
     *
     * @param addressBook the new address book data.
     */
    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        requireNonNull(addressBook);
        logger.info("Replacing current address book data with new data");
        this.addressBook.resetData(addressBook);
    }

    /**
     * Gets the current address book.
     *
     * @return the current {@code ReadOnlyAddressBook}.
     */
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /**
     * Checks if the address book contains the specified item.
     *
     * @param item the item to check.
     * @return true if the item exists in the address book, otherwise false.
     */
    @Override
    public boolean hasItem(InternshipApplication item) {
        requireNonNull(item);
        logger.info("Checking if the address book and unique list contain this internship application");
        return addressBook.hasItem(item);
    }

    /**
     * Deletes the specified item from the address book.
     *
     * @param target the item to delete.
     */
    @Override
    public void deleteItem(InternshipApplication target) {
        requireNonNull(target);
        logger.info("Removing an internship application from the address book and unique list");
        addressBook.removeItem(target);
    }

    /**
     * Adds a new item to the address book.
     *
     * @param item the item to add.
     */
    @Override
    public void addItem(InternshipApplication item) {
        requireNonNull(item);
        logger.info("Adding an internship application to the address book and unique list");
        addressBook.addItem(item);
        updateFilteredList(PREDICATE_SHOW_ALL);
    }

    /**
     * Replaces the specified item with the edited item in the address book.
     *
     * @param target the item to replace.
     * @param edited the edited item.
     */
    @Override
    public void setItem(InternshipApplication target, InternshipApplication edited) {
        CollectionUtil.requireAllNonNull(target, edited);
        logger.info("Replacing an internship application in the address book and unique list with an edited one");
        addressBook.setItem(target, edited);
    }

    //=========== Filtered List Accessors =============================================================

    /**
     * Gets the filtered list of items.
     *
     * @return an unmodifiable view of the filtered list.
     */
    @Override
    public ObservableList<InternshipApplication> getFilteredList() {
        return filtered;
    }

    /**
     * Updates the predicate for the filtered list.
     *
     * @param predicate the new predicate to filter the list.
     */
    @Override
    public void updateFilteredList(Predicate<InternshipApplication> predicate) {
        requireNonNull(predicate);
        logger.info("Updating the filtered list");
        filtered.setPredicate(predicate);
    }

    /**
     * Sorts the filtered list.
     *
     * @param comparator the sorting order used to sort the list.
     */
    @Override
    public void sortFilteredList(Comparator<InternshipApplication> comparator) {
        requireNonNull(comparator);
        logger.info("Sorting the list of internship applications in the address book and unique list");
        addressBook.sortItems(comparator);
    }

    /**
     * Provides status insights of items in the list
     */
    @Override
    public Map<Status, Integer> getChartData() {
        return addressBook.getChartData();
    }

    /**
     * Checks if this ModelManager is equal to another object.
     *
     * @param other the other object to compare with.
     * @return true if both objects are equal, otherwise false.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;

        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filtered.equals(otherModelManager.filtered);
    }
}
