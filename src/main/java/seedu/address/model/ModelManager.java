package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventParticipatedByVolunteerPredicate;
import seedu.address.model.exceptions.DuplicateAssignException;
import seedu.address.model.exceptions.OverlappingAssignException;
import seedu.address.model.exceptions.VolunteerDeleteMissingDateException;
import seedu.address.model.exceptions.VolunteerDuplicateDateException;
import seedu.address.model.exceptions.VolunteerIsAssignedToUnfreeDayTargetException;
import seedu.address.model.exceptions.VolunteerNotAvailableException;
import seedu.address.model.exceptions.VolunteerNotAvailableOnAnyDayException;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.VolunteerInvolvedInEventPredicate;
import seedu.address.model.volunteer.VolunteerIsAvailableForEventPredicate;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Volunteer> filteredVolunteers;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredVolunteers = new FilteredList<>(this.addressBook.getVolunteerList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasVolunteer(Volunteer volunteer) {
        requireNonNull(volunteer);
        return addressBook.hasVolunteer(volunteer);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return addressBook.hasEvent(event);
    }

    @Override
    public void deleteVolunteer(Volunteer target) {
        addressBook.removeVolunteer(target);
    }

    @Override
    public void deleteEvent(Event target) {
        addressBook.removeEvent(target);
    }

    @Override
    public void resetDisplayLists() {
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);
    }

    @Override
    public void addVolunteer(Volunteer volunteer) {
        addressBook.addVolunteer(volunteer);
        updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);
    }

    @Override
    public void addEvent(Event event) {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setVolunteer(Volunteer target, Volunteer editedVolunteer) {
        requireAllNonNull(target, editedVolunteer);

        addressBook.setVolunteer(target, editedVolunteer);
    }

    @Override
    public Volunteer getVolunteer(int volunteerId) {
        return filteredVolunteers.stream()
                .filter(volunteer -> volunteer.getId() == volunteerId)
                .findFirst()
                .orElse(null); // Return null if not found
    }

    @Override
    public Event getEvent(int eventId) {
        return filteredEvents.stream()
                .filter(event -> event.getId() == eventId)
                .findFirst()
                .orElse(null); // Return null if not found
    }

    /**
     * Displays the full list of volunteers participating in the event.
     * @param eventToView The event to view.
     */
    @Override
    public void viewEvent(Event eventToView) {
        String eventName = eventToView.getName().toString();
        VolunteerInvolvedInEventPredicate volsInEventPredicate = new VolunteerInvolvedInEventPredicate(eventName);
        filteredVolunteers.setPredicate(volsInEventPredicate);
    }

    @Override
    public void filterEvent(Event event) {
        VolunteerIsAvailableForEventPredicate volIsAvail = new VolunteerIsAvailableForEventPredicate(event,
                addressBook);
        filteredVolunteers.setPredicate(volIsAvail);
    }

    /**
     * Displays the full list of events the volunteer is participating in.
     * @param volunteerToView The volunteer to view.
     */
    @Override
    public void viewVolunteer(Volunteer volunteerToView) {
        String volunteerName = volunteerToView.getName().toString();
        EventParticipatedByVolunteerPredicate eventInVolPredicate =
                new EventParticipatedByVolunteerPredicate(volunteerName);
        filteredEvents.setPredicate(eventInVolPredicate);
    }

    /**
     * Assigns a volunteer to an event.
     * @param volunteer Volunteer to be assigned.
     * @param event Event to be assigned to.
     */
    @Override
    public void assignVolunteerToEvent(Volunteer volunteer, Event event) throws DuplicateAssignException,
            OverlappingAssignException, VolunteerNotAvailableException {
        VolunteerIsAvailableForEventPredicate volIsAvail = new VolunteerIsAvailableForEventPredicate(event,
                addressBook);
        if (!volIsAvail.test(volunteer)) {
            throw new VolunteerNotAvailableException(event.getName().toString());
        }
        addressBook.assignVolunteerToEvent(volunteer, event);
    }

    /**
     * Unassigns a volunteer from an event.
     * @param volunteer The volunteer to unassign.
     * @param event The event to unassign the volunteer from.
     * @throws CommandException
     */
    @Override
    public void unassignVolunteerFromEvent(Volunteer volunteer, Event event) throws CommandException {
        addressBook.unassignVolunteerFromEvent(volunteer, event);
    }

    @Override
    public void addDatesToVolunteer(Volunteer volunteerToAddDate, String dateList) throws
            VolunteerDuplicateDateException {
        addressBook.addDatesToVolunteer(volunteerToAddDate, dateList);
    }

    @Override
    public void removeDatesFromVolunteer(Volunteer volunteerToRemoveDate, String dateList) throws
            VolunteerDeleteMissingDateException, VolunteerNotAvailableOnAnyDayException,
            VolunteerIsAssignedToUnfreeDayTargetException {
        addressBook.removeDatesFromVolunteer(volunteerToRemoveDate, dateList);
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Volunteer} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Volunteer> getFilteredVolunteerList() {
        return filteredVolunteers;
    }

    /**
     * Updates the filter of the filtered volunteer list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredVolunteerList(Predicate<Volunteer> predicate) {
        requireNonNull(predicate);
        filteredVolunteers.setPredicate(predicate);
    }

    /**
     * Filters the list of volunteers to only include those matching the given predicate.
     * If no volunteers match the predicate, this method resets the filtered list to show all volunteers.
     *
     * @param predicate The condition used to filter volunteers.
     * @return {@code true} if any volunteers match the predicate, else {@code false}.
     */
    public boolean filterVolunteersByName(Predicate<Volunteer> predicate) {
        filteredVolunteers.setPredicate(predicate);
        // Check if any volunteers match the predicate
        boolean hasMatches = !filteredVolunteers.isEmpty();

        // If no volunteers match, reset to show all volunteers
        if (!hasMatches) {
            filteredVolunteers.setPredicate(PREDICATE_SHOW_ALL_VOLUNTEERS);
        }

        return hasMatches;
    }




    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }
    /**
     * Filters the list of events to only include those matching the given predicate.
     * If no events match the predicate, this method resets the filtered list to show all events.
     *
     * @param predicate The condition used to filter events.
     * @return {@code true} if any events match the predicate else {@code false}
     */
    public boolean filterEventsByName(Predicate<Event> predicate) {
        filteredEvents.setPredicate(predicate);
        // Check if any events match the predicate
        boolean hasMatches = !filteredEvents.isEmpty();

        // If no events match, reset to show all events
        if (!hasMatches) {
            filteredEvents.setPredicate(PREDICATE_SHOW_ALL_EVENTS);
        }

        return hasMatches;
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
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredVolunteers.equals(otherModelManager.filteredVolunteers)
                && filteredEvents.equals(otherModelManager.filteredEvents);
    }

}
