package seedu.edulog.testutil;

import seedu.edulog.model.calendar.Day;
import seedu.edulog.model.calendar.Description;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.LessonTime;

/**
 * A utility class to help with building Student objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_DESCRIPTION = "Sec 4 Math";
    public static final String DEFAULT_START_DAY = "Monday";
    public static final String DEFAULT_START_TIME = "0930";
    public static final String DEFAULT_END_TIME = "1130";
    private Description description;
    private Day startDay;
    private LessonTime startTime;
    private LessonTime endTime;

    /**
     * Creates a {@code LessonBuilder} with the default details.
     * once the todos above are done we can rewrite this as
     *         description = new Description(DEFAULT_DESCRIPTION);
     *         startDay = new StartDay(DEFAULT_START_DAY);
     *         startTime = new StartTime(DEFAULT_START_TIME);
     *         endTime = new EndTime(DEFAULT_END_TIME);
     */
    public LessonBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        startDay = new Day(DEFAULT_START_DAY);
        startTime = new LessonTime(DEFAULT_START_TIME);
        endTime = new LessonTime(DEFAULT_END_TIME);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        description = lessonToCopy.getDescription();
        startDay = lessonToCopy.getStartDay();
        startTime = lessonToCopy.getStartTime();
        endTime = lessonToCopy.getEndTime();
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public LessonBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the day of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDayOfWeek(String startDay) {
        this.startDay = new Day(startDay);
        return this;
    }

    /**
     * Sets the start time of the {@code Lesson} that we are building.
     */
    public LessonBuilder withStartTime(String startTime) {
        this.startTime = new LessonTime(startTime);
        return this;
    }

    /**
     * Sets the end time of the {@code Lesson} that we are building.
     */
    public LessonBuilder withEndTime(String endTime) {
        this.endTime = new LessonTime(endTime);
        return this;
    }


    public Lesson build() {
        return new Lesson(description, startDay, startTime, endTime);
    }

}
