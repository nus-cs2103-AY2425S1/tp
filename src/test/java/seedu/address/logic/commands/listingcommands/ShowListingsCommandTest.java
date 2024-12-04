package seedu.address.logic.commands.listingcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showListingAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SEVENTH_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_LISTING;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_LISTING;
import static seedu.address.testutil.TypicalListings.getTypicalListings;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ShowListingsCommand.
 */
public class ShowListingsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalListings());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getListings());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ShowListingsCommand(), model, ShowListingsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showListingAtIndex(model, INDEX_FIRST_LISTING);
        assertCommandSuccess(new ShowListingsCommand(), model, ShowListingsCommand.MESSAGE_SUCCESS, expectedModel);

        showListingAtIndex(model, INDEX_SECOND_LISTING);
        assertCommandSuccess(new ShowListingsCommand(), model, ShowListingsCommand.MESSAGE_SUCCESS, expectedModel);

        showListingAtIndex(model, INDEX_THIRD_LISTING);
        assertCommandSuccess(new ShowListingsCommand(), model, ShowListingsCommand.MESSAGE_SUCCESS, expectedModel);

        showListingAtIndex(model, INDEX_FOURTH_LISTING);
        assertCommandSuccess(new ShowListingsCommand(), model, ShowListingsCommand.MESSAGE_SUCCESS, expectedModel);

        showListingAtIndex(model, INDEX_FIFTH_LISTING);
        assertCommandSuccess(new ShowListingsCommand(), model, ShowListingsCommand.MESSAGE_SUCCESS, expectedModel);

        showListingAtIndex(model, INDEX_SIXTH_LISTING);
        assertCommandSuccess(new ShowListingsCommand(), model, ShowListingsCommand.MESSAGE_SUCCESS, expectedModel);

        showListingAtIndex(model, INDEX_SEVENTH_LISTING);
        assertCommandSuccess(new ShowListingsCommand(), model, ShowListingsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_emptyListings_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());
        assertCommandFailure(new ShowListingsCommand(), model, ShowListingsCommand.MESSAGE_NO_LISTINGS_IN_LIST);
    }
}
