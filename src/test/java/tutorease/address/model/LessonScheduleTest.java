package tutorease.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalStudents.ALICE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.exceptions.LessonIndexOutOfRange;
import tutorease.address.model.lesson.exceptions.LessonNotInList;
import tutorease.address.model.lesson.exceptions.OverlappingLessonException;
import tutorease.address.testutil.LessonBuilder;

public class LessonScheduleTest {
    private final LessonSchedule lessonSchedule = new LessonSchedule();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), lessonSchedule.getLessonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> lessonSchedule.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyLessonSchedule_replacesData() {
        LessonSchedule newData = new LessonSchedule();
        lessonSchedule.resetData(newData);
        assertEquals(newData, lessonSchedule);
    }

    @Test
    public void resetData_withDuplicateLessons_throwsDuplicateLessonException() throws ParseException {
        // Two lessons with the same fields
        Lesson editedLesson = new LessonBuilder().withName(ALICE).build();
        List<Lesson> newLessons = Arrays.asList(editedLesson, editedLesson);
        LessonScheduleStub newData = new LessonScheduleStub(newLessons);

        assertThrows(OverlappingLessonException.class, () -> lessonSchedule.resetData(newData));
    }

    @Test
    public void hasLesson_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> lessonSchedule.hasLesson(null));
    }

    @Test
    public void hasLesson_lessonNotInLessonSchedule_returnsFalse() throws ParseException {
        assertFalse(lessonSchedule.hasLesson(new LessonBuilder().build()));
    }

    @Test
    public void hasLesson_lessonInLessonSchedule_returnsTrue() throws ParseException {
        Lesson lesson = new LessonBuilder().build();
        lessonSchedule.addLesson(lesson);
        assertTrue(lessonSchedule.hasLesson(lesson));
    }

    @Test
    public void setLesson_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> lessonSchedule.setLessons((List<Lesson>) null));
    }

    @Test
    public void setLesson_validLessonList_replacesData() throws ParseException {
        List<Lesson> newLessons = Arrays.asList(new LessonBuilder().build());
        lessonSchedule.setLessons(newLessons);
        assertEquals(newLessons, lessonSchedule.getLessonList());
    }

    @Test
    public void deleteLesson_invalidLesson_throwsLessonNotInListException() throws ParseException {
        Lesson lesson = new LessonBuilder().build();
        assertThrows(LessonNotInList.class, () -> lessonSchedule.deleteLesson(lesson));
    }

    @Test
    public void deleteLesson_validLesson_removesData() throws ParseException {
        Lesson lesson = new LessonBuilder().build();
        lessonSchedule.addLesson(lesson);
        lessonSchedule.deleteLesson(lesson);
        assertFalse(lessonSchedule.hasLesson(lesson));
    }

    @Test
    public void getLesson_invalidIndex_throwsIndexOutOfBoundsException() {
        assertThrows(LessonIndexOutOfRange.class, () -> lessonSchedule.getLesson(0));
    }

    @Test
    public void copyLessonSchedule_validLessonSchedule_returnsCopy() throws ParseException {
        LessonSchedule newData = new LessonSchedule();
        Lesson lesson = new LessonBuilder().build();
        newData.addLesson(lesson);
        LessonSchedule copy = new LessonSchedule(newData);
        assertEquals(newData, copy);
    }

    @Test
    public void getSize_emptyLessonSchedule_returnsZero() {
        assertEquals(0, lessonSchedule.getSize());
    }

    @Test
    public void getSize_nonEmptyLessonSchedule_returnsSize() throws ParseException {
        Lesson lesson = new LessonBuilder().build();
        lessonSchedule.addLesson(lesson);
        assertEquals(1, lessonSchedule.getSize());
    }

    @Test
    public void equals() throws ParseException {
        LessonSchedule lessonSchedule = new LessonSchedule();
        LessonSchedule lessonScheduleCopy = new LessonSchedule();
        Lesson lesson = new LessonBuilder().build();
        lessonSchedule.addLesson(lesson);
        lessonScheduleCopy.addLesson(lesson);
        assertEquals(lessonSchedule, lessonSchedule);
        assertNotEquals(lessonSchedule, lesson);
        assertNotEquals(lessonSchedule, new LessonSchedule());
    }

    private static class LessonScheduleStub extends LessonSchedule {
        private final ObservableList<Lesson> lessons = FXCollections.observableArrayList();

        LessonScheduleStub(Collection<Lesson> lessons) {
            this.lessons.setAll(lessons);
        }
        @Override
        public ObservableList<Lesson> getLessonList() {
            return lessons;
        }
    }
}
