package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.model.VersionedAddressBook.MESSAGE_CONSTRAINTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class VersionedAddressBookTest {
    private ModelManager model;
    private AddressBook addressBook1;
    private AddressBook addressBook2;
    private VersionedAddressBook versionedAddressBook;

    @BeforeEach
    void setUp() {
        addressBook1 = new AddressBook();
        model = new ModelManager(addressBook1, new UserPrefs());
        versionedAddressBook = model.getVersionedAddressBook();
        addressBook2 = getTypicalAddressBook();
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
        model.setAddressBook(addressBook2);
        versionedAddressBook.commitAddressBook();

        ArrayList<AddressBook> expectedStateList = new ArrayList<>();
        expectedStateList.add(addressBook1);
        expectedStateList.add(addressBook2);

        assertEquals(expectedStateList, versionedAddressBook.getAddressBookStateList());
        assertEquals(2, versionedAddressBook.getAddressBookStateList().size());
    }

    @Test
    void testUndoAddressBook() throws CommandException {
        assertEquals(addressBook1, versionedAddressBook.current);
        assertEquals(addressBook1, versionedAddressBook.getAddressBookStateList().get(0));

        model.setAddressBook(addressBook2);
        versionedAddressBook.commitAddressBook();

        ArrayList<AddressBook> expectedStateList = new ArrayList<>();
        expectedStateList.add(new AddressBook(addressBook1));
        expectedStateList.add(new AddressBook(addressBook2));
        assertEquals(expectedStateList, versionedAddressBook.getAddressBookStateList());

        assertEquals(addressBook2, versionedAddressBook.current);
        assertEquals(addressBook2, versionedAddressBook.getAddressBookStateList().get(1));

        versionedAddressBook.undoAddressBook();
        // Check if the current state is reverted to addressBook1
        assertEquals(addressBook1, versionedAddressBook.current);
    }



    @Test
    void testEquals() throws CommandException {
        VersionedAddressBook sameVersionedAddressBook = new VersionedAddressBook(addressBook1);

        // different instances with same state pointer and state list
        assertEquals(versionedAddressBook, sameVersionedAddressBook);

        //different instances with different state pointer and different state list
        model.setAddressBook(addressBook2);
        versionedAddressBook.commitAddressBook();
        assertNotEquals(versionedAddressBook, sameVersionedAddressBook);

        //different state pointer but same state list
        model.setAddressBook(addressBook2);
        sameVersionedAddressBook.commitAddressBook();
        sameVersionedAddressBook.undoAddressBook();
        assertNotEquals(versionedAddressBook, sameVersionedAddressBook);

        assertNotEquals(sameVersionedAddressBook, null);
        assertEquals(sameVersionedAddressBook, sameVersionedAddressBook);
    }

    @Test
    public void undoAddressBook_pointerLessThanZero_throwsCommandException() throws CommandException {
        AddressBook addressBook = new AddressBook();
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(addressBook);

        assertThrows(CommandException.class, MESSAGE_CONSTRAINTS, versionedAddressBook :: undoAddressBook);
    }
}
