package tutorease.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tutorease.address.model.lesson.exceptions.LessonIndexOutOfRange;
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
     */
    public boolean contains(Lesson toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlapping);
    }

    /**
     * Adds a lesson to the list.
     * The lesson must not already exist in the list.
     */
    public void add(Lesson toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new OverlappingLessonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent lesson from the list.
     * The lesson must exist in the list.
     */
    public void remove(int index) {
        if (!isValidIndex(index)) {
            throw new LessonIndexOutOfRange();
        } else {
            internalList.remove(index);
        }
    }

    public Lesson get(int index) {
        if (!isValidIndex(index)) {
            throw new LessonIndexOutOfRange();
        } else {
            return internalList.get(index);
        }
    }

    public int size() {
        return internalList.size();
    }

    /**
     * Replaces the contents of this list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
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
     */
    public boolean isValidIndex(int index) {
        // index is 0-based
        return index >= 0 && index < internalList.size();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Lesson> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns true if {@code lessons} contains only non-overlapping lessons.
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
