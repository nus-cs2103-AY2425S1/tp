package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;

public class UniqueLessonListTest {

    private UniqueLessonList uniqueLessonList;
    private Lesson lesson1;
    private Lesson lesson2;

    @BeforeEach
    public void setUp() {
        uniqueLessonList = new UniqueLessonList();
        lesson1 = new Lesson(new Date("2024-11-01"), new Time("09:00"), new ArrayList<>());
        lesson2 = new Lesson(new Date("2024-11-02"), new Time("11:00"), new ArrayList<>());
    }

    @Test
    public void add_lessonNotInList_success() {
        uniqueLessonList.add(lesson1);
        assertTrue(uniqueLessonList.contains(lesson1));
    }

    @Test
    public void add_duplicateLesson_throwsDuplicateLessonException() {
        uniqueLessonList.add(lesson1);
        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.add(lesson1));
    }

    @Test
    public void setLesson_existingLesson_success() {
        uniqueLessonList.add(lesson1);
        Lesson editedLesson = new Lesson(lesson1);
        uniqueLessonList.setLesson(lesson1, editedLesson);
        assertTrue(uniqueLessonList.contains(editedLesson));
    }

    @Test
    public void setLesson_nonExistingLesson_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.setLesson(lesson1, lesson2));
    }

    @Test
    public void remove_existingLesson_success() {
        uniqueLessonList.add(lesson1);
        uniqueLessonList.remove(lesson1);
        assertFalse(uniqueLessonList.contains(lesson1));
    }

    @Test
    public void remove_nonExistingLesson_throwsLessonNotFoundException() {
        assertThrows(LessonNotFoundException.class, () -> uniqueLessonList.remove(lesson1));
    }

    @Test
    public void sort_lessonsSortedByDateAndTime_success() {
        uniqueLessonList.add(lesson2);
        uniqueLessonList.add(lesson1);
        uniqueLessonList.sort();

        List<Lesson> sortedList = uniqueLessonList.asUnmodifiableObservableList();
        assertEquals(lesson1, sortedList.get(0));
        assertEquals(lesson2, sortedList.get(1));
    }

    @Test
    public void filtered_filteredListContainsCorrectElements() {
        uniqueLessonList.add(lesson1);
        uniqueLessonList.add(lesson2);

        List<Lesson> filteredList = uniqueLessonList.filtered(l -> l.getDate().equals(new Date("2024-11-01")));
        assertEquals(1, filteredList.size());
        assertTrue(filteredList.contains(lesson1));
    }

    @Test
    public void setLessons_uniqueLessonList_success() {
        uniqueLessonList.add(lesson1);
        UniqueLessonList newLessonList = new UniqueLessonList();
        newLessonList.add(lesson2);

        uniqueLessonList.setLessons(newLessonList);
        assertTrue(uniqueLessonList.contains(lesson2));
        assertFalse(uniqueLessonList.contains(lesson1));
    }

    @Test
    public void setLessons_withDuplicateLessons_throwsDuplicateLessonException() {
        List<Lesson> duplicateLessons = new ArrayList<>();
        duplicateLessons.add(lesson1);
        duplicateLessons.add(lesson1);

        assertThrows(DuplicateLessonException.class, () -> uniqueLessonList.setLessons(duplicateLessons));
    }

    @Test
    public void equals_sameLessonList_returnsTrue() {
        UniqueLessonList anotherList = new UniqueLessonList();
        assertTrue(uniqueLessonList.equals(anotherList));
    }

    @Test
    public void equals_differentLessonList_returnsFalse() {
        uniqueLessonList.add(lesson1);
        UniqueLessonList anotherList = new UniqueLessonList();
        anotherList.add(lesson2);
        assertFalse(uniqueLessonList.equals(anotherList));
    }

    @Test
    public void hashCode_sameLessonList_returnsSameHashCode() {
        UniqueLessonList anotherList = new UniqueLessonList();
        assertEquals(uniqueLessonList.hashCode(), anotherList.hashCode());
    }
}
