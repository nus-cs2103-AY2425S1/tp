package seedu.address.model.link;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
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

        toAdd.getFrom().addLinkedEntity(toAdd.getTo());
        toAdd.getTo().addLinkedEntity(toAdd.getFrom());

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
        toRemove.getFrom().removeLinkedEntity(toRemove.getTo());
        toRemove.getTo().removeLinkedEntity(toRemove.getFrom());
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
     * replaces the old pet in every link in the list that contains the old pet with the edited pet
     * requires the link.getTo() to always return a pet
     * @param original
     * @param updated
     */
    public void updateLinkWithNewPet(Linkable original, Linkable updated) {
        // Collect links to be updated
        List<Link> linksToUpdate = new ArrayList<>();
        for (Link link : internalList) {
            if (link.getTo().equals(original)) {
                linksToUpdate.add(link);
            }
        }

        for (Link link : linksToUpdate) {
            Link l = new Link(link.getFrom(), updated);
            this.remove(link);
            this.add(l);
        }
    }

    /**
     * replaces the old owner in every link in the list that contains the old owner with the edited owner
     * requires the link.getFrom() to always return an owner
     * @param original
     * @param updated
     */
    public void updateLinkWithNewOwner(Linkable original, Linkable updated) {
        // Collect links to be updated
        List<Link> linksToUpdate = new ArrayList<>();
        for (Link link : internalList) {
            //Owner o = (Owner) link.getFrom();
            if (link.getFrom().equals(original)) {
                linksToUpdate.add(link);
            }
        }

        // update each link in the original list
        for (Link link : linksToUpdate) {
            Link l = new Link(updated, link.getTo());
            this.remove(link); // Calls the specific remove method
            this.add(l); // Calls the specific add method
        }
    }

    /**
     * Removes all links from list involving an ID.
     */
    public void deleteLinksWithId(String id) {
        List<Link> linksToRemove = new ArrayList<>();

        // Collect links to be removed
        for (Link link : internalList) {
            if (link.getFrom().getUniqueID().equals(id) || link.getTo().getUniqueID().equals(id)) {
                linksToRemove.add(link);
            }
        }

        // Remove each link, allowing specific remove behavior in Link class if needed
        for (Link link : linksToRemove) {
            this.remove(link);
        }
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
