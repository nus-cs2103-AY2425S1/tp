package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.academyassist.logic.Messages.MESSAGE_DUPLICATE_IC;
import static seedu.academyassist.testutil.Assert.assertThrows;
import static seedu.academyassist.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.academyassist.commons.core.GuiSettings;
import seedu.academyassist.logic.Messages;
import seedu.academyassist.logic.commands.exceptions.CommandException;
import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ReadOnlyAcademyAssist;
import seedu.academyassist.model.ReadOnlyUserPrefs;
import seedu.academyassist.model.person.Ic;
import seedu.academyassist.model.person.Person;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.model.person.Subject;
import seedu.academyassist.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder("S00001").build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_IC, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
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
     * A Model stub that contains a single person.
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
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        private int studentCount = 0;

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public boolean hasPersonWithIc(Ic ic) {
            requireNonNull(ic);
            return this.personsAdded.stream().anyMatch(person -> person.getIc() == ic);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
            incrementStudentCount();
        }

        @Override
        public void incrementStudentCount() {
            studentCount++;
        }

        @Override
        public int getStudentCount() {
            return studentCount;
        }

        @Override
        public ReadOnlyAcademyAssist getAcademyAssist() {
            return new AcademyAssist();
        }
    }

}
