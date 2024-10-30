package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.schedule.Meeting;

/**
 * A utility class to help with building EditScheduleDescriptor objects.
 */
public class EditScheduleDescriptorBuilder {

    private final EditScheduleDescriptor descriptor;

    public EditScheduleDescriptorBuilder() {
        descriptor = new EditScheduleDescriptor();
    }

    /**
     * Initializes the EditScheduleDescriptorBuilder with the details of the given meeting.
     */
    public EditScheduleDescriptorBuilder(Meeting meeting) {
        descriptor = new EditScheduleDescriptor();
        descriptor.setName(new Name(meeting.getMeetingName()));
        descriptor.setDate(Optional.of(meeting.getMeetingDate()).get());
        descriptor.setTime(Optional.of(meeting.getMeetingTime()).get());
        descriptor.setContactIndex(List.of());
    }

    /**
     * Sets the {@code Name} of the {@code EditScheduleDescriptor} that we are building.
     */
    public EditScheduleDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditScheduleDescriptor} that we are building.
     */
    public EditScheduleDescriptorBuilder withDate(LocalDate date) {
        descriptor.setDate(date);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditScheduleDescriptor} that we are building.
     */
    public EditScheduleDescriptorBuilder withTime(LocalTime time) {
        descriptor.setTime(time);
        return this;
    }

    /**
     * Sets the {@code ContactIndexes} of the {@code EditScheduleDescriptor} that we are building.
     */
    public EditScheduleDescriptorBuilder withContactIndexes(List<Index> contactIndexes) {
        descriptor.setContactIndex(contactIndexes);
        return this;
    }

    public EditScheduleDescriptor build() {
        return descriptor;
    }
}
