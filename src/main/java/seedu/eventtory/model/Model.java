package seedu.eventtory.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import seedu.eventtory.commons.core.GuiSettings;
import seedu.eventtory.model.association.Association;
import seedu.eventtory.model.commons.exceptions.AssociationDeleteException;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.ui.UiState;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluates to true */
    Predicate<Vendor> PREDICATE_SHOW_ALL_VENDORS = unused -> true;

    /** {@code Predicate} that always evaluates to true */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

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
     * Returns the user prefs' EventTory file path.
     */
    Path getEventToryFilePath();

    /**
     * Sets the user prefs' EventTory file path.
     */
    void setEventToryFilePath(Path eventToryFilePath);

    /**
     * Replaces EventTory data with the data in {@code eventTory}.
     */
    void setEventTory(ReadOnlyEventTory eventTory);

    /** Returns the AddressBook */
    ReadOnlyEventTory getEventTory();

    /**
     * Returns true if a vendor with the same identity as {@code vendor} exists in EventTory.
     */
    boolean hasVendor(Vendor vendor);

    /**
     * Deletes the given vendor. The vendor must exist in EventTory.
     */
    void deleteVendor(Vendor target) throws AssociationDeleteException;

    /**
     * Adds the given vendor. {@code vendor} must not already exist in EventTory.
     */
    void addVendor(Vendor vendor);

    /**
     * Replaces the given vendor {@code target} with {@code editedVendor}. {@code target} must exist in EventTory.
     * The vendor identity of {@code editedVendor} must not be the same as another existing vendor in EventTory.
     */
    void setVendor(Vendor target, Vendor editedVendor);

    /** Returns an unmodifiable view of the filtered vendor list */
    ObservableList<Vendor> getFilteredVendorList();

    /**
     * Updates the filter of the filtered vendor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredVendorList(Predicate<Vendor> predicate);

    /**
     * Returns true if the given {@code vendor} is already assigned to the given {@code event}. {@code vendor} and
     * {@code event} must exist in EventTory.
     */
    boolean isVendorAssignedToEvent(Vendor vendor, Event event);

    /**
     * Assigns the given vendor to the given event.
     */
    void assignVendorToEvent(Vendor vendor, Event event);

    /**
     * Unassigns the given vendor from the given event.
     */
    void unassignVendorFromEvent(Vendor vendor, Event event);

    /**
     * Returns list of associated vendors to an event.
     */
    ObservableList<Vendor> getAssociatedVendors(Event event);

    /**
     * Returns list of associated events to a vendor.
     */
    ObservableList<Event> getAssociatedEvents(Vendor vendor);

    /**
     * Returns list of associations.
     */
    ObservableList<Association> getAssociationList();

    /**
     * Returns the current selected vendor.
     */
    ObservableObjectValue<Vendor> getViewedVendor();

    /**
     * Sets the selected vendor.
     */
    void viewVendor(Vendor vendor);

    /*
     * Returns true if an event with the same identity as {@code event} exists in EventTory.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given event. The event must exist in EventTory.
     */
    void deleteEvent(Event target) throws AssociationDeleteException;

    /**
     * Adds the given event. {@code event} must not already exist in EventTory.
     */
    void addEvent(Event event);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}. {@code target} must exist in EventTory.
     * The event identity of {@code editedEvent} must not be the same as another existing event in EventTory.
     */
    void setEvent(Event target, Event editedEvent);

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Returns the current selected event.
     */
    ObservableObjectValue<Event> getViewedEvent();

    /**
     * Sets the selected Event.
     */
    void viewEvent(Event event);

    /**
     * Returns the current UI state.
     * @return {@code UiState} observable object.
     */
    public ObservableObjectValue<UiState> getUiState();

    /**
     * Sets the current UI state.
     * @param uiState {@code UiState} object.
     */
    public void setUiState(UiState uiState);
}
