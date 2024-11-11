package seedu.eventtory.logic;

import java.nio.file.Path;

import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.ObservableList;
import seedu.eventtory.commons.core.GuiSettings;
import seedu.eventtory.logic.commands.CommandResult;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.logic.parser.exceptions.ParseException;
import seedu.eventtory.model.ReadOnlyEventTory;
import seedu.eventtory.model.association.Association;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.ui.UiState;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the EventTory.
     * @see seedu.eventtory.model.Model#getEventTory()
     */
    ReadOnlyEventTory getEventTory();

    /** Returns an unmodifiable view of the filtered list of vendors */
    ObservableList<Vendor> getFilteredVendorList();

    /** Returns an unmodifiable view of the filtered list of events */
    ObservableList<Event> getFilteredEventList();

    /** Returns a view of the current selected vendor */
    ObservableObjectValue<Vendor> getViewedVendor();

    /** Returns a view of the current selected vendor */
    ObservableObjectValue<Event> getViewedEvent();

    /** Returns the current state of the UI */
    ObservableObjectValue<UiState> getUiState();

    /** Returns list of associated events */
    ObservableList<Event> getAssociatedEvents(Vendor vendor);

    /** Returns list of associated vendors */
    ObservableList<Vendor> getAssociatedVendors(Event event);

    /** Returns list of associations */
    ObservableList<Association> getAssociationList();

    /** Return the display index of the first vendor in the assigned list */
    ObservableIntegerValue getStartingIndexOfAssignedVendors();

    /** Return the display index of the first event in the assigned list */
    ObservableIntegerValue getStartingIndexOfAssignedEvents();

    /** Return the display index of the given vendor in the filtered list */
    int getRelativeIndexOfVendor(Vendor vendor);

    /** Return the display index of the given event in the filtered list */
    int getRelativeIndexOfEvent(Event event);

    /** Return the display index of the selected object */
    ObservableIntegerValue getIndexOfSelectedObject();

    /**
     * Returns the user prefs' EventTory file path.
     */
    Path getEventToryFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
