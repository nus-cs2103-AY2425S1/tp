package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Policy;
import seedu.address.testutil.PersonBuilder;

public class UndoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_undoDeleteCommand_success() throws CommandException {
        Person person = new PersonBuilder().withName("John Doe").withPolicies("Policy 1 2023-01-01 2023-12-31").build();
        model.addPerson(person);
        expectedModel.addPerson(person);

        // Get the indices for the person and the policy
        Index personIndex = Index.fromOneBased(model.getFilteredPersonList().indexOf(person) + 1);
        Index policyIndex = Index.fromOneBased(1);

        // Execute the DeleteCommand to delete the policy
        DeleteCommand deleteCommand = new DeleteCommand(personIndex, policyIndex);
        deleteCommand.execute(model);

        // Execute the UndoCommand to undo the deletion
        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = "Undo successful: Policy restored.";

        // Update the expectedModel to reflect the undo operation
        List<Policy> policies = new ArrayList<>(person.getPolicies());
        policies.add(new Policy("Policy 1", "2023-01-01", "2023-12-31", "2023-01-01 300"));
        person.setPolicies(policies);
        expectedModel.setPerson(person, person);

        // Use assertCommandSuccess to verify the command execution
        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }
}
