package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Nric;

/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {
    private EditAppointmentDescriptor descriptor;
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
     * Sets the {@code Phone} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withStartTime(String args) {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DATE, PREFIX_START_TIME);

        LocalDate date = LocalDate.parse(argumentMultimap.getValue(PREFIX_DATE).get(), dateFormatter);
        LocalTime startTime = LocalTime.parse(argumentMultimap.getValue(PREFIX_START_TIME).get(), timeFormatter);

        descriptor.setStartTime(LocalDateTime.of(date, startTime));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withEndTime(String args) {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DATE, PREFIX_END_TIME);

        LocalDate date = LocalDate.parse(argumentMultimap.getValue(PREFIX_DATE).get(), dateFormatter);
        LocalTime endTime = LocalTime.parse(argumentMultimap.getValue(PREFIX_END_TIME).get(), timeFormatter);

        descriptor.setEndTime(LocalDateTime.of(date, endTime));
        return this;
    }

    public EditAppointmentDescriptor build() {
        return descriptor;
    }
}
