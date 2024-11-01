package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_LISTINGS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.listing.ListingContainsKeywordsPredicate;
import seedu.address.testutil.TypicalListings;

public class FindListingCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), TypicalListings.getTypicalListings());
    private Model expectedModel =
            new ModelManager(new AddressBook(), new UserPrefs(), TypicalListings.getTypicalListings());
    @Test
    public void equals() {
        ListingContainsKeywordsPredicate firstPredicate =
                new ListingContainsKeywordsPredicate(Collections.singletonList("Pasir Ris Condo"));
        ListingContainsKeywordsPredicate secondPredicate =
                new ListingContainsKeywordsPredicate(Collections.singletonList("Tampines HDB"));

        FindListingCommand findListingFirstCommand = new FindListingCommand(firstPredicate);
        FindListingCommand findListingSecondCommand = new FindListingCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findListingFirstCommand.equals(findListingFirstCommand));

        // same values -> returns true
        FindListingCommand findListingFirstCommandCopy = new FindListingCommand(firstPredicate);
        assertTrue(findListingFirstCommand.equals(findListingFirstCommandCopy));

        // different types -> returns false
        assertFalse(findListingFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findListingFirstCommand.equals(null));

        // different predicate -> returns false
        assertFalse(findListingFirstCommand.equals(findListingSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noListingFound() {
        String expectedMessage = String.format(MESSAGE_LISTINGS_LISTED_OVERVIEW, 0);
        ListingContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindListingCommand command = new FindListingCommand(predicate);
        expectedModel.updateFilteredListingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredListingList());
    }

    @Test
    public void execute_multipleKeywords_multipleListingsFound() {
        String expectedMessage = String.format(MESSAGE_LISTINGS_LISTED_OVERVIEW, 2);
        ListingContainsKeywordsPredicate predicate =
                preparePredicate("Kent Ridge Buona Vista");
        FindListingCommand command = new FindListingCommand(predicate);
        expectedModel.updateFilteredListingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TypicalListings.KENT_RIDGE, TypicalListings.BUONA_VISTA),
                model.getFilteredListingList());
    }

    @Test
    public void toStringMethod() {
        ListingContainsKeywordsPredicate predicate = new ListingContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindListingCommand findListingCommand = new FindListingCommand(predicate);
        String expected = FindListingCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findListingCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code ListingContainsKeywordsPredicate}.
     */
    private ListingContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ListingContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
