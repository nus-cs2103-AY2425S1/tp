package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.model.VersionedAddressBook.MESSAGE_NO_MORE_HISTORY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;

public class VersionedAddressBookTest {
    private AddressBook addressBook1;
    private AddressBook addressBook2;
    private VersionedAddressBook versionedAddressBook;

    @BeforeEach
    void setUp() {
        addressBook1 = new AddressBook();
        versionedAddressBook = new VersionedAddressBook(addressBook1);
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
        versionedAddressBook.resetData(addressBook2);
        versionedAddressBook.commitAddressBook();

        ArrayList<AddressBook> expectedStateList = new ArrayList<>();
        expectedStateList.add(addressBook1);
        expectedStateList.add(addressBook2);

        assertEquals(expectedStateList, versionedAddressBook.getAddressBookStateList());
        assertEquals(2, versionedAddressBook.getAddressBookStateList().size());
    }

    @Test
    void testUndoAddressBook() throws CommandException {
        assertEquals(addressBook1, versionedAddressBook.getCurrentAddressBook());
        assertEquals(addressBook1, versionedAddressBook.getAddressBookStateList().get(0));

        versionedAddressBook.resetData(addressBook2);
        versionedAddressBook.commitAddressBook();

        ArrayList<AddressBook> expectedStateList = new ArrayList<>();
        expectedStateList.add(new AddressBook(addressBook1));
        expectedStateList.add(new AddressBook(addressBook2));
        assertEquals(expectedStateList, versionedAddressBook.getAddressBookStateList());

        assertEquals(addressBook2, versionedAddressBook.getCurrentAddressBook());
        assertEquals(addressBook2, versionedAddressBook.getAddressBookStateList().get(1));

        versionedAddressBook.undoAddressBook();
        // Check if the current state is reverted to addressBook1
        assertEquals(addressBook1, versionedAddressBook.getCurrentAddressBook());
    }

    @Test
    void testRedoAddressBook() throws CommandException {
        assertEquals(addressBook1, versionedAddressBook.getCurrentAddressBook());
        assertEquals(addressBook1, versionedAddressBook.getAddressBookStateList().get(0));

        versionedAddressBook.resetData(addressBook2);
        versionedAddressBook.commitAddressBook();

        ArrayList<AddressBook> expectedStateList = new ArrayList<>();
        expectedStateList.add(new AddressBook(addressBook1));
        expectedStateList.add(new AddressBook(addressBook2));
        assertEquals(expectedStateList, versionedAddressBook.getAddressBookStateList());

        assertEquals(addressBook2, versionedAddressBook.getCurrentAddressBook());
        assertEquals(addressBook2, versionedAddressBook.getAddressBookStateList().get(1));

        versionedAddressBook.undoAddressBook();
        // Check if the current state is reverted to addressBook1
        assertEquals(addressBook1, versionedAddressBook.getCurrentAddressBook());

        versionedAddressBook.redoAddressBook();
        // Check if the current state is reverted to addressBook2
        assertEquals(addressBook2, versionedAddressBook.getCurrentAddressBook());
    }

    @Test
    public void testDiscardUnsavedChanges() {
        // discard one change
        versionedAddressBook.addPerson(GEORGE);
        versionedAddressBook.discardUnsavedChanges();
        assertEquals(addressBook1, versionedAddressBook);

        // discard all changes
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.resetData(addressBook2);
        versionedAddressBook.discardUnsavedChanges();
        assertEquals(addressBook1, versionedAddressBook);

        // discard no changes
        versionedAddressBook.resetData(addressBook2);
        versionedAddressBook.commitAddressBook();
        versionedAddressBook.discardUnsavedChanges();
        assertEquals(addressBook2, versionedAddressBook);
    }


    @Test
    void testEquals() throws CommandException {
        VersionedAddressBook sameVersionedAddressBook = new VersionedAddressBook(addressBook1);

        // different instances with same state pointer, state list and current instance
        assertEquals(versionedAddressBook, sameVersionedAddressBook);

        //different instances with different state pointer, current and different state list
        versionedAddressBook.resetData(addressBook2);
        versionedAddressBook.resetData(addressBook2);
        versionedAddressBook.commitAddressBook();
        assertNotEquals(versionedAddressBook, sameVersionedAddressBook);

        //different state pointer and different current instance but same state list
        sameVersionedAddressBook.resetData(addressBook2);
        sameVersionedAddressBook.commitAddressBook();
        sameVersionedAddressBook.undoAddressBook();
        assertNotEquals(versionedAddressBook, sameVersionedAddressBook);

        //same state pointer, different current instance, same state list
        versionedAddressBook.undoAddressBook();
        versionedAddressBook.resetData(addressBook2);
        assertNotEquals(versionedAddressBook, sameVersionedAddressBook);

        assertNotEquals(sameVersionedAddressBook, null);
        assertEquals(sameVersionedAddressBook, sameVersionedAddressBook);
    }

    @Test
    public void undoAddressBook_withUnsavedChanges_throwsCommandException() {
        versionedAddressBook.resetData(addressBook2);
        versionedAddressBook.commitAddressBook();
        versionedAddressBook.removePerson(GEORGE);
        assertThrows(CommandException.class, VersionedAddressBook.MESSAGE_UNSAVED_CHANGES_UNDO,
                versionedAddressBook::undoAddressBook);
    }

    @Test
    public void redoAddressBook_withUnsavedChanges_throwsCommandException() throws CommandException {
        versionedAddressBook.resetData(addressBook2);
        versionedAddressBook.removePerson(GEORGE);
        versionedAddressBook.commitAddressBook();
        versionedAddressBook.undoAddressBook();
        versionedAddressBook.addPerson(GEORGE);
        assertThrows(CommandException.class, VersionedAddressBook.MESSAGE_UNSAVED_CHANGES_REDO,
                versionedAddressBook::redoAddressBook);
    }

    @Test
    public void undoAddressBook_pointerLessThanZero_throwsCommandException() throws CommandException {
        AddressBook addressBook = new AddressBook();
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedAddressBook.addPerson(GEORGE);
        assertThrows(CommandException.class, MESSAGE_NO_MORE_HISTORY, versionedAddressBook::undoAddressBook);
    }

    @Test
    public void redoAddressBook_pointerGreaterThanSize_throwsCommandException() throws CommandException {
        AddressBook addressBook = new AddressBook();
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedAddressBook.addPerson(GEORGE);
        versionedAddressBook.commitAddressBook();
        assertThrows(CommandException.class, VersionedAddressBook.MESSAGE_NO_MORE_UNDONE_STATES,
                versionedAddressBook::redoAddressBook);
    }
}
