package seedu.address.model.link;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOwners.ALICE;
import static seedu.address.testutil.TypicalOwners.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.link.exceptions.DuplicateLinkException;
import seedu.address.model.link.exceptions.LinkNotFoundException;

public class UniqueLinkListTest {

    private final UniqueLinkList uniqueLinkList = new UniqueLinkList();

    @Test
    public void contains_nullLink_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLinkList.contains(null));
    }

    @Test
    public void contains_linkNotInList_returnsFalse() {
        Link link = new Link(ALICE, BOB);
        assertFalse(uniqueLinkList.contains(link));
    }

    @Test
    public void contains_linkInList_returnsTrue() {
        Link link = new Link(ALICE, BOB);
        uniqueLinkList.add(link);
        assertTrue(uniqueLinkList.contains(link));
    }

    @Test
    public void contains_linkWithSameValuesInList_returnsTrue() {
        Link link = new Link(ALICE, BOB);
        Link link2 = new Link(ALICE, BOB);
        uniqueLinkList.add(link);
        assertTrue(uniqueLinkList.contains(link2));
    }

    @Test
    public void add_nullLink_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLinkList.add(null));
    }

    @Test
    public void add_duplicateLink_throwsDuplicatePersonException() {
        Link link = new Link(ALICE, BOB);
        uniqueLinkList.add(link);
        assertThrows(DuplicateLinkException.class, () -> uniqueLinkList.add(link));
    }

    @Test
    public void remove_nullLink_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLinkList.remove(null));
    }

    @Test
    public void remove_linkDoesNotExist_throwsLinkNotFoundException() {
        Link link = new Link(ALICE, BOB);
        assertThrows(LinkNotFoundException.class, () -> uniqueLinkList.remove(link));
    }

    @Test
    public void remove_existingLink_removesLink() {
        Link link = new Link(ALICE, BOB);
        uniqueLinkList.add(link);
        uniqueLinkList.remove(link);
        UniqueLinkList expectedUniqueLinkList = new UniqueLinkList();
        assertEquals(expectedUniqueLinkList, uniqueLinkList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueLinkList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueLinkList.asUnmodifiableObservableList().toString(), uniqueLinkList.toString());
    }
}
