package seedu.address.model.claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClaimSetTest {

    private ClaimSet claimSet;
    private Claim claim1;
    private Claim claim2;
    private Claim claim3;

    @BeforeEach
    public void setUp() {
        claimSet = new ClaimSet();
        claim1 = new Claim(ClaimStatus.PENDING, "car accident");
        claim2 = new Claim(ClaimStatus.APPROVED, "medical expenses");
        claim3 = new Claim(ClaimStatus.REJECTED, "house repair");
    }

    @Test
    public void add_claimNotExists_success() {
        assertTrue(claimSet.add(claim1));
        assertTrue(claimSet.add(claim2));
        assertEquals(2, claimSet.size());
    }

    @Test
    public void add_duplicateClaim_returnsFalse() {
        claimSet.add(claim1);
        assertFalse(claimSet.add(claim1));
        assertEquals(1, claimSet.size());
    }

    @Test
    public void addAll_validClaims_success() {
        Set<Claim> newClaims = new HashSet<>();
        newClaims.add(claim1);
        newClaims.add(claim2);

        assertTrue(claimSet.addAll(newClaims));
        assertEquals(2, claimSet.size());
    }

    @Test
    public void addAll_duplicateClaims_returnsFalse() {
        claimSet.add(claim1);
        Set<Claim> duplicateClaims = new HashSet<>();
        duplicateClaims.add(claim1);
        duplicateClaims.add(claim2);

        assertTrue(claimSet.addAll(duplicateClaims));
        assertEquals(2, claimSet.size());
    }

    @Test
    public void contains_existingClaim_returnsTrue() {
        claimSet.add(claim1);
        assertTrue(claimSet.contains(claim1));
    }

    @Test
    public void contains_nonExistingClaim_returnsFalse() {
        claimSet.add(claim1);
        assertFalse(claimSet.contains(claim2));
    }

    @Test
    public void remove_existingClaim_success() {
        claimSet.add(claim1);
        assertTrue(claimSet.remove(claim1));
        assertEquals(0, claimSet.size());
    }

    @Test
    public void remove_nonExistingClaim_returnsFalse() {
        claimSet.add(claim1);
        assertFalse(claimSet.remove(claim2));
    }

    @Test
    public void isEmpty_emptySet_returnsTrue() {
        assertTrue(claimSet.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptySet_returnsFalse() {
        claimSet.add(claim1);
        assertFalse(claimSet.isEmpty());
    }

    @Test
    public void size_correctSize() {
        claimSet.add(claim1);
        claimSet.add(claim2);
        assertEquals(2, claimSet.size());
    }

    @Test
    public void clear_claimSetCleared_success() {
        claimSet.add(claim1);
        claimSet.add(claim2);
        claimSet.clear();
        assertEquals(0, claimSet.size());
        assertTrue(claimSet.isEmpty());
    }

    @Test
    public void iterator_claimSetIteration_success() {
        claimSet.add(claim1);
        claimSet.add(claim2);
        claimSet.add(claim3);

        Iterator<Claim> iterator = claimSet.iterator();
        Set<Claim> result = new HashSet<>();

        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        assertEquals(3, result.size());
        assertTrue(result.contains(claim1));
        assertTrue(result.contains(claim2));
        assertTrue(result.contains(claim3));
    }

    @Test
    public void equals_sameClaimSet_returnsTrue() {
        ClaimSet anotherClaimSet = new ClaimSet();
        anotherClaimSet.add(claim1);
        anotherClaimSet.add(claim2);

        claimSet.add(claim1);
        claimSet.add(claim2);

        assertEquals(claimSet, anotherClaimSet);
    }

    @Test
    public void equals_differentClaimSet_returnsFalse() {
        ClaimSet anotherClaimSet = new ClaimSet();
        anotherClaimSet.add(claim1);

        claimSet.add(claim1);
        claimSet.add(claim2);

        assertNotEquals(claimSet, anotherClaimSet);
    }

    @Test
    public void hashCode_consistentHashCode() {
        claimSet.add(claim1);
        int initialHashCode = claimSet.hashCode();
        assertEquals(initialHashCode, claimSet.hashCode());

        claimSet.add(claim2);
        int newHashCode = claimSet.hashCode();
        assertNotEquals(initialHashCode, newHashCode);
    }

    @Test
    public void toArray_returnsCorrectArray() {
        claimSet.add(claim1);
        claimSet.add(claim2);

        Object[] array = claimSet.toArray();
        assertEquals(2, array.length);
        assertTrue(Arrays.asList(array).contains(claim1));
        assertTrue(Arrays.asList(array).contains(claim2));
    }

    @Test
    public void toString_returnsNonEmptyString() {
        claimSet.add(claim1);
        claimSet.add(claim2);

        String result = claimSet.toString();
        assertTrue(result.contains(claim1.getClaimDescription()));
        assertTrue(result.contains(claim2.getClaimDescription()));
    }

    @Test
    public void add_claimNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> claimSet.add(null));
    }

    @Test
    public void addAll_nullCollection_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> claimSet.addAll(null));
    }

    @Test
    public void contains_nullClaim_returnsFalse() {
        assertFalse(claimSet.contains(null));
    }

    @Test
    public void containsAll_validClaims_returnsTrue() {
        claimSet.add(claim1);
        claimSet.add(claim2);
        Set<Claim> claims = new HashSet<>(Arrays.asList(claim1, claim2));
        assertTrue(claimSet.containsAll(claims));
    }

    @Test
    public void containsAll_nonExistingClaim_returnsFalse() {
        claimSet.add(claim1);
        Set<Claim> claims = new HashSet<>(Arrays.asList(claim1, claim2));
        assertFalse(claimSet.containsAll(claims));
    }

    @Test
    public void removeAll_validClaims_removesAll() {
        claimSet.add(claim1);
        claimSet.add(claim2);
        Set<Claim> claimsToRemove = new HashSet<>(Arrays.asList(claim1, claim2));
        assertTrue(claimSet.removeAll(claimsToRemove));
        assertEquals(0, claimSet.size());
    }

    @Test
    public void removeAll_nonExistingClaim_doesNotRemove() {
        claimSet.add(claim1);
        Set<Claim> claimsToRemove = Collections.singleton(claim2);
        assertFalse(claimSet.removeAll(claimsToRemove));
        assertEquals(1, claimSet.size());
    }

    @Test
    public void toArray_withLargerTypedArray_returnsCorrectArray() {
        claimSet.add(claim1);
        claimSet.add(claim2);
        Claim[] array = claimSet.toArray(new Claim[5]); // Array larger than claimSet size
        assertEquals(2, Arrays.stream(array).filter(Objects::nonNull).count());
        assertTrue(Arrays.asList(array).contains(claim1));
        assertTrue(Arrays.asList(array).contains(claim2));
        assertNull(array[2]);
    }

    @Test
    public void clear_emptySet_returnsTrue() {
        claimSet.clear();
        assertTrue(claimSet.isEmpty());
    }

    @Test
    public void clear_nonEmptySet_returnsTrue() {
        claimSet.add(claim1);
        claimSet.clear();
        assertTrue(claimSet.isEmpty());
        assertEquals(0, claimSet.size());
    }

    @Test
    public void retainAll_throwsUnsupportedOperationException() {
        Set<Claim> claimsToRetain = new HashSet<>(Arrays.asList(claim1, claim2));
        assertThrows(UnsupportedOperationException.class, () -> claimSet.retainAll(claimsToRetain));
    }
}

