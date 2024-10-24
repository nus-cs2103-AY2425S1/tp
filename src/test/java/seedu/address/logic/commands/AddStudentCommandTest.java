package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.MICHAEL;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.testutil.StudentBuilder;

public class AddStudentCommandTest {

    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddStudentCommand(null));
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = new AddStudentCommand(validStudent).executeCommand(modelStub);

        assertEquals(String.format(AddStudentCommand.MESSAGE_SUCCESS, Messages.format(validStudent)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        AddStudentCommand addStudentCommand = new AddStudentCommand(validStudent);
        ModelStub modelStub = new ModelStubWithStudent(validStudent);

        assertThrows(CommandException.class,
                AddStudentCommand.MESSAGE_DUPLICATE_PERSON, () -> addStudentCommand.executeCommand(modelStub));
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();
        AddStudentCommand addAliceCommand = new AddStudentCommand(alice);
        AddStudentCommand addBobCommand = new AddStudentCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddStudentCommand addAliceCommandCopy = new AddStudentCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different student -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddStudentCommand addCommand = new AddStudentCommand(MICHAEL);
        String expected = AddStudentCommand.class.getCanonicalName() + "{toAdd=" + MICHAEL + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that has all the methods failing.
     */
    private class ModelStub implements Model {
        private final List<Person> persons = new ArrayList<>();
        private final List<Person> markedAttendance = new ArrayList<>();

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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            persons.add(person);
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
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return persons.stream().anyMatch(p -> p.isSamePerson(person));
        }

        @Override
        public void deletePerson(Person target) {
            requireNonNull(target);
            persons.remove(target);
            markedAttendance.remove(target);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireAllNonNull(target, editedPerson);
            int index = persons.indexOf(target);
            if (index == -1) {
                throw new AssertionError("This method should not be called.");
            }
            persons.set(index, editedPerson);
            if (markedAttendance.contains(target)) {
                markedAttendance.remove(target);
                markedAttendance.add(editedPerson);
            }
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
        public void setFilteredPersonList(List<Person> sortedList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markAttendance() {
            persons.forEach(person -> {
                if (!markedAttendance.contains(person)) {
                    markedAttendance.add(person);
                }
            });
        }

        @Override
        public void unmarkAttendance(Person person) {
            requireNonNull(person);
            markedAttendance.removeIf(p -> p.isSamePerson(person));
        }

        @Override
        public void resetAttendance() {
            markedAttendance.clear();
        }

        // Helper method for tests
        public boolean isPersonMarkedPresent(Person person) {
            return markedAttendance.stream().anyMatch(p -> p.isSamePerson(person));
        }

        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }


    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.student.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accepts the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Student> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return studentsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            studentsAdded.add((Student) person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
