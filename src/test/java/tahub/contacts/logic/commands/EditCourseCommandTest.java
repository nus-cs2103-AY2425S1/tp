package tahub.contacts.logic.commands;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static tahub.contacts.logic.commands.CommandTestUtil.assertCommandSuccess;

public class EditCourseCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), new UniqueCourseList());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Course currentCourse = new Course(new CourseCode("CS1101S"), new CourseName("Programming Methodology"));
        model.addCourse(currentCourse);
        
        Course editedCourse = new Course(new CourseCode("CS1101S"), new CourseName("Programming Basics"));
        EditCourseCommand.EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder(editedCourse).build();
        EditCourseCommand editCourseCommand = new EditCourseCommand(new CourseCode("CS1101S"), descriptor);
        String expectedMessage = String.format(EditCourseCommand.MESSAGE_EDIT_COURSE_SUCCESS, editedCourse);
        
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(), new UniqueCourseList());
        Course editedCourseCopy = new Course(new CourseCode("CS1101S"), new CourseName("Programming Basics"));
        expectedModel.addCourse(editedCourseCopy);
        
        assertCommandSuccess(editCourseCommand, model, expectedMessage, expectedModel);
    }
    
    @Test
    public void execute_courseNotExisting_failure() {
        EditCourseCommand.EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder().withCourseName("Software Engineering").build();
        EditCourseCommand editCourseCommand = new EditCourseCommand(new CourseCode("CS2100"), descriptor);

        assertCommandFailure(editCourseCommand, model, Messages.MESSAGE_NO_EXISTING_COURSE);
    }

    @Test
    public void equals() {
        final EditCourseCommand standardCommand = new EditCourseCommand(new CourseCode("CS2103T"), new EditCourseDescriptorBuilder().withCourseName("Software Engineering").build());

        // same values -> returns true
        EditCourseCommand.EditCourseDescriptor copyDescriptor = new EditCourseDescriptorBuilder().withCourseName("Software Engineering").build();
        EditCourseCommand commandWithSameValues = new EditCourseCommand(new CourseCode("CS2103T"), copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // different course code -> returns false
        EditCourseCommand commandWithDifferentCode = new EditCourseCommand(new CourseCode("CS1101S"), copyDescriptor);
        assertEquals(false, standardCommand.equals(commandWithDifferentCode));

        // different descriptor -> returns false
        EditCourseCommand commandWithDifferentDescriptor = new EditCourseCommand(new CourseCode("CS2103T"), new EditCourseDescriptorBuilder().withCourseName("Software Construction").build());
        assertEquals(false, standardCommand.equals(commandWithDifferentDescriptor));
    }
}