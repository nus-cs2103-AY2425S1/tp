package seedu.edulog.model.calendar;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Calendar class
 */
public class EdulogCalendar {
    public static final int MAX_SIMULTANEOUS_TIMING = 2;
    private ObservableList<Lesson> lessons;

    public EdulogCalendar() {
        lessons = FXCollections.observableArrayList();
    }

    public ObservableList<Lesson> getLessonList() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = FXCollections.observableArrayList(lessons);
    }

    /**
     * Add a new lesson to Calendar
     */
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    /**
     * Checks if a provided lesson is equal (based on description) to any other lesson in the calendar.
     */
    public boolean hasLesson(Lesson lesson) {
        return lessons.stream().anyMatch(l -> l.isSameLesson(lesson));
    }

    /**
     * Find a lesson in the calendar based on its description
     */
    public Lesson findLesson(String description) {
        return lessons.stream()
            .filter(lesson -> lesson.isDescription(description))
            .findFirst()
            .orElse(null);
    }

    /**
     * Returns true if a lesson with a given timeslot can be added in the calendar without exceeding the count limit.
     */
    public boolean checkTimeslot(Lesson lesson) {
        return lessons.stream()
            .filter(l -> l.getStartDay().equals(lesson.getStartDay()))
            .filter(l -> l.getStartTime().isBefore(lesson.getEndTime())
                && lesson.getStartTime().isBefore(l.getEndTime()))
            .count() < MAX_SIMULTANEOUS_TIMING;
    }

    /**
     * Remove the 1st index in lessons that returns true on Object.equals(lesson, lessons.get(i))
     */
    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }
}
