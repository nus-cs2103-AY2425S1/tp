package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_NAME_ONE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_FOUR;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;
import static seedu.address.testutil.TypicalWeddings.getTypicalWeddingBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.testutil.WeddingBuilder;

public class WeddingBookTest {

    private final WeddingBook weddingBook = new WeddingBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), weddingBook.getWeddingList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weddingBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyWeddingBook_replacesData() {
        WeddingBook newData = getTypicalWeddingBook();
        weddingBook.resetData(newData);
        assertEquals(newData, weddingBook);
    }

    @Test
    public void resetData_withDuplicateWeddings_throwsDuplicateWeddingException() {
        // Two weddings with the same identity
        Wedding editedWedding = new WeddingBuilder(WEDDING_ONE).withWeddingName(VALID_WEDDING_NAME_ONE)
                .withVenue(VALID_VENUE_ONE).withDate(VALID_DATE_ONE).build();
        List<Wedding> newWeddings = Arrays.asList(WEDDING_ONE, editedWedding);
        WeddingBookStub newData = new WeddingBookStub(newWeddings);

        assertThrows(DuplicateWeddingException.class, () -> weddingBook.resetData(newData));
    }

    @Test
    public void hasWedding_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weddingBook.hasWedding(null));
    }

    @Test
    public void hasWedding_weddingNotInWeddingBook_returnsFalse() {
        assertFalse(weddingBook.hasWedding(WEDDING_ONE));
    }

    @Test
    public void hasWedding_weddingInWeddingBook_returnsTrue() {
        weddingBook.addWedding(WEDDING_ONE);
        assertTrue(weddingBook.hasWedding(WEDDING_ONE));
    }

    @Test
    public void hasWedding_weddingWithSameIdentityFieldsInWeddingBook_returnsTrue() {
        weddingBook.addWedding(WEDDING_ONE);
        Wedding editedWedding = new WeddingBuilder(WEDDING_ONE).build();
        assertTrue(weddingBook.hasWedding(editedWedding));
    }

    @Test
    public void getWeddingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> weddingBook.getWeddingList().remove(0));
    }

    @Test
    public void equals() {
        // same values -> returns true
        WeddingBook originalBook = getTypicalWeddingBook();

        WeddingBook tempBook = getTypicalWeddingBook();
        tempBook.addWedding(WEDDING_FOUR);
        WeddingBook weddingBookCopy = tempBook;

        assertTrue(originalBook.equals(getTypicalWeddingBook()));

        // same object -> returns true
        assertTrue(originalBook.equals(originalBook));

        // null -> returns false
        assertFalse(originalBook.equals(null));

        // different type -> returns false
        assertFalse(originalBook.equals(5));

        // different wedding book -> returns false
        assertFalse(originalBook.equals(weddingBookCopy));
    }


    @Test
    public void toStringMethod() {
        String expected = WeddingBook.class.getCanonicalName() + "{weddings=" + weddingBook.getWeddingList() + "}";
        assertEquals(expected, weddingBook.toString());
    }

    /**
     * A stub ReadOnlyWeddingBook whose weddings list can violate interface constraints.
     */
    private static class WeddingBookStub implements ReadOnlyWeddingBook {
        private final ObservableList<Wedding> weddings = FXCollections.observableArrayList();

        WeddingBookStub(Collection<Wedding> weddings) {
            this.weddings.setAll(weddings);
        }

        @Override
        public ObservableList<Wedding> getWeddingList() {
            return weddings;
        }
    }

}
