package tutorease.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tutorease.address.commons.core.LogsCenter;
import tutorease.address.model.lesson.exceptions.LessonIndexOutOfRange;
import tutorease.address.model.lesson.exceptions.LessonNotInList;
import tutorease.address.model.lesson.exceptions.OverlappingLessonException;
import tutorease.address.model.person.Person;
import tutorease.address.model.person.Student;

// adapted from UniquePersonList

/**
 * A list of lessons that enforces uniqueness between its elements and does not allow nulls.
 * A lesson is considered unique by comparing using {@code Lesson#isOverlapping(Lesson)}. As such, adding and updating
 * of lessons uses Lesson#isOverlapping(Lesson) for equality so as to ensure that the lesson being added or updated is
 * unique in terms of overlapping with other lessons in the UniqueLessonList.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Lesson#isOverlapping(Lesson)
 */
public class UniqueLessonList implements Iterable<Lesson> {
    // adapted from UniquePersonList
    private static Logger logger = LogsCenter.getLogger(UniqueLessonList.class);
    private final ObservableList<Lesson> internalList = FXCollections.observableArrayList();
    private final ObservableList<Lesson> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent lesson as the given argument.
     *
     * @param toCheck The lesson to check for overlap in the list.
     * @return True if a lesson in the list overlaps with the given lesson, false otherwise.
     */
    public boolean contains(Lesson toCheck) {
        logger.log(Level.INFO, "Checking if lesson is in list: " + toCheck);
        requireNonNull(toCheck);

        boolean contains = internalList.stream().anyMatch(toCheck::isOverlapping);
        logger.log(Level.INFO, "Lesson is in list: " + contains);
        return contains;
    }

    /**
     * Adds a lesson to the list.
     * The lesson must not already exist in the list.
     *
     * @param toAdd The lesson to add.
     * @throws NullPointerException       If {@code toAdd} is null.
     * @throws OverlappingLessonException If the lesson overlaps with an existing lesson in the list.
     */
    public void add(Lesson toAdd) {
        logger.log(Level.INFO, "Adding lesson to list: " + toAdd);
        requireNonNull(toAdd);

        // check if lesson overlaps with any other lesson in the list
        if (contains(toAdd)) {
            logger.log(Level.WARNING, "Lesson is overlapping with another lesson in the list: " + toAdd);
            throw new OverlappingLessonException();
        }

        internalList.add(toAdd);
        internalList.sort(Lesson::compareTo);
        logger.log(Level.INFO, "Added lesson to list: " + toAdd);
    }

    /**
     * Removes the specified lesson from the list.
     *
     * @param lesson The lesson to be removed.
     * @throws LessonNotInList If the lesson is not in the list.
     */
    public void remove(Lesson lesson) {
        logger.log(Level.INFO, "Removing lesson from list: " + lesson);
        requireNonNull(lesson);

        boolean isLessonInList = contains(lesson);
        if (isLessonInList) {
            internalList.remove(lesson);
            logger.log(Level.INFO, "Removed lesson from list: " + lesson);
        } else {
            throw new LessonNotInList();
        }
    }

    /**
     * Returns the lesson at the specified index in the list.
     *
     * @param index The index of the lesson to retrieve.
     * @return The lesson at the specified index.
     * @throws LessonIndexOutOfRange If the index is invalid (less than 0 or greater than or equal to the
     *                               size of the list).
     */
    public Lesson get(int index) {
        logger.log(Level.INFO, "Getting lesson at index: " + index);
        if (isValidIndex(index)) {
            Lesson lesson = internalList.get(index);
            logger.log(Level.INFO, "Got lesson at index: " + index + " " + lesson);
            return lesson;
        } else {
            logger.log(Level.WARNING, "Index is out of range or invalid: " + index);
            throw new LessonIndexOutOfRange();
        }
    }

    /**
     * Returns the number of lessons in the list.
     *
     * @return The number of lessons in the list.
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Replaces the contents of the lesson list with the specified {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     *
     * @param lessons The list of lessons to replace the current list.
     * @throws NullPointerException       If {@code lessons} is null.
     * @throws OverlappingLessonException If any lessons in the list overlap with each other.
     */
    public void setLessons(UniqueLessonList lessons) {
        logger.log(Level.INFO, "Setting lessons in list: " + lessons);
        requireNonNull(lessons);

        internalList.setAll(lessons.internalList);
        logger.log(Level.INFO, "Set lessons in list: " + lessons);
    }

    /**
     * Replaces the contents of the lesson list with the specified {@code lessons}.
     * @param lessons The list of lessons to replace the current list.
     */
    public void setLessons(List<Lesson> lessons) {
        logger.log(Level.INFO, "Setting lessons in list: " + lessons);
        requireAllNonNull(lessons);

        if (!lessonsAreUnique(lessons)) {
            logger.log(Level.WARNING, "Lessons are overlapping with each other: " + lessons);
            throw new OverlappingLessonException();
        }

        internalList.setAll(lessons);
        logger.log(Level.INFO, "Set lessons in list: " + lessons);
    }

    /**
     * Checks if the index is valid.
     *
     * @param index The index to check.
     * @return True if the index is within bounds (greater than or equal to 0 and less than the size of the
     *      list), false otherwise.
     */
    public boolean isValidIndex(int index) {
        // index is 0-based
        return index >= 0 && index < internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return An unmodifiable {@code ObservableList} of lessons.
     */
    public ObservableList<Lesson> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns true if {@code lessons} contains only non-overlapping lessons.
     *
     * @param lessons The list of lessons to check for uniqueness.
     * @return True if all lessons in the list do not overlap with any other, false if any two lessons overlap.
     */
    public boolean lessonsAreUnique(List<Lesson> lessons) {
        logger.log(Level.INFO, "Checking if lessons are unique: " + lessons);

        for (int i = 0; i < lessons.size() - 1; i++) {
            for (int j = i + 1; j < lessons.size(); j++) {
                Lesson lessonOne = lessons.get(i);
                Lesson lessonTwo = lessons.get(j);
                if (lessonOne.isOverlapping(lessonTwo)) {
                    logger.log(Level.WARNING, "Lessons are overlapping: " + lessonOne + " " + lessonTwo);
                    return false;
                }
            }
        }

        logger.log(Level.INFO, "Lessons are unique: " + lessons);
        return true;
    }

    @Override
    public Iterator<Lesson> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueLessonList)) {
            return false;
        }

        UniqueLessonList otherUniqueLessonList = (UniqueLessonList) other;
        return internalList.equals(otherUniqueLessonList.internalList);
    }

    /**
     * Updates the person in all lessons in the list.
     *
     * @param target       The person to be updated.
     * @param editedPerson The updated person.
     */
    public void updatePersonInLessons(Person target, Person editedPerson) {
        logger.log(Level.INFO, "Updating person in lessons: " + target + " " + editedPerson);
        if (target.isStudent() && editedPerson.isStudent()) {
            assert target instanceof Student && editedPerson instanceof Student;
            for (int i = 0; i < internalList.size(); i++) {
                logger.log(Level.INFO, "Updating person in lesson: " + i);
                updatePersonInLesson(target, editedPerson, i);
            }
        }
    }

    private void updatePersonInLesson(Person target, Person editedPerson, int i) {
        logger.log(Level.INFO, "Updating person in lesson: " + target + " " + editedPerson + " " + i);
        assert target.isStudent() && editedPerson.isStudent();
        Lesson lesson = internalList.get(i);

        if (lesson.getStudent().equals(target)) {
            Lesson updatedLesson = new Lesson(editedPerson, lesson.getFee(),
                    lesson.getStartDateTime(), lesson.getEndDateTime());
            logger.log(Level.INFO, "Updated lesson: " + updatedLesson);
            internalList.set(i, updatedLesson);
        }
    }
}
