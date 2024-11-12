package seedu.address.model.predicate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.participation.Participation;
import seedu.address.model.person.Attendance;
import seedu.address.testutil.ParticipationBuilder;

public class StudentAttendedTutorialPredicateTest {

    private final LocalDate startDate = LocalDate.of(2024, 1, 1);
    private final LocalDate endDate = LocalDate.of(2024, 12, 31);

    // Predicate to test
    private final StudentAttendedTutorialPredicate predicate = new StudentAttendedTutorialPredicate(startDate, endDate);

    @Test
    public void test_attendanceWithinDateRange_returnsTrue() {
        Participation participation = new ParticipationBuilder()
                .withAttendanceList(Arrays.asList(
                        new Attendance(LocalDate.of(2024, 6, 15))
                ))
                .build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_attendanceBeforeDateRange_returnsFalse() {
        Participation participation = new ParticipationBuilder()
                .withAttendanceList(Arrays.asList(
                        new Attendance(LocalDate.of(2023, 12, 31))
                ))
                .build();
        assertFalse(predicate.test(participation));
    }

    @Test
    public void test_attendanceAfterDateRange_returnsFalse() {
        Participation participation = new ParticipationBuilder()
                .withAttendanceList(Arrays.asList(
                        new Attendance(LocalDate.of(2025, 1, 1))
                ))
                .build();
        assertFalse(predicate.test(participation));
    }

    @Test
    public void test_attendanceOnStartDate_returnsTrue() {
        Participation participation = new ParticipationBuilder()
                .withAttendanceList(Arrays.asList(
                        new Attendance(startDate)
                ))
                .build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_attendanceOnEndDate_returnsTrue() {
        Participation participation = new ParticipationBuilder()
                .withAttendanceList(Arrays.asList(
                        new Attendance(endDate)
                ))
                .build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_attendanceListEmpty_returnsFalse() {
        Participation participation = new ParticipationBuilder()
                .withAttendanceList(Collections.emptyList())
                .build();
        assertFalse(predicate.test(participation));
    }

    @Test
    public void test_multipleAttendancesOneWithinDateRange_returnsTrue() {
        Participation participation = new ParticipationBuilder()
                .withAttendanceList(Arrays.asList(
                        new Attendance(LocalDate.of(2023, 12, 31)),
                        new Attendance(LocalDate.of(2024, 6, 15)),
                        new Attendance(LocalDate.of(2025, 1, 1))
                ))
                .build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_multipleAttendancesAllOutsideDateRange_returnsFalse() {
        Participation participation = new ParticipationBuilder()
                .withAttendanceList(Arrays.asList(
                        new Attendance(LocalDate.of(2023, 1, 1)),
                        new Attendance(LocalDate.of(2023, 6, 15))
                ))
                .build();
        assertFalse(predicate.test(participation));
    }

    @Test
    public void test_attendanceOnBoundaryAndWithinDateRange_returnsTrue() {
        Participation participation = new ParticipationBuilder()
                .withAttendanceList(Arrays.asList(
                        new Attendance(startDate),
                        new Attendance(LocalDate.of(2024, 6, 15)),
                        new Attendance(endDate)
                ))
                .build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_participationWithDuplicateAttendancesWithinRange_returnsTrue() {
        Participation participation = new ParticipationBuilder()
                .withAttendanceList(Arrays.asList(
                        new Attendance(LocalDate.of(2024, 6, 15)),
                        new Attendance(LocalDate.of(2024, 6, 15))
                ))
                .build();
        assertTrue(predicate.test(participation));
    }

    @Test
    public void test_startDateAfterEndDate_assertionError() {
        assertThrows(AssertionError.class, () -> {
            StudentAttendedTutorialPredicate invalidPredicate = new StudentAttendedTutorialPredicate(
                    endDate, startDate);
        });
    }

    @Test
    public void test_nullStartDateAndEndDate_throwsNullPointerException() {
        // Creating a participation with null start and end dates is not valid, but testing for safety
        assertThrows(NullPointerException.class, () -> {
            StudentAttendedTutorialPredicate invalidPredicate = new StudentAttendedTutorialPredicate(
                    null, null);
        });
    }

    @Test
    public void test_predicateEqualsMethod() {
        StudentAttendedTutorialPredicate predicate1 = new StudentAttendedTutorialPredicate(startDate, endDate);
        StudentAttendedTutorialPredicate predicate2 = new StudentAttendedTutorialPredicate(startDate, endDate);
        assertEquals(predicate1, predicate1);
        assertEquals(predicate1, predicate2);
    }


    @Test
    public void equals_differentStartDate_returnsFalse() {
        LocalDate startDate1 = LocalDate.of(2024, 1, 1);
        LocalDate startDate2 = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        StudentAttendedTutorialPredicate predicate1 = new StudentAttendedTutorialPredicate(startDate1, endDate);
        StudentAttendedTutorialPredicate predicate2 = new StudentAttendedTutorialPredicate(startDate2, endDate);

        // Test inequality with different start dates
        assertNotEquals(predicate1, predicate2);
    }

    @Test
    public void equals_differentEndDate_returnsFalse() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate1 = LocalDate.of(2024, 12, 31);
        LocalDate endDate2 = LocalDate.of(2025, 12, 31);

        StudentAttendedTutorialPredicate predicate1 = new StudentAttendedTutorialPredicate(startDate, endDate1);
        StudentAttendedTutorialPredicate predicate2 = new StudentAttendedTutorialPredicate(startDate, endDate2);

        // Test inequality with different end dates
        assertNotEquals(predicate1, predicate2);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        StudentAttendedTutorialPredicate predicate = new StudentAttendedTutorialPredicate(startDate, endDate);

        // Test inequality with null
        assertNotEquals(predicate, null);
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        StudentAttendedTutorialPredicate predicate = new StudentAttendedTutorialPredicate(startDate, endDate);

        // Test inequality with an object of a different type
        assertNotEquals(predicate, new Object());
    }

    @Test
    public void toString_correctFormat_returnsExpectedString() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        StudentAttendedTutorialPredicate predicate = new StudentAttendedTutorialPredicate(startDate, endDate);

        // Construct the expected string format based on the toString implementation
        String expected = StudentAttendedTutorialPredicate.class.getCanonicalName() + "{startDate=" + startDate
                + ", endDate=" + endDate + "}";
        // Verify the string format matches the expected output
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void toString_differentDates_returnsDifferentString() {
        LocalDate startDate = LocalDate.of(2024, 5, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
        StudentAttendedTutorialPredicate predicate = new StudentAttendedTutorialPredicate(startDate, endDate);

        // Construct the expected string format for different dates
        String expected = StudentAttendedTutorialPredicate.class.getCanonicalName() + "{startDate=" + startDate
                + ", endDate=" + endDate + "}";

        // Verify the string format matches the expected output for different dates
        assertEquals(expected, predicate.toString());
    }
}

