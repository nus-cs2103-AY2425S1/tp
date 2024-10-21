package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.property.Property;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Buyer> PREDICATE_SHOW_ALL_BUYERS = unused -> true;
    Predicate<MeetUp> PREDICATE_SHOW_ALL_MEETUPS = unused -> true;
    Predicate<Property> PREDICATE_SHOW_ALL_PROPERTIES = unused -> true;

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
     * Returns the user prefs' buyer list file path.
     */
    Path getBuyerListFilePath();

    /**
     * Sets the user prefs' buyer list file path.
     */
    void setBuyerListFilePath(Path buyerListFilePath);

    /**
     * Replaces buyer list data with the data in {@code buyerList}.
     */
    void setBuyerList(ReadOnlyBuyerList buyerList);

    /** Returns the BuyerList */
    ReadOnlyBuyerList getBuyerList();

    /**
     * Returns true if a buyer with the same identity as {@code buyer} exists in the buyer list.
     */
    boolean hasBuyer(Buyer buyer);

    /**
     * Deletes the given buyer.
     * The buyer must exist in the buyer list.
     */
    void deleteBuyer(Buyer target);

    /**
     * Adds the given buyer.
     * {@code buyer} must not already exist in the buyer list.
     */
    void addBuyer(Buyer buyer);

    /**
     * Replaces the given buyer {@code target} with {@code editedBuyer}.
     * {@code target} must exist in the buyer list.
     * The buyer identity of {@code editedBuyer} must not be the same as another existing buyer in the buyer list.
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
     * {@code MeetUp} must not already exist in the buyer list.
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

    // ============================ Property Model ==========================

    /**
     * Returns the user pref's property list file path.
     */
    Path getPropertyListFilePath();

    /**
     * Sets the user prefs' property list file path.
     */
    void setPropertyListFilePath(Path propertyListFilePath);

    /**
     * Replaces property list data with the data in {@code propertyList}.
     */
    void setPropertyList(ReadOnlyPropertyList propertyList);

    /** Returns the PropertyList */
    ReadOnlyPropertyList getPropertyList();

    /**
     * Returns true if a property with the same identity as {@code property} exists in the property list.
     */
    boolean hasProperty(Property property);

    /**
     * Deletes the given property.
     * The property must exist in the property list.
     */
    void deleteProperty(Property target);

    /**
     * Adds the given property.
     * {@code property} must not already exist in the property list.
     */
    void addProperty(Property property);

    /**
     * Replaces the given property {@code target} with {@code editedProperty}.
     * {@code target} must exist in the property list.
     * The property identity of {@code editedProperty} must not be the same as another existing property
     * in the property list.
     */
    void setProperty(Property target, Property editedProperty);

    /** Returns an unmodifiable view of the filtered property list */
    ObservableList<Property> getFilteredPropertyList();

    /*
     * Updates the filter of the property list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPropertyList(Predicate<Property> predicate);
}
