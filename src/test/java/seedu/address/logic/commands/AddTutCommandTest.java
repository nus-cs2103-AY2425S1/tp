package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUT_SAMPLE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.student.Student;
import seedu.address.model.tut.Tut;

public class AddTutCommandTest {

    @Test
    public void constructor_nullTut_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTutCommand(null));
    }

    @Test
    public void execute_tutAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTutAdded modelStub = new ModelStubAcceptingTutAdded();
        Tut validTut = TUT_SAMPLE;

        CommandResult commandResult = new AddTutCommand(validTut).execute(modelStub);

        assertEquals(String.format(AddTutCommand.MESSAGE_SUCCESS, validTut),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validTut), modelStub.tutorialsAdded);
    }

    @Test
    public void execute_duplicateTut_throwsCommandException() {
        Tut validTut = TUT_SAMPLE;
        AddTutCommand addTutCommand = new AddTutCommand(validTut);
        ModelStub modelStub = new ModelStubWithTut(validTut);

        assertThrows(CommandException.class,
                     AddTutCommand.MESSAGE_DUPLICATE_TUTORIAL, () -> addTutCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Tut tutSample1 = TUT_SAMPLE;
        Tut tutSample2 = new Tut("CS2040S", 2); // Different tutorial

        AddTutCommand addTutSample1Command = new AddTutCommand(tutSample1);
        AddTutCommand addTutSample2Command = new AddTutCommand(tutSample2);

        // same object -> returns true
        assertTrue(addTutSample1Command.equals(addTutSample1Command));

        // same values -> returns true
        AddTutCommand addTutSample1CommandCopy = new AddTutCommand(tutSample1);
        assertTrue(addTutSample1Command.equals(addTutSample1CommandCopy));

        // different types -> returns false
        assertFalse(addTutSample1Command.equals(1));

        // null -> returns false
        assertFalse(addTutSample1Command.equals(null));

        // different tutorial -> returns false
        assertFalse(addTutSample1Command.equals(addTutSample2Command));
    }

    @Test
    public void toStringMethod() {
        AddTutCommand addTutCommand = new AddTutCommand(TUT_SAMPLE);
        String expected = AddTutCommand.class.getCanonicalName() + "{toAdd=" + TUT_SAMPLE + "}";
        assertEquals(expected, addTutCommand.toString());
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
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTutorial(Tut tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTutorial(Tut tutorial) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student student) {
            return false;
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single tutorial.
     */
    private class ModelStubWithTut extends ModelStub {
        private final Tut tutorial;

        ModelStubWithTut(Tut tutorial) {
            this.tutorial = tutorial;
        }

        @Override
        public boolean hasTutorial(Tut tutorial) {
            requireNonNull(tutorial);
            return this.tutorial.equals(tutorial);
        }
    }

    /**
     * A Model stub that always accepts the tutorial being added.
     */
    private class ModelStubAcceptingTutAdded extends ModelStub {
        final ArrayList<Tut> tutorialsAdded = new ArrayList<>();

        @Override
        public boolean hasTutorial(Tut tutorial) {
            requireNonNull(tutorial);
            return tutorialsAdded.stream().anyMatch(tutorial::equals);
        }

        @Override
        public void addTutorial(Tut tutorial) {
            requireNonNull(tutorial);
            tutorialsAdded.add(tutorial);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
