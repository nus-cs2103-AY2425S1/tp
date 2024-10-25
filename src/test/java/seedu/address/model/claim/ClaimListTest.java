package seedu.address.model.claim;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClaimListTest {

    private ClaimList claimList;
    private Claim claim1;
    private Claim claim2;
    private Claim claim3;

    @BeforeEach
    public void setUp() {
        claimList = new ClaimList();
        claim1 = new Claim(ClaimStatus.PENDING, "car accident");
        claim2 = new Claim(ClaimStatus.APPROVED, "medical expenses");
        claim3 = new Claim(ClaimStatus.REJECTED, "house repair");
    }

    @Test
    public void getList_unmodifiableList_success() {
        claimList.add(claim1);
        claimList.add(claim2);
        List<Claim> unmodifiableList = claimList.getList();

        assertEquals(2, unmodifiableList.size());
        assertTrue(unmodifiableList.contains(claim1));
        assertTrue(unmodifiableList.contains(claim2));

        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(claim3));
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.remove(claim1));
        assertThrows(UnsupportedOperationException.class, unmodifiableList::clear);
    }

    @Test
    public void add_claimNotExists_success() {
        assertTrue(claimList.add(claim1));
        assertTrue(claimList.add(claim2));
        assertEquals(2, claimList.size());
    }

    @Test
    public void add_duplicateClaim_returnsFalse() {
        claimList.add(claim1);
        assertFalse(claimList.add(claim1));
        assertEquals(1, claimList.size());
    }
    @Test
    public void addAtIndex_uniqueClaim_success() {
        claimList.add(claim1); // First, add one claim
        claimList.add(0, claim2); // Add another claim at index 0

        // Check that claim2 is inserted at the correct index
        assertEquals(claim2, claimList.get(0));
        assertEquals(claim1, claimList.get(1)); // claim1 should now be at index 1
        assertEquals(2, claimList.size()); // Verify the size
    }

    @Test
    public void addAtIndex_duplicateClaim_doesNotAdd() {
        claimList.add(claim1); // Add claim1 once

        // Try to add claim1 again at index 0
        claimList.add(0, claim1);

        // Check that claim1 is still only present once
        assertEquals(1, claimList.size());
        assertEquals(claim1, claimList.get(0)); // claim1 should remain at index 0
    }

    @Test
    public void addAtIndex_invalidIndex_throwsIndexOutOfBoundsException() {
        // No claims in the list yet; adding at an out-of-bounds index should throw an exception
        assertThrows(IndexOutOfBoundsException.class, () -> claimList.add(5, claim1));
        assertThrows(IndexOutOfBoundsException.class, () -> claimList.add(-1, claim2));
    }

    @Test
    public void addAll_validClaims_success() {
        Set<Claim> newClaims = new HashSet<>();
        newClaims.add(claim1);
        newClaims.add(claim2);

        assertTrue(claimList.addAll(newClaims));
        assertEquals(2, claimList.size());
    }
    @Test
    public void addAll_allDuplicateClaims_returnsFalse() {
        claimList.add(claim1);
        claimList.add(claim2);
        Set<Claim> duplicateClaims = new HashSet<>(Arrays.asList(claim1, claim2));
        assertFalse(claimList.addAll(duplicateClaims));
        assertEquals(2, claimList.size()); // Size remains the same
    }
    @Test
    public void addAllAtIndex_mixedClaims_someDuplicates_returnsFalse() {
        claimList.add(claim1);
        List<Claim> mixedClaims = Arrays.asList(claim1, claim2);
        assertFalse(claimList.addAll(1, mixedClaims));
        assertEquals(2, claimList.size());
        assertEquals(claim1, claimList.get(0));
        assertEquals(claim2, claimList.get(1));
    }

    @Test
    public void addAllAtIndex_invalidIndex_throwsIndexOutOfBoundsException() {
        List<Claim> claimsToAdd = Arrays.asList(claim1, claim2);
        assertThrows(IndexOutOfBoundsException.class, () -> claimList.addAll(5, claimsToAdd));
        assertThrows(IndexOutOfBoundsException.class, () -> claimList.addAll(-1, claimsToAdd));
    }
    @Test
    public void contains_existingClaim_returnsTrue() {
        claimList.add(claim1);
        assertTrue(claimList.contains(claim1));
    }

    @Test
    public void contains_nonExistingClaim_returnsFalse() {
        claimList.add(claim1);
        assertFalse(claimList.contains(claim2));
    }
    @Test
    public void iterator_emptyList_noException() {
        Iterator<Claim> iterator = claimList.iterator();

        // Check that the iterator is not null
        assertNotNull(iterator);

        // Verify that hasNext() is false for an empty list
        assertFalse(iterator.hasNext());
    }
    @Test
    public void indexOf_existingObject_returnsCorrectIndex() {
        claimList.add(claim1);
        claimList.add(claim2);
        claimList.add(claim3);
        assertEquals(0, claimList.indexOf(claim1));
        assertEquals(1, claimList.indexOf(claim2));
        assertEquals(2, claimList.indexOf(claim3));
    }

    @Test
    public void indexOf_nonExistingObject_returnsNegativeOne() {
        claimList.add(claim1);
        claimList.add(claim2);
        assertEquals(-1, claimList.indexOf(claim3));
    }
    @Test
    public void lastIndexOf_singleOccurrence_returnsCorrectIndex() {
        claimList.add(claim1);
        claimList.add(claim2);
        assertEquals(1, claimList.lastIndexOf(claim2));
    }
    @Test
    public void remove_validIndex_returnsRemovedElement() {
        claimList.add(claim1);
        claimList.add(claim2);
        claimList.add(claim3);
        Claim removedClaim = claimList.remove(1);
        assertEquals(claim2, removedClaim);
        assertEquals(2, claimList.size());
        assertTrue(claimList.contains(claim1));
        assertFalse(claimList.contains(claim2));
        assertTrue(claimList.contains(claim3));
    }
    @Test
    public void remove_existingClaim_success() {
        claimList.add(claim1);
        assertTrue(claimList.remove(claim1));
        assertEquals(0, claimList.size());
    }

    @Test
    public void remove_nonExistingClaim_returnsFalse() {
        claimList.add(claim1);
        assertFalse(claimList.remove(claim2));
    }
    @Test
    public void retainAll_retainsSpecifiedClaims_success() {
        claimList.add(claim1);
        claimList.add(claim2);
        claimList.add(claim3);

        // Create a collection with only claim1 and claim3
        Set<Claim> claimsToRetain = new HashSet<>(Arrays.asList(claim1, claim3));

        // Retain only the elements in claimsToRetain
        assertTrue(claimList.retainAll(claimsToRetain));

        // Verify that only claim1 and claim3 remain in claimList
        assertEquals(2, claimList.size());
        assertTrue(claimList.contains(claim1));
        assertFalse(claimList.contains(claim2));
        assertTrue(claimList.contains(claim3));
    }

    @Test
    public void replaceAll_applyOperator_success() {
        claimList.add(claim1);
        claimList.add(claim2);

        // Replace each claim with a new claim with status updated to APPROVED
        claimList.replaceAll(claim -> new Claim(ClaimStatus.APPROVED, claim.getClaimDescription()));

        // Verify that all claims now have the status APPROVED
        for (Claim claim : claimList) {
            assertEquals(ClaimStatus.APPROVED, claim.getStatus());
        }
    }

    @Test
    public void sort_sortsByStatus_success() {
        claimList.add(claim3); // Assume claim3 has status REJECTED
        claimList.add(claim1); // Assume claim1 has status PENDING
        claimList.add(claim2); // Assume claim2 has status APPROVED

        // Sort the claims by status (for example, alphabetical order of status)
        claimList.sort(Comparator.comparing(Claim::getStatus));

        // Verify the sorting order (REJECTED, PENDING, APPROVED)
        assertEquals(claim3, claimList.get(2));
        assertEquals(claim1, claimList.get(0));
        assertEquals(claim2, claimList.get(1));
    }

    @Test
    public void set_validIndex_replacesClaim_success() {
        claimList.add(claim1);
        claimList.add(claim2);

        // Replace the claim at index 1 with claim3
        Claim previousClaim = claimList.set(1, claim3);

        // Verify the previous element at index 1 was claim2
        assertEquals(claim2, previousClaim);

        // Verify the new element at index 1 is now claim3
        assertEquals(claim3, claimList.get(1));
        assertEquals(2, claimList.size());
    }

    @Test
    public void set_invalidIndex_throwsIndexOutOfBoundsException() {
        claimList.add(claim1);

        // Attempt to set an element at an out-of-bounds index
        assertThrows(IndexOutOfBoundsException.class, () -> claimList.set(5, claim2));
        assertThrows(IndexOutOfBoundsException.class, () -> claimList.set(-1, claim3));
    }

    @Test
    public void isEmpty_emptySet_returnsTrue() {
        assertTrue(claimList.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptySet_returnsFalse() {
        claimList.add(claim1);
        assertFalse(claimList.isEmpty());
    }

    @Test
    public void size_correctSize() {
        claimList.add(claim1);
        claimList.add(claim2);
        assertEquals(2, claimList.size());
    }

    @Test
    public void clear_claimSetCleared_success() {
        claimList.add(claim1);
        claimList.add(claim2);
        claimList.clear();
        assertEquals(0, claimList.size());
        assertTrue(claimList.isEmpty());
    }

    @Test
    public void equals() {
        final Set<Claim> claimsSet1 = new HashSet<>(Arrays.asList(claim1, claim2));
        final Set<Claim> claimsSet2 = new HashSet<>(Collections.singletonList(claim1));

        final ClaimList standardClaimList = new ClaimList();
        standardClaimList.addAll(claimsSet1);

        final ClaimList sameValuesClaimList = new ClaimList();
        sameValuesClaimList.addAll(claimsSet1);

        final ClaimList differentClaimsClaimList = new ClaimList();
        differentClaimsClaimList.addAll(claimsSet2);

        // same values -> returns true
        assertTrue(standardClaimList.equals(sameValuesClaimList));

        // same object -> returns true
        assertTrue(standardClaimList.equals(standardClaimList));

        // null -> returns false
        assertFalse(standardClaimList.equals(null));

        // different type -> returns false
        assertFalse(standardClaimList.equals("foo"));

        // different claims -> returns false
        assertFalse(standardClaimList.equals(differentClaimsClaimList));
    }


    @Test
    public void hashCode_consistentHashCode() {
        claimList.add(claim1);
        int initialHashCode = claimList.hashCode();
        assertEquals(initialHashCode, claimList.hashCode());

        claimList.add(claim2);
        int newHashCode = claimList.hashCode();
        assertNotEquals(initialHashCode, newHashCode);
    }

    @Test
    public void toArray_returnsCorrectArray() {
        claimList.add(claim1);
        claimList.add(claim2);

        Object[] array = claimList.toArray();
        assertEquals(2, array.length);
        assertTrue(Arrays.asList(array).contains(claim1));
        assertTrue(Arrays.asList(array).contains(claim2));
    }

    @Test
    public void toString_returnsNonEmptyString() {
        claimList.add(claim1);
        claimList.add(claim2);

        String result = claimList.toString();
        assertTrue(result.contains(claim1.getClaimDescription()));
        assertTrue(result.contains(claim2.getClaimDescription()));
    }

    @Test
    public void add_claimNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> claimList.add(null));
    }

    @Test
    public void addAll_nullCollection_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> claimList.addAll(null));
    }

    @Test
    public void contains_nullClaim_returnsFalse() {
        assertFalse(claimList.contains(null));
    }

    @Test
    public void containsAll_validClaims_returnsTrue() {
        claimList.add(claim1);
        claimList.add(claim2);
        Set<Claim> claims = new HashSet<>(Arrays.asList(claim1, claim2));
        assertTrue(claimList.containsAll(claims));
    }

    @Test
    public void containsAll_nonExistingClaim_returnsFalse() {
        claimList.add(claim1);
        Set<Claim> claims = new HashSet<>(Arrays.asList(claim1, claim2));
        assertFalse(claimList.containsAll(claims));
    }

    @Test
    public void removeAll_validClaims_removesAll() {
        claimList.add(claim1);
        claimList.add(claim2);
        Set<Claim> claimsToRemove = new HashSet<>(Arrays.asList(claim1, claim2));
        assertTrue(claimList.removeAll(claimsToRemove));
        assertEquals(0, claimList.size());
    }

    @Test
    public void removeAll_nonExistingClaim_doesNotRemove() {
        claimList.add(claim1);
        Set<Claim> claimsToRemove = Collections.singleton(claim2);
        assertFalse(claimList.removeAll(claimsToRemove));
        assertEquals(1, claimList.size());
    }

    @Test
    public void subList_validIndices_returnsCorrectSubList() {
        claimList.add(claim1);
        claimList.add(claim2);
        claimList.add(claim3);

        // Get a sublist from index 0 to 2 (exclusive of 2)
        List<Claim> subList = claimList.subList(0, 2);

        // Verify the contents of the sublist
        assertEquals(2, subList.size());
        assertEquals(claim1, subList.get(0));
        assertEquals(claim2, subList.get(1));
    }
    @Test
    public void spliterator_traversesAllElements_success() {
        claimList.add(claim1);
        claimList.add(claim2);
        claimList.add(claim3);

        Spliterator<Claim> spliterator = claimList.spliterator();

        // Use the spliterator to collect elements
        List<Claim> collectedClaims = new ArrayList<>();
        spliterator.forEachRemaining(collectedClaims::add);

        // Verify all elements were traversed
        assertEquals(3, collectedClaims.size());
        assertTrue(collectedClaims.contains(claim1));
        assertTrue(collectedClaims.contains(claim2));
        assertTrue(collectedClaims.contains(claim3));
    }
    @Test
    public void toArray_withLargerTypedArray_returnsCorrectArray() {
        claimList.add(claim1);
        claimList.add(claim2);
        Claim[] array = claimList.toArray(new Claim[5]); // Array larger than claimSet size
        assertEquals(2, Arrays.stream(array).filter(Objects::nonNull).count());
        assertTrue(Arrays.asList(array).contains(claim1));
        assertTrue(Arrays.asList(array).contains(claim2));
        assertNull(array[2]);
    }

    @Test
    public void clear_emptySet_returnsTrue() {
        claimList.clear();
        assertTrue(claimList.isEmpty());
    }

    @Test
    public void clear_nonEmptySet_returnsTrue() {
        claimList.add(claim1);
        claimList.clear();
        assertTrue(claimList.isEmpty());
        assertEquals(0, claimList.size());
    }
}

