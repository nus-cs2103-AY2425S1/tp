package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an appointment's details in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class AppointmentDescriptor {
    private final AppointmentType appointmentType;
    private final LocalDateTime appointmentDateTime;
    private final int personId;

    // Data fields
    private final Sickness sickness;
    private final Medicine medicine;

    /**
     * Every field must be present and not null.
     */
    public AppointmentDescriptor(
            AppointmentType appointmentType,
            LocalDateTime appointmentDateTime,
            int personId,
            Sickness sickness,
            Medicine medicine) {
        requireAllNonNull(appointmentType, personId, sickness, medicine);
        this.appointmentType = appointmentType;
        this.appointmentDateTime = appointmentDateTime;
        this.personId = personId;
        this.sickness = sickness;
        this.medicine = medicine;
    }

    public int getPersonId() {
        return personId;
    }

    public AppointmentType getAppointmentType() {
        return this.appointmentType;
    }

    public LocalDateTime getAppointmentDateTime() {
        return this.appointmentDateTime;
    }

    public Sickness getSickness() {
        return this.sickness;
    }

    public Medicine getMedicine() {
        return this.medicine;
    }

    /**
     * Returns true if both appointments have the same personId and appointmentDateTime.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(AppointmentDescriptor otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                && otherAppointment.getPersonId() == getPersonId()
                && otherAppointment.getAppointmentDateTime().equals(getAppointmentDateTime());
    }

    /**
     * Returns true if both appointments have the same name.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        return otherAppointment != null
                && otherAppointment.getPersonId() == getPersonId()
                && otherAppointment.getAppointmentDateTime().equals(getAppointmentDateTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentDescriptor)) {
            return false;
        }

        AppointmentDescriptor otherAppointment = (AppointmentDescriptor) other;
        return appointmentType.equals(otherAppointment.getAppointmentType())
                && appointmentDateTime.equals(otherAppointment.getAppointmentDateTime())
                && personId == otherAppointment.getPersonId()
                && sickness.equals(otherAppointment.getSickness())
                && medicine.equals(otherAppointment.getMedicine());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(appointmentType, appointmentDateTime, personId, sickness, medicine);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Appointment Type", appointmentType)
                .add("Appointment Date-Time", appointmentDateTime)
                .add("Person Id", personId)
                .add("Sickness", sickness)
                .add("medicine", medicine)
                .toString();
    }
}
