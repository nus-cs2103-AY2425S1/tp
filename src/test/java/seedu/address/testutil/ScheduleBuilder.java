package seedu.address.testutil;

import seedu.address.model.person.Schedule;

/**
 * A utility class to help with building Schedule objects.
 */
public class ScheduleBuilder {
    public static final String DEFAULT_SCHEDULE_NAME = "schedule";
    public static final String DEFAULT_SCHEDULE_DATE = "2024-10-21";
    public static final String DEFAULT_SCHEDULE_TIME = "10:00";

    private String scheduleName;
    private String scheduleDate;
    private String scheduleTime;

    /**
     * Creates a {@code ScheduleBuilder} with the default details.
     */
    public ScheduleBuilder() {
        scheduleName = DEFAULT_SCHEDULE_NAME;
        scheduleDate = DEFAULT_SCHEDULE_DATE;
        scheduleTime = DEFAULT_SCHEDULE_TIME;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public ScheduleBuilder(Schedule scheduleToCopy) {
        scheduleName = scheduleToCopy.scheduleName;
        scheduleDate = scheduleToCopy.dateString;
        scheduleTime = scheduleToCopy.timeString;
    }

    /**
     * Sets the {@code scheduleName} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
        return this;
    }

    /**
     * Sets the {@code scheduleDate} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
        return this;
    }

    /**
     * Sets the {@code scheduleTime} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
        return this;
    }

    public Schedule build() {
        return new Schedule(scheduleName, scheduleDate, scheduleTime);
    }
}
