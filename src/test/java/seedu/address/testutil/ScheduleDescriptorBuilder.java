package seedu.address.testutil;

import seedu.address.logic.commands.ScheduleCommand.ScheduleDescriptor;
import seedu.address.model.person.Schedule;
/**
 * A utility class to help with building ScheduleDescriptor objects.
 */
public class ScheduleDescriptorBuilder {

    private ScheduleDescriptor descriptor;

    public ScheduleDescriptorBuilder() {
        descriptor = new ScheduleDescriptor();
    }

    public ScheduleDescriptorBuilder(ScheduleDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Returns an {@code ScheduleDescriptor} with fields containing {@code schedule}'s details
     */
    public ScheduleDescriptorBuilder(Schedule schedule) {
        descriptor = new ScheduleDescriptor();
        descriptor.setScheduleName(schedule.scheduleName);
        descriptor.setDateString(schedule.dateString);
        descriptor.setTimeString(schedule.timeString);
    }

    /**
     * Sets the {@code scheduleName} of the {@code ScheduleDescriptorBuilder} that we are building.
     */
    public ScheduleDescriptorBuilder withScheduleName(String scheduleName) {
        descriptor.setScheduleName(scheduleName);
        return this;
    }

    /**
     * Sets the {@code scheduleDate} of the {@code ScheduleDescriptorBuilder} that we are building.
     */
    public ScheduleDescriptorBuilder withScheduleDate(String scheduleDate) {
        descriptor.setDateString(scheduleDate);
        return this;
    }

    /**
     * Sets the {@code scheduleTime} of the {@code ScheduleDescriptorBuilder} that we are building.
     */
    public ScheduleDescriptorBuilder withScheduleTime(String scheduleTime) {
        descriptor.setTimeString(scheduleTime);
        return this;
    }

    public ScheduleDescriptor build() {
        return this.descriptor;
    }
}
