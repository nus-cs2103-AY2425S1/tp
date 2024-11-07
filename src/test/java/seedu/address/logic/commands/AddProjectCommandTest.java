package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalProjects.ALPHA;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.project.Project;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.ProjectBuilder;

public class AddProjectCommandTest {

    @Test
    public void constructor_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProjectCommand(null));
    }

    @Test
    public void execute_projectAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingProjectAdded modelStub = new ModelStubAcceptingProjectAdded();
        Project validProject = new ProjectBuilder().build();

        CommandResult commandResult = new AddProjectCommand(validProject).execute(modelStub);

        assertEquals(String.format(AddProjectCommand.MESSAGE_SUCCESS, Messages.format(validProject)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProject), modelStub.projectsAdded);
    }

    @Test
    public void execute_duplicateProject_throwsCommandException() {
        Project validProject = new ProjectBuilder().build();
        AddProjectCommand addProjectCommand = new AddProjectCommand(validProject);
        ModelStub modelStub = new ModelStubWithProject(validProject);

        assertThrows(CommandException.class, AddProjectCommand.MESSAGE_DUPLICATE_PROJECT, (
                ) -> addProjectCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Project alpha = new ProjectBuilder().withName("Project Alpha").withId("A0276123J").build();
        Project beta = new ProjectBuilder().withName("Project Alpha").withId("A0276123K").build();
        AddProjectCommand addAlphaCommand = new AddProjectCommand(alpha);
        AddProjectCommand addBetaCommand = new AddProjectCommand(beta);

        // same object -> returns true
        assertTrue(addAlphaCommand.equals(addAlphaCommand));

        // same values -> returns true
        AddProjectCommand addAlphaCommandCopy = new AddProjectCommand(alpha);
        assertTrue(addAlphaCommand.equals(addAlphaCommandCopy));

        // different types -> returns false
        assertFalse(addAlphaCommand.equals(1));

        // null -> returns false
        assertFalse(addAlphaCommand.equals(null));

        // different employee -> returns false
        assertFalse(addAlphaCommand.equals(addBetaCommand));
    }

    @Test
    public void toStringMethod() {
        AddProjectCommand addProjectCommand = new AddProjectCommand(ALPHA);
        String expected = AddProjectCommand.class.getCanonicalName() + "{toAdd=" + ALPHA + "}";
        assertEquals(expected, addProjectCommand.toString());
    }

    /**
     * A Model stub that contains a single employee.
     */
    private class ModelStubWithProject extends ModelStub {
        private final Project project;

        ModelStubWithProject(Project project) {
            requireNonNull(project);
            this.project = project;
        }

        @Override
        public boolean hasProject(Project project) {
            requireNonNull(project);
            return this.project.isSameProject(project);
        }
    }

    /**
     * A Model stub that always accept the employee being added.
     */
    private class ModelStubAcceptingProjectAdded extends ModelStub {
        final ArrayList<Project> projectsAdded = new ArrayList<>();

        @Override
        public boolean hasProject(Project project) {
            requireNonNull(project);
            return projectsAdded.stream().anyMatch(project::isSameProject);
        }

        @Override
        public void addProject(Project project) {
            requireNonNull(project);
            projectsAdded.add(project);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
