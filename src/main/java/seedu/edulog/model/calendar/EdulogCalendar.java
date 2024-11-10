package seedu.edulog.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.edulog.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edulog.model.calendar.exceptions.DuplicateLessonException;
import seedu.edulog.model.calendar.exceptions.LessonNotFoundException;

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

    /**
     * Returns true if the list contains an equivalent lesson as the given argument.
     */
    public boolean contains(Lesson toCheck) {
        requireNonNull(toCheck);
        return lessons.stream().anyMatch(toCheck::isSameLesson);
    }

    /**
     * Adds a lesson to the list.
     * The lesson must not already exist in the list.
     */
    public void add(Lesson toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLessonException();
        }
        lessons.add(toAdd);
    }

    /**
     * Removes the equivalent lesson from the list.
     * The lesson must exist in the list.
     */
    public void remove(Lesson toRemove) {
        requireNonNull(toRemove);
        if (!lessons.remove(toRemove)) {
            throw new LessonNotFoundException();
        }
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
     * Replaces the lesson {@code target} in the list with {@code editedLesson}.
     * {@code target} must exist in the list.
     * The lesson identity of {@code editedLesson} must not be the same as another existing lesson in the list.
     */
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireAllNonNull(target, editedLesson);

        int index = lessons.indexOf(target);
        if (index == -1) {
            throw new LessonNotFoundException();
        }

        if (!target.isSameLesson(editedLesson) && contains(editedLesson)) {
            throw new DuplicateLessonException();
        }

        lessons.set(index, editedLesson);
    }

    /**
     * Remove the 1st index in lessons that returns true on Object.equals(lesson, lessons.get(i))
     */
    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }

    @Override
    public int hashCode() {
        return lessons.hashCode();
    }

    @Override
    public String toString() {
        return lessons.toString();
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EdulogCalendar)) {
            return false;
        }

        EdulogCalendar otherEdulogCalendar = (EdulogCalendar) other;
        return lessons.equals(otherEdulogCalendar.lessons);
    }
}
