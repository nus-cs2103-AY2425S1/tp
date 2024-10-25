package tutorease.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tutorease.address.model.lesson.exceptions.LessonIndexOutOfRange;
import tutorease.address.model.lesson.exceptions.LessonNotInList;
import tutorease.address.model.lesson.exceptions.OverlappingLessonException;

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
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlapping);
    }

    /**
     * Adds a lesson to the list.
     * The lesson must not already exist in the list.
     *
     * @param toAdd The lesson to add.
     * @throws NullPointerException If {@code toAdd} is null.
     * @throws OverlappingLessonException If the lesson overlaps with an existing lesson in the list.
     */
    public void add(Lesson toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new OverlappingLessonException();
        }
        internalList.add(toAdd);
        internalList.sort(Lesson::compareTo);
    }

    /**
     * Removes the specified lesson from the list.
     *
     * @param lesson The lesson to be removed.
     * @throws LessonNotInList If the lesson is not in the list
     */
    public void remove(Lesson lesson) {
        requireNonNull(lesson);
        if (!contains(lesson)) {
            throw new LessonNotInList();
        } else {
            internalList.remove(lesson);
        }
    }

    /**
     * Returns the lesson at the specified index in the list.
     *
     * @param index The index of the lesson to retrieve.
     * @return The lesson at the specified index.
     * @throws LessonIndexOutOfRange If the index is invalid (less than 0 or greater than or equal to the
     *     size of the list).
     */
    public Lesson get(int index) {
        if (!isValidIndex(index)) {
            throw new LessonIndexOutOfRange();
        } else {
            return internalList.get(index);
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
     * @throws NullPointerException If {@code lessons} is null.
     * @throws OverlappingLessonException If any lessons in the list overlap with each other.
     */
    public void setLessons(UniqueLessonList lessons) {
        requireNonNull(lessons);
        internalList.setAll(lessons.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setLessons(List<Lesson> lessons) {
        requireAllNonNull(lessons);
        if (!lessonsAreUnique(lessons)) {
            throw new OverlappingLessonException();
        }

        internalList.setAll(lessons);
    }

    /**
     * Checks if the index is valid.
     *
     * @param index The index to check.
     * @return True if the index is within bounds (greater than or equal to 0 and less than the size of the
     *     list), false otherwise.
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
        for (int i = 0; i < lessons.size() - 1; i++) {
            for (int j = i + 1; j < lessons.size(); j++) {
                if (lessons.get(i).isOverlapping(lessons.get(j))) {
                    return false;
                }
            }
        }
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
}
