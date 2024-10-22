package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Volunteer> PREDICATE_SHOW_ALL_VOLUNTEERS = unused -> true;
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
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a volunteer with the same identity as {@code volunteer} exists in the address book.
     */
    boolean hasVolunteer(Volunteer volunteer);

    /**
     * Returns true if a Event with the same identity as {@code event} exists in the address book.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given volunteer.
     * The volunteer must exist in the address book.
     */
    void deleteVolunteer(Volunteer target);

    /**
     * Deletes the given event.
     * The event must exist in the address book.
     */
    void deleteEvent(Event event);

    void resetDisplayLists();

    /**
     * Adds the given volunteer.
     * {@code volunteer} must not already exist in the address book.
     */
    void addVolunteer(Volunteer volunteer);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the address book.
     */
    void addEvent(Event event);

    /**
     * Replaces the given volunteer {@code target} with {@code editedVolunteer}.
     * {@code target} must exist in the address book.
     * The volunteer identity of {@code editedVolunteer} must not be the same
     * as another existing volunteer in the address book.
     */
    void setVolunteer(Volunteer volunteer, Volunteer editedVolunteer);

    /** Returns an unmodifiable view of the filtered volunteer list */
    ObservableList<Volunteer> getFilteredVolunteerList();

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered volunteer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredVolunteerList(Predicate<Volunteer> predicate);

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Retrieves a volunteer by their unique ID.
     * @param volunteerId The ID of the volunteer to retrieve.
     * @return The volunteer with the matching ID, or null if not found.
     */
    Volunteer getVolunteer(int volunteerId);

    /**
     * Retrieves an event by its unique ID.
     * @param eventId The ID of the event to retrieve.
     * @return The event with the matching ID, or null if not found.
     */
    Event getEvent(int eventId);

    /**
     * Displays the full list of volunteers participating in the event.
     * @param eventToView The event to view.
     */
    void viewEvent(Event eventToView);

    /**
     * Displays the full list of events that the volunteer is participating in.
     * @param volunteerToView The volunteer to view.
     */
    void viewVolunteer(Volunteer volunteerToView);

    /**
     * Assigns a volunteer to an event.
     * @param volunteer The volunteer to assign.
     * @param event The event to assign the volunteer to.
     * @throws CommandException If the volunteer is already assigned to the event.
     */
    void assignVolunteerToEvent(Volunteer volunteer, Event event) throws CommandException;

    /**
     * Unassigns a volunteer from an event.
     * @param volunteer The volunteer to unassign.
     * @param event The event to unassign the volunteer from.
     * @throws CommandException If the volunteer is not assigned to the event.
     */
    void unassignVolunteerFromEvent(Volunteer volunteer, Event event) throws CommandException;
}
