package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.exceptions.StudentNotInTutDateException;
import seedu.address.model.tut.TutDate;

public class PresentDatesTest {

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    public void constructor_nullSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PresentDates(null));
    }

    @Test
    public void constructor_emptySet_success() {
        Set<TutDate> emptySet = new HashSet<>();
        PresentDates presentDates = new PresentDates(emptySet);
        assertTrue(presentDates.getList().isEmpty());
    }

    @Test
    public void constructor_nonEmptySet_success() {
        Date date1 = toDate(LocalDate.of(2023, 10, 14));
        Date date2 = toDate(LocalDate.of(2023, 10, 21));
        TutDate tutDate1 = new TutDate(date1);
        TutDate tutDate2 = new TutDate(date2);
        Set<TutDate> dateSet = new HashSet<>();
        dateSet.add(tutDate1);
        dateSet.add(tutDate2);

        PresentDates presentDates = new PresentDates(dateSet);
        assertEquals(2, presentDates.getList().size());
        assertTrue(presentDates.getList().contains(tutDate1));
        assertTrue(presentDates.getList().contains(tutDate2));
    }

    @Test
    public void add_validDate_success() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        Date date = toDate(LocalDate.of(2023, 10, 14));
        TutDate tutDate = new TutDate(date);
        presentDates.add(tutDate);
        assertTrue(presentDates.getList().contains(tutDate));
    }

    @Test
    public void add_nullDate_throwsNullPointerException() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        assertThrows(NullPointerException.class, () -> presentDates.add(null));
    }

    @Test
    public void add_duplicateDate_setRemainsUnique() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        Date date = toDate(LocalDate.of(2023, 10, 14));
        TutDate tutDate = new TutDate(date);
        presentDates.add(tutDate);
        presentDates.add(tutDate); // Attempt to add duplicate

        assertEquals(1, presentDates.getList().size());
        assertTrue(presentDates.getList().contains(tutDate));
    }

    @Test
    public void getList_returnsUnmodifiableSet() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        Set<TutDate> dates = presentDates.getList();
        assertThrows(UnsupportedOperationException.class, () -> dates.add(new TutDate(new Date())));
    }

    @Test
    public void equals_sameObject_true() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        assertEquals(presentDates, presentDates);
    }

    @Test
    public void equals_nullObject_false() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        assertNotEquals(null, presentDates);
    }

    @Test
    public void equals_differentClass_false() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        String notPresentDates = "Not a PresentDates object";
        assertNotEquals(presentDates, notPresentDates);
    }

    @Test
    public void equals_sameDates_true() {
        Date date1 = toDate(LocalDate.of(2023, 10, 14));
        Date date2 = toDate(LocalDate.of(2023, 10, 21));
        TutDate tutDate1 = new TutDate(date1);
        TutDate tutDate2 = new TutDate(date2);

        Set<TutDate> dateSet1 = new HashSet<>();
        dateSet1.add(tutDate1);
        dateSet1.add(tutDate2);

        Set<TutDate> dateSet2 = new HashSet<>();
        dateSet2.add(tutDate1);
        dateSet2.add(tutDate2);

        PresentDates presentDates1 = new PresentDates(dateSet1);
        PresentDates presentDates2 = new PresentDates(dateSet2);

        assertEquals(presentDates1, presentDates2);
    }

    @Test
    public void equals_differentDates_false() {
        Date date1 = toDate(LocalDate.of(2023, 10, 14));
        Date date2 = toDate(LocalDate.of(2023, 10, 21));
        Date date3 = toDate(LocalDate.of(2023, 10, 28));
        TutDate tutDate1 = new TutDate(date1);
        TutDate tutDate2 = new TutDate(date2);
        TutDate tutDate3 = new TutDate(date3);

        Set<TutDate> dateSet1 = new HashSet<>();
        dateSet1.add(tutDate1);
        dateSet1.add(tutDate2);

        Set<TutDate> dateSet2 = new HashSet<>();
        dateSet2.add(tutDate1);
        dateSet2.add(tutDate3);

        PresentDates presentDates1 = new PresentDates(dateSet1);
        PresentDates presentDates2 = new PresentDates(dateSet2);

        assertNotEquals(presentDates1, presentDates2);
    }

    @Test
    public void toString_validDates_correctFormat() {
        Date date1 = toDate(LocalDate.of(2023, 10, 14));
        Date date2 = toDate(LocalDate.of(2023, 10, 21));
        TutDate tutDate1 = new TutDate(date1);
        TutDate tutDate2 = new TutDate(date2);

        Set<TutDate> dateSet = new HashSet<>();
        dateSet.add(tutDate1);
        dateSet.add(tutDate2);

        PresentDates presentDates = new PresentDates(dateSet);

        String expectedString = "[ " + tutDate1 + " " + tutDate2 + " ]";
        String alternativeExpectedString = "[ " + tutDate2 + " " + tutDate1 + " ]";
        String actualString = presentDates.toString();

        assertTrue(actualString.equals(expectedString) || actualString.equals(alternativeExpectedString));
    }

    @Test
    public void setAttendance_addsDate_success() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        Date date = toDate(LocalDate.of(2023, 10, 14));
        TutDate tutDate = new TutDate(date);
        presentDates.setAttendance(tutDate);
        assertTrue(presentDates.getList().contains(tutDate));
    }

    @Test
    public void setAttendance_nullDate_throwsNullPointerException() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        assertThrows(NullPointerException.class, () -> presentDates.setAttendance(null));
    }

    @Test
    public void setAbsent_datePresent_removesDate() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        TutDate tutDate = new TutDate(new Date());
        presentDates.add(tutDate);
        assertTrue(presentDates.getList().contains(tutDate));

        presentDates.setAbsent(tutDate);

        assertFalse(presentDates.getList().contains(tutDate));
    }

    @Test
    public void setAbsent_dateNotPresent_throwsException() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        TutDate tutDate = new TutDate(new Date());
        assertThrows(StudentNotInTutDateException.class, () -> {
            presentDates.setAbsent(tutDate);
        });
    }

    @Test
    public void setAbsent_nullDate_throwsNullPointerException() {
        PresentDates presentDates = new PresentDates(new HashSet<>());
        assertThrows(NullPointerException.class, () -> {
            presentDates.setAbsent(null);
        });
    }
}
