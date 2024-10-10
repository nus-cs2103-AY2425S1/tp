package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.meetup.MeetUp;
import seedu.address.model.person.Person;

/**
 * Represents a Schedule in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Schedule {

    // Identity fields
    private final Person person;
    private final MeetUp meetUp;

    /**
     * Every field must be present and not null.
     */
    public Schedule(Person person, MeetUp meetUp) {
        requireAllNonNull(person, meetUp);
        this.person = person;
        this.meetUp = meetUp;
    }

    public Person getPerson() {
        return person;
    }

    public MeetUp getMeetUp() {
        return meetUp;
    }

    /**
     * Returns true if Person is involved in this schedule.
     */
    public boolean isPersonInvolved(Person person) {
        return this.person.equals(person);
    }

    /**
     * Returns true if MeetUp is involved in this schedule
     */
    public boolean isMeetupInvolved(MeetUp meetup) {
        return this.meetUp.equals(meetup);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two schedule.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return person.equals(otherSchedule.person)
                && meetUp.equals(otherSchedule.meetUp);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(person, meetUp);
    }

    @Override
    public String toString() {
        return String.format("%s is scheduled for %s", person.toString(), meetUp.toString());
    }

}
