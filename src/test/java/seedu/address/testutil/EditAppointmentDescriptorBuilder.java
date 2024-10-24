package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;

/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {
    private EditAppointmentDescriptor descriptor;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public EditAppointmentDescriptorBuilder() {
        descriptor = new EditAppointmentDescriptor();
    }

    public EditAppointmentDescriptorBuilder(EditAppointmentDescriptor descriptor) {
        this.descriptor = new EditAppointmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAppointmentDescriptor} with fields containing {@code appointment}'s details
     */
    public EditAppointmentDescriptorBuilder(Appointment appointment) {
        descriptor = new EditAppointmentDescriptor();
        descriptor.setName(appointment.getName());
        descriptor.setNric(appointment.getNric());
        descriptor.setStartTime(appointment.getStartTime());
        descriptor.setEndTime(appointment.getEndTime());
    }

    /**
     * Sets the {@code Name} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withName(String name) {
        descriptor.setName(name);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withNric(String nric) {
        descriptor.setNric(new Nric(nric));
        return this;
    }

    /**
     * Sets the {@code date} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withDate(String date) {
        this.date = LocalDate.parse(date, dateFormatter);
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime, timeFormatter);
        System.out.println("here" + this.date + this.startTime);
        if (this.date != null && this.startTime != null) {
            descriptor.setStartTime(LocalDateTime.of(date, this.startTime));
        }
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime, timeFormatter);
        if (this.date != null && this.endTime != null) {
            descriptor.setEndTime(LocalDateTime.of(date, this.endTime));
        }
        return this;
    }

    /**
     * Builds the EditAppointmentDescriptor object.
     * @return
     */
    public EditAppointmentDescriptor build() {
        return descriptor;
    }
}
