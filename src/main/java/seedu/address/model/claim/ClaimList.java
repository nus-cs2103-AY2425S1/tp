package seedu.address.model.claim;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

/**
 * A class to store a list of {@code Claim}.
 * This class ensures that the order of insertion is preserved and allows duplicate claims.
 */
public class ClaimList implements List<Claim> {
    private final List<Claim> claims;

    public ClaimList() {
        this.claims = new ArrayList<>();
    }
    public List<Claim> getList() {
        return Collections.unmodifiableList(claims);
    }
    @Override
    public boolean add(Claim claim) {
        requireNonNull(claim);
        if (!claims.contains(claim)) {
            claims.add(claim);
            return true;
        }
        return false;
    }

    @Override
    public void add(int index, Claim claim) {
        requireNonNull(claim);
        if (!claims.contains(claim)) {
            claims.add(index, claim);
        }
    }

    @Override
    public boolean addAll(Collection<? extends Claim> collection) {
        boolean allAdded = true;
        for (Claim claim : collection) {
            if (!add(claim)) {
                allAdded = false;
            }
        }
        return allAdded; // Return true only if all claims were added
    }

    @Override
    public boolean addAll(int index, Collection<? extends Claim> collection) {
        boolean allAdded = true;
        int currentIndex = index;
        for (Claim claim : collection) {
            if (!claims.contains(claim)) {
                claims.add(currentIndex++, claim);
            } else {
                allAdded = false;
            }
        }
        return allAdded; // Return true only if all claims were added
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
            result.append(claim.toString()).append("\n");
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
