package seedu.address.model.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.consultation.exceptions.ConsultationNotFoundException;
import seedu.address.model.consultation.exceptions.DuplicateConsultationException;
import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;

public class UniqueConsultListTest {

    private UniqueConsultList uniqueConsultList;
    private Consultation consult1;
    private Consultation consult2;

    @BeforeEach
    public void setUp() {
        uniqueConsultList = new UniqueConsultList();
        consult1 = new Consultation(new Date("2024-10-20"), new Time("14:00"), new ArrayList<>());
        consult2 = new Consultation(new Date("2024-10-21"), new Time("10:00"), new ArrayList<>());
    }

    @Test
    public void add_consultationNotInList_success() {
        uniqueConsultList.add(consult1);
        assertTrue(uniqueConsultList.contains(consult1));
    }

    @Test
    public void add_duplicateConsultation_throwsDuplicateConsultationException() {
        uniqueConsultList.add(consult1);
        assertThrows(DuplicateConsultationException.class, () -> uniqueConsultList.add(consult1));
    }

    @Test
    public void setConsult_existingConsult_success() {
        uniqueConsultList.add(consult1);
        Consultation editedConsult = new Consultation(consult1);
        uniqueConsultList.setConsult(consult1, editedConsult);
        assertTrue(uniqueConsultList.contains(editedConsult));
    }

    @Test
    public void setConsult_nonExistingConsult_throwsConsultationNotFoundException() {
        assertThrows(ConsultationNotFoundException.class, () -> uniqueConsultList.setConsult(consult1, consult2));
    }

    @Test
    public void remove_existingConsult_success() {
        uniqueConsultList.add(consult1);
        uniqueConsultList.remove(consult1);
        assertFalse(uniqueConsultList.contains(consult1));
    }

    @Test
    public void remove_nonExistingConsult_throwsConsultationNotFoundException() {
        assertThrows(ConsultationNotFoundException.class, () -> uniqueConsultList.remove(consult1));
    }

    @Test
    public void sort_consultsSortedByDateAndTime_success() {
        uniqueConsultList.add(consult2);
        uniqueConsultList.add(consult1);
        uniqueConsultList.sort();

        List<Consultation> sortedList = uniqueConsultList.asUnmodifiableObservableList();
        assertEquals(consult1, sortedList.get(0));
        assertEquals(consult2, sortedList.get(1));
    }

    @Test
    public void filtered_filteredListContainsCorrectElements() {
        uniqueConsultList.add(consult1);
        uniqueConsultList.add(consult2);

        List<Consultation> filteredList = uniqueConsultList.filtered(c -> c.getDate().equals(new Date("2024-10-20")));
        assertEquals(1, filteredList.size());
        assertTrue(filteredList.contains(consult1));
    }

    @Test
    public void setConsults_uniqueConsultList_success() {
        uniqueConsultList.add(consult1);
        UniqueConsultList newConsultList = new UniqueConsultList();
        newConsultList.add(consult2);

        uniqueConsultList.setConsults(newConsultList);
        assertTrue(uniqueConsultList.contains(consult2));
        assertFalse(uniqueConsultList.contains(consult1));
    }

    @Test
    public void setConsults_withDuplicateConsultations_throwsDuplicateConsultationException() {
        List<Consultation> duplicateConsultations = new ArrayList<>();
        duplicateConsultations.add(consult1);
        duplicateConsultations.add(consult1);

        assertThrows(DuplicateConsultationException.class, () -> uniqueConsultList.setConsults(duplicateConsultations));
    }

    @Test
    public void equals_sameConsultList_returnsTrue() {
        UniqueConsultList anotherList = new UniqueConsultList();
        assertTrue(uniqueConsultList.equals(anotherList));
    }

    @Test
    public void equals_differentConsultList_returnsFalse() {
        uniqueConsultList.add(consult1);
        UniqueConsultList anotherList = new UniqueConsultList();
        anotherList.add(consult2);
        assertFalse(uniqueConsultList.equals(anotherList));
    }

    @Test
    public void hashCode_sameConsultList_returnsSameHashCode() {
        UniqueConsultList anotherList = new UniqueConsultList();
        assertEquals(uniqueConsultList.hashCode(), anotherList.hashCode());
    }
}
