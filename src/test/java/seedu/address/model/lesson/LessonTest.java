package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;
import seedu.address.model.student.Student;
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
        assertThrows(NullPointerException.class, () -> new Lesson(null, time, Collections.emptyList()));
    }

    @Test
    public void constructor_nullTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(date, null, Collections.emptyList()));
    }

    @Test
    public void getDate_success() {
        Lesson lesson = new Lesson(date, time, Collections.emptyList());
        assertEquals(date, lesson.getDate());
    }

    @Test
    public void getTime_success() {
        Lesson lesson = new Lesson(date, time, Collections.emptyList());
        assertEquals(time, lesson.getTime());
    }

    @Test
    public void getStudents_modifyList_throwsUnsupportedOperationException() {
        Lesson lesson = new Lesson(date, time, Arrays.asList(student1, student2));
        List<Student> students = lesson.getStudents();
        assertThrows(UnsupportedOperationException.class, () -> students.add(new StudentBuilder().build()));
    }

    @Test
    public void addStudent_success() {
        Lesson lesson = new Lesson(date, time, Collections.emptyList());
        lesson.addStudent(student1);
        assertTrue(lesson.getStudents().contains(student1));
    }

    @Test
    public void removeStudent_success() {
        Lesson lesson = new Lesson(date, time, Arrays.asList(student1, student2));
        lesson.removeStudent(student1);
        assertTrue(!lesson.getStudents().contains(student1));
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        Lesson lesson1 = new Lesson(date, time, Arrays.asList(student1, student2));
        Lesson lesson2 = new Lesson(date, time, Arrays.asList(student1, student2));
        assertEquals(lesson1, lesson2);
    }

    @Test
    public void equals_differentAttributes_returnsFalse() {
        Lesson lesson1 = new Lesson(date, time, Arrays.asList(student1, student2));
        Lesson lesson2 = new Lesson(date, new Time("11:00"), Arrays.asList(student1));
        assertNotEquals(lesson1, lesson2);
    }

    @Test
    public void hashCode_sameAttributes_returnsSameHashCode() {
        Lesson lesson1 = new Lesson(date, time, Arrays.asList(student1, student2));
        Lesson lesson2 = new Lesson(date, time, Arrays.asList(student1, student2));
        assertEquals(lesson1.hashCode(), lesson2.hashCode());
    }

    @Test
    public void hashCode_differentAttributes_returnsDifferentHashCode() {
        Lesson lesson1 = new Lesson(date, time, Arrays.asList(student1, student2));
        Lesson lesson2 = new Lesson(date, new Time("11:00"), Arrays.asList(student1));
        assertNotEquals(lesson1.hashCode(), lesson2.hashCode());
    }
}
