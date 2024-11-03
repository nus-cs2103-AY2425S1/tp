package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {

    @Test
    public void getComparator_sameStatus_returnsZero() {
        Comparator<AttendanceStatus> ascendingComparator = AttendanceStatus.getComparator(true);
        Comparator<AttendanceStatus> descendingComparator = AttendanceStatus.getComparator(false);

        // Same status comparisons should return 0 in both ascending and descending order
        assertEquals(0, ascendingComparator.compare(AttendanceStatus.PRESENT, AttendanceStatus.PRESENT));
        assertEquals(0, ascendingComparator.compare(AttendanceStatus.ABSENT, AttendanceStatus.ABSENT));
        assertEquals(0, ascendingComparator.compare(
                AttendanceStatus.NOT_TAKEN_PLACE, AttendanceStatus.NOT_TAKEN_PLACE));

        assertEquals(0, descendingComparator.compare(AttendanceStatus.PRESENT, AttendanceStatus.PRESENT));
        assertEquals(0, descendingComparator.compare(AttendanceStatus.ABSENT, AttendanceStatus.ABSENT));
        assertEquals(0, descendingComparator.compare(
                AttendanceStatus.NOT_TAKEN_PLACE, AttendanceStatus.NOT_TAKEN_PLACE));
    }

    @Test
    public void getComparator_ascendingOrder() {
        Comparator<AttendanceStatus> comparator = AttendanceStatus.getComparator(true);

        // Ascending Order: PRESENT < ABSENT < NOT_TAKEN_PLACE
        assertEquals(-1, comparator.compare(AttendanceStatus.PRESENT, AttendanceStatus.ABSENT));
        assertEquals(-1, comparator.compare(AttendanceStatus.PRESENT, AttendanceStatus.NOT_TAKEN_PLACE));
        assertEquals(-1, comparator.compare(AttendanceStatus.ABSENT, AttendanceStatus.NOT_TAKEN_PLACE));

        assertEquals(1, comparator.compare(AttendanceStatus.ABSENT, AttendanceStatus.PRESENT));
        assertEquals(1, comparator.compare(AttendanceStatus.NOT_TAKEN_PLACE, AttendanceStatus.PRESENT));
        assertEquals(1, comparator.compare(AttendanceStatus.NOT_TAKEN_PLACE, AttendanceStatus.ABSENT));
    }

    @Test
    public void getComparator_descendingOrder() {
        Comparator<AttendanceStatus> comparator = AttendanceStatus.getComparator(false);

        // Descending Order: ABSENT > PRESENT > NOT_TAKEN_PLACE
        assertEquals(-1, comparator.compare(AttendanceStatus.ABSENT, AttendanceStatus.PRESENT));
        assertEquals(-1, comparator.compare(AttendanceStatus.ABSENT, AttendanceStatus.NOT_TAKEN_PLACE));
        assertEquals(-1, comparator.compare(AttendanceStatus.PRESENT, AttendanceStatus.NOT_TAKEN_PLACE));

        assertEquals(1, comparator.compare(AttendanceStatus.PRESENT, AttendanceStatus.ABSENT));
        assertEquals(1, comparator.compare(AttendanceStatus.NOT_TAKEN_PLACE, AttendanceStatus.ABSENT));
        assertEquals(1, comparator.compare(AttendanceStatus.NOT_TAKEN_PLACE, AttendanceStatus.PRESENT));
    }
}
