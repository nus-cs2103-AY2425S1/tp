package seedu.address.model.appointment;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Appointment {

    private final Person client;
    private final Start start;
    private final End end;

    public Appointment(Person client, Start start, End end) {
        requireAllNonNull(client, start, end);
        this.client = client;
        this.start = start;
        this.end = end;
    }

    public End getEnd() {
        return end;
    }

    public Start getStart() {
        return start;
    }

    public Person getClient() {
        return client;
    }

    public boolean isSameAppointment(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                && otherAppointment.equals(this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("client", client)
                .add("start", start)
                .add("end", end)
                .toString();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(client, start, end);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        
        // instanceof handles nulls
        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getClient().equals(this.getClient())
                && otherAppointment.getStart().equals(this.getStart())
                && otherAppointment.getEnd().equals(this.getEnd());
    }
}