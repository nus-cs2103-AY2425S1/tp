package seedu.address.model.event;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;

/**
 * Represents an Event's list of participants in the address book.
 */
public class EventParticipants {

    public final ArrayList<Person> participants;

    /**
     * Constructs a {@code EventParticipants}.
     */
    public EventParticipants() {
        this.participants = new ArrayList<>();
    }

    @Override
    public String toString() {
        return participants.stream()
                .map(person -> person.getName().toString())
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventParticipants)) {
            return false;
        }

        EventParticipants otherEventParticipants = (EventParticipants) other;
        return participants.equals(otherEventParticipants.participants);
    }

    @Override
    public int hashCode() {
        return participants.hashCode();
    }

    public List<Person> getParticipants() {
        return this.participants;
    }

}
