package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ListingAddCommand.MESSAGE_ADDED_LISTING_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicateListingException;
import seedu.address.model.tag.PropertyTagType;
import seedu.address.testutil.PersonBuilder;

public class ListingAddCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validAddListing() {
        final Listing listing = new Listing(PropertyTagType.HDB, new Address("123 NUS Streetsss"));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person firstPerson = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person expectedEditedPerson = new PersonBuilder(firstPerson).build();
        expectedEditedPerson.getListings().add(listing);

        String expectedMessage = String.format(MESSAGE_ADDED_LISTING_SUCCESS, Messages.format(expectedEditedPerson));

        assertCommandSuccess(new ListingAddCommand(listing, INDEX_FIRST_PERSON), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateListing_throwsCommandException() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Listing existingListing = firstPerson.getListings().getListings().get(0);

        assertCommandFailure(new ListingAddCommand(existingListing, INDEX_FIRST_PERSON),
                model, (new DuplicateListingException()).getMessage());
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        final Listing listing = new Listing(PropertyTagType.HDB, new Address("123 NUS Street"));

        int personListSize = model.getFilteredPersonList().size();
        Index outOfBoundIndex = Index.fromZeroBased(
                personListSize + 1);

        ListingAddCommand command = new ListingAddCommand(listing, outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

}
