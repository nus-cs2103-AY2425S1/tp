package tutorease.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_DURATION;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_LOCATION_INDEX;
import static tutorease.address.logic.commands.CommandTestUtil.VALID_START_DATE;
import static tutorease.address.testutil.Assert.assertThrows;
import static tutorease.address.testutil.TypicalStudents.getTypicalStudents;

import org.junit.jupiter.api.Test;

import tutorease.address.commons.util.DateTimeUtil;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.person.Person;

public class LessonTest {
    private Person person = getTypicalStudents().get(0);
    private LocationIndex locationIndex = new LocationIndex(VALID_LOCATION_INDEX);
    private StartDateTime startDateTime = StartDateTime.createStartDateTime(VALID_START_DATE);
    private EndDateTime endDateTime = EndDateTime.createEndDateTime(startDateTime, VALID_DURATION);
    private StartDateTime startDateTimeOverlap = StartDateTime.createStartDateTime(VALID_START_DATE);
    private EndDateTime endDateTimeOverlap = EndDateTime.createEndDateTime(startDateTime, "2");
    private StartDateTime startDateTimeNoOverlap = StartDateTime.createStartDateTime(
            DateTimeUtil.dateTimeToString(startDateTime.getDateTime().plusDays(1)));

    private Lesson lesson = new Lesson(person, locationIndex, startDateTime, endDateTime);
    private Lesson lessonOverlap = new Lesson(person, locationIndex, startDateTimeOverlap, endDateTimeOverlap);
    private Lesson lessonNoOverlap = new Lesson(person, locationIndex, startDateTimeNoOverlap, endDateTimeOverlap);

    public LessonTest() throws ParseException {
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, null, null, null));
    }

    @Test
    public void isOverlapping() {
        assertTrue(lesson.isOverlapping(lessonOverlap));
        assertFalse(lesson.isOverlapping(lessonNoOverlap));
    }
    @Test
    public void equals() {
        // same values -> returns true
        Lesson lessonCopy = new Lesson(person, locationIndex, startDateTime, endDateTime);
        assertTrue(lesson.equals(lessonCopy));

        // same object -> returns true
        assertTrue(lesson.equals(lesson));

        // null -> returns false
        assertFalse(lesson.equals(null));

        // different type -> returns false
        assertFalse(lesson.equals(5));

        // different lesson -> returns false
        assertFalse(lesson.equals(lessonOverlap));
    }
    @Test
    public void getStudent() {
        assertTrue(lesson.getStudent().equals(person));
    }
    @Test
    public void getLocationIndex() {
        assertTrue(lesson.getLocationIndex().equals(locationIndex));
    }
    @Test
    public void getStartDateTime() {
        assertTrue(lesson.getStartDateTime().equals(startDateTime));
    }
    @Test
    public void getEndDateTime() {
        assertTrue(lesson.getEndDateTime().equals(endDateTime));
    }
    @Test
    public void getStartDateTimeString() {
        assertEquals(VALID_START_DATE, lesson.getStartDateTimeString());
    }
    @Test
    public void getEndDateTimeString() {
        assertEquals(DateTimeUtil.dateTimeToString(endDateTime.getDateTime()), lesson.getEndDateTimeString());
    }
    @Test
    public void getStudentName() {
        assertEquals(person.getName().toString(), lesson.getStudentName());
    }
    @Test
    public void getAddress() {
        assertEquals(person.getAddress().toString(), lesson.getAddress());
    }
    @Test
    public void toStringTest() {
        assertEquals("Student: "
                + person.getName().toString()
                + " Location: "
                + locationIndex.toString()
                + " Start: "
                + startDateTime.toString()
                + " End: "
                + endDateTime.toString(), lesson.toString());
    }
}
