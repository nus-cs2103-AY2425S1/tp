package seedu.address.model.volunteer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

public class Volunteer {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Date availableDate;
    private final Time startTimeAvailability;
    private final Time endTimeAvailability;



    public Volunteer(Name name, Phone phone, Email email, Date availableDate
                    , Time startTimeAvailability, Time endTimeAvailability) {
        requireAllNonNull(name, phone, email, availableDate, startTimeAvailability, endTimeAvailability);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.availableDate = availableDate;
        this.startTimeAvailability = startTimeAvailability;
        this.endTimeAvailability = endTimeAvailability;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public Time getStartTimeAvailability() {
        return startTimeAvailability;
    }

    public Time getEndTimeAvailability() {
        return endTimeAvailability;
    }

    public boolean isSameVolunteer(Volunteer otherVolunteer) {
        if (otherVolunteer == this) {
            return true;
        }
        return otherVolunteer != null
                && otherVolunteer.getName().equals(getName());
    }

    /**
     * Returns true if both volunteers have the same identity and data fields.
     * This defines a stronger notion of equality between two volunteers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Volunteer)) {
            return false;
        }

        Volunteer otherVolunteer = (Volunteer) other;
        return name.equals(otherVolunteer.name)
                && phone.equals(otherVolunteer.phone)
                && email.equals(otherVolunteer.email)
                && availableDate.equals(otherVolunteer.availableDate)
                && startTimeAvailability.equals(otherVolunteer.startTimeAvailability)
                && endTimeAvailability.equals(otherVolunteer.endTimeAvailability);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, availableDate, startTimeAvailability, endTimeAvailability);
    }

    @Override
    public String toString() {
        return "Volunteer{name=" + name
                + ", phone=" + phone
                + ", email=" + email
                + ", availableDate=" + availableDate
                + ", startTimeAvailability=" + startTimeAvailability
                + ", endTimeAvailability=" + endTimeAvailability + "}";
    }

}
