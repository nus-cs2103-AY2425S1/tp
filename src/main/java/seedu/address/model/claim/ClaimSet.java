package seedu.address.model.claim;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A class to store a set of {@code Claim}.
 * This class ensures that a single {@code ClaimSet} cannot have multiple claims with the same {@code Claim}
 * description.
 */
public class ClaimSet implements Set<Claim> {
    private static final int size = 100; // Assume a max limit for simplicity
    private final Claim[] claims = new Claim[size];

    /**
     * A hash function to map each {@code Claim} description to an array index in {@code claims}.
     * The hash is computed using the description's hash code modulo the size of the array.
     *
     * @param description The description of the claim to determine the hash for.
     * @return The index of the {@code description} in the {@code claims} array.
     * @throws NullPointerException If {@code description} is null.
     */
    private static int hash(String description) {
        requireNonNull(description);
        // Ensure a non-negative index, even for Integer.MIN_VALUE.
        return (description.hashCode() & Integer.MAX_VALUE) % size;
    }

    /**
     * Adds the specified {@code Claim} to this set if there are no other {@code Claim} with the same description.
     * If this set already contains a claim with the same description, the call leaves the set unchanged.
     *
     * @param claim to be added to this set.
     * @return true if this set did not already contain the specified {@code Claim} description.
     */
    @Override
    public boolean add(Claim claim) {
        // First, check if the claim already exists
        for (Claim existingClaim : claims) {
            if (existingClaim != null && existingClaim.equals(claim)) {
                return false; // Claim already exists
            }
        }

        // If the claim doesn't exist, find the correct index and add it
        int index = hash(claim.getClaimDescription());
        claims[index] = claim;
        return true;
    }

    /**
     * Adds all of the elements in the specified {@code collection} to this set.
     *
     * @param collection containing elements to be added to this set.
     * @return true if this set has been changed as a result of the call.
     */
    @Override
    public boolean addAll(Collection<? extends Claim> collection) {
        boolean isChanged = false;
        for (Claim claim : collection) {
            if (add(claim)) {
                isChanged = true;
            }
        }
        return isChanged;
    }

    @Override
    public void clear() {
        Arrays.fill(claims, null);
    }

    /**
     * Checks if the set contains a {@code Claim} with the specified {@code Claim} description.
     *
     * @param obj to be tested for containment.
     * @return true if this set contains a {@code Claim} with the specified {@code Claim} description.
     */
    @Override
    public boolean contains(Object obj) {
        if (!(obj instanceof Claim)) {
            return false;
        }

        Claim claim = (Claim) obj;
        int index = hash(claim.getClaimDescription());
        return claims[index] != null;
    }

    /**
     * Removes the {@code Claim} with the specified description.
     *
     * @param obj whose associated {@code Claim} is to be removed.
     * @return true if this set had a {@code Claim} associated with the description.
     */
    @Override
    public boolean remove(Object obj) {
        if (!(obj instanceof Claim)) {
            return false;
        }

        Claim claim = (Claim) obj;
        int index = hash(claim.getClaimDescription());
        if (claims[index] == null) {
            return false;
        }
        claims[index] = null;
        return true;
    }

    @Override
    public boolean isEmpty() {
        for (Claim claim : claims) {
            if (claim != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        int count = 0;
        for (Claim claim : claims) {
            if (claim != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Iterator<Claim> iterator() {
        List<Claim> claimList = new ArrayList<>();
        for (Claim claim : claims) {
            if (claim != null) {
                claimList.add(claim);
            }
        }
        return claimList.iterator();
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object obj : collection) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean isChanged = false;
        for (Object obj : collection) {
            if (remove(obj)) {
                isChanged = true;
            }
        }
        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("ClaimSet does not support retainAll operation.");
    }

    @Override
    public Object[] toArray() {
        return Arrays.stream(claims).filter(c -> c != null).toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return Arrays.stream(claims).filter(c -> c != null).toArray(size -> a);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClaimSet)) {
            return false;
        }

        ClaimSet otherClaimSet = (ClaimSet) other;
        return Arrays.equals(claims, otherClaimSet.claims);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(claims);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Claim claim : claims) {
            if (claim != null) {
                result.append(claim.toString()).append("\n");
            }
        }
        return result.toString();
    }
}
