package tahub.contacts.logic.commands;

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

public class DeleteCourseCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), new UniqueCourseList());

    @Test
    public void execute_validCourseCodeUnfilteredList_success() {
        Course courseToDelete = new Course(new CourseCode("CS1101S"), new CourseName("Programming Methodology"));
        model.addCourse(courseToDelete);
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(new CourseCode("CS1101S"));

        String expectedMessage = String.format(DeleteCourseCommand.MESSAGE_DELETE_COURSE_SUCCESS, courseToDelete);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new UniqueCourseList());

        assertCommandSuccess(deleteCourseCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_courseNotExisting_failure() {
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(new CourseCode("CS2100"));

        assertCommandFailure(deleteCourseCommand, model, Messages.MESSAGE_NO_EXISTING_COURSE);
    }

    @Test
    public void equals() {
        DeleteCourseCommand deleteFirstCommand = new DeleteCourseCommand(new CourseCode("CS1101S"));
        DeleteCourseCommand deleteSecondCommand = new DeleteCourseCommand(new CourseCode("CS2101"));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCourseCommand deleteFirstCommandCopy = new DeleteCourseCommand(new CourseCode("CS1101S"));
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
        DeleteCourseCommand deleteCourseCommand = new DeleteCourseCommand(courseCode);
        String expected = DeleteCourseCommand.class.getCanonicalName() + "{courseCode=" + courseCode + "}";
        assertEquals(expected, deleteCourseCommand.toString());
    }
}
