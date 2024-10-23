package seedu.hireme.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.hireme.commons.core.GuiSettings;
import seedu.hireme.commons.core.LogsCenter;
import seedu.hireme.commons.util.CollectionUtil;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager<T extends HireMeComparable<T>> implements Model<T> {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final AddressBook<T> addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<T> filtered;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook<T> addressBook, ReadOnlyUserPrefs userPrefs) {
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook<>(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filtered = new FilteredList<>(this.addressBook.getList());
    }

    public ModelManager() {
        this(new AddressBook<>(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getHireMeFilePath() {
        return userPrefs.getHireMeFilePath();
    }

    @Override
    public void setHireMeFilePath(Path hireMeFilePath) {
        requireNonNull(hireMeFilePath);
        userPrefs.setHireMeFilePath(hireMeFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook<T> addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook<T> getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasItem(T item) {
        requireNonNull(item);
        return addressBook.hasItem(item);
    }

    @Override
    public void deleteItem(T target) {
        addressBook.removeItem(target);
    }

    @Override
    public void addItem(T item) {
        addressBook.addItem(item);
        @SuppressWarnings("unchecked")
        Predicate<T> showAll = (Predicate<T>) PREDICATE_SHOW_ALL;
        updateFilteredList(showAll);
    }

    @Override
    public void setItem(T target, T edited) {
        CollectionUtil.requireAllNonNull(target, edited);

        addressBook.setItem(target, edited);
    }

    //=========== Filtered List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of items backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<T> getFilteredList() {
        return filtered;
    }

    @Override
    public void updateFilteredList(Predicate<T> predicate) {
        requireNonNull(predicate);
        filtered.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        // Check if the objects are the same instance
        if (other == this) {
            return true;
        }

        // Check if the other object is an instance of ModelManager and not null
        if (!(other instanceof ModelManager<?>)) {
            return false;
        }

        // Cast the other object to ModelManager
        ModelManager<?> otherModelManager = (ModelManager<?>) other;

        // Compare the state of addressBook, userPrefs, and filtered lists
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filtered.equals(otherModelManager.filtered);
    }

}
