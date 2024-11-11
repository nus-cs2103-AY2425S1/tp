package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOwners.getTypicalPawPatrol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.owner.Owner;
import seedu.address.testutil.OwnerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPawPatrol(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Owner validPerson = new OwnerBuilder().build();

        Model expectedModel = new ModelManager(model.getPawPatrol(), new UserPrefs());
        expectedModel.addOwner(validPerson);

        assertCommandSuccess(new AddOwnerCommand(validPerson), model,
                String.format(AddOwnerCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicateOwner_throwsCommandException() {
        Owner ownerInList = model.getPawPatrol().getOwnerList().get(0);
        assertCommandFailure(new AddOwnerCommand(ownerInList), model,
            AddOwnerCommand.MESSAGE_DUPLICATE_OWNER);
    }
}
