package tutorease.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.exceptions.LessonIndexOutOfRange;
import tutorease.address.model.lesson.exceptions.LessonNotInList;
import tutorease.address.model.lesson.exceptions.OverlappingLessonException;
import tutorease.address.testutil.LessonBuilder;

public class UniqueLessonListTest {
    private static final UniqueLessonList uniqueLessonList = new UniqueLessonList();
    private static final Lesson lesson;

    static {
        try {
            lesson = new LessonBuilder().build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public UniqueLessonListTest() throws ParseException {
    }

    @BeforeEach
    public void setUp() throws ParseException {
        uniqueLessonList.add(lesson);
    }

    @AfterEach
    public void tearDown() {
        while (uniqueLessonList.size() > 0) {
            Lesson lesson = uniqueLessonList.get(0);
            uniqueLessonList.remove(lesson);
        }
    }

    @Test
    public void contains_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.contains(null));
    }

    @Test
    public void add_nullLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.add(null));
    }

    @Test
    public void add_lessonAlreadyExists_throwsDuplicateLessonException() {
        assertThrows(OverlappingLessonException.class, () -> uniqueLessonList.add(lesson));
    }

    @Test
    public void setPersons_nullUniqueLessonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLessons((UniqueLessonList) null));
    }
    @Test
    public void setPersons_nonUniqueLessonList_throwsOverlappingLessonException() {
        assertThrows(OverlappingLessonException.class, () -> uniqueLessonList.setLessons(List.of(lesson, lesson)));
    }
    @Test
    public void isValidIndex() {
        assertTrue(uniqueLessonList.isValidIndex(0));
        assertFalse(uniqueLessonList.isValidIndex(-1));
        assertFalse(uniqueLessonList.isValidIndex(-2));
    }
    @Test
    public void lessonsAreUnique() {
        assertFalse(uniqueLessonList.lessonsAreUnique(List.of(lesson, lesson)));

    }

    @Test
    public void get_validIndex_returnsCorrectLesson() {
        assertTrue(uniqueLessonList.contains(lesson));

        // Verify that we can retrieve lessons by index
        assertEquals(lesson, uniqueLessonList.get(0));
    }

    @Test
    public void get_invalidIndex_throwsLessonIndexOutOfRange() {
        assertTrue(uniqueLessonList.contains(lesson));

        // Verify that retrieving a lesson with an invalid index throws the exception
        assertThrows(LessonIndexOutOfRange.class, () -> uniqueLessonList.get(5));
    }

    @Test
    public void remove_validLesson_lessonSuccessfullyRemoved() {
        assertTrue(uniqueLessonList.contains(lesson));

        // Remove the first lesson and verify it's removed
        Lesson lesson = uniqueLessonList.get(0);
        uniqueLessonList.remove(lesson);
        assertEquals(0, uniqueLessonList.size());
        assertFalse(uniqueLessonList.contains(lesson));
    }

    @Test
    public void remove_invalidLesson_throwsLessonNotInList() {
        assertTrue(uniqueLessonList.contains(lesson));

        Lesson lesson = uniqueLessonList.get(0);
        uniqueLessonList.remove(lesson);
        assertEquals(0, uniqueLessonList.size());
        assertFalse(uniqueLessonList.contains(lesson));

        // Verify that removing a lesson that is not in the list throws the exception
        assertThrows(LessonNotInList.class, () -> uniqueLessonList.remove(lesson));
    }

    @Test
    public void size_afterAddingLessons_returnsCorrectSize() {
        assertTrue(uniqueLessonList.contains(lesson));

        // Verify that the size of the list reflects the added lessons
        assertEquals(1, uniqueLessonList.size());
    }

    @Test
    public void setLessons_withValidUniqueLessonList_replacesData() {
        UniqueLessonList newData = new UniqueLessonList();
        newData.add(lesson);
        uniqueLessonList.setLessons(newData);
        assertEquals(newData, uniqueLessonList);
    }

    @Test
    public void setLessons_withValidLessonList_replacesData() {
        List<Lesson> newData = List.of(lesson);
        UniqueLessonList originalData = new UniqueLessonList();
        originalData.add(lesson);
        uniqueLessonList.setLessons(newData);
        assertEquals(originalData, uniqueLessonList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueLessonList.asUnmodifiableObservableList().remove(0));
    }

}
