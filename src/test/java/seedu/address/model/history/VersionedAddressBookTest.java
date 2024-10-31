package seedu.address.model.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;

public class VersionedAddressBookTest {
    private AddressBook addressBook1;
    private AddressBook addressBook2;
    private VersionedAddressBook versionedAddressBook;

    @BeforeEach
    void setUp() {
        addressBook1 = new AddressBook();
        addressBook2 = getTypicalAddressBook();
        versionedAddressBook = new VersionedAddressBook(addressBook1);
    }

    @Test
    void testConstructor() {
        ArrayList<AddressBook> expectedStateList = new ArrayList<>();
        expectedStateList.add(addressBook1);

        assertEquals(expectedStateList, versionedAddressBook.getAddressBookStateList());
        assertEquals(1, versionedAddressBook.getAddressBookStateList().size());
    }

    @Test
    void testCommitAddressBook() {
        versionedAddressBook.commitAddressBook(addressBook2);

        ArrayList<AddressBook> expectedStateList = new ArrayList<>();
        expectedStateList.add(addressBook1);
        expectedStateList.add(addressBook2);

        assertEquals(expectedStateList, versionedAddressBook.getAddressBookStateList());
        assertEquals(2, versionedAddressBook.getAddressBookStateList().size());
    }

    @Test
    void testUndoAddressBook() {
        versionedAddressBook.commitAddressBook(addressBook2);

        ArrayList<AddressBook> expectedStateList = new ArrayList<>();
        expectedStateList.add(new AddressBook(addressBook1));
        expectedStateList.add(new AddressBook(addressBook2));

        assertEquals(expectedStateList, versionedAddressBook.getAddressBookStateList());
        assertEquals(addressBook1, versionedAddressBook.getAddressBookStateList().get(0));
        assertEquals(addressBook2, versionedAddressBook.getAddressBookStateList().get(1));

        versionedAddressBook.undoAddressBook(addressBook2);
        assertEquals(addressBook1, addressBook2);  // Check if the current state is reverted to addressBook1
    }

    @Test
    void testEquals() {
        VersionedAddressBook sameVersionedAddressBook = new VersionedAddressBook(addressBook1);

        assertEquals(versionedAddressBook, sameVersionedAddressBook);

        versionedAddressBook.commitAddressBook(addressBook2);
        assertNotEquals(versionedAddressBook, sameVersionedAddressBook);

        sameVersionedAddressBook.commitAddressBook(addressBook2);
        assertEquals(versionedAddressBook, sameVersionedAddressBook);
    }
}
