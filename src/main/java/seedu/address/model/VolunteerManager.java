package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.event.Event;
import seedu.address.model.exceptions.DuplicateAssignException;
import seedu.address.model.exceptions.NotAssignedException;
import seedu.address.model.volunteer.UniqueVolunteerList;
import seedu.address.model.volunteer.Volunteer;

/**
 * Manages the volunteer-related logic.
 */
public class VolunteerManager {
    private final UniqueVolunteerList volunteers = new UniqueVolunteerList();

    /**
     * Replaces the contents of the person list with {@code volunteers}.
     * {@code volunteers} must not contain duplicate volunteers.
     */
    public void setVolunteers(List<Volunteer> volunteers) {
        this.volunteers.setVolunteers(volunteers);
    }

    public void addVolunteer(Volunteer volunteer) {
        volunteers.add(volunteer);
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

    public void removeVolunteer(Volunteer volunteer) {
        volunteers.remove(volunteer);
    }

    /**
     * Returns true if a volunteer with the same identity as {@code volunteer} exists in the volunteer book.
     */
    public boolean hasVolunteer(Volunteer volunteer) {
        requireNonNull(volunteer);
        return volunteers.contains(volunteer);
    }


    /**
     * Assigns an event to a volunteer.
     * @param volunteer Volunteer to be assigned.
     * @param event Event to be assigned to.
     * @throws DuplicateAssignException if the volunteer is already assigned to the event.
     */
    public void assignEventToVolunteer(Volunteer volunteer, Event event) throws DuplicateAssignException {
        if (volunteer.getEvents().contains(event.getName().toString())) {
            throw new DuplicateAssignException();
        }
        volunteer.addEvent(event.getName().toString());
    }

    /**
     * Unassign an event to a volunteer.
     * @param volunteer Volunteer to be assigned.
     * @param event Event to be assigned to.
     * @throws DuplicateAssignException if the volunteer is already assigned to the event.
     */
    public void unassignEventFromVolunteer(Volunteer volunteer, Event event) throws NotAssignedException {
        if (!volunteer.getEvents().contains(event.getName().toString())) {
            throw new NotAssignedException();
        }
        volunteer.removeEvent(event.getName().toString());
    }

    public void unassignEventFromAllVolunteers(Event event) {
        volunteers.forEach(volunteer -> volunteer.removeEvent(event.getName().toString()));
    }

    public UniqueVolunteerList getVolunteers() {
        return volunteers;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VolunteerManager)) {
            return false;
        }

        VolunteerManager otherManager = (VolunteerManager) other;
        return volunteers.equals(otherManager.volunteers);
    }

    @Override
    public int hashCode() {
        return volunteers.hashCode();
    }

}
