package seedu.hireme.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.hireme.commons.core.GuiSettings;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * The API of the Model component.
 */
public interface Model<T> {
    /** {@code Predicate} that always evaluate to true */
    Predicate<InternshipApplication> PREDICATE_SHOW_ALL = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' hire me file path.
     */
    Path getHireMeFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setHireMeFilePath(Path hireMeFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook<T> addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook<T> getAddressBook();

    /**
     * Returns true if an item with the same identity as {@code item} exists in the address book.
     */
    boolean hasItem(T item);

    /**
     * Deletes the given item.
     * The item must exist in the address book.
     */
    void deleteItem(T target);

    /**
     * Adds the given item.
     * {@code item} must not already exist in the address book.
     */
    void addItem(T item);

    /**
     * Replaces the given item {@code target} with {@code edited}.
     * {@code target} must exist in the address book.
     * The item identity of {@code edited} must not be the same as another existing item in the address book.
     */
    void setItem(T target, T edited);

    /** Returns an unmodifiable view of the filtered list */
    ObservableList<T> getFilteredList();

    /**
     * Updates the filter of the filtered list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredList(Predicate<T> predicate);

    /**
     * Sorts the filtered list using the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortFilteredList(Comparator<T> comparator);
}
