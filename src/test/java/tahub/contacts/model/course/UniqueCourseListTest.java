package tahub.contacts.model.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import tahub.contacts.model.course.exceptions.CourseNotFoundException;
import tahub.contacts.model.course.exceptions.DuplicateCourseException;

public class UniqueCourseListTest {

    private final UniqueCourseList uniqueCourseList = new UniqueCourseList();

    @Test
    public void contains_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCourseList.contains(null));
    }

    @Test
    public void contains_courseNotInList_returnsFalse() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        assertFalse(uniqueCourseList.contains(course));
    }

    @Test
    public void contains_courseInList_returnsTrue() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        uniqueCourseList.add(course);
        assertTrue(uniqueCourseList.contains(course));
    }

    @Test
    public void add_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCourseList.add(null));
    }

    @Test
    public void add_duplicateCourse_throwsDuplicateCourseException() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        uniqueCourseList.add(course);
        assertThrows(DuplicateCourseException.class, () -> uniqueCourseList.add(course));
    }

    @Test
    public void setCourse_nullTargetCourse_throwsNullPointerException() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        assertThrows(NullPointerException.class, () -> uniqueCourseList.setCourse(null, course));
    }

    @Test
    public void setCourse_nullEditedCourse_throwsNullPointerException() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        uniqueCourseList.add(course);
        assertThrows(NullPointerException.class, () -> uniqueCourseList.setCourse(course, null));
    }

    @Test
    public void setCourse_targetCourseNotInList_throwsCourseNotFoundException() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        Course editedCourse = new Course(new CourseCode("CS1020"), new CourseName("Data Structures"));
        assertThrows(CourseNotFoundException.class, () -> uniqueCourseList.setCourse(course, editedCourse));
    }

    @Test
    public void setCourse_editedCourseIsSameCourse_success() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        uniqueCourseList.add(course);
        uniqueCourseList.setCourse(course, course);
        UniqueCourseList expectedUniqueCourseList = new UniqueCourseList();
        expectedUniqueCourseList.add(course);
        assertEquals(expectedUniqueCourseList, uniqueCourseList);
    }

    @Test
    public void setCourse_editedCourseHasSameIdentity_success() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        uniqueCourseList.add(course);
        Course editedCourse = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        uniqueCourseList.setCourse(course, editedCourse);
        UniqueCourseList expectedUniqueCourseList = new UniqueCourseList();
        expectedUniqueCourseList.add(editedCourse);
        assertEquals(expectedUniqueCourseList, uniqueCourseList);
    }

    @Test
    public void setCourse_editedCourseHasDifferentIdentity_success() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        uniqueCourseList.add(course);
        Course editedCourse = new Course(new CourseCode("CS1020"), new CourseName("Data Structures"));
        uniqueCourseList.setCourse(course, editedCourse);
        UniqueCourseList expectedUniqueCourseList = new UniqueCourseList();
        expectedUniqueCourseList.add(editedCourse);
        assertEquals(expectedUniqueCourseList, uniqueCourseList);
    }

    @Test
    public void remove_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCourseList.remove(null));
    }

    @Test
    public void remove_courseDoesNotExist_throwsCourseNotFoundException() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        assertThrows(CourseNotFoundException.class, () -> uniqueCourseList.remove(course));
    }

    @Test
    public void remove_existingCourse_removesCourse() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        uniqueCourseList.add(course);
        uniqueCourseList.remove(course);
        UniqueCourseList expectedUniqueCourseList = new UniqueCourseList();
        assertEquals(expectedUniqueCourseList, uniqueCourseList);
    }

    @Test
    public void setCourses_nullUniqueCourseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCourseList.setCourses((UniqueCourseList) null));
    }

    @Test
    public void setCourses_uniqueCourseList_replacesOwnListWithProvidedUniqueCourseList() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        uniqueCourseList.add(course);
        UniqueCourseList expectedUniqueCourseList = new UniqueCourseList();
        expectedUniqueCourseList.add(course);
        uniqueCourseList.setCourses(expectedUniqueCourseList);
        assertEquals(expectedUniqueCourseList, uniqueCourseList);
    }

    @Test
    public void setCourses_nullList_throwsNullPointerException() {
        // Test for the method that takes a List<Course>
        assertThrows(NullPointerException.class, () -> uniqueCourseList.setCourses((List<Course>) null));

        // Test for the method that takes a UniqueCourseList
        assertThrows(NullPointerException.class, () -> uniqueCourseList.setCourses((UniqueCourseList) null));
    }

    @Test
    public void setCourses_list_replacesOwnListWithProvidedList() {
        Course course1 = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        Course course2 = new Course(new CourseCode("CS1020"), new CourseName("Data Structures"));
        uniqueCourseList.add(course1);
        List<Course> courseList = List.of(course2);
        uniqueCourseList.setCourses(courseList);

        UniqueCourseList expectedUniqueCourseList = new UniqueCourseList();
        expectedUniqueCourseList.add(course2);

        assertEquals(expectedUniqueCourseList, uniqueCourseList);
        assertFalse(uniqueCourseList.contains(course1));
        assertTrue(uniqueCourseList.contains(course2));
    }

    @Test
    public void setCourses_listWithDuplicateCourses_throwsDuplicateCourseException() {
        Course course = new Course(new CourseCode("CS1010"), new CourseName("Programming Methodology"));
        List<Course> listWithDuplicateCourses = List.of(course, course);
        assertThrows(DuplicateCourseException.class, () -> uniqueCourseList.setCourses(listWithDuplicateCourses));
    }
}
