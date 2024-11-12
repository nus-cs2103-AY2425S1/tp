package seedu.address.model.wedding;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_THREE;
import static seedu.address.testutil.TypicalWeddings.WEDDING_TWO;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.wedding.exceptions.DuplicateWeddingException;
import seedu.address.model.wedding.exceptions.PersonNotAssignedToWeddingException;
import seedu.address.model.wedding.exceptions.WeddingNotFoundException;


public class UniqueWeddingListTest {
    private final UniqueWeddingList weddings = new UniqueWeddingList();
    private Wedding w1;
    private Wedding w2;

    private Person alice = ALICE;

    @BeforeEach
    public void setUp() {
        w1 = new Wedding(WEDDING_ONE.getWeddingName(), WEDDING_ONE.getWeddingDate());
        w2 = new Wedding(WEDDING_TWO.getWeddingName(), WEDDING_TWO.getWeddingDate());
        weddings.addWedding(w1);
    }

    @Test
    public void contains_weddingExists_true() {
        assertTrue(weddings.contains(w1));
    }

    @Test
    public void contains_weddingDoesNotExist_false() {
        Wedding w2 = new Wedding(WEDDING_TWO.getWeddingName(), WEDDING_TWO.getWeddingDate());
        assertFalse(weddings.contains(w2));
    }

    @Test
    public void contains_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weddings.contains(null));
    }

    @Test
    public void addWedding_nullWedding_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weddings.addWedding(null));
    }

    @Test
    public void addWedding_duplicateWedding_throwsDuplicateWeddingException() {
        assertThrows(DuplicateWeddingException.class, () -> weddings.addWedding(w1));
    }

    @Test
    public void removeWedding_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weddings.removeWedding(null));
    }

    @Test
    public void removeWedding_weddingDoesNotExist_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> weddings.removeWedding(w2));
    }

    @Test
    public void setWedding_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weddings.setWedding(w1, null));
        assertThrows(NullPointerException.class, () -> weddings.setWedding(null, w2));
        assertThrows(NullPointerException.class, () -> weddings.setWedding(null, null));
    }

    @Test
    public void setWedding_weddingDoesNotExist_throwsWeddingNotFoundException() {
        Wedding w3 = new Wedding(WEDDING_THREE.getWeddingName(), WEDDING_THREE.getWeddingDate());
        assertThrows(WeddingNotFoundException.class, () -> weddings.setWedding(w2, w3));
    }

    @Test
    public void setWeddings_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weddings.setWeddings(null));
    }

    @Test
    public void setWeddings_weddingListContainsDuplicates_throwsDuplicateWeddingException() {
        List<Wedding> weddingList = new ArrayList<>();
        weddingList.add(w1);
        weddingList.add(w1);
        assertThrows(DuplicateWeddingException.class, () -> weddings.setWeddings(weddingList));
    }

    @Test
    public void assignToWedding_null_throwsNullPointerException() {
        //null wedding
        assertThrows(NullPointerException.class, () -> weddings.assignToWedding(null, alice));

        //null person
        assertThrows(NullPointerException.class, () -> weddings.assignToWedding(w1, null));

        //both null
        assertThrows(NullPointerException.class, () -> weddings.assignToWedding(null, null));
    }

    @Test
    public void assignToWedding_weddingDoesNotExist_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> weddings.assignToWedding(w2, alice));
    }

    @Test
    public void unassignFromWedding_null_throwsNullPointerException() {
        //null wedding
        assertThrows(NullPointerException.class, () -> weddings.unassignFromWedding(null, alice));

        //null person
        assertThrows(NullPointerException.class, () -> weddings.unassignFromWedding(w1, null));
    }

    @Test
    public void unassignFromWedding_weddingDoesNotExist_throwsWeddingNotFoundException() {
        assertThrows(WeddingNotFoundException.class, () -> weddings.unassignFromWedding(w2, alice));
    }

    @Test
    public void unassignFromWedding_personNotAssigned_throwsPersonNotAssignedToWeddingException() {
        assertThrows(PersonNotAssignedToWeddingException.class, () -> weddings.unassignFromWedding(w1, alice));
    }
}
