package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

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

}
