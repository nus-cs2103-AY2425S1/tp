package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.StudentNotFoundException;
import seedu.address.testutil.StudentBuilder;

public class LessonTest {

    private Date date;
    private Time time;
    private Student student1;
    private Student student2;

    @BeforeEach
    public void setUp() {
        date = new Date("2024-10-21");
        time = new Time("10:00");
        student1 = new StudentBuilder().withName("Alice Pauline").build();
        student2 = new StudentBuilder().withName("Benson Meier").build();
    }

    @Test
    public void constructor_nullDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, time,
                Collections.emptyList(), Collections.emptyMap()));
    }

    @Test
    public void constructor_nullTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(date, null,
                Collections.emptyList(), Collections.emptyMap()));
    }

    @Test
    public void constructor_studentNotInAttendanceMap_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Lesson(date, time,
                List.of(student1), Collections.emptyMap()));
    }

    @Test
    public void getDate_success() {
        Lesson lesson = new Lesson(date, time, Collections.emptyList(), Collections.emptyMap());
        assertEquals(date, lesson.getDate());
    }

    @Test
    public void getTime_success() {
        Lesson lesson = new Lesson(date, time, Collections.emptyList(), Collections.emptyMap());
        assertEquals(time, lesson.getTime());
    }

    @Test
    public void getStudents_modifyList_throwsUnsupportedOperationException() {
        Lesson lesson = new Lesson(date, time, Arrays.asList(student1, student2),
                Map.of(student1, false, student2, false));
        List<Student> students = lesson.getStudents();
        assertThrows(UnsupportedOperationException.class, () -> students.add(new StudentBuilder().build()));
    }

    @Test
    public void getStudents_modifyAttendanceMap_throwsUnsupportedOperationException() {
        Lesson lesson = new Lesson(date, time, Arrays.asList(student1, student2),
                Map.of(student1, false, student2, false));
        Map<Student, Boolean> attendanceMap = lesson.getAttendanceMap();
        assertThrows(UnsupportedOperationException.class, () -> attendanceMap.put(student1, true));
    }

    @Test
    public void addStudent_success() {
        Lesson lesson = new Lesson(date, time, Collections.emptyList(), Collections.emptyMap());
        lesson.addStudent(student1);
        assertTrue(lesson.getStudents().contains(student1));
    }

    @Test
    public void addStudent_alsoAddToAttendanceMap() {
        Lesson lesson = new Lesson(date, time, Collections.emptyList(), Collections.emptyMap());
        lesson.addStudent(student1);
        assertTrue(lesson.getAttendanceMap().containsKey(student1));
    }

    @Test
    public void removeStudent_success() {
        Lesson lesson = new Lesson(date, time, Arrays.asList(student1, student2),
                Map.of(student1, false, student2, false));
        lesson.removeStudent(student1);
        assertFalse(lesson.getStudents().contains(student1));
    }

    @Test
    public void removeStudent_alsoRemoveFromAttendanceMap() {
        Lesson lesson = new Lesson(date, time, Arrays.asList(student1, student2),
                Map.of(student1, false, student2, false));
        lesson.removeStudent(student1);
        assertFalse(lesson.getAttendanceMap().containsKey(student1));
    }

    @Test
    public void getAttendance_studentExists_success() {
        boolean attendance = true;
        Lesson lesson = new Lesson(date, time, List.of(student1), Map.of(student1, attendance));
        assertEquals(lesson.getAttendance(student1), attendance);
    }

    @Test
    public void getAttendance_studentDoesNotExist_throwsStudentNotFoundException() {
        boolean attendance = true;
        Lesson lesson = new Lesson(date, time, List.of(student1), Map.of(student1, attendance));
        assertFalse(lesson.getStudents().contains(student2));
        assertThrows(StudentNotFoundException.class, () -> lesson.getAttendance(student2));
    }

    @Test
    public void setAttendance_studentExists_success() {
        Lesson lesson = new Lesson(date, time, List.of(student1), Map.of(student1, false));
        boolean newAttendance = true;
        lesson.setAttendance(student1, newAttendance);
        assertEquals(lesson.getAttendance(student1), newAttendance);
    }

    @Test
    public void setAttendance_studentDoesNotExist_throwsStudentNotFoundException() {
        Lesson lesson = new Lesson(date, time, List.of(student1), Map.of(student1, false));
        boolean newAttendance = true;
        assertFalse(lesson.getStudents().contains(student2));
        assertThrows(StudentNotFoundException.class, () -> lesson.setAttendance(student2, newAttendance));
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        Lesson lesson1 = new Lesson(date, time, Arrays.asList(student1, student2),
                Map.of(student1, false, student2, true));
        Lesson lesson2 = new Lesson(date, time, Arrays.asList(student1, student2),
                Map.of(student1, false, student2, true));
        assertEquals(lesson1, lesson2);
    }

    @Test
    public void equals_differentAttributes_returnsFalse() {
        Lesson lesson1 = new Lesson(date, time, Arrays.asList(student1, student2),
                Map.of(student1, false, student2, false));
        Lesson lesson2 = new Lesson(date, new Time("11:00"), Arrays.asList(student1), Map.of(student1, false));
        assertNotEquals(lesson1, lesson2);
    }

    @Test
    public void hashCode_sameAttributes_returnsSameHashCode() {
        Lesson lesson1 = new Lesson(date, time, Arrays.asList(student1, student2),
                Map.of(student1, false, student2, false));
        Lesson lesson2 = new Lesson(date, time, Arrays.asList(student1, student2),
                Map.of(student1, false, student2, false));
        assertEquals(lesson1.hashCode(), lesson2.hashCode());
    }

    @Test
    public void hashCode_differentAttributes_returnsDifferentHashCode() {
        Lesson lesson1 = new Lesson(date, time, Arrays.asList(student1, student2),
                Map.of(student1, false, student2, false));
        Lesson lesson2 = new Lesson(date, new Time("11:00"), Arrays.asList(student1),
                Map.of(student1, false));
        assertNotEquals(lesson1.hashCode(), lesson2.hashCode());
    }
}
