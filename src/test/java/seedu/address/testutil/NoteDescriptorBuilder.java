package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.NoteCommand.NoteDescriptor;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building NoteDescriptor objects.
 */
public class NoteDescriptorBuilder {

    private NoteDescriptor descriptor;

    public NoteDescriptorBuilder() {
        descriptor = new NoteDescriptor();
    }

    public NoteDescriptorBuilder(NoteDescriptor descriptor) {
        this.descriptor = new NoteDescriptor(descriptor);
    }

    /**
     * Returns an {@code NoteDescriptor} with fields containing {@code person}'s details
     */
    public NoteDescriptorBuilder(Person person) {
        descriptor = new NoteDescriptor();
        descriptor.setAppointments(person.getNote().previousAppointments);
        descriptor.setMedications(person.getNote().medications);
        descriptor.setRemarks(person.getNote().remarks);
    }

    /**
     * Parses the {@code appointments} into a {@code Set<Appointment>} and set it to the {@code NoteDescriptor}
     * that we are building.
     */
    public NoteDescriptorBuilder withAppointments(String... appointments) {
        Set<Appointment> appointmentSet = Stream.of(appointments).map(Appointment::new).collect(Collectors.toSet());
        descriptor.setAppointments(appointmentSet);
        return this;
    }

    /**
     * Parses the {@code medications} into a {@code Set<String>} and set it to the {@code NoteDescriptor}
     * that we are building.
     */
    public NoteDescriptorBuilder withMedications(String... medications) {
        Set<String> medicationSet = Stream.of(medications).collect(Collectors.toSet());
        descriptor.setMedications(medicationSet);
        return this;
    }

    /**
     * Parses the {@code remarks} into a {@code Set<Appointment>} and set it to the {@code NoteDescriptor}
     * that we are building.
     */
    public NoteDescriptorBuilder withRemarks(String... remarks) {
        Set<String> remarkSet = Stream.of(remarks).collect(Collectors.toSet());
        descriptor.setRemarks(remarkSet);
        return this;
    }

    public NoteDescriptor build() {
        return descriptor;
    }
}
