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
    private final Sickness sickness;
    private final Medicine medicine;

    /**
     * Every field must be present and not null.
     */
    public AppointmentDescriptor(
            AppointmentType appointmentType,
            LocalDateTime appointmentDateTime,
            Sickness sickness,
            Medicine medicine) {
        requireAllNonNull(appointmentType, appointmentDateTime, sickness, medicine);
        this.appointmentType = appointmentType;
        this.appointmentDateTime = appointmentDateTime;
        this.sickness = sickness;
        this.medicine = medicine;
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
     * Returns true if both appointments have the same appointmentDateTime and appointmentType.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(AppointmentDescriptor otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        // todo: add other checks for equality
        return otherAppointment != null
                && otherAppointment.getAppointmentDateTime().equals(getAppointmentDateTime())
                && otherAppointment.getAppointmentType().equals(getAppointmentType());
    }

    /**
     * Returns true if both appointments have the same appointmentDateTime and appointmentType.
     * This defines a weaker notion of equality between two appointments.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        return otherAppointment != null
                && otherAppointment.getAppointmentDateTime().equals(getAppointmentDateTime())
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
                && sickness.equals(otherAppointment.getSickness())
                && medicine.equals(otherAppointment.getMedicine());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(appointmentType, appointmentDateTime, sickness, medicine);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("appointment type", appointmentType)
                .add("appointment datetime", appointmentDateTime)
                .add("sickness", sickness)
                .add("medicine", medicine)
                .toString();
    }
}
