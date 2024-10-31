package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
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
        assertThrows(NullPointerException.class, () -> new Lesson(null, time));
    }

    @Test
    public void constructor_nullTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(date, null));
    }

    @Test
    public void getDate_success() {
        Lesson lesson = new Lesson(date, time);
        assertEquals(date, lesson.getDate());
    }

    @Test
    public void getTime_success() {
        Lesson lesson = new Lesson(date, time);
        assertEquals(time, lesson.getTime());
    }

    @Test
    public void getStudents_modifyList_throwsUnsupportedOperationException() {
        Lesson lesson = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 1),
                new StudentLessonInfo(student2, false, 0)));
        List<Student> students = lesson.getStudents();
        assertThrows(UnsupportedOperationException.class, () -> students.add(new StudentBuilder().build()));
    }

    @Test
    public void addStudent_success() {
        Lesson lesson = new Lesson(date, time);
        lesson.addStudent(student1);
        assertTrue(lesson.getStudents().contains(student1));
    }

    @Test
    public void removeStudent_success() {
        Lesson lesson = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 1),
                new StudentLessonInfo(student2, false, 0)));
        lesson.removeStudent(student1);
        assertFalse(lesson.getStudents().contains(student1));
    }

    @Test
    public void getAttendance_studentExists_success() {
        boolean attendance = true;
        Lesson lesson = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, attendance, 1)));
        assertEquals(lesson.getAttendance(student1), attendance);
    }

    @Test
    public void getAttendance_studentDoesNotExist_throwsStudentNotFoundException() {
        boolean attendance = true;
        Lesson lesson = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, attendance, 1)));
        assertFalse(lesson.getStudents().contains(student2));
        assertThrows(StudentNotFoundException.class, () -> lesson.getAttendance(student2));
    }

    @Test
    public void setAttendance_studentExists_success() {
        Lesson lesson = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, false, 1)));
        boolean newAttendance = true;
        lesson.setAttendance(student1, newAttendance);
        assertEquals(lesson.getAttendance(student1), newAttendance);
    }

    @Test
    public void setAttendance_studentDoesNotExist_throwsStudentNotFoundException() {
        Lesson lesson = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, false, 1)));
        boolean newAttendance = true;
        assertFalse(lesson.getStudents().contains(student2));
        assertThrows(StudentNotFoundException.class, () -> lesson.setAttendance(student2, newAttendance));
    }

    @Test
    public void getParticipation_studentExists_success() {
        int participation = 3;
        Lesson lesson = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, participation)));
        assertEquals(lesson.getParticipation(student1), participation);
    }

    @Test
    public void getParticipation_studentDoesNotExist_throwsStudentNotFoundException() {
        int participation = 3;
        Lesson lesson = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, participation)));
        assertFalse(lesson.getStudents().contains(student2));
        assertThrows(StudentNotFoundException.class, () -> lesson.getAttendance(student2));
    }

    @Test
    public void setParticipation_studentExists_success() {
        Lesson lesson = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 0)));
        int newParticipation = 2;
        lesson.setParticipation(student1, newParticipation);
        assertEquals(lesson.getParticipation(student1), newParticipation);
    }

    @Test
    public void setParticipation_studentDoesNotExist_throwsStudentNotFoundException() {
        Lesson lesson = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 0)));
        int newParticipation = 2;
        assertFalse(lesson.getStudents().contains(student2));
        assertThrows(StudentNotFoundException.class, () -> lesson.setParticipation(student2, newParticipation));
    }

    @Test
    public void equals_isNotLesson_returnsFalse() {
        Lesson lesson1 = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 1),
                new StudentLessonInfo(student2, false, 0)));
        assertNotEquals(lesson1, "abc123");
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        Lesson lesson1 = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 1),
                new StudentLessonInfo(student2, false, 0)));
        Lesson lesson2 = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 1),
                new StudentLessonInfo(student2, false, 0)));
        assertEquals(lesson1, lesson2);
    }

    @Test
    public void equals_differentAttributes_returnsFalse() {
        Lesson lesson1 = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 1),
                new StudentLessonInfo(student2, false, 0)));
        Lesson lesson2 = new Lesson(date, new Time("01:23"), List.of(
                new StudentLessonInfo(student1, true, 1),
                new StudentLessonInfo(student2, false, 0)));
        assertNotEquals(lesson1, lesson2);
    }

    @Test
    public void hashCode_sameAttributes_returnsSameHashCode() {
        Lesson lesson1 = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 1),
                new StudentLessonInfo(student2, false, 0)));
        Lesson lesson2 = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 1),
                new StudentLessonInfo(student2, false, 0)));
        assertEquals(lesson1.hashCode(), lesson2.hashCode());
    }

    @Test
    public void hashCode_differentAttributes_returnsDifferentHashCode() {
        Lesson lesson1 = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, true, 1),
                new StudentLessonInfo(student2, false, 0)));
        Lesson lesson2 = new Lesson(date, time, List.of(
                new StudentLessonInfo(student1, false, 1),
                new StudentLessonInfo(student2, false, 0)));
        assertNotEquals(lesson1.hashCode(), lesson2.hashCode());
    }

    @Test
    public void toString_returnsExpectedString() {
        List<StudentLessonInfo> studentLessonInfoList =
                List.of(new StudentLessonInfo(student1, true, 0));
        Lesson lesson = new Lesson(date, time, studentLessonInfoList);
        String expected = String.format("Lesson[date=%s, time=%s, studentLessonInfoList=%s]",
                date, time, studentLessonInfoList);
        assertEquals(lesson.toString(), expected);
    }
}
