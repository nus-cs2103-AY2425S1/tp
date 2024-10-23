package seedu.address.model.link;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.link.exceptions.DuplicateLinkException;
import seedu.address.model.link.exceptions.LinkNotFoundException;

/**
 * A list of list that enforces uniqueness between its elements and does not
 * allow nulls.
 * A link is considered unique by comparing using
 * {@code Link#isSameLink(Link)}. Regardless of adding or updating,
 * links use Link#equals(Object) for equality to ensure that the link being
 * added or updated is unique in terms of
 * the unique ID for the from and to fields in the UniqueLinkList.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Link#equals(Object)
 */
public class UniqueLinkList implements Iterable<Link> {

    private final ObservableList<Link> internalList = FXCollections.observableArrayList();
    private final ObservableList<Link> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent link as the given argument.
     */
    public boolean contains(Link toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a Link to the list.
     * The link must not already exist in the list.
     */
    public void add(Link toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLinkException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent link from the list.
     * The link must exist in the list.
     */
    public void remove(Link toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LinkNotFoundException();
        }
    }

    public void setLinks(UniqueLinkList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code links}.
     * {@code links} must not contain duplicate links.
     */
    public void setLinks(List<Link> links) {
        requireAllNonNull(links);
        if (!linksAreUnique(links)) {
            throw new DuplicateLinkException();
        }

        internalList.setAll(links);
    }

    /**
     * Removes all links from list involving an ID.
     */
    public void deleteLinksWithId(String id) {
        internalList.removeIf(link -> link.getFrom().getUniqueID().equals(id) || link.getTo().getUniqueID().equals(id));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Link> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Link> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueLinkList)) {
            return false;
        }

        UniqueLinkList otherUniqueLinkList = (UniqueLinkList) other;
        return internalList.equals(otherUniqueLinkList.internalList);
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
     * Returns true if {@code links} contains only unique links.
     */
    private boolean linksAreUnique(List<Link> links) {
        for (int i = 0; i < links.size() - 1; i++) {
            for (int j = i + 1; j < links.size(); j++) {
                if (links.get(i).equals(links.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
