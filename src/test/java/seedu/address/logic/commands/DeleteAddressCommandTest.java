package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addresses.Network;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteAddressCommand}.
 */
public class DeleteAddressCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_validIndexValidNetwork_success() throws Exception {
        Person personToDeleteAddress = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON, Network.BTC);

        String expectedMessage = String.format(DeleteAddressCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDeleteAddress));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person updatedPerson = new PersonBuilder(personToDeleteAddress).build();
        updatedPerson.setPublicAddressesByNetwork(Network.BTC, new HashSet<>());
        expectedModel.setPerson(personToDeleteAddress, updatedPerson);

        assertCommandSuccess(deleteAddressCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteAddressCommand deleteAddressCommand = new DeleteAddressCommand(outOfBoundIndex, Network.BTC);

        assertCommandFailure(deleteAddressCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        DeleteAddressCommand deleteFirstCommand = new DeleteAddressCommand(INDEX_FIRST_PERSON, Network.BTC);
        DeleteAddressCommand deleteSecondCommand = new DeleteAddressCommand(Index.fromOneBased(2), Network.BTC);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteAddressCommand deleteFirstCommandCopy = new DeleteAddressCommand(INDEX_FIRST_PERSON, Network.BTC);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }


}
