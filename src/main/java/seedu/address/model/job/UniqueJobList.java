package seedu.address.model.job;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;

/**
 * A list of unique jobs.
 */
public class UniqueJobList implements Iterable<Job> {

    private final ObservableList<Job> internalList = FXCollections.observableArrayList();
    private final ObservableList<Job> internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a job to the list.
     * The job must not already exist in the list.
     */
    public void add(Job toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateJobException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the job {@code target} in the list with {@code editedJob}.
     * {@code target} must exist in the list.
     * The job identity of {@code editedJob} must not be the same as another existing job in the list.
     */
    public void setJob(Job target, Job editedJob) {
        requireAllNonNull(target, editedJob);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new JobNotFoundException();
        }

        if (!target.isSameJob(editedJob) && contains(editedJob)) {
            throw new DuplicateJobException();
        }

        internalList.set(index, editedJob);
    }

    public void setJobs(UniqueJobList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code jobs}.
     * {@code jobs} must not contain duplicate jobs.
     */
    public void setJobs(List<Job> jobs) {
        requireAllNonNull(jobs);
        if (!jobsAreUnique(jobs)) {
            throw new DuplicateJobException();
        }

        internalList.setAll(jobs);
    }

    /**
     * Removes the equivalent job from the list.
     * The job must exist in the list.
     */
    public void remove(Job toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new JobNotFoundException();
        }
    }

    /**
     * Returns true if the list contains an equivalent jobs as the given argument.
     */
    public boolean contains(Job toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameJob);
    }

    /**
     * Returns true if {@code jobs} contains only unique jobs.
     */
    private boolean jobsAreUnique(List<Job> jobs) {
        for (int i = 0; i < jobs.size() - 1; i++) {
            for (int j = i + 1; j < jobs.size(); j++) {
                if (jobs.get(i).isSameJob(jobs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Job> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Job> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueJobList)) {
            return false;
        }

        UniqueJobList otherUniqueJobList = (UniqueJobList) other;
        return internalList.equals(otherUniqueJobList.internalList);
    }

    @Override
    public String toString() {
        return internalList.toString();
    }
}
