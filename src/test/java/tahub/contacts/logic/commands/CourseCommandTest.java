package tahub.contacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tahub.contacts.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tahub.contacts.commons.core.GuiSettings;
import tahub.contacts.logic.Messages;
import tahub.contacts.logic.commands.exceptions.CommandException;
import tahub.contacts.model.Model;
import tahub.contacts.model.ReadOnlyAddressBook;
import tahub.contacts.model.ReadOnlyUserPrefs;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.model.course.UniqueCourseList;
import tahub.contacts.model.person.Person;

public class CourseCommandTest {

    @Test
    public void constructor_nullCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CourseCommand(null));
    }

    @Test
    public void execute_courseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCourseAdded modelStub = new ModelStubAcceptingCourseAdded();
        Course validCourse = new Course(new CourseCode("CS2103T"), new CourseName("Software Engineering"));

        CommandResult commandResult = new CourseCommand(validCourse).execute(modelStub);

        assertEquals(String.format(CourseCommand.MESSAGE_SUCCESS, Messages.format(validCourse)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCourse), modelStub.coursesAdded);
    }

    @Test
    public void execute_duplicateCourse_throwsCommandException() {
        Course validCourse = new Course(new CourseCode("CS2103T"), new CourseName("Software Engineering"));
        CourseCommand courseCommand = new CourseCommand(validCourse);
        ModelStub modelStub = new ModelStubWithCourse(validCourse);

        assertThrows(CommandException.class,
                CourseCommand.MESSAGE_DUPLICATE_PERSON, () -> courseCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Course course1 = new Course(new CourseCode("CS2103T"), new CourseName("Software Engineering"));
        Course course2 = new Course(new CourseCode("CS2101"), new CourseName("Effective Communication"));
        CourseCommand addCourse1Command = new CourseCommand(course1);
        CourseCommand addCourse2Command = new CourseCommand(course2);

        // same object -> returns true
        assertTrue(addCourse1Command.equals(addCourse1Command));

        // same values -> returns true
        CourseCommand addCourse1CommandCopy = new CourseCommand(course1);
        assertTrue(addCourse1Command.equals(addCourse1CommandCopy));

        // different types -> returns false
        assertFalse(addCourse1Command.equals(1));

        // null -> returns false
        assertFalse(addCourse1Command.equals(null));

        // different course -> returns false
        assertFalse(addCourse1Command.equals(addCourse2Command));
    }

    @Test
    public void toStringMethod() {
        CourseCommand command = new CourseCommand(new Course(new CourseCode("CS2103T"), new CourseName("Software Engineering")));
        String expected = "tahub.contacts.logic.commands.CourseCommand{toAdd=[CS2103T: Software Engineering]}";
        assertEquals(expected, command.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(java.nio.file.Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourseListFilePath(java.nio.file.Path courseListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public java.nio.file.Path getCourseListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCourseList(UniqueCourseList courseList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueCourseList getCourseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCourse(Course course) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single course.
     */
    private class ModelStubWithCourse extends ModelStub {
        private final Course course;

        ModelStubWithCourse(Course course) {
            requireNonNull(course);
            this.course = course;
        }

        @Override
        public boolean hasCourse(Course course) {
            requireNonNull(course);
            return this.course.isConflictCourse(course);
        }
    }

    /**
     * A Model stub that always accept the course being added.
     */
    private class ModelStubAcceptingCourseAdded extends ModelStub {
        final ArrayList<Course> coursesAdded = new ArrayList<>();

        @Override
        public boolean hasCourse(Course course) {
            requireNonNull(course);
            return coursesAdded.stream().anyMatch(course::isConflictCourse);
        }

        @Override
        public void addCourse(Course course) {
            requireNonNull(course);
            coursesAdded.add(course);
        }

        @Override
        public UniqueCourseList getCourseList() {
            return new UniqueCourseList();
        }
    }
}
