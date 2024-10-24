package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class GradeCommandTest {

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GradeCommand(null,
                VALID_MODULE_AMY, VALID_GRADE_AMY));
        assertThrows(NullPointerException.class, () -> new GradeCommand(Index.fromOneBased(1),
                null, VALID_GRADE_AMY));
    }

    @Test
    public void execute_validGrade_success() throws Exception {
        ModelStubAcceptingGradeAdded modelStub = new ModelStubAcceptingGradeAdded();
        Person validPerson = new PersonBuilder().withModules(VALID_MODULE_AMY).build();

        CommandResult commandResult = new GradeCommand(Index.fromOneBased(1),
                VALID_MODULE_AMY, VALID_GRADE_AMY).execute(modelStub);

        assertEquals(String.format(GradeCommand.MESSAGE_GRADE_SUCCESS, VALID_MODULE_AMY, validPerson.getName()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsUpdated);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithPerson(ALICE);
        GradeCommand gradeCommand = new GradeCommand(Index.fromOneBased(99), VALID_MODULE_AMY, VALID_GRADE_AMY);

        CommandException exception = assertThrows(CommandException.class, () -> gradeCommand.execute(modelStub));
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_invalidModule_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithPerson(ALICE);
        String invalidModule = "INVALID_MODULE";
        GradeCommand gradeCommand = new GradeCommand(Index.fromOneBased(1), invalidModule, VALID_GRADE_AMY);

        CommandException exception = assertThrows(CommandException.class, () -> gradeCommand.execute(modelStub));
        assertEquals(GradeCommand.MESSAGE_INVALID_MODULE, exception.getMessage());
    }

    @Test
    public void execute_invalidGrade_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithPerson(ALICE);
        int invalidGrade = -10; // Negative grade, invalid
        GradeCommand gradeCommand = new GradeCommand(Index.fromOneBased(1), VALID_MODULE_AMY, invalidGrade);

        CommandException exception = assertThrows(CommandException.class, () -> gradeCommand.execute(modelStub));
        assertEquals(GradeCommand.MESSAGE_INVALID_GRADE, exception.getMessage());
    }

    @Test
    public void equals() {
        GradeCommand gradeFirstCommand = new GradeCommand(Index.fromOneBased(1),
                VALID_MODULE_AMY, VALID_GRADE_AMY);
        GradeCommand gradeSecondCommand = new GradeCommand(Index.fromOneBased(2),
                VALID_MODULE_AMY, VALID_GRADE_AMY);

        // same object -> returns true
        assertTrue(gradeFirstCommand.equals(gradeFirstCommand));

        // same values -> returns true
        GradeCommand gradeFirstCommandCopy = new GradeCommand(Index.fromOneBased(1),
                VALID_MODULE_AMY, VALID_GRADE_AMY);
        assertTrue(gradeFirstCommand.equals(gradeFirstCommandCopy));

        // different types -> returns false
        assertFalse(gradeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gradeFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(gradeFirstCommand.equals(gradeSecondCommand));
    }

    /**
     * A Model stub that contains a single person with modules.
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
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(person);
        }
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
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void saveAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that always accepts the grading.
     */
    private class ModelStubAcceptingGradeAdded extends ModelStub {
        final ArrayList<Person> personsUpdated = new ArrayList<>();

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return javafx.collections.FXCollections.observableArrayList(new PersonBuilder()
                    .withModules(VALID_MODULE_AMY).build());
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireNonNull(editedPerson);
            personsUpdated.add(editedPerson);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
