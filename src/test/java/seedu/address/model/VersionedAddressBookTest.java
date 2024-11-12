package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VersionedAddressBookTest {
    private VersionedAddressBook versionedAddressBook;
    private AddressBook initialState;

    @BeforeEach
    public void setUp() {
        initialState = new AddressBook();
        versionedAddressBook = new VersionedAddressBook(initialState);
    }

    @Test
    public void save_savesNewState() {
        AddressBook newState = new AddressBook();
        versionedAddressBook.save();
        assertEquals(2, versionedAddressBook.getTotalStates());
        assertEquals(newState, versionedAddressBook.getCurrentState());
    }

    @Test
    public void undo_canUndoPreviousState() {
        versionedAddressBook.save();
        versionedAddressBook.save();

        versionedAddressBook.undo();

        assertEquals(initialState, versionedAddressBook.getCurrentState());
        assertTrue(versionedAddressBook.canRedo());
    }

    @Test
    public void undo_noStatesToUndo_throwsException() {
        assertThrows(VersionedAddressBook.InvalidUndoException.class, () -> versionedAddressBook.undo());
    }

    @Test
    public void redo_canRedoUndoneState() {
        versionedAddressBook.save();
        versionedAddressBook.save();
        ReadOnlyAddressBook expectedSecondState = versionedAddressBook.getCurrentState();

        versionedAddressBook.undo();
        assertEquals(initialState, versionedAddressBook.getCurrentState());

        versionedAddressBook.redo();
        assertEquals(expectedSecondState, versionedAddressBook.getCurrentState());
    }

    @Test
    public void redo_noStatesToRedo_throwsException() {
        versionedAddressBook.save();
        versionedAddressBook.save();
        versionedAddressBook.undo();

        versionedAddressBook.redo();

        assertThrows(VersionedAddressBook.InvalidRedoException.class, () -> versionedAddressBook.redo());
    }

    @Test
    public void clearRedoStack_clearsRedoHistory() {
        versionedAddressBook.save();
        versionedAddressBook.save();
        versionedAddressBook.undo();

        versionedAddressBook.redo();
        versionedAddressBook.save();

        assertFalse(versionedAddressBook.canRedo());
    }

    @Test
    public void getCurrentState_returnsCurrentState() {
        versionedAddressBook.save();
        versionedAddressBook.save();

        ReadOnlyAddressBook currentState = versionedAddressBook.getCurrentState();

        assertEquals(currentState, versionedAddressBook.getCurrentState());
    }

    @Test
    public void getTotalStates_returnsCorrectCount() {
        assertEquals(1, versionedAddressBook.getTotalStates());

        versionedAddressBook.save();
        assertEquals(2, versionedAddressBook.getTotalStates());
    }
}
