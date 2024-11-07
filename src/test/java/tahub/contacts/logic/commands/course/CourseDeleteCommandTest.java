package tahub.contacts.logic.commands.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import tahub.contacts.logic.Messages;
import tahub.contacts.model.AddressBook;
import tahub.contacts.model.Model;
import tahub.contacts.model.ModelManager;
import tahub.contacts.model.UserPrefs;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.course.UniqueCourseList;

public class CourseDeleteCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), new UniqueCourseList(), null);

    @Test
    public void execute_validCourseCodeUnfilteredList_success() {
        Course courseToDelete = new Course(new CourseCode("CS1101S"), new CourseName("Programming Methodology"));
        model.addCourse(courseToDelete);
        CourseDeleteCommand courseDeleteCommand = new CourseDeleteCommand(new CourseCode("CS1101S"));

        String expectedMessage = String.format(CourseDeleteCommand.MESSAGE_DELETE_COURSE_SUCCESS, courseToDelete);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new UniqueCourseList(), null);

        assertCommandSuccess(courseDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_courseNotExisting_failure() {
        CourseDeleteCommand courseDeleteCommand = new CourseDeleteCommand(new CourseCode("CS2100"));

        assertCommandFailure(courseDeleteCommand, model, Messages.MESSAGE_NO_EXISTING_COURSE);
    }

    @Test
    public void equals() {
        CourseDeleteCommand deleteFirstCommand = new CourseDeleteCommand(new CourseCode("CS1101S"));
        CourseDeleteCommand deleteSecondCommand = new CourseDeleteCommand(new CourseCode("CS2101"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        CourseDeleteCommand deleteFirstCommandCopy = new CourseDeleteCommand(new CourseCode("CS1101S"));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different course code -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        CourseCode courseCode = new CourseCode("CS1101S");
        CourseDeleteCommand courseDeleteCommand = new CourseDeleteCommand(courseCode);
        String expected = CourseDeleteCommand.class.getCanonicalName() + "{courseCode=" + courseCode + "}";
        assertEquals(expected, courseDeleteCommand.toString());
    }
}
