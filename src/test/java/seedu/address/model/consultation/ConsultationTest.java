package seedu.address.model.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class ConsultationTest {

    private final Date validDate = new Date("2024-10-19");
    private final Time validTime = new Time("14:00");
    private final Student student1 = new StudentBuilder().withName("John Doe").build();
    private final Student student2 = new StudentBuilder().withName("Jane Doe").build();

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Consultation(null, validTime, new ArrayList<>()));
    }

    @Test
    public void constructor_nullTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Consultation(validDate, null, new ArrayList<>()));
    }

    @Test
    public void getDate_validConsultation_returnsCorrectDate() {
        Consultation consultation = new Consultation(validDate, validTime, new ArrayList<>());
        assertEquals(validDate, consultation.getDate());
    }

    @Test
    public void getTime_validConsultation_returnsCorrectTime() {
        Consultation consultation = new Consultation(validDate, validTime, new ArrayList<>());
        assertEquals(validTime, consultation.getTime());
    }

    @Test
    public void getStudents_returnsImmutableList() {
        List<Student> students = new ArrayList<>();
        students.add(student1);
        Consultation consultation = new Consultation(validDate, validTime, students);

        assertThrows(UnsupportedOperationException.class, () -> consultation.getStudents().add(student2));
    }

    @Test
    public void addStudent_studentAddedSuccessfully() {
        Consultation consultation = new Consultation(validDate, validTime, new ArrayList<>());
        consultation.addStudent(student1);

        assertTrue(consultation.getStudents().contains(student1));
    }

    @Test
    public void removeStudent_studentRemovedSuccessfully() {
        List<Student> students = new ArrayList<>();
        students.add(student1);
        Consultation consultation = new Consultation(validDate, validTime, students);

        consultation.removeStudent(student1);
        assertFalse(consultation.getStudents().contains(student1));
    }

    @Test
    public void equals_sameConsultation_returnsTrue() {
        List<Student> students = new ArrayList<>();
        students.add(student1);

        Consultation consultation1 = new Consultation(validDate, validTime, students);
        Consultation consultation2 = new Consultation(validDate, validTime, students);

        assertTrue(consultation1.equals(consultation2));
    }

    @Test
    public void equals_differentDate_returnsFalse() {
        List<Student> students = new ArrayList<>();
        students.add(student1);

        Consultation consultation1 = new Consultation(new Date("2024-10-18"), validTime, students);
        Consultation consultation2 = new Consultation(new Date("2024-10-19"), validTime, students);

        assertFalse(consultation1.equals(consultation2));
    }

    @Test
    public void equals_differentTime_returnsFalse() {
        List<Student> students = new ArrayList<>();
        students.add(student1);

        Consultation consultation1 = new Consultation(validDate, new Time("13:00"), students);
        Consultation consultation2 = new Consultation(validDate, new Time("14:00"), students);

        assertFalse(consultation1.equals(consultation2));
    }

    @Test
    public void equals_differentStudents_returnsFalse() {
        List<Student> students1 = new ArrayList<>();
        students1.add(student1);

        List<Student> students2 = new ArrayList<>();
        students2.add(student2);

        Consultation consultation1 = new Consultation(validDate, validTime, students1);
        Consultation consultation2 = new Consultation(validDate, validTime, students2);

        assertFalse(consultation1.equals(consultation2));
    }

    @Test
    public void hashCode_sameConsultation_sameHashCode() {
        List<Student> students = new ArrayList<>();
        students.add(student1);

        Consultation consultation1 = new Consultation(validDate, validTime, students);
        Consultation consultation2 = new Consultation(validDate, validTime, students);

        assertEquals(consultation1.hashCode(), consultation2.hashCode());
    }

    @Test
    public void hashCode_differentConsultation_differentHashCode() {
        List<Student> students1 = new ArrayList<>();
        students1.add(student1);

        List<Student> students2 = new ArrayList<>();
        students2.add(student2);

        Consultation consultation1 = new Consultation(validDate, validTime, students1);
        Consultation consultation2 = new Consultation(new Date("2024-10-20"), validTime, students2);

        assertFalse(consultation1.hashCode() == consultation2.hashCode());
    }

    @Test
    public void toString_validConsultation_correctFormat() {
        List<Student> students = new ArrayList<>();
        students.add(student1);
        Consultation consultation = new Consultation(validDate, validTime, students);

        String expected = String.format("Consultation[date=%s, time=%s, students=%s]",
                                        validDate.toString(), validTime.toString(), students.toString());

        assertEquals(expected, consultation.toString());
    }
    @Test
    public void equals_sameObject_returnsTrue() {
        List<Student> students = new ArrayList<>();
        students.add(student1);
        Consultation consultation = new Consultation(validDate, validTime, students);

        assertTrue(consultation.equals(consultation));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        List<Student> students = new ArrayList<>();
        students.add(student1);
        Consultation consultation = new Consultation(validDate, validTime, students);

        assertFalse(consultation.equals("Not a Consultation object"));
    }
}
