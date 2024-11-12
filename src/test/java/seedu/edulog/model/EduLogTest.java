package seedu.edulog.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.edulog.testutil.Assert.assertThrows;
import static seedu.edulog.testutil.TypicalEdulog.getTypicalEduLog;
import static seedu.edulog.testutil.TypicalLessons.SEC_2_MATH;
import static seedu.edulog.testutil.TypicalLessons.SEC_3_MATH;
import static seedu.edulog.testutil.TypicalLessons.SEC_4_MATH;
import static seedu.edulog.testutil.TypicalStudents.ALICE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.exceptions.DuplicateLessonException;
import seedu.edulog.model.student.Student;
import seedu.edulog.model.student.exceptions.DuplicateStudentException;
import seedu.edulog.testutil.LessonBuilder;
import seedu.edulog.testutil.StudentBuilder;

public class EduLogTest {

    private final EduLog eduLog = new EduLog();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), eduLog.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduLog.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyEduLog_replacesData() {
        EduLog newData = getTypicalEduLog();
        eduLog.resetData(newData);
        assertEquals(newData, eduLog);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        EduLogStub newData = new EduLogStub(newStudents, new ArrayList<Lesson>());

        assertThrows(DuplicateStudentException.class, () -> eduLog.resetData(newData));
    }

    /**
     * Checks that a lesson with all the same fields throws an exception.
     */
    @Test
    public void resetData_withIdenticalFields_throwsDuplicateLessonException() {
        // Two lessons with the same identity fields
        Lesson editedLesson = new LessonBuilder(SEC_2_MATH)
            .withDescription(SEC_2_MATH.getDescription().toString())
            .withDayOfWeek(SEC_2_MATH.getStartDay().toString())
            .withStartTime(SEC_2_MATH.getFormattedStartTime())
            .withEndTime(SEC_2_MATH.getFormattedEndTime())
            .build();

        List<Lesson> newLessons = Arrays.asList(SEC_2_MATH, editedLesson);
        EduLogStub newData = new EduLogStub(new ArrayList<Student>(), newLessons);

        assertThrows(DuplicateLessonException.class, () -> eduLog.resetData(newData));
    }

    /**
     * Checks that a lesson with just the description matching also throws an exception.
     */
    @Test
    public void resetData_withIdenticalDescription_throwsDuplicateLessonException() {
        // Two lessons with the same identity fields
        Lesson editedLesson = new LessonBuilder(SEC_2_MATH)
            .withDescription(SEC_2_MATH.getDescription().toString())
            .withDayOfWeek(SEC_4_MATH.getStartDay().toString())
            .withStartTime(SEC_3_MATH.getFormattedStartTime())
            .withEndTime(SEC_4_MATH.getFormattedEndTime())
            .build();

        List<Lesson> newLessons = Arrays.asList(SEC_2_MATH, editedLesson);
        EduLogStub newData = new EduLogStub(new ArrayList<Student>(), newLessons);

        assertThrows(DuplicateLessonException.class, () -> eduLog.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduLog.hasStudent(null));
    }

    @Test
    public void hasStudent_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> eduLog.hasLesson(null));
    }

    @Test
    public void hasStudent_studentNotInEduLog_returnsFalse() {
        assertFalse(eduLog.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInEduLog_returnsTrue() {
        eduLog.addStudent(ALICE);
        assertTrue(eduLog.hasStudent(ALICE));
    }

    @Test
    public void hasLesson_lessonNotInEduLog_returnsFalse() {
        assertFalse(eduLog.hasLesson(SEC_2_MATH));
    }

    @Test
    public void hasLesson_lessonInEduLog_returnsTrue() {
        eduLog.addLesson(SEC_2_MATH);
        assertTrue(eduLog.hasLesson(SEC_2_MATH));
    }


    @Test
    public void hasStudent_studentWithSameIdentityFieldsInEduLog_returnsTrue() {
        eduLog.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(eduLog.hasStudent(editedAlice));
    }

    @Test
    public void hasLesson_lessonWithSameIdentityFieldsInEduLog_returnsTrue() {
        eduLog.addLesson(SEC_2_MATH);
        Lesson editedLesson = new LessonBuilder(SEC_2_MATH)
            .withDescription(SEC_2_MATH.getDescription().toString())
            .withDayOfWeek(SEC_2_MATH.getStartDay().toString())
            .withStartTime(SEC_2_MATH.getFormattedStartTime())
            .withEndTime(SEC_2_MATH.getFormattedEndTime())
            .build();
        assertTrue(eduLog.hasLesson(editedLesson));
    }

    @Test
    public void findLesson_lessonWithDescriptionInEduLog_returnsTrue() {
        eduLog.addLesson(SEC_2_MATH);
        assertEquals(eduLog.getEdulogCalendar().findLesson(SEC_2_MATH.getDescription()), SEC_2_MATH);
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> eduLog.getStudentList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = EduLog.class.getCanonicalName() + "{students=" + eduLog.getStudentList() + ", "
            + "lessons=" + eduLog.getLessonList() + "}";
        assertEquals(expected, eduLog.toString());
    }

    /**
     * A stub ReadOnlyEduLog whose students or/and lessons list can violate interface constraints.
     */
    private static class EduLogStub implements ReadOnlyEduLog {
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<Lesson> lessons = FXCollections.observableArrayList();

        /**
         * Constructor fn for test cases testing lesson and/or student storage.
         */
        EduLogStub(Collection<Student> students, Collection<Lesson> lessons) {
            this.students.setAll(students);
            this.lessons.setAll(lessons);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Lesson> getLessonList() {
            return lessons;
        }
    }

}
