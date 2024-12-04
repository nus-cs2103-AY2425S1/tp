package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutor;

/**
 * A list of lessons that enforces uniqueness between its elements and does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class UniqueLessonList implements Iterable<Lesson> {

    private final ObservableList<Lesson> internalList = FXCollections.observableArrayList();
    private final ObservableList<Lesson> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent lesson as the given argument.
     */
    public boolean contains(Lesson toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
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
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent lesson from the list.
     * The lesson must exist in the list.
     */
    public void remove(Lesson toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LessonNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<Lesson> lessons) {
        requireAllNonNull(lessons);
        if (!lessonsAreUnique(lessons)) {
            throw new DuplicateLessonException();
        }

        internalList.setAll(lessons);
    }

    /**
     * Returns a list of associated people (Tutors or Tutees) for the given person.
     * If the given person is a Tutor, it returns all associated Tutees.
     * If the given person is a Tutee, it returns all associated Tutors.
     *
     * @param person The person for whom the associated people are to be retrieved.
     *               Must be non-null and either a Tutor or Tutee.
     * @return A list of associated persons.
     */
    public List<Person> getAssociatedPeople(Person person) {
        requireAllNonNull(person);
        if (person instanceof Tutor) {
            return internalList.stream().filter(item -> item.getTutor().equals(person))
                    .map(Lesson::getTutee).collect(Collectors.toList());
        } else {
            return internalList.stream().filter(item -> item.getTutee().equals(person))
                    .map(Lesson::getTutor).collect(Collectors.toList());
        }
    }

    /**
     * Returns a list of associated lessons for the given person.
     *
     * @param person The person for whom the associated lessons are to be retrieved.
     *               Must be non-null and either a Tutor or Tutee.
     * @return A list of associated lessons.
     */
    public List<Lesson> getAssociatedLessons(Person person) {
        requireAllNonNull(person);
        if (person instanceof Tutor) {
            return internalList.stream().filter(item -> item.getTutor().equals(person))
                    .collect(Collectors.toList());
        } else {
            return internalList.stream().filter(item -> item.getTutee().equals(person))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Returns the set of subjects that the person (either Tutor or Tutee) has taught or is currently being tutored in.
     *
     * @param person The person for whom the unique subjects are to be retrieved.
     *               Must be non-null.
     * @return A set of unique subjects.
     */
    public Set<Subject> getUniqueSubjectsInLessons(Person person) {
        requireAllNonNull(person);
        return internalList.stream().filter(lesson -> lesson.getTutor().equals(person)
                || lesson.getTutee().equals(person))
                .map(Lesson::getSubject)
                .collect(HashSet::new, Set::add, Set::addAll);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Lesson> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
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
        if (!(other instanceof seedu.address.model.lesson.UniqueLessonList)) {
            return false;
        }

        seedu.address.model.lesson.UniqueLessonList otherUniqueLessonList =
                (seedu.address.model.lesson.UniqueLessonList) other;
        return internalList.equals(otherUniqueLessonList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code lessons} contains only unique lessons.
     */
    private boolean lessonsAreUnique(List<Lesson> lessons) {
        for (int i = 0; i < lessons.size() - 1; i++) {
            for (int j = i + 1; j < lessons.size(); j++) {
                if (lessons.get(i).equals(lessons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
