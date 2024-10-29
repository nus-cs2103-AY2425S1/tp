package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand.UpdateStudentDescriptor;
import seedu.address.model.student.Address;
import seedu.address.model.student.EmergencyContact;
import seedu.address.model.student.LessonTime;
import seedu.address.model.student.Level;
import seedu.address.model.student.Name;
import seedu.address.model.student.Note;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;

/**
 * A utility class to help with building UpdateStudentDescriptor objects.
 */
public class UpdateStudentDescriptorBuilder {

    private UpdateStudentDescriptor descriptor;

    public UpdateStudentDescriptorBuilder() {
        descriptor = new UpdateStudentDescriptor();
    }

    public UpdateStudentDescriptorBuilder(UpdateStudentDescriptor descriptor) {
        this.descriptor = new UpdateStudentDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdateStudentDescriptor} with fields containing {@code student}'s details
     */
    public UpdateStudentDescriptorBuilder(Student student) {
        descriptor = new UpdateStudentDescriptor();
        descriptor.setName(student.getName());
        descriptor.setPhone(student.getPhone());
        descriptor.setEmergencyContact(student.getEmergencyContact());
        descriptor.setAddress(student.getAddress());
        descriptor.setNote(student.getNote());
        descriptor.setLevel(student.getLevel());
        descriptor.setSubjects(student.getSubjects());
        descriptor.setLessonTimes(student.getLessonTimes());
    }

    /**
     * Sets the {@code Name} of the {@code UpdateStudentDescriptor} that we are building.
     */
    public UpdateStudentDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code UpdateStudentDescriptor} that we are building.
     */
    public UpdateStudentDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code EmergencyContact} of the {@code UpdateStudentDescriptor} that we are building.
     */
    public UpdateStudentDescriptorBuilder withEmergencyContact(String emergencyContact) {
        descriptor.setEmergencyContact(new EmergencyContact(emergencyContact));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code UpdateStudentDescriptor} that we are building.
     */
    public UpdateStudentDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Note} of the {@code UpdateStudentDescriptor} that we are building.
     */
    public UpdateStudentDescriptorBuilder withNote(String note) {
        descriptor.setNote(new Note(note));
        return this;
    }

    /**
     * Parses the {@code subjects} into a {@code Set<Subject>} and set it to the {@code UpdateStudentDescriptor}
     * that we are building.
     */
    public UpdateStudentDescriptorBuilder withSubjects(String... subjects) {
        Set<Subject> subjectSet = Stream.of(subjects).map(Subject::new).collect(Collectors.toSet());
        descriptor.setSubjects(subjectSet);
        return this;
    }

    /**
     * Sets the {@code Level} of the {@code UpdateStudentDescriptor} that we are building.
     */
    public UpdateStudentDescriptorBuilder withLevel(String level) {
        descriptor.setLevel(new Level(level));
        return this;
    }

    /**
     * Parses the {@code lessonTimes} into a {@code Set<LessonTime>} and set it to the {@code UpdateStudentDescriptor}
     * that we are building.
     */
    public UpdateStudentDescriptorBuilder withLessonTimes(String... lessonTimes) {
        Set<LessonTime> lessonTimeSet = Stream.of(lessonTimes).map(LessonTime::new).collect(Collectors.toSet());
        descriptor.setLessonTimes(lessonTimeSet);
        return this;
    }

    public UpdateStudentDescriptor build() {
        return descriptor;
    }
}
