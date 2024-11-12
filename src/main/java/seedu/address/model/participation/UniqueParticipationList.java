package seedu.address.model.participation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Messages;
import seedu.address.model.participation.exceptions.DuplicateParticipationException;
import seedu.address.model.participation.exceptions.ParticipationNotFoundException;

/**
 * Manages {@code participation} objects
 * Enforces uniqueness between its elements and does not allow nulls.
 * A participation is considered unique by comparing using {@code Participation#isSameParticipation(Participation)}.
 * As such, adding and updating of participations uses Participation#isSameParticipation(Participation)
 * for equality to ensure that the participation being
 * added or updated is unique in terms of identity in the UniqueParticipationList. However, the removal of a
 * participation uses Participation#equals(Object) to ensure that the participation with exactly the same fields
 * will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueParticipationList implements Iterable<Participation> {

    public static final String MESSAGE_PARTICIPATION_NOT_FOUND = "No such participation exists in EduVault now";

    private static final Logger logger = LogsCenter.getLogger(UniqueParticipationList.class);
    private final ObservableList<Participation> internalList = FXCollections.observableArrayList();
    private final ObservableList<Participation> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    /**
     * Returns true if the list contains an equivalent participation as the given argument.
     */
    public boolean contains(Participation toCheck) {
        requireNonNull(toCheck);
        assert !internalList.contains(null) : "Internal list should not contain null elements";
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a participation to the list.
     * The participation must not already exist in the list.
     */
    public void add(Participation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, UniqueParticipationList.class));
            throw new DuplicateParticipationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the participation {@code target} in the list with {@code editedParticipation}.
     *
     * @param target The participation to be replaced. Must already exist in the list.
     * @param editedParticipation The new participation to replace {@code target}.
     *        Must not duplicate any existing participation in the list.
     * @throws ParticipationNotFoundException if {@code target} does not exist.
     * @throws DuplicateParticipationException if {@code editedParticipation} duplicates an existing participation.
     */

    public void setParticipation(Participation target, Participation editedParticipation) {
        requireAllNonNull(target, editedParticipation);

        int index = internalList.indexOf(target);
        if (index == -1) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, UniqueParticipationList.class));
            throw new ParticipationNotFoundException(MESSAGE_PARTICIPATION_NOT_FOUND);
        }

        // New participation that replaces the target cannot be a duplicate of another participation in the list
        if (!target.equals(editedParticipation) && contains(editedParticipation)) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, UniqueParticipationList.class));
            throw new DuplicateParticipationException();
        }

        internalList.set(index, editedParticipation);
    }

    /**
     * Replaces this object with a new UniqueParticipationList object
     *
     * @param replacement new UniqueParticipationList
     */
    public void setParticipation(UniqueParticipationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the list {@code internalList} with Participation objects from a new list{@code participation}.
     *
     * @param participation new List of Participation
     */
    public void setParticipation(List<Participation> participation) {
        requireAllNonNull(participation);
        if (!participationAreUnique(participation)) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, UniqueParticipationList.class));
            throw new DuplicateParticipationException();
        }

        internalList.setAll(participation);
    }

    /**
     * Removes a participation from the list.
     * The participation must already exist in the list.
     */
    public void remove(Participation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            logger.warning(String.format(Messages.MESSAGE_LOGGER_FOR_EXCEPTION, UniqueParticipationList.class));
            throw new ParticipationNotFoundException(MESSAGE_PARTICIPATION_NOT_FOUND);
        }
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
     * Returns true if {@code participation} contains only unique participations
     */
    private boolean participationAreUnique(List<Participation> participation) {
        // double loop required here to check every element against each other
        for (int i = 0; i < participation.size() - 1; i++) {
            for (int j = i + 1; j < participation.size(); j++) {
                if (participation.get(i).equals(participation.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

