package seedu.address.logic.commands;


import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        // No need to mock the confirmation dialog
    }

    /*@Test
    public void execute_validPolicyIndex_success() {
        Person person = new PersonBuilder().withName("John Doe").withPolicies("Policy 1").build();
        model.addPerson(person);
        Index personIndex = Index.fromOneBased(model.getFilteredPersonList().indexOf(person) + 1);
        Index policyIndex = Index.fromOneBased(1);

        // Debug information
        System.out.println("Person: " + person);
        System.out.println("Policies: " + person.getPolicies());
        System.out.println("Person Index: " + personIndex);
        System.out.println("Policy Index: " + policyIndex);

        DeleteCommand deleteCommand = new DeleteCommand(personIndex, policyIndex);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_POLICY_SUCCESS,
        policyIndex.getOneBased(), person.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        person.removePolicy(person.getPolicies().get(policyIndex.getZeroBased()));
        expectedModel.setPerson(person, person);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }*/

    @Test
    public void execute_invalidPolicyIndex_throwsCommandException() {
        Person person = new PersonBuilder().withName("John Doe").withPolicies("Policy 1").build();
        model.addPerson(person);
        Index personIndex = Index.fromOneBased(model.getFilteredPersonList().indexOf(person) + 1);
        Index policyIndex = Index.fromOneBased(2); // Invalid policy index
        DeleteCommand deleteCommand = new DeleteCommand(personIndex, policyIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_POLICY_DISPLAYED_INDEX);
    }
}
