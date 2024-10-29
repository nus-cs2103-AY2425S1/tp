package seedu.address.model.participation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

class ParticipationTest {
    private Person student1 = ALICE;
    private Person student2 = BOB;
    private Tutorial tutorial1 = new Tutorial("Mathematics");
    private Tutorial tutorial2 = new Tutorial("English");
    private List<Attendance> attendanceList1 = new ArrayList<Attendance>();
    private List<Attendance> attendanceList2 = new ArrayList<Attendance>();
    private List<Attendance> attendanceList3 = new ArrayList<>(attendanceList1);

    @BeforeEach
    void setUp() {
        attendanceList1.add(new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)));
        attendanceList2.add(new Attendance(LocalDate.parse("02/10/2024", Attendance.VALID_DATE_FORMAT)));
    }

    @Test
    void constructor_validInput_success() {
        Participation participation = new Participation(student1, tutorial1, attendanceList1);
        assertNotNull(participation);
        assertEquals(student1, participation.getStudent());
        assertEquals(tutorial1, participation.getTutorial());
        assertEquals(attendanceList1, participation.getAttendanceList());
    }

    @Test
    void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new Participation(null, tutorial1, attendanceList1);
        });
    }

    @Test
    void constructor_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new Participation(student1, null, attendanceList1);
        });
    }

    @Test
    void constructor_nullAttendanceList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new Participation(student1, tutorial1, null);
        });
    }

    @Test
    void isValidParticipationList_validInput_success() {
        String validInput = "Valid input";
        assertTrue(Participation.isValidParticipationList(validInput));
    }

    @Test
    void isValidParticipationList_invalidInput_returnsFalse() {
        String invalidInput1 = " "; // Starts with whitespace character
        String invalidInput2 = ""; // Empty string
        assertFalse(Participation.isValidParticipationList(invalidInput1));
        assertFalse(Participation.isValidParticipationList(invalidInput2));
    }

    @Test
    void isSameParticipation() {
        Participation participation1 = new Participation(student1, tutorial1, attendanceList1);
        Participation participation2 = new Participation(student2, tutorial2, attendanceList2);
        Participation participation3 = new Participation(student1, tutorial2, attendanceList1);
        Participation participation4 = new Participation(student2, tutorial2, attendanceList1);

        // same object -> returns true
        assertTrue(participation1.isSameParticipation(participation1));

        // null -> returns false
        assertFalse(participation1.isSameParticipation(null));

        // different students -> returns false
        assertFalse(participation2.isSameParticipation(participation3));

        //different tutorials -> returns false
        assertFalse(participation1.isSameParticipation(participation3));

        //different attendance lists -> returns false
        assertFalse(participation2.isSameParticipation(participation4));
    }

    @Test
    void toString_success() {
        Participation participation = new Participation(student1, tutorial1, attendanceList1);
        String expectedString = String.format("Attends: %s", tutorial1.toString());
        assertEquals(expectedString, participation.toString());
    }
}
