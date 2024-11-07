package tahub.contacts.logic.commands.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import tahub.contacts.testutil.EditCourseDescriptorBuilder;

public class CourseEditCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), new UniqueCourseList(), null);

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Course currentCourse = new Course(new CourseCode("CS1101S"), new CourseName("Programming Methodology"));
        model.addCourse(currentCourse);

        Course editedCourse = new Course(new CourseCode("CS1101S"), new CourseName("Programming Basics"));
        CourseEditCommand.EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder(editedCourse).build();
        CourseEditCommand courseEditCommand = new CourseEditCommand(new CourseCode("CS1101S"), descriptor);
        String expectedMessage = String.format(CourseEditCommand.MESSAGE_EDIT_COURSE_SUCCESS, editedCourse);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new UniqueCourseList(), null);
        Course editedCourseCopy = new Course(new CourseCode("CS1101S"), new CourseName("Programming Basics"));
        expectedModel.addCourse(editedCourseCopy);

        assertCommandSuccess(courseEditCommand, model, expectedMessage, expectedModel);
    }

    // No fields specified success
    @Test
    public void execute_noFieldsSpecifiedUnfilteredList_success() {
        Course currentCourse = new Course(new CourseCode("CS1101S"), new CourseName("Programming Methodology"));
        model.addCourse(currentCourse);

        CourseEditCommand.EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder().build();
        CourseEditCommand courseEditCommand = new CourseEditCommand(new CourseCode("CS1101S"), descriptor);
        String expectedMessage = String.format(CourseEditCommand.MESSAGE_EDIT_COURSE_SUCCESS, currentCourse);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), new UniqueCourseList(), null);
        Course currentCourseCopy = new Course(new CourseCode("CS1101S"), new CourseName("Programming Methodology"));
        expectedModel.addCourse(currentCourseCopy);

        assertCommandSuccess(courseEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_courseNotExisting_failure() {
        CourseEditCommand.EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder()
                .withCourseName("Software Engineering").build();
        CourseEditCommand courseEditCommand = new CourseEditCommand(new CourseCode("CS2100"), descriptor);

        assertCommandFailure(courseEditCommand, model, Messages.MESSAGE_NO_EXISTING_COURSE);
    }

    @Test
    public void equals() {
        final CourseEditCommand standardCommand = new CourseEditCommand(
                new CourseCode("CS2103T"), new EditCourseDescriptorBuilder()
                    .withCourseName("Software Engineering").build());

        // same values -> returns true
        CourseEditCommand.EditCourseDescriptor copyDescriptor = new EditCourseDescriptorBuilder()
                .withCourseName("Software Engineering").build();
        CourseEditCommand commandWithSameValues = new CourseEditCommand(new CourseCode("CS2103T"), copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // different course code -> returns false
        CourseEditCommand commandWithDifferentCode = new CourseEditCommand(new CourseCode("CS1101S"), copyDescriptor);
        assertEquals(false, standardCommand.equals(commandWithDifferentCode));

        // different descriptor -> returns false
        CourseEditCommand commandWithDifferentDescriptor = new CourseEditCommand(new
                CourseCode("CS2103T"), new EditCourseDescriptorBuilder()
                    .withCourseName("Software Construction").build());
        assertEquals(false, standardCommand.equals(commandWithDifferentDescriptor));
    }

    @Test
    public void toStringMethod() {
        CourseCode courseCode = new CourseCode("CS1101S");
        CourseEditCommand.EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder()
                .withCourseName("Programming basics").build();
        CourseEditCommand courseEditCommand = new CourseEditCommand(courseCode, descriptor);
        String expected = CourseEditCommand.class.getCanonicalName()
                + "{courseCode=" + courseCode + ", editCourseDescriptor=" + descriptor + "}";
        assertEquals(expected, courseEditCommand.toString());
    }
}
