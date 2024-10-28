package seedu.address.model.participation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

class ParticipationTest {
    private Person student = ALICE;
    private Tutorial tutorial = new Tutorial("Mathematics");
    private List<Attendance> attendanceList = new ArrayList<Attendance>();

    @BeforeEach
    void setUp() {
        attendanceList.add(new Attendance(LocalDate.parse("12/12/2024", Attendance.VALID_DATE_FORMAT)));
    }

    @Test
    void constructor_validInput_success() {
        Participation participation = new Participation(student, tutorial, attendanceList);
        assertNotNull(participation);
        assertEquals(student, participation.getStudent());
        assertEquals(tutorial, participation.getTutorial());
        assertEquals(attendanceList, participation.getAttendanceList());
    }

    @Test
    void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new Participation(null, tutorial, attendanceList);
        });
    }

    @Test
    void constructor_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new Participation(student, null, attendanceList);
        });
    }

    @Test
    void constructor_nullAttendanceList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            new Participation(student, tutorial, null);
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
    void toString_success() {
        Participation participation = new Participation(student, tutorial, attendanceList);
        String expectedString = String.format("Attends: %s", tutorial.toString());
        assertEquals(expectedString, participation.toString());
    }
}