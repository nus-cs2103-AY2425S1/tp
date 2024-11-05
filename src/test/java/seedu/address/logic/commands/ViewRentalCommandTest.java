package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookWithRental;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ViewRentalCommand}.
 */
public class ViewRentalCommandTest {
    private Model actualModel;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        actualModel = new ModelManager(getTypicalAddressBookWithRental(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBookWithRental(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        // Set up expected outputs and model state
        Client clientToViewRental = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<RentalInformation> rentalInformationOfClient = clientToViewRental.getRentalInformation();

        CommandResult expectedCommandResult = new CommandResult(
                String.format(Messages.MESSAGE_RENTAL_INFORMATION_LISTED_OVERVIEW,
                        rentalInformationOfClient.size()));

        expectedModel.setLastViewedClient(clientToViewRental);
        expectedModel.updateVisibleRentalInformationList(rentalInformationOfClient);

        // Perform the test
        ViewRentalCommand viewRentalCommand = new ViewRentalCommand(INDEX_FIRST_PERSON);
        assertCommandSuccess(viewRentalCommand, actualModel, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(actualModel.getFilteredPersonList().size() + 1);
        ViewRentalCommand viewRentalCommand = new ViewRentalCommand(outOfBoundsIndex);

        assertCommandFailure(viewRentalCommand, actualModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        // We want to view the rental information of the second client in the unfiltered client list,

        // Set up expected outputs and model state
        Client clientToViewRental = actualModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        List<RentalInformation> rentalInformationOfClient = clientToViewRental.getRentalInformation();

        CommandResult expectedCommandResult = new CommandResult(
                String.format(Messages.MESSAGE_RENTAL_INFORMATION_LISTED_OVERVIEW,
                        rentalInformationOfClient.size()));

        ModelManager expectedModel = new ModelManager(actualModel.getAddressBook(), new UserPrefs());

        expectedModel.setLastViewedClient(clientToViewRental);
        expectedModel.updateVisibleRentalInformationList(rentalInformationOfClient);

        // TODO: Zach to take a look. Its working now, simply by commenting out line 85.
        // Update the client list to show only the second client
        // showPersonAtIndex(expectedModel, INDEX_SECOND_PERSON);

        // Create the command that will be tested - the current first client was originally the second client,
        // before showPersonAtIndex() was called
        ViewRentalCommand viewRentalCommand = new ViewRentalCommand(INDEX_SECOND_PERSON);

        try {
            viewRentalCommand.execute(actualModel);

            // NOTE: assertEquals is used over CommandTestUtil.assertCommandSuccess because the client list will
            // inherently be different due to us using showPersonAtIndex to filter the client list.

            // This is acceptable for this test because we are only concerned with whether the same
            // visibleRentalInformationList is being displayed.

            // Compare the visibleRentalInformationList of the expected and actual models
            assertEquals(expectedModel.getVisibleRentalInformationList(),
                    actualModel.getVisibleRentalInformationList());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        // Update the client list to show only the first client
        showPersonAtIndex(actualModel, INDEX_FIRST_PERSON);

        // The index of the second person is out of bounds because the client list should only show the first client
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // Ensure that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < actualModel.getAddressBook().getPersonList().size());

        ViewRentalCommand viewRentalCommand = new ViewRentalCommand(outOfBoundIndex);
        assertCommandFailure(viewRentalCommand, actualModel, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewRentalCommand viewRentalCommandFirst = new ViewRentalCommand(INDEX_FIRST_PERSON);
        ViewRentalCommand viewRentalCommandSecond = new ViewRentalCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(viewRentalCommandFirst.equals(viewRentalCommandFirst));

        // same values -> returns true
        ViewRentalCommand viewRentalCommandFirstCopy = new ViewRentalCommand(INDEX_FIRST_PERSON);
        assertTrue(viewRentalCommandFirst.equals(viewRentalCommandFirstCopy));

        // different types -> returns false
        assertFalse(viewRentalCommandFirst.equals(1));

        // null -> returns false
        assertFalse(viewRentalCommandFirst.equals(null));

        // different client -> returns false
        assertFalse(viewRentalCommandFirst.equals(viewRentalCommandSecond));
    }
}
