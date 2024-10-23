package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ListingDeleteCommand.MESSAGE_DELETE_LISTING_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueListingList;
import seedu.address.model.person.exceptions.ListingNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class ListingDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validDeleteListing() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Person firstPerson = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UniqueListingList newListings = new UniqueListingList(firstPerson.getListings());
        newListings.remove(0);
        Person expectedNewPerson = new PersonBuilder(firstPerson).withListings(newListings).build();
        expectedModel.setPerson(firstPerson, expectedNewPerson);

        String expectedMessage = String.format(MESSAGE_DELETE_LISTING_SUCCESS, Messages.format(expectedNewPerson));

        assertCommandSuccess(new ListingDeleteCommand(INDEX_FIRST_PERSON, Index.fromZeroBased(0)),
                model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        int personListSize = model.getFilteredPersonList().size();
        Index outOfBoundIndex = Index.fromZeroBased(
                personListSize + 1);

        ListingDeleteCommand command = new ListingDeleteCommand(
                outOfBoundIndex, Index.fromZeroBased(0));

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidListingIndex_throwsCommandException() {
        Index outOfBoundListing = Index.fromZeroBased(
                999);

        ListingDeleteCommand command = new ListingDeleteCommand(INDEX_FIRST_PERSON, outOfBoundListing);

        assertCommandFailure(command, model, (
                new ListingNotFoundException(outOfBoundListing.getZeroBased())).getMessage());
    }


}
