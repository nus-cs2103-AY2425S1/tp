package seedu.address.model.link;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOwners.ALICE;
import static seedu.address.testutil.TypicalOwners.BOB;

import java.util.Arrays;
import java.util.List;

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
    public void add_duplicateLink_throwsDuplicateLinkException() {
        Link link = new Link(ALICE, BOB);
        uniqueLinkList.add(link);
        assertThrows(DuplicateLinkException.class, () -> uniqueLinkList.add(link));
    }

    @Test
    public void setLinks_nullLinkList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLinkList.setLinks((UniqueLinkList) null));
    }

    @Test
    public void setLinks_duplicateLinks_throwsDuplicateLinkException() {
        List<Link> links = Arrays.asList(new Link(ALICE, BOB), new Link(ALICE, BOB));
        assertThrows(DuplicateLinkException.class, () -> uniqueLinkList.setLinks(links));
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
    public void equals_sameObject_returnsTrue() {
        assertEquals(uniqueLinkList, uniqueLinkList);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertNotEquals(uniqueLinkList, 1);
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
