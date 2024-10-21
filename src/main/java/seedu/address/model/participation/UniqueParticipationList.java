package seedu.address.model.participation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.participation.exceptions.ParticipationNotFoundException;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.model.tutorial.exceptions.DuplicateTutorialException;
import seedu.address.model.tutorial.exceptions.TutorialNotFoundException;

/**
 * A list of participation that enforces uniqueness between its elements and does not allow nulls.
 * A participation is considered unique by comparing using {@code Tutorial#isSameParticipation(Tutorial)}.
 * As such, adding and updating of tutorials uses Tutorial#isSameParticipation(Tutorial)
 * for equality so as to ensure that the tutorial being
 * added or updated is unique in terms of identity in the UniqueParticipationList. However, the removal of a tutorial
 * uses
 * Participation#equals(Object) so as to ensure that the tutorial with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see seedu.address.model.participation.Participation#isSameParticipation(Participation)
 */
public class UniqueParticipationList implements Iterable<Participation> {

    private final ObservableList<Participation> internalList = FXCollections.observableArrayList();
    private final ObservableList<Participation> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent participation as the given argument.
     */
    public boolean contains(Participation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameParticipation);
    }

    /**
     * Adds a participation to the list.
     * The participation must not already exist in the list.
     */
    public void add(Participation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTutorialException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the participation {@code target} in the list with {@code editedParticipation}.
     * {@code target} must exist in the list.
     * The participation identity of {@code editedTutorial} must not be the same as another existing participation in
     * the list.
     */
    public void setParticipation(Participation target, Participation editedParticipation) {
        requireAllNonNull(target, editedParticipation);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ParticipationNotFoundException();
        }

        if (!target.isSameParticipation(editedParticipation) && contains(editedParticipation)) {
            throw new DuplicateTutorialException();
        }

        internalList.set(index, editedParticipation);
    }

    /**
     * Removes the equivalent participation from the list.
     * The participation must exist in the list.
     */
    public void remove(Participation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ParticipationNotFoundException();
        }
    }

    public void setParticipation(UniqueParticipationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code participation}.
     * {@code tutorials} must not contain duplicate participation.
     */
    public void setParticipation(List<Participation> participation) {
        requireAllNonNull(participation);
        if (!participationAreUnique(participation)) {
            throw new DuplicateTutorialException();
        }

        internalList.setAll(participation);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Participation> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Participation> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueParticipationList)) {
            return false;
        }

        UniqueParticipationList otherUniqueParticipationList = (UniqueParticipationList) other;
        return internalList.equals(otherUniqueParticipationList.internalList);
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
     * Returns true if {@code participation} contains only unique tutorials.
     */
    private boolean participationAreUnique(List<Participation> participation) {
        for (int i = 0; i < participation.size() - 1; i++) {
            for (int j = i + 1; j < participation.size(); j++) {
                if (participation.get(i).isSameParticipation(participation.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

