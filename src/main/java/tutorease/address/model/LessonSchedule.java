package tutorease.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import tutorease.address.commons.core.LogsCenter;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.UniqueLessonList;
import tutorease.address.model.person.Person;

/**
 * Wraps all data at the lesson-schedule level.
 * Duplicates are not allowed (by .isOverlapping comparison).
 */
public class LessonSchedule implements ReadOnlyLessonSchedule {
    private static final Logger logger = LogsCenter.getLogger(LessonSchedule.class);

    private final UniqueLessonList lessons;

    {
        lessons = new UniqueLessonList();
    }

    public LessonSchedule() {
    }

    /**
     * Creates a LessonSchedule using the lessons in the {@code toBeCopied}.
     *
     * @param toBeCopied The ReadOnlyLessonSchedule to copy from.
     */
    public LessonSchedule(ReadOnlyLessonSchedule toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Sets the lessons in the lesson schedule.
     *
     * @param lessons The new list of lessons.
     */
    public void setLessons(List<Lesson> lessons) {
        logger.log(Level.INFO, "Setting lessons in model");
        this.lessons.setLessons(lessons);
    }

    /**
     * Returns the lesson list.
     */
    public ObservableList<Lesson> getLessonList() {
        return lessons.asUnmodifiableObservableList();
    }

    /**
     * Returns true if the lesson list contains an equivalent lesson as the given argument.
     *
     * @param lesson The lesson to check for.
     * @return True if the lesson list contains the given lesson.
     */
    public boolean hasLesson(Lesson lesson) {
        return lessons.contains(lesson);
    }

    /**
     * Adds the specified lesson to the lesson list.
     *
     * @param lesson The lesson to be added.
     * @throws NullPointerException If the specified lesson is null.
     */
    public void addLesson(Lesson lesson) {
        logger.log(Level.INFO, "Adding lesson to model: " + lesson);
        requireNonNull(lesson);

        lessons.add(lesson);
        logger.log(Level.INFO, "Lesson added to model: " + lesson);
    }

    /**
     * Deletes the specified lesson from the lesson list.
     *
     * @param lesson The lesson to be removed. Must exist in the lesson list.
     */
    public void deleteLesson(Lesson lesson) {
        logger.log(Level.INFO, "Deleting lesson from model: " + lesson);
        requireNonNull(lesson);

        lessons.remove(lesson);
        logger.log(Level.INFO, "Lesson deleted from model: " + lesson);
    }

    /**
     * Returns the lesson at the specified index.
     *
     * @param index The index of the lesson to retrieve. Must be a valid index.
     * @return The lesson at the specified index.
     */
    public Lesson getLesson(int index) {
        return lessons.get(index);
    }

    /**
     * Returns the number of lessons in the list.
     *
     * @return The size of the lesson list.
     */
    public int getSize() {
        return lessons.size();
    }

    /**
     * Replaces the contents of this lesson schedule with {@code newData}.
     */
    public void resetData(ReadOnlyLessonSchedule newData) {
        lessons.setLessons(newData.getLessonList());
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonSchedule)) {
            return false;
        }

        LessonSchedule otherLessonSchedule = (LessonSchedule) other;
        return lessons.equals(otherLessonSchedule.lessons);
    }

    /**
     * Updates the person in all lessons in the list.
     *
     * @param target The person to be updated.
     * @param editedPerson The updated person.
     */
    public void updatePersonInLessons(Person target, Person editedPerson) {
        this.lessons.updatePersonInLessons(target, editedPerson);
    }
}
