package seedu.edulog.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edulog.model.calendar.exceptions.DuplicateLessonException;

/**
 * Calendar class
 */
public class EdulogCalendar {
    private final ObservableList<Lesson> lessons = FXCollections.observableArrayList();
    private final ObservableList<Lesson> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(lessons);

    public ObservableList<Lesson> getLessonList() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        requireAllNonNull(lessons);
        if (!lessonsAreUnique(lessons)) {
            throw new DuplicateLessonException();
        }

        this.lessons.setAll(lessons);
    }

    public void setLessons(EdulogCalendar edulogCalendar) {
        if (!lessonsAreUnique(edulogCalendar.lessons)) {
            throw new DuplicateLessonException();
        }

        requireNonNull(edulogCalendar);
        lessons.setAll(edulogCalendar.lessons);
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
    public Lesson findLesson(Description description) {
        return lessons.stream()
            .filter(lesson -> lesson.isDescription(description))
            .findFirst()
            .orElse(null);
    }

    /**
     * Remove the 1st index in lessons that returns true on Object.equals(lesson, lessons.get(i))
     */
    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    /**
     * Returns true if {@code lessons} contains only unique lessons.
     */
    private boolean lessonsAreUnique(List<Lesson> lessons) {
        for (int i = 0; i < lessons.size() - 1; i++) {
            for (int j = i + 1; j < lessons.size(); j++) {
                if (lessons.get(i).isSameLesson(lessons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Lesson> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }
}
