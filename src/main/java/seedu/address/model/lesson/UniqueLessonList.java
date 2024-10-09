package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.lesson.exceptions.LessonIndexOutOfRange;
import seedu.address.model.lesson.exceptions.OverlappingLessonException;

// adapted from UniquePersonList
public class UniqueLessonList implements Iterable<Lesson>{
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
        if (!validIndex(index)) {
            throw new LessonIndexOutOfRange();
        } else {
            internalList.remove(index - 1);
        }
    }

    public void setPersons(UniqueLessonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * replaces the contents of the lesson list with {@code lessons}.
     * {@code lessons} must not contain duplicate persons.
     */
    public void setLessons(List<Lesson> lessons) {
        requireNonNull(lessons);
        if (!lessonsAreUnique(lessons)) {
            throw new OverlappingLessonException();
        }
        internalList.setAll(lessons);
    }

    /**
     * Checks if the index is valid.
     */
    public boolean validIndex(int index) {
        // index is 1-based
        return index >= 1 && index < internalList.size();
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
