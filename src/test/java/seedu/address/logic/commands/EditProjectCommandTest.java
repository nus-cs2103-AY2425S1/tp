package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BETA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_BETA;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showProjectAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalProjects.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.project.Project;
import seedu.address.testutil.EditProjectDescriptorBuilder;
import seedu.address.testutil.ProjectBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditProjectCommand.
 */
public class EditProjectCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Project editedProject = new ProjectBuilder().withId("1").build();
        EditProjectCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder(editedProject).build();
        EditProjectCommand editCommand = new EditProjectCommand(INDEX_FIRST_PROJECT, descriptor);

        String expectedMessage = String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                Messages.format(editedProject));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()));
        expectedModel.setProject(model.getFilteredProjectList().get(0), editedProject);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastProject = Index.fromOneBased(model.getFilteredProjectList().size());
        Project lastProject = model.getFilteredProjectList().get(indexLastProject.getZeroBased());

        ProjectBuilder projectInList = new ProjectBuilder(lastProject);
        Project editedProject = projectInList.withName(VALID_PROJECT_NAME_BETA).build();

        EditProjectCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder()
                .withName(VALID_PROJECT_NAME_BETA)
                .build();
        EditProjectCommand editCommand = new EditProjectCommand(indexLastProject, descriptor);

        String expectedMessage = String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                Messages.format(editedProject));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()));
        expectedModel.setProject(lastProject, editedProject);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditProjectCommand editCommand = new EditProjectCommand(INDEX_FIRST_PROJECT,
                new EditProjectCommand.EditProjectDescriptor());
        Project editedProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());

        String expectedMessage = String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                Messages.format(editedProject));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        Project projectInFilteredList = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project editedProject = new ProjectBuilder(projectInFilteredList).withName(VALID_PROJECT_NAME_BETA).build();
        EditProjectCommand editCommand = new EditProjectCommand(INDEX_FIRST_PROJECT,
                new EditProjectDescriptorBuilder().withName(VALID_PROJECT_NAME_BETA).build());

        String expectedMessage = String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                Messages.format(editedProject));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()));
        expectedModel.setProject(model.getFilteredProjectList().get(0), editedProject);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateProjectAttributesUnfilteredList_success() {
        Project firstProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        EditProjectCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder(firstProject).build();
        EditProjectCommand editCommand = new EditProjectCommand(INDEX_SECOND_PROJECT, descriptor);

        // Create the expected model
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()));
        Project secondProject = model.getFilteredProjectList().get(INDEX_SECOND_PROJECT.getZeroBased());
        Project editedSecondProject = new ProjectBuilder(firstProject)
                .withId(secondProject.getId().toString()).build();
        expectedModel.setProject(model.getFilteredProjectList().get(1), editedSecondProject);

        String expectedMessage = String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                Messages.format(editedSecondProject));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateProjectAttributesFilteredList_success() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);

        // edit project in filtered list into a duplicate in address book
        Project projectInList = model.getAddressBook().getProjectList().get(INDEX_SECOND_PROJECT.getZeroBased());
        EditProjectCommand editCommand = new EditProjectCommand(INDEX_FIRST_PROJECT,
                new EditProjectDescriptorBuilder(projectInList).build());

        // Create the expected model
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()));
        Project firstProject = model.getFilteredProjectList().get(INDEX_FIRST_PROJECT.getZeroBased());
        Project editedFirstProject = new ProjectBuilder(projectInList)
                .withId(firstProject.getId().toString()).build();
        expectedModel.setProject(model.getFilteredProjectList().get(0), editedFirstProject);

        String expectedMessage = String.format(EditProjectCommand.MESSAGE_EDIT_PROJECT_SUCCESS,
                Messages.format(editedFirstProject));

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidProjectIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProjectList().size() + 1);
        EditProjectCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder()
                .withName(VALID_PROJECT_NAME_BETA).build();
        EditProjectCommand editCommand = new EditProjectCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidProjectIndexFilteredList_failure() {
        showProjectAtIndex(model, INDEX_FIRST_PROJECT);
        Index outOfBoundIndex = INDEX_SECOND_PROJECT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getProjectList().size());

        EditProjectCommand editCommand = new EditProjectCommand(outOfBoundIndex,
                new EditProjectDescriptorBuilder().withName(VALID_PROJECT_NAME_BETA).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditProjectCommand standardCommand = new EditProjectCommand(INDEX_FIRST_PROJECT, DESC_ALPHA);

        // same values -> returns true
        EditProjectCommand.EditProjectDescriptor copyDescriptor = new EditProjectCommand
                .EditProjectDescriptor(DESC_ALPHA);
        EditProjectCommand commandWithSameValues = new EditProjectCommand(INDEX_FIRST_PROJECT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // same EditProjectDescriptor -> returns true
        assertTrue(copyDescriptor.equals(copyDescriptor));

        // null EditProjectDescriptor -> returns false
        assertFalse(copyDescriptor.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditProjectCommand(INDEX_SECOND_PROJECT, DESC_ALPHA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditProjectCommand(INDEX_FIRST_PROJECT, DESC_BETA)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditProjectCommand.EditProjectDescriptor editProjectDescriptor = new EditProjectCommand.EditProjectDescriptor();
        EditProjectCommand editCommand = new EditProjectCommand(index, editProjectDescriptor);
        String expected = EditProjectCommand.class.getCanonicalName() + "{index=" + index + ", editProjectDescriptor="
                + editProjectDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
