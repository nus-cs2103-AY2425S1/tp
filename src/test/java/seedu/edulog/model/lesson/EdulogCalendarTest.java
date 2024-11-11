package seedu.edulog.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_DAY_MATH;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MATH;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_END_TIME_MATH;
import static seedu.edulog.logic.commands.CommandTestUtil.VALID_START_TIME_MATH;
import static seedu.edulog.testutil.Assert.assertThrows;
import static seedu.edulog.testutil.TypicalLessons.SEC_3_MATH;
import static seedu.edulog.testutil.TypicalLessons.SEC_4_MATH;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.edulog.model.calendar.EdulogCalendar;
import seedu.edulog.model.calendar.Lesson;
import seedu.edulog.model.calendar.exceptions.DuplicateLessonException;
import seedu.edulog.model.calendar.exceptions.LessonNotFoundException;
import seedu.edulog.testutil.LessonBuilder;

public class EdulogCalendarTest {

    private final String randomDescription = "Random Description";

    private final EdulogCalendar edulogCalendar = new EdulogCalendar();

    @Test
    public void contains_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> edulogCalendar.contains(null));
    }

    @Test
    public void contains_lessonNotInList_returnsFalse() {
        assertFalse(edulogCalendar.contains(SEC_3_MATH));
    }

    @Test
    public void contains_lessonInList_returnsTrue() {
        edulogCalendar.add(SEC_3_MATH);
        assertTrue(edulogCalendar.contains(SEC_3_MATH));
    }

    @Test
    public void contains_lessonWithDifferentDescriptionInList_returnsFalse() {
        edulogCalendar.add(SEC_3_MATH);
        Lesson editedMathLesson = new LessonBuilder(SEC_3_MATH).withDayOfWeek(VALID_DAY_MATH)
                .withStartTime(VALID_START_TIME_MATH).withEndTime(VALID_END_TIME_MATH)
                .build();
        assertTrue(edulogCalendar.contains(editedMathLesson));
    }

    @Test
    public void contains_lessonWithSameIdentityFieldsInList_returnsFalse() {
        edulogCalendar.add(SEC_3_MATH);
        Lesson editedMathLesson = new LessonBuilder(SEC_3_MATH).withDescription(randomDescription)
                .withDayOfWeek(VALID_DAY_MATH).withStartTime(VALID_START_TIME_MATH).withEndTime(VALID_END_TIME_MATH)
                .build();
        assertFalse(edulogCalendar.contains(editedMathLesson));
    }

    @Test
    public void add_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> edulogCalendar.add(null));
    }

    @Test
    public void add_duplicateLesson_throwsDuplicateLessonException() {
        edulogCalendar.add(SEC_3_MATH);
        assertThrows(DuplicateLessonException.class, () -> edulogCalendar.add(SEC_3_MATH));
    }

    @Test
    public void setLesson_nullTargetLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> edulogCalendar.setLesson(null, SEC_3_MATH));
    }

    @Test
    public void setLesson_nullEditedLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> edulogCalendar.setLesson(SEC_3_MATH, null));
    }

    @Test
    public void setLesson_targetLessonNotInList_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> edulogCalendar.setLesson(SEC_3_MATH, SEC_3_MATH));
    }

    @Test
    public void setLesson_editedLessonIsSameLesson_success() {
        edulogCalendar.add(SEC_3_MATH);
        edulogCalendar.setLesson(SEC_3_MATH, SEC_3_MATH);
        EdulogCalendar expectedEdulogCalendar = new EdulogCalendar();
        expectedEdulogCalendar.add(SEC_3_MATH);
        assertEquals(expectedEdulogCalendar, edulogCalendar);
    }

    @Test
    public void setLesson_editedLessonHasSameIdentity_success() {
        edulogCalendar.add(SEC_3_MATH);
        Lesson editedSec3Math = new LessonBuilder(SEC_3_MATH).withDescription(VALID_DESCRIPTION_MATH)
                .withDayOfWeek(VALID_DAY_MATH)
                .build();
        edulogCalendar.setLesson(SEC_3_MATH, editedSec3Math);
        EdulogCalendar expectedEdulogCalendar = new EdulogCalendar();
        expectedEdulogCalendar.add(editedSec3Math);
        assertEquals(expectedEdulogCalendar, edulogCalendar);
    }

    @Test
    public void setLesson_editedLessonHasDifferentIdentity_success() {
        edulogCalendar.add(SEC_3_MATH);
        edulogCalendar.setLesson(SEC_3_MATH, SEC_4_MATH);
        EdulogCalendar expectedEdulogCalendar = new EdulogCalendar();
        expectedEdulogCalendar.add(SEC_4_MATH);
        assertEquals(expectedEdulogCalendar, edulogCalendar);
    }

    @Test
    public void setLesson_editedLessonHasNonUniqueIdentity_throwsDuplicateLessonException() {
        edulogCalendar.add(SEC_3_MATH);
        edulogCalendar.add(SEC_4_MATH);
        assertThrows(DuplicateLessonException.class, () -> edulogCalendar.setLesson(SEC_3_MATH, SEC_4_MATH));
    }

    @Test
    public void remove_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> edulogCalendar.remove(null));
    }

    @Test
    public void remove_lessonDoesNotExist_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> edulogCalendar.remove(SEC_3_MATH));
    }

    @Test
    public void remove_existingLesson_removesLesson() {
        edulogCalendar.add(SEC_3_MATH);
        edulogCalendar.remove(SEC_3_MATH);
        EdulogCalendar expectedEdulogCalendar = new EdulogCalendar();
        assertEquals(expectedEdulogCalendar, edulogCalendar);
    }

    @Test
    public void setLessons_nullUniqueLessonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> edulogCalendar.setLessons((EdulogCalendar) null));
    }

    @Test
    public void setLessons_uniqueLessonList_replacesOwnListWithProvidedUniqueLessonList() {
        edulogCalendar.add(SEC_3_MATH);
        EdulogCalendar expectedEdulogCalendar = new EdulogCalendar();
        expectedEdulogCalendar.add(SEC_4_MATH);
        edulogCalendar.setLessons(expectedEdulogCalendar);
        assertEquals(expectedEdulogCalendar, edulogCalendar);
    }

    @Test
    public void setLessons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> edulogCalendar.setLessons((List<Lesson>) null));
    }

    @Test
    public void setLessons_list_replacesOwnListWithProvidedList() {
        edulogCalendar.add(SEC_3_MATH);
        List<Lesson> lessonList = Collections.singletonList(SEC_4_MATH);
        edulogCalendar.setLessons(lessonList);
        EdulogCalendar expectedEdulogCalendar = new EdulogCalendar();
        expectedEdulogCalendar.add(SEC_4_MATH);
        assertEquals(expectedEdulogCalendar, edulogCalendar);
    }

    @Test
    public void setLessons_listWithDuplicateLessons_throwsDuplicateLessonException() {
        List<Lesson> listWithDuplicateLessons = Arrays.asList(SEC_3_MATH, SEC_3_MATH);
        assertThrows(DuplicateLessonException.class, () -> edulogCalendar.setLessons(listWithDuplicateLessons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> edulogCalendar.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(edulogCalendar.asUnmodifiableObservableList().toString(), edulogCalendar.toString());
    }
}
