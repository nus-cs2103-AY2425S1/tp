package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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

    @Test
    public void execute_undoPolicyDeletion_success() {
        Person person = new PersonBuilder().withName("John Doe").withPolicies("Policy 1 2023-01-01 2023-12-31").build();
        model.addPerson(person);
        Index personIndex = Index.fromOneBased(model.getFilteredPersonList().indexOf(person) + 1);
        Index policyIndex = Index.fromOneBased(1);

        DeleteCommand deleteCommand = new DeleteCommand(personIndex, policyIndex);
        try {
            deleteCommand.execute(model);
        } catch (CommandException e) {
            throw new RuntimeException(e);
        }

        UndoCommand undoCommand = new UndoCommand();
        String expectedMessage = "Undo successful: Policy restored.";

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        person.setPolicies(List.of(new Policy("Policy 1", "2023-01-01", "2023-12-31", "2023-01-01 300")));
        expectedModel.setPerson(person, person);

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }
}
