package keycontacts.testutil;

import keycontacts.model.lesson.Day;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;

/**
 * A utility class to help with building RegularLesson objects.
 */
public class RegularLessonBuilder {

    public static final String DEFAULT_LESSON_DAY = "Monday";
    public static final String DEFAULT_START_TIME = "16:00";
    public static final String DEFAULT_END_TIME = "18:00";

    private Day lessonDay;
    private Time startTime;
    private Time endTime;

    /**
     * Creates a {@code RegularLessonBuilder} with the default details.
     */
    public RegularLessonBuilder() {
        lessonDay = new Day(DEFAULT_LESSON_DAY);
        startTime = new Time(DEFAULT_START_TIME);
        endTime = new Time(DEFAULT_END_TIME);
    }

    /**
     * Initializes the RegularLessonBuilder with the data of {@code regularLessonToCopy}.
     */
    public RegularLessonBuilder(RegularLesson regularLessonToCopy) {
        lessonDay = regularLessonToCopy.getLessonDay();
        startTime = regularLessonToCopy.getStartTime();
        endTime = regularLessonToCopy.getEndTime();
    }

    /**
     * Sets the {@code lessonDay} of the {@code RegularLesson} that we are building.
     */
    public RegularLessonBuilder withLessonDay(String lessonDay) {
        this.lessonDay = new Day(lessonDay);
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code RegularLesson} that we are building.
     */
    public RegularLessonBuilder withStartTime(String startTime) {
        this.startTime = new Time(startTime);
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code RegularLesson} that we are building.
     */
    public RegularLessonBuilder withEndTime(String endTime) {
        this.endTime = new Time(endTime);
        return this;
    }


    public RegularLesson build() {
        return new RegularLesson(lessonDay, startTime, endTime);
    }
}
