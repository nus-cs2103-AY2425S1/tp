package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tutorease.address.commons.core.GuiSettings;
import tutorease.address.commons.core.index.Index;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.Model;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.ReadOnlyUserPrefs;
import tutorease.address.model.TutorEase;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.LessonBuilder;

public class DeleteLessonCommandTest {

    public DeleteLessonCommandTest() throws ParseException {
    }

    private class ModelStubAcceptingLessonDeleted extends ModelStub {
        final ArrayList<Lesson> lessonsAdded = new ArrayList<>();

        @Override
        public boolean hasLessons(Lesson lesson) {
            requireNonNull(lesson);
            return lessonsAdded.stream().anyMatch(lesson::isOverlapping);
        }

        @Override
        public void addLesson(Lesson lesson) {
            requireNonNull(lesson);
            lessonsAdded.add(lesson);
        }

        @Override
        public void deleteLesson(int index) {
            lessonsAdded.remove(index);
        }

        @Override
        public Lesson getLesson(int index) {
            return lessonsAdded.get(index);
        }

        @Override
        public int getLessonScheduleSize() {
            return lessonsAdded.size();
        }

        @Override
        public ReadOnlyTutorEase getTutorEase() {
            return new TutorEase();
        }
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        // Ensure constructor throws NullPointerException if index is null
        assertThrows(NullPointerException.class, () -> new DeleteLessonCommand(null));
    }

    @Test
    public void equals() throws CommandException, ParseException {
        Index index = Index.fromZeroBased(0);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(index);
        // Test equality with the same object (identity)
        assertTrue(deleteLessonCommand.equals(deleteLessonCommand));

        // Test equality with null
        assertFalse(deleteLessonCommand.equals(null));

        // Test equality with an object of different type
        assertFalse(deleteLessonCommand.equals(new Object()));

        // Test equality with a different DeleteLessonCommand (different index)
        DeleteLessonCommand differentCommand = new DeleteLessonCommand(Index.fromOneBased(2));
        assertFalse(deleteLessonCommand.equals(differentCommand));
    }

    @Test
    public void toString_correctFormat() {
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(Index.fromOneBased(1));
        // Verify that the toString method returns the correct format
        String expectedString = String.format(DeleteLessonCommand.DELETE_COMMAND_STRING_FORMAT, 0);
        assertEquals(expectedString, deleteLessonCommand.toString());
    }

    @Test
    public void execute_validIndex_success() throws CommandException, ParseException {
        ModelStubAcceptingLessonDeleted modelStub = new ModelStubAcceptingLessonDeleted();
        LessonBuilder lessonBuilder = new LessonBuilder();
        Lesson lesson = lessonBuilder.build();
        Index index = Index.fromZeroBased(0);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(index);

        modelStub.addLesson(lesson);
        assertTrue(modelStub.hasLessons(lesson));
        CommandResult result = deleteLessonCommand.execute(modelStub);

        assertEquals(String.format(DeleteLessonCommand.MESSAGE_SUCCESS, lesson), result.getFeedbackToUser());
        assertFalse(modelStub.hasLessons(lesson));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws ParseException {
        ModelStubAcceptingLessonDeleted modelStub = new ModelStubAcceptingLessonDeleted();
        LessonBuilder lessonBuilder = new LessonBuilder();
        Lesson lesson = lessonBuilder.build();
        Index index = Index.fromZeroBased(10);
        DeleteLessonCommand deleteLessonCommand = new DeleteLessonCommand(index);

        modelStub.addLesson(lesson);
        assertTrue(modelStub.hasLessons(lesson));

        CommandException thrown = Assertions.assertThrows(
                CommandException.class, () -> deleteLessonCommand.execute(modelStub));
        assertEquals(DeleteLessonCommand.MESSAGE_INVALID_INDEX, thrown.getMessage());
    }

    /**
     * A Model stub that always accept the lesson being deleted.
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
        public Path getTutorEaseFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTutorEaseFilePath(Path tutorEaseFilePath) {
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
        public void deleteLesson(int index) {
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
}
