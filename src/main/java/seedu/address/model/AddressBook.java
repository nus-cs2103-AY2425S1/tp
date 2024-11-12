package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.exceptions.DuplicateAssignException;
import seedu.address.model.exceptions.NotAssignedException;
import seedu.address.model.exceptions.OverlappingAssignException;
import seedu.address.model.exceptions.VolunteerDeleteMissingDateException;
import seedu.address.model.exceptions.VolunteerDuplicateDateException;
import seedu.address.model.exceptions.VolunteerIsAssignedToUnfreeDayTargetException;
import seedu.address.model.exceptions.VolunteerNotAvailableOnAnyDayException;
import seedu.address.model.volunteer.Volunteer;

/**
 * Represents the top-level AddressBook. It holds managers responsible for volunteers and events.
 */
public class AddressBook implements ReadOnlyAddressBook {
    private final EventManager eventManager;
    private final VolunteerManager volunteerManager;

    /**
     * Creates an AddressBook with empty managers.
     */
    public AddressBook() {
        eventManager = new EventManager();
        volunteerManager = new VolunteerManager();
    }

    /**
     * Creates an AddressBook using the data in the {@code toBeCopied}.
     *
     * @param toBeCopied A ReadOnlyAddressBook whose data will be copied.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this(); // Initialize with empty managers
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this AddressBook with {@code newData}.
     *
     * @param newData The new data to be copied.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        volunteerManager.setVolunteers(newData.getVolunteerList());
        eventManager.setEvents(newData.getEventList());
    }

    /**
     * Validates all data in the address book.
     */
    public void validateAllData() {
        validateAllVolunteers();
        validateAllEvents();
        removeOverlappingEvents();
    }

    /**
     * Validates all volunteers in all events. If a volunteer has no records in the json file, they are unassigned.
     * This is to prevent non-existent volunteers from being assigned to events when the data is loaded from the json
     * file, in the event that the json file is corrupted or tampered with.
     */
    public void validateAllVolunteers() {
        boolean hasUnassignedVolunteer;
        do {
            hasUnassignedVolunteer = false;
            for (Event event : eventManager.getEvents()) {
                for (String volunteerName : event.getVolunteers()) {
                    if (!volunteerManager.hasVolunteer(volunteerName)) {
                        event.unassignVolunteer(volunteerName);
                        hasUnassignedVolunteer = true;
                        break;
                    }
                }
                if (hasUnassignedVolunteer) {
                    break;
                }
            }
        } while (hasUnassignedVolunteer);
    }

    /**
     * Validates all events volunteers are involved in. If an event has no records in the json file,
     * they are unassigned.
     * This is to prevent volunteers from being assigned to non-existent events when the data is loaded from the json
     * file, in the event that the json file is corrupted or tampered with.
     */
    public void validateAllEvents() {
        boolean hasRemovedEvent;
        do {
            hasRemovedEvent = false;
            for (Volunteer volunteer : volunteerManager.getVolunteers()) {
                for (String eventName : volunteer.getEvents()) {
                    if (!eventManager.hasEvent(eventName)) {
                        volunteer.removeEvent(eventName);
                        hasRemovedEvent = true;
                        break;
                    }
                }
                if (hasRemovedEvent) {
                    break;
                }
            }
        } while (hasRemovedEvent);
    }

    /**
     * Runs through all events that volunteers are assigned to and checks for overlapping events.
     * Removes the first event that overlaps with another event.
     */
    public void removeOverlappingEvents() {
        // check to ensure that no volunteer is assigned to overlapping events
        // this is only used when the data is being reset
        for (Volunteer v : volunteerManager.getVolunteers()) {
            ObservableList<String> involvedIn = v.getEvents();

            // get the list of events that the volunteer is involved in
            List<Event> eventsInvolvedIn = new LinkedList<>();
            for (String eventName : involvedIn) {
                eventsInvolvedIn.add(eventManager.getEventFromName(eventName));
            }

            // check if any events are overlapping
            for (Event event : eventsInvolvedIn) {
                boolean hasOverlappingEvents = false;
                for (Event otherEvent : eventsInvolvedIn) {
                    if (event != otherEvent && event.isOverlappingWith(otherEvent)) {
                        unassignVolunteerFromEvent(v, event);
                        eventsInvolvedIn.remove(event);
                        hasOverlappingEvents = true;
                    }
                }
                if (hasOverlappingEvents) {
                    break;
                }
            }
        }
    }

    /**
     * Adds a volunteer to the volunteer manager.
     */
    public void addVolunteer(Volunteer volunteer) {
        volunteerManager.addVolunteer(volunteer);
    }

    /**
     * Replaces the given volunteer {@code target} in the list with {@code editedVolunteer}.
     * {@code target} must exist in the volunteer book.
     * The volunteer identity of {@code editedVolunteer} must not be the same as another
     * existing volunteer in the volunteer book.
     */
    public void setVolunteer(Volunteer target, Volunteer editedVolunteer) {
        volunteerManager.setVolunteer(target, editedVolunteer);
    }

    /**
     * Removes a volunteer and unassign them from all events.
     */
    public void removeVolunteer(Volunteer volunteer) {
        volunteerManager.removeVolunteer(volunteer);
        eventManager.unassignVolunteerFromAllEvents(volunteer);
    }

    /**
     * Returns true if a volunteer with the same identity as {@code volunteer} exists in the volunteer book.
     */
    public boolean hasVolunteer(Volunteer volunteer) {
        return volunteerManager.hasVolunteer(volunteer);
    }

    /**
     * Adds an event to the event manager.
     */
    public void addEvent(Event event) {
        eventManager.addEvent(event);
    }

    /**
     * Removes an event and unassign all volunteers from it.
     */
    public void removeEvent(Event event) {
        volunteerManager.unassignEventFromAllVolunteers(event);
        eventManager.removeEvent(event);
    }

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        return eventManager.hasEvent(event);
    }

    /**
     * Assigns a volunteer to an event.
     */
    public void assignVolunteerToEvent(Volunteer volunteer, Event event) throws DuplicateAssignException,
            OverlappingAssignException {
        eventManager.assignVolunteerToEvent(volunteer, event);
        volunteerManager.assignEventToVolunteer(volunteer, event);
    }

    /**
     * Unassign a volunteer from an event.
     */
    public void unassignVolunteerFromEvent(Volunteer volunteer, Event event) throws NotAssignedException {
        eventManager.unassignVolunteerFromEvent(volunteer, event);
        volunteerManager.unassignEventFromVolunteer(volunteer, event);
    }

    public ObservableList<Volunteer> getVolunteerList() {
        return volunteerManager.getVolunteers().asUnmodifiableObservableList();
    }

    public ObservableList<Event> getEventList() {
        return eventManager.getEvents().asUnmodifiableObservableList();
    }

    @Override
    public List<Event> getEventFromListOfNames(ObservableList<String> stringOfEvents) {
        return this.eventManager.getEventsFromListOfNames(stringOfEvents);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls and ensures the correct type
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;

        // Compare the volunteerManager and eventManager directly
        return volunteerManager.equals(otherAddressBook.volunteerManager)
                && eventManager.equals(otherAddressBook.eventManager);
    }

    @Override
    public int hashCode() {
        return volunteerManager.hashCode() * 31 + eventManager.hashCode();
    }

    public void addDatesToVolunteer(Volunteer volunteerToAddDate, String dateList) throws
            VolunteerDuplicateDateException {
        volunteerManager.addDatesToVolunteer(volunteerToAddDate, dateList);
    }

    /**
     * Removes free dates from a volunteer
     * @param volunteerToRemoveDate
     * @param dateList
     * @throws VolunteerDeleteMissingDateException
     * @throws VolunteerNotAvailableOnAnyDayException
     * @throws VolunteerIsAssignedToUnfreeDayTargetException
     */
    public void removeDatesFromVolunteer(Volunteer volunteerToRemoveDate, String dateList) throws
            VolunteerDeleteMissingDateException, VolunteerNotAvailableOnAnyDayException,
            VolunteerIsAssignedToUnfreeDayTargetException {
        List<Event> participatingEvents = getEventFromListOfNames(volunteerToRemoveDate.getEvents());
        volunteerManager.removeDatesFromVolunteer(volunteerToRemoveDate, dateList, participatingEvents);
    }
}
