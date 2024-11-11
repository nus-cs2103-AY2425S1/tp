package seedu.eventtory.testutil;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import seedu.eventtory.commons.core.GuiSettings;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.ReadOnlyEventTory;
import seedu.eventtory.model.ReadOnlyUserPrefs;
import seedu.eventtory.model.association.Association;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.ui.UiState;

/**
 * A default model stub that has all the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getEventToryFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEventToryFilePath(Path eventToryFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addVendor(Vendor vendor) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEventTory(ReadOnlyEventTory newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyEventTory getEventTory() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasVendor(Vendor vendor) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteVendor(Vendor target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setVendor(Vendor target, Vendor editedVendor) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteEvent(Event target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Vendor> getFilteredVendorList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredVendorList(Predicate<Vendor> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isVendorAssignedToEvent(Vendor vendor, Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void assignVendorToEvent(Vendor vendor, Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void unassignVendorFromEvent(Vendor vendor, Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Vendor> getAssociatedVendors(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Event> getAssociatedEvents(Vendor vendor) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUiState(UiState uiState) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableObjectValue<UiState> getUiState() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void viewEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableObjectValue<Event> getViewedEvent() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void viewVendor(Vendor vendor) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableObjectValue<Vendor> getViewedVendor() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Association> getAssociationList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableIntegerValue getStartingIndexOfAssignedVendors() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableIntegerValue getStartingIndexOfAssignedEvents() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getRelativeIndexOfEvent(Event event) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getRelativeIndexOfVendor(Vendor vendor) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableIntegerValue getIndexOfSelectedObject() {
        throw new AssertionError("This method should not be called.");
    }
}
