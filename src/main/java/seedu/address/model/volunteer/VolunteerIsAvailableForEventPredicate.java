package seedu.address.model.volunteer;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;

/**
 * Tests that a {@code Volunteer}'s {@code involvedIn} contains the event name given.
 */
public class VolunteerIsAvailableForEventPredicate implements Predicate<Volunteer> {

    private final Event event;
    private final ReadOnlyAddressBook addressBook;

    /**
     * Constructor for the predicate
     * @param event
     * @param addressBook
     */
    public VolunteerIsAvailableForEventPredicate(Event event, ReadOnlyAddressBook addressBook) {
        this.event = event;
        this.addressBook = addressBook;
    }

    @Override
    public boolean test(Volunteer volunteer) {
        if (volunteer.isInvolvedInEvent(event.getName().toString())) {
            return false;
        }

        if (!volunteer.isFreeOn(event.getDate().date)) {
            return false;
        }

        List<Event> volnEvents = this.addressBook.getEventFromListOfNames(volunteer.getEvents());

        return !this.addressBook.eventHasOverlapWithList(event, volnEvents);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerIsAvailableForEventPredicate)) {
            return false;
        }

        VolunteerIsAvailableForEventPredicate otherVolInEventPredicate = (VolunteerIsAvailableForEventPredicate) other;
        return event.equals(otherVolInEventPredicate.event) && addressBook.equals(otherVolInEventPredicate.addressBook);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("event name", event.getName().toString()).toString();
    }

}
