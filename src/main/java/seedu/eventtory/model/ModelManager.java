package seedu.eventtory.model;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.eventtory.model.util.PredicateBuilder.combinePredicates;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.eventtory.commons.core.GuiSettings;
import seedu.eventtory.commons.core.LogsCenter;
import seedu.eventtory.model.association.Association;
import seedu.eventtory.model.commons.exceptions.AssociationDeleteException;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.ui.UiState;

/**
 * Represents the in-memory model of EventTory data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final EventTory eventTory;
    private final UserPrefs userPrefs;
    private final FilteredList<Vendor> filteredVendors;
    private final ObjectProperty<Vendor> selectedVendor;
    private final FilteredList<Event> filteredEvents;
    private final ObjectProperty<Event> selectedEvent;
    private final ObjectProperty<UiState> currentUiState;
    private final ObjectProperty<Predicate<Vendor>> suppliedVendorFilterPredicate;
    private final ObjectProperty<Predicate<Event>> suppliedEventFilterPredicate;

    /**
     * Initializes a ModelManager with the given eventTory and userPrefs.
     */
    public ModelManager(ReadOnlyEventTory eventTory, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(eventTory, userPrefs);

        logger.fine("Initializing with EventTory: " + eventTory + " and user prefs " + userPrefs);

        this.eventTory = new EventTory(eventTory);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredVendors = new FilteredList<>(this.eventTory.getVendorList());
        filteredEvents = new FilteredList<>(this.eventTory.getEventList());
        selectedVendor = new SimpleObjectProperty<>(null);
        selectedEvent = new SimpleObjectProperty<>(null);
        currentUiState = new SimpleObjectProperty<>(UiState.DEFAULT);
        suppliedVendorFilterPredicate = new SimpleObjectProperty<>(PREDICATE_SHOW_ALL_VENDORS);
        suppliedEventFilterPredicate = new SimpleObjectProperty<>(PREDICATE_SHOW_ALL_EVENTS);
    }

    public ModelManager() {
        this(new EventTory(), new UserPrefs());
    }

    // =========== UserPrefs ==================================================================================

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
    public Path getEventToryFilePath() {
        return userPrefs.getEventToryFilePath();
    }

    @Override
    public void setEventToryFilePath(Path eventToryFilePath) {
        requireNonNull(eventToryFilePath);
        userPrefs.setEventToryFilePath(eventToryFilePath);
    }

    // =========== EventTory ================================================================================

    @Override
    public void setEventTory(ReadOnlyEventTory eventTory) {
        this.eventTory.resetData(eventTory);
    }

    @Override
    public ReadOnlyEventTory getEventTory() {
        return eventTory;
    }

    @Override
    public boolean hasVendor(Vendor vendor) {
        requireNonNull(vendor);
        return eventTory.hasVendor(vendor);
    }

    @Override
    public void deleteVendor(Vendor target) throws AssociationDeleteException {
        eventTory.removeVendor(target);
    }

    @Override
    public void addVendor(Vendor vendor) {
        eventTory.addVendor(vendor);
        updateFilteredVendorList(PREDICATE_SHOW_ALL_VENDORS);
    }

    @Override
    public void setVendor(Vendor target, Vendor editedVendor) {
        requireAllNonNull(target, editedVendor);

        eventTory.setVendor(target, editedVendor);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return eventTory.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) throws AssociationDeleteException {
        eventTory.removeEvent(target);
    }

    @Override
    public void addEvent(Event event) {
        eventTory.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        eventTory.setEvent(target, editedEvent);
    }

    // =========== Assigning vendors and events =============================================================

    @Override
    public boolean isVendorAssignedToEvent(Vendor vendor, Event event) {
        requireAllNonNull(vendor, event);
        return eventTory.isVendorAssignedToEvent(vendor, event);
    }

    @Override
    public void assignVendorToEvent(Vendor vendor, Event event) {
        requireAllNonNull(vendor, event);
        eventTory.assignVendorToEvent(vendor, event);
        applyFiltersBasedOnUiState();
    }

    @Override
    public void unassignVendorFromEvent(Vendor vendor, Event event) {
        requireAllNonNull(vendor, event);
        eventTory.unassignVendorFromEvent(vendor, event);
        applyFiltersBasedOnUiState();
    }

    @Override
    public ObservableList<Event> getAssociatedEvents(Vendor vendor) {
        requireNonNull(vendor);
        return eventTory.getAssociatedEvents(vendor);
    }

    @Override
    public ObservableList<Vendor> getAssociatedVendors(Event event) {
        requireNonNull(event);
        return eventTory.getAssociatedVendors(event);
    }

    @Override
    public ObservableList<Association> getAssociationList() {
        return eventTory.getAssociationList();
    }

    // =========== Filtered Vendor List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Vendor} backed by the internal list of
     * {@code versionedEventTory}
     */
    @Override
    public ObservableList<Vendor> getFilteredVendorList() {
        return filteredVendors;
    }

    @Override
    public void updateFilteredVendorList(Predicate<Vendor> predicate) {
        requireNonNull(predicate);
        suppliedVendorFilterPredicate.setValue(predicate);
        applyFiltersBasedOnUiState();
    }

    // =========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedEventTory}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        suppliedEventFilterPredicate.setValue(predicate);
        applyFiltersBasedOnUiState();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return eventTory.equals(otherModelManager.eventTory) && userPrefs.equals(otherModelManager.userPrefs)
                && filteredVendors.equals(otherModelManager.filteredVendors)
                && filteredEvents.equals(otherModelManager.filteredEvents);
    }

    // =========== Association Filters =============================================================
    private void applyFiltersBasedOnUiState() {
        Predicate<Vendor> vendorPredicate = getVendorPredicateForUiState();
        Predicate<Event> eventPredicate = getEventPredicateForUiState();

        filteredVendors.setPredicate(vendorPredicate);
        filteredEvents.setPredicate(eventPredicate);
    }

    private Predicate<Vendor> getVendorPredicateForUiState() {
        if (currentUiState.getValue() == UiState.EVENT_DETAILS) {
            Predicate<Vendor> notAssociatedPredicate = vendor ->
                !isVendorAssignedToEvent(vendor, selectedEvent.getValue());
            return combinePredicates(notAssociatedPredicate, suppliedVendorFilterPredicate.getValue());
        }
        return suppliedVendorFilterPredicate.getValue();
    }

    private Predicate<Event> getEventPredicateForUiState() {
        if (currentUiState.getValue() == UiState.VENDOR_DETAILS) {
            Predicate<Event> notAssociatedPredicate = event ->
                !isVendorAssignedToEvent(selectedVendor.getValue(), event);
            return combinePredicates(notAssociatedPredicate, suppliedEventFilterPredicate.getValue());
        }
        return suppliedEventFilterPredicate.getValue();
    }

    // =========== Viewed Vendor Accessors =============================================================

    @Override
    public ObservableObjectValue<Vendor> getViewedVendor() {
        return selectedVendor;
    }

    @Override
    public void viewVendor(Vendor vendor) {
        requireNonNull(vendor);
        selectedVendor.setValue(vendor);
    }

    // =========== Viewed Events Accessors =============================================================

    @Override
    public ObservableObjectValue<Event> getViewedEvent() {
        return selectedEvent;
    }

    @Override
    public void viewEvent(Event event) {
        requireNonNull(event);
        selectedEvent.setValue(event);
    }

    // =========== UI State Accessors =============================================================
    @Override
    public ObservableObjectValue<UiState> getUiState() {
        return currentUiState;
    }

    @Override
    public void setUiState(UiState uiState) {
        requireNonNull(uiState);
        currentUiState.setValue(uiState);
        applyFiltersBasedOnUiState();
    }

}
