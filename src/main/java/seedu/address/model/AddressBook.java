package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.exceptions.DuplicateAssignException;
import seedu.address.model.exceptions.NotAssignedException;
import seedu.address.model.volunteer.UniqueVolunteerList;
import seedu.address.model.volunteer.Volunteer;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueVolunteerList volunteers;

    private final UniqueEventList events;


    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {

        volunteers = new UniqueVolunteerList();

        events = new UniqueEventList();

    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code volunteers}.
     * {@code volunteers} must not contain duplicate volunteers.
     */
    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers.setVolunteers(volunteers);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setVolunteers(newData.getVolunteerList());
        setEvents(newData.getEventList());
    }

    //// volunteer-level operations

    /**
     * Returns true if a volunteer with the same identity as {@code volunteer} exists in the volunteer book.
     */
    public boolean hasVolunteer(Volunteer volunteer) {
        requireNonNull(volunteer);
        return volunteers.contains(volunteer);
    }

    /**
     * Adds a volunteer to the volunteer book.
     * The volunteer must not already exist in the volunteer book.
     */
    public void addVolunteer(Volunteer v) {
        volunteers.add(v);
    }

    /**
     * Replaces the given volunteer {@code target} in the list with {@code editedVolunteer}.
     * {@code target} must exist in the volunteer book.
     * The volunteer identity of {@code editedVolunteer} must not be the same as another
     * existing volunteer in the volunteer book.
     */
    public void setVolunteer(Volunteer target, Volunteer editedVolunteer) {
        requireNonNull(editedVolunteer);

        volunteers.setVolunteer(target, editedVolunteer);
    }

    /**
     * Removes {@code key} from this {@code VolunteerBook}.
     * {@code key} must exist in the volunteer book.
     */
    public void removeVolunteer(Volunteer key) {
        events.removeParticipant(key.getName().fullName);
        volunteers.remove(key);
    }
    //// event level operations

    /**
     * Returns true if an event with the same identity as {@code event} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the address book.
     * The event must not already exist in the address book.
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the address book.
     *
     * NOTE: This method is used for editing events, which is NOT SUPPORTED YET
     * Wei Kiat Note: Renamed this due to style errors
     */
    public void setEventsTbd(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEvent(Event key) {
        volunteers.removeEvent(key.getName().toString());
        events.remove(key);
    }

    /**
     * Assigns a volunteer to an event.
     * @param v Volunteer to be assigned.
     * @param e Event to be assigned to.
     * @throws DuplicateAssignException if the volunteer is already assigned to the event.
     */
    public void assignVolunteerToEvent(Volunteer v, Event e) throws DuplicateAssignException {
    // Store the names to avoid duplication
        String volunteerName = v.getName().fullName;
        String eventName = e.getName().toString();
    
        // Check if the volunteer is already assigned to the event
        if (e.getVolunteers().contains(volunteerName)) {
            throw new DuplicateAssignException();
        }
    
        // Check if the event is already in the volunteer's list
        if (v.getEvents().contains(eventName)) {
            throw new DuplicateAssignException();
        }
    
        // Add the event and volunteer association
        v.addEvent(eventName);
        e.addParticipant(volunteerName);
    }

    /**
     * Unassigns a volunteer from an event.
     * @param v Volunteer to be unassigned.
     * @param e Event to be unassigned from.
     * @throws NotAssignedException if the volunteer is not assigned to the event.
     */
    public void unassignVolunteerFromEvent(Volunteer v, Event e) throws NotAssignedException {
        // Store the names to avoid duplication
        String volunteerName = v.getName().toString();
        String eventName = e.getName().toString();
    
        // Check if the volunteer is not assigned to the event
        if (!e.getVolunteers().contains(volunteerName)) {
            throw new NotAssignedException();
        }
        if (!v.getEvents().contains(eventName)) {
            throw new NotAssignedException();
        }
    
        // Remove the volunteer and event association
        v.removeEvent(eventName);
        e.removeParticipant(volunteerName);
    }

    //// util methods
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("volunteers", volunteers)
                .add("events", events)
                .toString();
    }

    @Override
    public ObservableList<Volunteer> getVolunteerList() {
        return volunteers.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return volunteers.equals(otherAddressBook.volunteers) && events.equals(otherAddressBook.events);
    }

    @Override
    public int hashCode() {
        return events.hashCode() + volunteers.hashCode();
    }
}
