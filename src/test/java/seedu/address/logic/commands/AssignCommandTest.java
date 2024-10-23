package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ALICE_ALPHA;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalProjects.ALPHA;
import static seedu.address.testutil.TypicalProjects.BETA;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.testutil.AssignmentBuilder;

public class AssignCommandTest {
    @Test
    public void constructor_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignCommand(null));
    }

    @Test
    public void execute_assignmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAssignmentAdded modelStub = new ModelStubAcceptingAssignmentAdded();
        Assignment validAssignment = new AssignmentBuilder().build();
        CommandResult commandResult = new AssignCommand(validAssignment).execute(modelStub);
        assertEquals(String.format(AssignCommand.MESSAGE_SUCCESS, Messages.format(validAssignment)),
                  commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAssignment), modelStub.assignmentsAdded);
    }
    @Test
    public void execute_duplicateAssignment_throwsCommandException() {
        Assignment validAssignment = new AssignmentBuilder().build();
        AssignCommand assignCommand = new AssignCommand(validAssignment);
        ModelStub modelStub = new ModelStubWithAssignment(validAssignment);
        assertThrows(CommandException.class,
                AssignCommand.MESSAGE_DUPLICATE_ASSIGNMENT, () -> assignCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Assignment alphaAlice = new AssignmentBuilder().withProject(ALPHA)
                .withPerson(ALICE).build();
        Assignment betaBenson = new AssignmentBuilder().withProject(BETA)
                .withPerson(BENSON).build();
        AssignCommand addAlphaAliceCommand = new AssignCommand(alphaAlice);
        AssignCommand addBetaBensonCommand = new AssignCommand(betaBenson);

        // same object -> returns true
        assertTrue(addAlphaAliceCommand.equals(addAlphaAliceCommand));

        // same values -> returns true
        AssignCommand addAlphaAliceCommandCopy = new AssignCommand(alphaAlice);
        assertTrue(addAlphaAliceCommand.equals(addAlphaAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAlphaAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAlphaAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAlphaAliceCommand.equals(addBetaBensonCommand));
    }

    @Test
    public void toStringMethod() {
        AssignCommand assignCommand = new AssignCommand(ALICE_ALPHA);
        String expected = AssignCommand.class.getCanonicalName() + "{assignmentId="
                + ALICE_ALPHA.getAssignmentId().toString() + ", projectId="
                + ALICE_ALPHA.getProject().getId().toString()
                + ", employeeId=" + ALICE_ALPHA.getPerson().getEmployeeId().toString() + "}";

        assertEquals(expected, assignCommand.toString());
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
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookPerson(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookProject(ReadOnlyAddressBook newData) {
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
        public boolean hasEmployeeId(EmployeeId employeeId) {
            return true;
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
        public ObservableList<Person> getPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProject(Project project) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasProjectId(ProjectId projectId) {
            return true;
        }

        @Override
        public void deleteProject(Project target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setProject(Project target, Project editedproject) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Project> getProjectList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(AssignmentId assignmentId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssignment(ProjectId projectId, EmployeeId employeeId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(ProjectId projectId, EmployeeId employeeId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(AssignmentId target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssignment(Assignment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Project> getFilteredProjectList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assignment> getFilteredAssignmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredProjectList(Predicate<Project> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAssignmentList(Predicate<Assignment> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single assignment.
     */
    private class ModelStubWithAssignment extends ModelStub {
        private final Assignment assignment;
        private final ArrayList<Person> persons = new ArrayList<>();
        private final ArrayList<Project> projects = new ArrayList<>();

        ModelStubWithAssignment(Assignment assignment) {
            requireNonNull(assignment);
            this.assignment = assignment;
        }

        @Override
        public ObservableList<Person> getPersonList() {
            persons.add(ALICE);
            persons.add(BENSON);
            return FXCollections.observableArrayList(persons);
        }

        @Override
        public ObservableList<Project> getProjectList() {
            projects.add(ALPHA);
            projects.add(BETA);
            return FXCollections.observableArrayList(projects);
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return this.assignment.isSameAssignment(assignment);
        }
    }

    /**
     * A Model stub that always accept the assignment being added.
     */
    private class ModelStubAcceptingAssignmentAdded extends ModelStub {
        final ArrayList<Assignment> assignmentsAdded = new ArrayList<>();
        final ArrayList<Person> persons = new ArrayList<>();
        final ArrayList<Project> projects = new ArrayList<>();

        @Override
        public ObservableList<Person> getPersonList() {
            persons.add(ALICE);
            persons.add(BENSON);
            return FXCollections.observableArrayList(persons);
        }

        @Override
        public ObservableList<Project> getProjectList() {
            projects.add(ALPHA);
            projects.add(BETA);
            return FXCollections.observableArrayList(projects);
        }

        @Override
        public boolean hasAssignment(Assignment assignment) {
            requireNonNull(assignment);
            return assignmentsAdded.stream().anyMatch(assignment::isSameAssignment);
        }

        @Override
        public void addAssignment(Assignment assignment) {
            requireNonNull(assignment);
            assignmentsAdded.add(assignment);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
