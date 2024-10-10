package seedu.address.model.appointment;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Appointment {

    private final Date date;
    private final From from;
    private final To to;

    public Appointment(Date date, From from, To to) {
        requireAllNonNull(date, from, to);
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public From getFrom() {
        return from;
    }

    public To getTo() {
        return to;
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

        Appointment a = (Appointment) other;
        return date.equals(a.date)
                && from.equals(a.from)
                 && to.equals(a.to);
    }

    public boolean isEmpty() {
        return date.value.isEmpty() && from.value.isEmpty() && to.value.isEmpty();
    }
    @Override
    public String toString() {
        return String.format("Date: %s, From: %s, To: %s", date.value, from.value, to.value);
    }

    @Override
    public int hashCode() {
        return from.hashCode();
    }
}
