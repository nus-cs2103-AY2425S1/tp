package seedu.address.model.claim;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

import seedu.address.model.claim.exceptions.DuplicateClaimException;

/**
 * A class to store a list of {@code Claim}.
 * This class ensures that the order of insertion is preserved and disallows duplicate claims.
 */
public class ClaimList implements List<Claim> {
    private final List<Claim> claims = new ArrayList<>();

    public ClaimList() {}

    /**
     * Constructor for a new {@code ClaimList} initialized with the following claims.
     *
     * @param claims the claims to be added to this {@code ClaimList}.
     * @throws NullPointerException when {@code claims} or a {@code Claim} in claims is null.
     * @throws DuplicateClaimException when the collection of {@code claims} contains duplicates.
     */
    public ClaimList(Collection<Claim> claims) {
        requireAllNonNull(claims);
        for (Claim claim : claims) {
            if (!add(claim)) {
                throw new DuplicateClaimException();
            }
        }
    }

    /**
     * Adds the {@code claim} to this list. Returns true if it has been successfully added. Returns
     * false if there exists a similar claim as {@code claim}.
     *
     * @param claim the claim to be added to this list.
     * @return true if there are no similar claim already in this list.
     * @throws NullPointerException when claim is null.
     */
    @Override
    public boolean add(Claim claim) {
        requireNonNull(claim);
        if (!claims.contains(claim)) {
            claims.add(claim);
            return true;
        }
        return false;
    }

    /**
     * Inserts the {@code claim} to this list at the specified {@code index}, if there are no duplicates.
     *
     * @param claim the claim to be added to this list.
     * @throws NullPointerException when claim is null.
     * @throws IndexOutOfBoundsException if the index is out of range of this list.
     */
    @Override
    public void add(int index, Claim claim) {
        requireNonNull(claim);
        if (!claims.contains(claim)) {
            claims.add(index, claim);
        }
    }

    /**
     * Appends all {@code Claim} in {@code claims} to the end of this list. The behaviour of this operation
     * is equivalent to calling {@code add(Claim)} for each {@code Claim}.
     * This call will not add duplicate claims to this list.
     * Returns true if this list changed as a result of this call.
     *
     * @param claims the collection of claims to be added to this list.
     * @return true if this list changed as a result of this call.
     * @throws NullPointerException when {@code claims} or any {@code Claim} in {@code claims} is null.
     */
    @Override
    public boolean addAll(Collection<? extends Claim> claims) {
        requireAllNonNull(claims);
        boolean isChanged = false;
        for (Claim claim : claims) {
            if (add(claim)) {
                isChanged = true;
            }
        }
        return isChanged;
    }

    /**
     * Inserts all {@code Claim} in {@code claims} at the specified {@code index}, ignoring duplicates.
     * Returns true if this list changed as a result of this call.
     *
     * @param index the index to insert the claims.
     * @param claims the collection of claims to be added to this list.
     * @return true if this list changed as a result of this call.
     * @throws NullPointerException when {@code claims} or any {@code Claim} in {@code claims} is null.
     * @throws IndexOutOfBoundsException if the index is out of range of this list.
     */
    @Override
    public boolean addAll(int index, Collection<? extends Claim> claims) {
        requireAllNonNull(claims);
        boolean isChanged = false;
        int currentIndex = index;
        for (Claim claim : claims) {
            if (!contains(claim)) {
                add(currentIndex++, claim);
                isChanged = true;
            }
        }
        return isChanged;
    }



    @Override
    public void clear() {
        claims.clear();
    }

    @Override
    public boolean contains(Object obj) {
        return claims.contains(obj);
    }

    @Override
    public Iterator<Claim> iterator() {
        return claims.iterator();
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return claims.containsAll(collection);
    }

    @Override
    public Claim get(int index) {
        return claims.get(index);
    }

    @Override
    public int indexOf(Object obj) {
        requireNonNull(obj);
        return claims.indexOf(obj);
    }

    @Override
    public boolean isEmpty() {
        return claims.isEmpty();
    }

    @Override
    public int lastIndexOf(Object obj) {
        return claims.lastIndexOf(obj);
    }

    @Override
    public boolean remove(Object obj) {
        requireNonNull(obj);
        return claims.remove(obj);
    }

    @Override
    public Claim remove(int index) {
        return claims.remove(index);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return claims.removeAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return claims.retainAll(collection);
    }

    @Override
    public void replaceAll(UnaryOperator<Claim> operator) {
        List.super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super Claim> c) {
        List.super.sort(c);
    }

    @Override
    public Claim set(int index, Claim claim) {
        requireNonNull(claim);
        return claims.set(index, claim);
    }

    @Override
    public int size() {
        return claims.size();
    }

    @Override
    public List<Claim> subList(int fromIndex, int toIndex) {
        return claims.subList(fromIndex, toIndex);
    }

    @Override
    public Spliterator<Claim> spliterator() {
        return List.super.spliterator();
    }

    @Override
    public Object[] toArray() {
        return claims.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return claims.toArray(a);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClaimList)) {
            return false;
        }
        ClaimList otherList = (ClaimList) other;
        return claims.equals(otherList.claims);
    }

    @Override
    public int hashCode() {
        return claims.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Claim claim : claims) {
            result.append("        ").append(claim.toString()).append("\n");
        }
        return result.toString();
    }

    @Override
    public ListIterator<Claim> listIterator() {
        return claims.listIterator();
    }

    @Override
    public ListIterator<Claim> listIterator(int index) {
        return claims.listIterator(index);
    }
}
