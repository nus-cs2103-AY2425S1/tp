package seedu.address.model.volunteer;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Volunteer in the system.
 */
public class Volunteer {

    private static int NEXT_ID = 0;

    // Identity fields
    private final int id;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Date availableDate;
    private final Time startTimeAvailability;
    private final Time endTimeAvailability;

    /**
     * Represents a Volunteer in the system.
     *
     * @param name The name of the volunteer.
     * @param phone The phone number of the volunteer.
     * @param email The email address of the volunteer.
     * @param availableDate The date the volunteer is available.
     * @param startTimeAvailability The start time of the volunteer's availability.
     * @param endTimeAvailability The end time of the volunteer's availability.
     */
    public Volunteer(Name name, Phone phone, Email email, Date availableDate,
                     Time startTimeAvailability, Time endTimeAvailability) {
        requireAllNonNull(name, phone, email, availableDate, startTimeAvailability, endTimeAvailability);
        this.id = NEXT_ID;
        NEXT_ID++;

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.availableDate = availableDate;
        this.startTimeAvailability = startTimeAvailability;
        this.endTimeAvailability = endTimeAvailability;
    }

    public int getId() { return id; }

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

    /**
     * Returns true if both volunteers have the same name.
     * This defines a weaker notion of equality between two volunteers.
     *
     * @param otherVolunteer The other volunteer to compare with.
     * @return True if both volunteers have the same name.
     */
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
