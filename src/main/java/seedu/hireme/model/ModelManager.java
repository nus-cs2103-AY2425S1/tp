package seedu.hireme.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.hireme.commons.core.GuiSettings;
import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.commons.util.CollectionUtil;

/**
 * Represents the in-memory model of the address book data.
 *
 * @param <T> the type of elements in the AddressBook, which extends {@code HireMeComparable}
 */
public class ModelManager<T extends HireMeComparable<T>> implements Model<T> {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final AddressBook<T> addressBook;
    private final UserPrefs userPrefs;
    private FilteredList<T> filtered;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     *
     * @param addressBook The address book containing the application data.
     * @param userPrefs   The user preferences.
     */
    public ModelManager(ReadOnlyAddressBook<T> addressBook, ReadOnlyUserPrefs userPrefs) {
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.info("Initializing ModelManager with AddressBook and UserPrefs");
        logger.fine("AddressBook: " + addressBook + ", UserPrefs: " + userPrefs);

        this.addressBook = new AddressBook<>(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filtered = new FilteredList<>(this.addressBook.getList());

        logger.fine("Filtered list initialized with " + filtered.size() + " items.");
    }

    /**
     * Initializes a ModelManager with default values.
     */
    public ModelManager() {
        this(new AddressBook<>(), new UserPrefs());
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
    public void setAddressBook(ReadOnlyAddressBook<T> addressBook) {
        this.addressBook.resetData(addressBook);
    }

    /**
     * Gets the current address book.
     *
     * @return the current {@code ReadOnlyAddressBook}.
     */
    @Override
    public ReadOnlyAddressBook<T> getAddressBook() {
        return addressBook;
    }

    /**
     * Checks if the address book contains the specified item.
     *
     * @param item the item to check.
     * @return true if the item exists in the address book, otherwise false.
     */
    @Override
    public boolean hasItem(T item) {
        requireNonNull(item);
        return addressBook.hasItem(item);
    }

    /**
     * Deletes the specified item from the address book.
     *
     * @param target the item to delete.
     */
    @Override
    public void deleteItem(T target) {
        addressBook.removeItem(target);
    }

    /**
     * Adds a new item to the address book.
     *
     * @param item the item to add.
     */
    @Override
    public void addItem(T item) {
        addressBook.addItem(item);
        @SuppressWarnings("unchecked")
        Predicate<T> showAll = (Predicate<T>) PREDICATE_SHOW_ALL;
        updateFilteredList(showAll);
    }

    /**
     * Replaces the specified item with the edited item in the address book.
     *
     * @param target the item to replace.
     * @param edited the edited item.
     */
    @Override
    public void setItem(T target, T edited) {
        CollectionUtil.requireAllNonNull(target, edited);
        addressBook.setItem(target, edited);
    }

    //=========== Filtered List Accessors =============================================================

    /**
     * Gets the filtered list of items.
     *
     * @return an unmodifiable view of the filtered list.
     */
    @Override
    public ObservableList<T> getFilteredList() {
        return filtered;
    }

    /**
     * Updates the predicate for the filtered list.
     *
     * @param predicate the new predicate to filter the list.
     */
    @Override
    public void updateFilteredList(Predicate<T> predicate) {
        requireNonNull(predicate);
        filtered.setPredicate(predicate);
    }

    /**
     * Sorts the filtered list.
     *
     * @param comparator the sorting order used to sort the list.
     */
    @Override
    public void sortFilteredList(Comparator<T> comparator) {
        requireNonNull(comparator);
        addressBook.sortItems(comparator);
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

        if (!(other instanceof ModelManager<?>)) {
            return false;
        }

        ModelManager<?> otherModelManager = (ModelManager<?>) other;

        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filtered.equals(otherModelManager.filtered);
    }
}
