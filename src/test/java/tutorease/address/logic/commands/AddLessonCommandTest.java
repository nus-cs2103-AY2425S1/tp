package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tutorease.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import tutorease.address.commons.core.GuiSettings;
import tutorease.address.logic.Messages;
import tutorease.address.logic.commands.exceptions.CommandException;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.Model;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.ReadOnlyUserPrefs;
import tutorease.address.model.TutorEase;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.lesson.StudentId;
import tutorease.address.model.person.Person;
import tutorease.address.testutil.LessonBuilder;
import tutorease.address.testutil.StudentBuilder;

public class AddLessonCommandTest {
    @Test
    public void execute_studentOutOfRange_throwsCommandException() throws ParseException {
        ModelStub modelStub = new ModelStubAcceptingLessonAdded();
        StudentId studentId = new StudentId("100");
        Lesson lesson = new LessonBuilder().build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(studentId, lesson.getFee(),
                lesson.getStartDateTime(), lesson.getEndDateTime());

        assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, ()
            -> addLessonCommand.execute(modelStub));
    }
    @Test
    public void execute_lessonAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLessonAdded modelStub = new ModelStubAcceptingLessonAdded();
        modelStub.addPerson(new StudentBuilder().build());
        Lesson validLesson = new LessonBuilder().build();
        StudentId studentId = new StudentId("1");
        AddLessonCommand addLessonCommand = new AddLessonCommand(studentId, validLesson.getFee(),
                validLesson.getStartDateTime(), validLesson.getEndDateTime());

        CommandResult commandResult = addLessonCommand.execute(modelStub);

        assertEquals(String.format(AddLessonCommand.MESSAGE_SUCCESS, validLesson),
                commandResult.getFeedbackToUser());
        assertEquals(validLesson, modelStub.lessonsAdded.get(0));
    }
    @Test
    public void equals() throws ParseException {
        Lesson lesson = new LessonBuilder().build();
        StudentId studentId = new StudentId("1");
        AddLessonCommand addLessonCommand = new AddLessonCommand(studentId, lesson.getFee(),
                lesson.getStartDateTime(), lesson.getEndDateTime());

        // same object -> returns true
        assertTrue(addLessonCommand.equals(addLessonCommand));

        // same values -> returns true
        AddLessonCommand addLessonCommandCopy = new AddLessonCommand(studentId, lesson.getFee(),
                lesson.getStartDateTime(), lesson.getEndDateTime());
        assertTrue(addLessonCommand.equals(addLessonCommandCopy));

        // different types -> returns false
        assertFalse(addLessonCommand.equals(1));

        // null -> returns false
        assertFalse(addLessonCommand.equals(null));

        // different lesson -> returns false
        Lesson differentLesson = new LessonBuilder().withFee("20").build();
        AddLessonCommand addDifferentLessonCommand = new AddLessonCommand(studentId, differentLesson.getFee(),
                differentLesson.getStartDateTime(), differentLesson.getEndDateTime());
        assertFalse(addLessonCommand.equals(addDifferentLessonCommand));

        // different studentId -> returns false
        StudentId differentStudentId = new StudentId("2");
        AddLessonCommand addDifferentStudentIdCommand = new AddLessonCommand(differentStudentId, lesson.getFee(),
                lesson.getStartDateTime(), lesson.getEndDateTime());
        assertFalse(addLessonCommand.equals(addDifferentStudentIdCommand));
    }
    @Test
    public void toString_validLesson_returnsString() throws ParseException {
        Lesson lesson = new LessonBuilder().build();
        StudentId studentId = new StudentId("1");
        AddLessonCommand addLessonCommand = new AddLessonCommand(studentId, lesson.getFee(),
                lesson.getStartDateTime(), lesson.getEndDateTime());
        assertEquals(String.format(AddLessonCommand.TO_STRING_FORMAT,
                studentId, lesson.getFee(), lesson.getStartDateTime(), lesson.getEndDateTime()),
                addLessonCommand.toString());
    }

    private class ModelStubAcceptingLessonAdded extends ModelStub {
        final ArrayList<Lesson> lessonsAdded = new ArrayList<>();
        final TutorEase tutorEase = new TutorEase();
        @Override
        public boolean hasLessons(Lesson lesson) {
            requireNonNull(lesson);
            return lessonsAdded.stream().anyMatch(lesson::isOverlapping);
        }
        @Override
        public void addLesson(Lesson lesson) {
            lessonsAdded.add(lesson);
        }

        @Override
        public ReadOnlyTutorEase getTutorEase() {
            return tutorEase;
        }
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return tutorEase.getPersonList();
        }
        @Override
        public void addPerson(Person person) {
            tutorEase.addPerson(person);
        }
    }
    /**
     * A Model stub that always accept the lesson being added.
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
}
