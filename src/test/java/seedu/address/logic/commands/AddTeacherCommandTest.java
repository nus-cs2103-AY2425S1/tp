package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
import seedu.address.model.person.Teacher;
import seedu.address.testutil.TeacherBuilder;

public class AddTeacherCommandTest {

    @Test
    public void constructor_nullTeacher_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTeacherCommand(null));
    }

    @Test
    public void execute_teacherAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTeacherAdded modelStub = new ModelStubAcceptingTeacherAdded();
        Teacher validTeacher = new TeacherBuilder().build();

        CommandResult commandResult = new AddTeacherCommand(validTeacher).execute(modelStub);

        assertEquals(String.format(AddTeacherCommand.MESSAGE_SUCCESS, Messages.format(validTeacher)),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTeacher), modelStub.teachersAdded);
    }

    @Test
    public void execute_duplicateTeacher_throwsCommandException() {
        Teacher validTeacher = new TeacherBuilder().build();
        AddTeacherCommand addTeacherCommand = new AddTeacherCommand(validTeacher);
        ModelStub modelStub = new ModelStubWithTeacher(validTeacher);

        assertThrows(CommandException.class,
                AddTeacherCommand.MESSAGE_DUPLICATE_TEACHER, () -> addTeacherCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Teacher alice = new TeacherBuilder().withName("Alice").build();
        Teacher bob = new TeacherBuilder().withName("Bob").build();
        AddTeacherCommand addAliceCommand = new AddTeacherCommand(alice);
        AddTeacherCommand addBobCommand = new AddTeacherCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddTeacherCommand addAliceCommandCopy = new AddTeacherCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different teacher -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        Teacher michael = new TeacherBuilder().withName("Michael").build();
        AddTeacherCommand addCommand = new AddTeacherCommand(michael);
        String expected = AddTeacherCommand.class.getCanonicalName() + "{toAdd=" + michael + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that has all the methods failing.
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
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
        public void setFilteredPersonList(List<Person> sortedList) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single teacher.
     */
    private class ModelStubWithTeacher extends ModelStub {
        private final Teacher teacher;

        ModelStubWithTeacher(Teacher teacher) {
            requireNonNull(teacher);
            this.teacher = teacher;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.teacher.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accepts the teacher being added.
     */
    private class ModelStubAcceptingTeacherAdded extends ModelStub {
        final ArrayList<Teacher> teachersAdded = new ArrayList<>();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return teachersAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            teachersAdded.add((Teacher) person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
