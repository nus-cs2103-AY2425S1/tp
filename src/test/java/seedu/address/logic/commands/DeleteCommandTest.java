package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.ui.MainWindow;

public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        // No need to mock the confirmation dialog
    }
    @Test
    public void execute_invalidPolicyIndex_throwsCommandException() {
        Person person = new PersonBuilder().withName("John Doe").withPolicies("Policy 1").build();
        model.addPerson(person);
        Index personIndex = Index.fromOneBased(model.getFilteredPersonList().indexOf(person) + 1);
        Index policyIndex = Index.fromOneBased(2); // Invalid policy index
        DeleteCommand deleteCommand = new DeleteCommand(personIndex, policyIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void toString_withTargetIndexOnly_returnsExpectedString() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);

        String expected = "seedu.address.logic.commands.DeleteCommand{targetIndex=1}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void toString_withTargetIndexAndPolicyIndex_returnsExpectedString() {
        Index targetIndex = Index.fromOneBased(1);
        Index policyIndex = Index.fromOneBased(2);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex, policyIndex);

        String expected = "seedu.address.logic.commands.DeleteCommand{targetIndex=1, policyIndex=2}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void toString_withTargetNameOnly_returnsExpectedString() {
        Name targetName = new Name("Alex Yeoh");
        DeleteCommand deleteCommand = new DeleteCommand(targetName);

        String expected = "seedu.address.logic.commands.DeleteCommand{targetName=Alex Yeoh}";
        assertEquals(expected, deleteCommand.toString());
    }

    @Test
    public void execute_deletePersonByName_success() {
        Person person = new PersonBuilder().withName("John Doe").build();
        model.addPerson(person);
        DeleteCommand deleteCommand = new DeleteCommand(new Name("John Doe"));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, person);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(person);

        try (MockedStatic<MainWindow> mockedStatic = mockStatic(MainWindow.class)) {
            mockedStatic.when(() -> MainWindow.showConfirmationDialog("Are you sure you want to delete John Doe?"))
                    .thenReturn(true);

            assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
        }
    }

    @Test
    public void executeDeletePersonByName_personNotFoundThrowsException() {
        DeleteCommand deleteCommand = new DeleteCommand(new Name("Nonexistent Person"));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NAME);
    }

    @Test
    public void execute_handleDuplicateNames_returnsCommandResult() throws CommandException {
        Person person1 = new PersonBuilder().withName("John Doe").withAddress("123").build();
        Person person2 = new PersonBuilder().withName("John Doe").withAddress("456").build();
        model.addPerson(person1);
        model.addPerson(person2);
        DeleteCommand deleteCommand = new DeleteCommand(new Name("John Doe"));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DUPLICATE_NAMES,
                "John Doe", "1. John Doe\n2. John Doe\n");
        CommandResult result = deleteCommand.execute(model);

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void executeDeletePolicyFromPerson_policyNotFoundThrowsException() {
        Person person = new PersonBuilder().withName("John Doe").withPolicies("Policy 1").build();
        model.addPerson(person);
        Index personIndex = Index.fromOneBased(model.getFilteredPersonList().indexOf(person) + 1);
        Index policyIndex = Index.fromOneBased(2); // Invalid policy index
        DeleteCommand deleteCommand = new DeleteCommand(personIndex, policyIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deletePersonByIndex_success() {
        Person person = new PersonBuilder().withName("John Doe").build();
        model.addPerson(person);
        Index personIndex = Index.fromOneBased(model.getFilteredPersonList().indexOf(person) + 1);
        DeleteCommand deleteCommand = new DeleteCommand(personIndex);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, person);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(person);

        try (MockedStatic<MainWindow> mockedStatic = mockStatic(MainWindow.class)) {
            mockedStatic.when(() -> MainWindow.showConfirmationDialog("Are you sure you want to delete John Doe?"))
                    .thenReturn(true);

            assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
        }
    }

    @Test
    public void execute_deletePolicyFromPerson_success() {
        Person person = new PersonBuilder().withName("John Doe").withPolicies("Policy 1").build();
        model.addPerson(person);
        Index personIndex = Index.fromOneBased(model.getFilteredPersonList().indexOf(person) + 1);
        Index policyIndex = Index.fromOneBased(1); // Valid policy index
        DeleteCommand deleteCommand = new DeleteCommand(personIndex, policyIndex);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_POLICY_SUCCESS,
                policyIndex.getOneBased(), person.getName());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_deletePersonByIndex_deletionCancelled() throws CommandException {
        Person person = new PersonBuilder().withName("John Doe").build();
        model.addPerson(person);
        Index personIndex = Index.fromOneBased(model.getFilteredPersonList().indexOf(person) + 1);
        DeleteCommand deleteCommand = new DeleteCommand(personIndex);

        try (MockedStatic<MainWindow> mockedStatic = mockStatic(MainWindow.class)) {
            mockedStatic.when(() -> MainWindow.showConfirmationDialog("Are you sure you want to delete John Doe?"))
                    .thenReturn(false);

            CommandResult result = deleteCommand.execute(model);
            assertEquals("Deletion cancelled.", result.getFeedbackToUser());
        }
    }

    @Test
    public void equals() {
        Index index1 = Index.fromOneBased(1);
        Index index2 = Index.fromOneBased(2);
        Name name1 = new Name("John Doe");
        Name name2 = new Name("Jane Doe");

        DeleteCommand deleteCommandByIndex1 = new DeleteCommand(index1);
        DeleteCommand deleteCommandByIndex2 = new DeleteCommand(index2);
        DeleteCommand deleteCommandByName1 = new DeleteCommand(name1);
        DeleteCommand deleteCommandByName2 = new DeleteCommand(name2);
        DeleteCommand deleteCommandByIndexAndPolicy1 = new DeleteCommand(index1, index1);
        DeleteCommand deleteCommandByIndexAndPolicy2 = new DeleteCommand(index1, index2);

        // Same object -> returns true
        assertTrue(deleteCommandByIndex1.equals(deleteCommandByIndex1));

        // Same values -> returns true
        DeleteCommand deleteCommandByIndex1Copy = new DeleteCommand(index1);
        assertTrue(deleteCommandByIndex1.equals(deleteCommandByIndex1Copy));

        // Different types -> returns false
        assertFalse(deleteCommandByIndex1.equals(1));

        // Null -> returns false
        assertFalse(deleteCommandByIndex1.equals(null));

        // Different index -> returns false
        assertFalse(deleteCommandByIndex1.equals(deleteCommandByIndex2));

        // Different name -> returns false
        assertFalse(deleteCommandByName1.equals(deleteCommandByName2));

        // Different index and policy -> returns false
        assertFalse(deleteCommandByIndexAndPolicy1.equals(deleteCommandByIndexAndPolicy2));

        // Same index and policy -> returns true
        DeleteCommand deleteCommandByIndexAndPolicy1Copy = new DeleteCommand(index1, index1);
        assertTrue(deleteCommandByIndexAndPolicy1.equals(deleteCommandByIndexAndPolicy1Copy));
    }

}
