package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.exceptions.OverlappingLessonException;
import seedu.address.testutil.LessonBuilder;

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

    @BeforeAll
    public static void setUp() throws ParseException {
        uniqueLessonList.add(lesson);
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
    public void setLesson_nullTargetLesson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLessons(null));
    }

    @Test
    public void setPersons_nullUniqueLessonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLessonList.setLessons((List<Lesson>) null));
    }
    @Test
    public void setPersons_nonUniqueLessonList_throwsOverlappingLessonException() {
        assertThrows(OverlappingLessonException.class, () -> uniqueLessonList.setLessons(List.of(lesson, lesson)));
    }
    @Test
    public void isValidIndex() {
        assertTrue(uniqueLessonList.isValidIndex(1));
        assertFalse(uniqueLessonList.isValidIndex(0));
        assertFalse(uniqueLessonList.isValidIndex(-1));
    }
    @Test
    public void lessonsAreUnique() {
        assertFalse(uniqueLessonList.lessonsAreUnique(List.of(lesson, lesson)));

    }
}
