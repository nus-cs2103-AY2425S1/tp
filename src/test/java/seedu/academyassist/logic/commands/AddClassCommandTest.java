package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.academyassist.logic.Messages.MESSAGE_NO_STUDENT_FOUND;
import static seedu.academyassist.logic.commands.AddClassCommand.MESSAGE_DUPLICATE_CLASS;
import static seedu.academyassist.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.academyassist.commons.core.GuiSettings;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ReadOnlyAcademyAssist;
import seedu.academyassist.model.ReadOnlyUserPrefs;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.testutil.PersonBuilder;

public class AddClassCommandTest {

    @Test
    public void constructor_nullId_throwsNullPointerException() {
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("Math"));
        assertThrows(NullPointerException.class, () -> new AddClassCommand(null, subjects));
    }

    @Test
    public void constructor_nullSubjects_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddClassCommand(new StudentId("S00001"), null));
    }

    @Test
    public void execute_duplicateClass_throwsCommandException() {
        // studentId: S00008, subjects: Math
        Person validPerson = new PersonBuilder().build();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("Math"));
        subjects.add(new Subject("Chinese"));
        AddClassCommand addClassCommand = new AddClassCommand(new StudentId("S00008"), subjects);
        assertThrows(CommandException.class, MESSAGE_DUPLICATE_CLASS, () -> addClassCommand.execute(modelStub));
    }

    @Test
    public void execute_noStudentWithThatId_throwsCommandException() {
        // studentId: S00008, subjects: Math
        Person validPerson = new PersonBuilder().build();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("Chinese"));
        AddClassCommand addClassCommand = new AddClassCommand(new StudentId("S00001"), subjects);
        assertThrows(CommandException.class, MESSAGE_NO_STUDENT_FOUND, () -> addClassCommand.execute(modelStub));
    }

    @Test
    public void execute_singleSubjectAcceptedByModel() throws Exception {
        // studentId: S00008, subjects: Math
        Person validPerson = new PersonBuilder().build();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("Chinese"));
        AddClassCommand addClassCommand = new AddClassCommand(new StudentId("S00008"), subjects);
        CommandResult commandResult = addClassCommand.execute(modelStub);
        assertEquals(String.format(AddClassCommand.MESSAGE_SUCCESS, "S00008",
                        "Amy Bee", subjects.toString()), commandResult.getFeedbackToUser());

    }

    @Test
    public void execute_multipleSubjectsAcceptedByModel() throws Exception {
        // studentId: S00008, subjects: Math
        Person validPerson = new PersonBuilder().build();
        ModelStub modelStub = new ModelStubWithPerson(validPerson);
        Set<Subject> subjects = new HashSet<>();
        subjects.add(new Subject("Chinese"));
        subjects.add(new Subject("Science"));
        AddClassCommand addClassCommand = new AddClassCommand(new StudentId("S00008"), subjects);
        CommandResult commandResult = addClassCommand.execute(modelStub);
        assertEquals(String.format(AddClassCommand.MESSAGE_SUCCESS, "S00008",
                "Amy Bee", subjects.toString()), commandResult.getFeedbackToUser());

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
        public Path getAcademyAssistFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAcademyAssistFilePath(Path academyAssistFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAcademyAssist(ReadOnlyAcademyAssist newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAcademyAssist getAcademyAssist() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPersonWithIc(Ic ic) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPersonWithStudentId(StudentId studentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean personDuplicateClass(Set<Subject> subjects, Person student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSubjectsToPerson(Set<Subject> subjects, Person person) {
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
        public void sortAcademyAssistByName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortAcademyAssistByClass() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortAcademyAssistById() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void incrementStudentCount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getStudentCount() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getPersonWithIc(Ic ic) {
            return null;
        }

        @Override
        public Person getPersonWithStudentId(StudentId studentId) {
            return null;
        }
    }

    /**
     * A Model stub that contains a single person. Allows subjects to be added.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }

        @Override
        public boolean hasPersonWithIc(Ic ic) {
            requireNonNull(ic);
            return this.person.getIc().equals(ic);
        }

        @Override
        public void addSubjectsToPerson(Set<Subject> subjects, Person person) {
            return;
        }

        @Override
        public boolean hasPersonWithStudentId(StudentId studentId) {
            return this.person.getStudentId().equals(studentId);
        }

        @Override
        public Person getPersonWithStudentId(StudentId studentId) {
            if (this.person.getStudentId().equals(studentId)) {
                return person;
            }
            return null;
        }

        @Override
        public boolean personDuplicateClass(Set<Subject> subjects, Person student) {
            for (Subject s : subjects) {
                if (student.getSubjects().contains(s)) {
                    return true;
                }
            }
            return false;
        }
    }
}
