package seedu.address.model.calendar;

import java.util.ArrayList;

/**
 * Calendar class
 */
public class Calendar {
    private ArrayList<Lesson> lessons;

    public Calendar() {
        lessons = new ArrayList<>();
    }

    /**
     * Add a new lesson to Calendar
     */
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    /**
     * Remove the 1st index in lessons that returns true on Object.equals(lesson, lessons.get(i))
     */
    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }
}
