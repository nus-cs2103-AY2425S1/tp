package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.meetup.MeetUp;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Buyer> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<MeetUp> PREDICATE_SHOW_ALL_MEETUPS = unused -> true;

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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyBuyerList addressBook);

    /** Returns the AddressBook */
    ReadOnlyBuyerList getAddressBook();

    /**
     * Returns true if a buyer with the same identity as {@code buyer} exists in the address book.
     */
    boolean hasBuyer(Buyer buyer);

    /**
     * Deletes the given buyer.
     * The buyer must exist in the address book.
     */
    void deletePerson(Buyer target);

    /**
     * Adds the given buyer.
     * {@code buyer} must not already exist in the address book.
     */
    void addBuyer(Buyer buyer);

    /**
     * Replaces the given buyer {@code target} with {@code editedBuyer}.
     * {@code target} must exist in the address book.
     * The buyer identity of {@code editedBuyer} must not be the same as another existing buyer in the address book.
     */
    void setBuyer(Buyer target, Buyer editedBuyer);

    /** Returns an unmodifiable view of the filtered buyer list */
    ObservableList<Buyer> getFilteredBuyerList();

    /**
     * Updates the filter of the filtered buyer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBuyerList(Predicate<Buyer> predicate);

    // ============================ MeetUp Model - To be refactored ==========================

    /**
     * Returns the user prefs' meet up list file path.
     */
    Path getMeetUpListFilePath(); // Remove after refactoring

    /**
     * Sets the user prefs' meet up list file path.
     */
    void setMeetUpListFilePath(Path meetUpListFilePath); // Remove after refactoring

    /**
     * Replaces meet up list data with the data in {@code meetUpList}.
     */
    void setMeetUpList(ReadOnlyMeetUpList meetUpList); // Remove after refactoring

    /** Returns the MeetUpList */
    ReadOnlyMeetUpList getMeetUpList();

    /** Returns an unmodifiable view of the filtered meetup list */
    ObservableList<MeetUp> getFilteredMeetUpList();

    /**
     * Adds the given MeetUp.
     * {@code MeetUp} must not already exist in the address book.
     */
    void addMeetUp(MeetUp meetUp);

    /**
     * Updates the given schedule's meetup in the schedule list.
     * The schedule must exist in the schedule list.
     */
    void setMeetUp(MeetUp target, MeetUp editedMeetUp);

    /**
     * Deletes the given schedule in the schedule list
     */
    void deleteMeetUp(MeetUp target);

    /*
     * Updates the filter of the meetup list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMeetUpList(Predicate<MeetUp> predicate);

    /**
     * Returns true if a MeetUp with the same identity as {@code MeetUp} exists in the MeetUp list.
     */
    boolean hasMeetUp(MeetUp meetUp);
}
