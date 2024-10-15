package tutorease.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import tutorease.address.commons.core.GuiSettings;
import tutorease.address.model.LessonSchedule;
import tutorease.address.model.Model;
import tutorease.address.model.ReadOnlyTutorEase;
import tutorease.address.model.ReadOnlyUserPrefs;
import tutorease.address.model.TutorEase;
import tutorease.address.model.lesson.Lesson;
import tutorease.address.model.person.Person;

public class AddLessonCommandTest {
    private class ModelStubAcceptingLessonAdded extends ModelStub {
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
        public ReadOnlyTutorEase getTutorEase() {
            return new TutorEase();
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
