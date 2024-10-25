package tutorease.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tutorease.address.commons.core.GuiSettings;
import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.Model;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.ReadOnlyUserPrefs;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.StudentBuilder;

@Nested
class DeleteContactCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteContactCommand(null));
    }

    @Test
    public void testToString_returnsCorrectString() {
        Index index = Index.fromZeroBased(0);
        DeleteContactCommand deleteCommand = new DeleteContactCommand(index);
        String expectedString = String.format(DeleteContactCommand.DELETE_COMMAND_STRING_FORMAT, 0);

        // Test toString method
        assertEquals(expectedString, deleteCommand.toString());
    }

    @Test
    public void equals() {
        Index index = Index.fromZeroBased(0);
        DeleteContactCommand deleteCommand = new DeleteContactCommand(index);
        // Test the same object
        assertEquals(deleteCommand, deleteCommand);

        Index index1 = Index.fromZeroBased(1);
        DeleteContactCommand deleteCommand1 = new DeleteContactCommand(index1);
        DeleteContactCommand deleteCommand2 = new DeleteContactCommand(index1);

        // Test different objects but same Index
        assertEquals(deleteCommand1, deleteCommand2);

        Index index2 = Index.fromZeroBased(2);
        DeleteContactCommand deleteCommandWithIndex1 = new DeleteContactCommand(index1);
        DeleteContactCommand deleteCommandWithIndex2 = new DeleteContactCommand(index2);

        // Test different objects with different Index
        assertNotEquals(deleteCommandWithIndex1, deleteCommandWithIndex2);

        // Test against an object of a different class
        assertFalse(deleteCommand.equals("some string"));
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        ModelStubAcceptingPersonDeleted modelStub = new ModelStubAcceptingPersonDeleted();
        Person validPerson = new StudentBuilder().build();
        modelStub.addPerson(validPerson);

        CommandResult commandResult = new DeleteContactCommand(Index.fromZeroBased(0)).execute(modelStub);

        String expectedMessage = String.format("Contact [%s; Phone: %s; Email: %s; Address: %s; Tags: ] "
                        + "deleted successfully",
                validPerson.getName(), validPerson.getPhone(), validPerson.getEmail(), validPerson.getAddress());

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertEquals(1, modelStub.personsDeleted.size());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        DeleteContactCommand deleteCommand = new DeleteContactCommand(Index.fromZeroBased(0));
        ModelStub modelStub = new ModelStubWithPersons();

        CommandException thrown = assertThrows(CommandException.class, () -> deleteCommand.execute(modelStub));
        assertEquals(DeleteContactCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, thrown.getMessage());
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        DeleteContactCommand deleteCommand = new DeleteContactCommand(Index.fromZeroBased(0));
        ModelStubEmptyList modelStub = new ModelStubEmptyList();

        CommandException thrown = assertThrows(CommandException.class, () -> deleteCommand.execute(modelStub));
        assertEquals(DeleteContactCommand.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, thrown.getMessage());
    }


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
        public Path getTutorEaseFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTutorEaseFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTutorEase(ReadOnlyTutorEase newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTutorEase getTutorEase() {
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
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public LessonSchedule getLessonSchedule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Lesson> getFilteredLessonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLessonList(Predicate<Lesson> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLessons(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLesson(Lesson lesson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Lesson getLesson(int index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getLessonScheduleSize() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudentLesson(Person student) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPersons extends ModelStub {
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList();
        }
    }

    /**
     * A Model stub that accepts a person being deleted.
     */
    private class ModelStubAcceptingPersonDeleted extends ModelStub {
        final List<Person> personsDeleted = new ArrayList<>();
        final ObservableList<Person> persons = FXCollections.observableArrayList();

        @Override
        public void addPerson(Person person) {
            persons.add(person); // Add the person to the list
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons; // Return the list containing added persons
        }

        @Override
        public void deletePerson(Person target) {
            personsDeleted.add(target); // Store the deleted person
            persons.remove(target); // Remove the person from the original list
        }
    }

    /**
     * A Model stub that simulates an empty list of persons.
     */
    private class ModelStubEmptyList extends ModelStub {
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(); // Return an empty list
        }
    }

}
