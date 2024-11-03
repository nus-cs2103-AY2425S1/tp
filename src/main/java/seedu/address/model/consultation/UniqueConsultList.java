package seedu.address.model.consultation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.consultation.exceptions.ConsultationNotFoundException;
import seedu.address.model.consultation.exceptions.DuplicateConsultationException;

/**
 * A list of consultations that enforces uniqueness between its elements and does not allow nulls.
 * A consultation is considered unique by comparing using {@code Consultation#isSameConsultation(Consultation)}.
 */
public class UniqueConsultList implements Iterable<Consultation> {

    private final ObservableList<Consultation> internalList = FXCollections.observableArrayList();
    private final ObservableList<Consultation> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent consultation as the given argument.
     */
    public boolean contains(Consultation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameConsultation);
    }

    /**
     * Adds a consultation to the list.
     * The consultation must not already exist in the list.
     */
    public void add(Consultation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateConsultationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the consultation {@code target} in the list with {@code editedConsult}.
     * {@code target} must exist in the list.
     * The consultation identity of {@code editedConsult}
     * must not be the same as another existing consultation in the list.
     */
    public void setConsult(Consultation target, Consultation editedConsult) {
        requireAllNonNull(target, editedConsult);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ConsultationNotFoundException();
        }

        if (!target.isSameConsultation(editedConsult) && contains(editedConsult)) {
            throw new DuplicateConsultationException();
        }

        internalList.set(index, editedConsult);
    }

    /**
     * Removes the equivalent consultation from the list.
     * The consultation must exist in the list.
     */
    public void remove(Consultation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ConsultationNotFoundException();
        }
    }

    public void setConsults(UniqueConsultList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code consultations}.
     * {@code consultations} must not contain duplicate consultations.
     */
    public void setConsults(List<Consultation> consultations) {
        requireAllNonNull(consultations);
        if (!consultsAreUnique(consultations)) {
            throw new DuplicateConsultationException();
        }

        internalList.setAll(consultations);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Consultation> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Consultation> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueConsultList // instanceof handles nulls
                && internalList.equals(((UniqueConsultList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code consultations} contains only unique consultations.
     */
    private boolean consultsAreUnique(List<Consultation> consultations) {
        for (int i = 0; i < consultations.size() - 1; i++) {
            for (int j = i + 1; j < consultations.size(); j++) {
                if (consultations.get(i).isSameConsultation(consultations.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a list of consultations that match the given predicate.
     */
    public List<Consultation> filtered(Predicate<Consultation> predicate) {
        requireNonNull(predicate);
        return internalList.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Sorts the list of consultations by date first, and then by time if the dates are the same.
     * This method ensures that the consultations are ordered chronologically within the list.
     * The list is sorted in-place, meaning the order of elements in the internal list is modified.
     */
    public void sort() {
        internalList.sort(Comparator.comparing(Consultation::getDate)
                                     .thenComparing(Consultation::getTime));
    }
}
