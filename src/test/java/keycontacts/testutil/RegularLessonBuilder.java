package keycontacts.testutil;

import keycontacts.model.lesson.Day;
import keycontacts.model.lesson.RegularLesson;
import keycontacts.model.lesson.Time;

/**
 * A utility class to help with building RegularLesson objects.
 */
public class RegularLessonBuilder {
    private Day lessonDay;
    private Time startTime;
    private Time endTime;

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
