package tutorease.address.model;

import java.util.List;

import javafx.collections.ObservableList;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.UniqueLessonList;

/**
 * Wraps all data at the lesson-schedule level
 * Duplicates are not allowed (by .isOverlapping comparison)
 */
public class LessonSchedule {
    private final UniqueLessonList lessons;

    {
        lessons = new UniqueLessonList();
    }
    public LessonSchedule() {
    }

    /**
     * Creates an LessonSchedule using the Lessons in the {@code toBeCopied}
     */
    public LessonSchedule(LessonSchedule toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the lesson list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons.setLessons(lessons);
    }

    public ObservableList<Lesson> getLessonList() {
        return lessons.asUnmodifiableObservableList();
    }

    /**
     * Returns true if a lesson with the same identity as {@code lesson} overlaps in the lesson schedule.
     */
    public boolean hasLesson(Lesson lesson) {
        return lessons.contains(lesson);
    }

    /**
     * Adds a lesson to the lesson schedule.
     * The lesson must not already exist in the lesson schedule.
     */
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    /**
     * Replaces the contents of this lesson schedule with {@code newData}.
     */
    public void resetData(LessonSchedule newData) {
        lessons.setLessons(newData.getLessonList());
    }
}
