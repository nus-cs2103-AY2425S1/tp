package seedu.address.model.person;

import java.util.ArrayList;

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
