package seedu.address.logic.commands.property;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.AddressContainsKeywordsPredicate;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.LandlordNameContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PROPERTIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
            getTypicalPropertyList());
    private Model expectedModel = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
            getTypicalPropertyList());

    @Test
    public void equals() {
        AddressContainsKeywordsPredicate firstAddressPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("first"));
        AddressContainsKeywordsPredicate secondAddressPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("second"));
        LandlordNameContainsKeywordsPredicate firstLandlordNamePredicate =
                new LandlordNameContainsKeywordsPredicate(Collections.singletonList("first"));
        LandlordNameContainsKeywordsPredicate secondLandlordNamePredicate =
                new LandlordNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findAddressFirstCommand = new FindCommand(firstAddressPredicate);
        FindCommand findAddressSecondCommand = new FindCommand(secondAddressPredicate);
        FindCommand findLandlordNameFirstCommand = new FindCommand(firstLandlordNamePredicate);
        FindCommand findLandlordNameSecondCommand = new FindCommand(secondLandlordNamePredicate);

        // same object -> returns true
        assertTrue(findAddressFirstCommand.equals(findAddressFirstCommand));

        // same values -> returns true
        FindCommand findAddressFirstCommandCopy = new FindCommand(firstAddressPredicate);
        assertTrue(findAddressFirstCommand.equals(findAddressFirstCommandCopy));

        // different types -> returns false
        assertFalse(findAddressFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findAddressFirstCommand.equals(null));

        // different buyer -> returns false
        assertFalse(findAddressFirstCommand.equals(findAddressSecondCommand));

        // same object -> returns true
        assertTrue(findAddressFirstCommand.equals(findAddressFirstCommand));

        // same values -> returns true
        FindCommand findLandlordNameFirstCommandCopy = new FindCommand(firstLandlordNamePredicate);
        assertTrue(findLandlordNameFirstCommand.equals(findLandlordNameFirstCommandCopy));

        // different types -> returns false
        assertFalse(findLandlordNameFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findLandlordNameFirstCommand.equals(null));

        // different buyer -> returns false
        assertFalse(findLandlordNameFirstCommand.equals(findLandlordNameSecondCommand));
    }

    @Test
    public void execute_zeroKeywordsForAddress_noBuyerFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED_OVERVIEW, 0);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPropertyList());
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate prepareAddressPredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
