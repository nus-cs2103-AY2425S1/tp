package seedu.address.logic.commands.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PROPERTIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.CARL;
import static seedu.address.testutil.property.TypicalProperties.ELLE;
import static seedu.address.testutil.property.TypicalProperties.FIONA;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.property.AddressContainsKeywordsPredicate;
import seedu.address.model.property.LandlordNameContainsKeywordsPredicate;

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
        LandlordNameContainsKeywordsPredicate firstNamePredicate =
                new LandlordNameContainsKeywordsPredicate(Collections.singletonList("first"));
        LandlordNameContainsKeywordsPredicate secondNamePredicate =
                new LandlordNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findAddressFirstCommand = new FindCommand(firstAddressPredicate);
        FindCommand findAddressSecondCommand = new FindCommand(secondAddressPredicate);
        FindCommand findNameFirstCommand = new FindCommand(firstNamePredicate);
        FindCommand findNameSecondCommand = new FindCommand(secondNamePredicate);

        // same object -> returns true
        assertTrue(findAddressFirstCommand.equals(findAddressFirstCommand));

        // same values -> returns true
        FindCommand findAddressFirstCommandCopy = new FindCommand(firstAddressPredicate);
        assertTrue(findAddressFirstCommand.equals(findAddressFirstCommandCopy));

        // different types -> returns false
        assertFalse(findAddressFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findAddressFirstCommand.equals(null));

        // different address -> returns false
        assertFalse(findAddressFirstCommand.equals(findAddressSecondCommand));

        // same object -> returns true
        assertTrue(findAddressFirstCommand.equals(findAddressFirstCommand));

        // same object -> returns true
        assertTrue(findAddressFirstCommand.equals(findAddressFirstCommand));

        // same values -> returns true
        FindCommand findNameFirstCommandCopy = new FindCommand(firstNamePredicate);
        assertTrue(findNameFirstCommand.equals(findNameFirstCommandCopy));

        // different types -> returns false
        assertFalse(findNameFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findNameFirstCommand.equals(null));

        // different name -> returns false
        assertFalse(findNameFirstCommand.equals(findNameSecondCommand));

        // same object -> returns true
        assertTrue(findNameFirstCommand.equals(findNameFirstCommand));

        // different object -> returns false
        assertFalse(findNameFirstCommand.equals(findAddressFirstCommand));
    }

    @Test
    public void execute_zeroKeywordsAddress_noPropertiesFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED_OVERVIEW, 0);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPropertyList());
    }

    @Test
    public void execute_multipleKeywordsAddress_multiplePropertiesFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED_OVERVIEW, 3);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate("Aljunied Marsiling Shibuya");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPropertyList());
    }

    @Test
    public void toStringMethodForAddress() {
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{addressPredicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    @Test
    public void execute_zeroKeywordsLandlordName_noPropertiesFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED_OVERVIEW, 0);
        LandlordNameContainsKeywordsPredicate predicate = prepareLandlordNamePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPropertyList());
    }

    @Test
    public void execute_multipleKeywordsLandlordName_multiplePropertiesFound() {
        String expectedMessage = String.format(MESSAGE_PROPERTIES_LISTED_OVERVIEW, 3);
        LandlordNameContainsKeywordsPredicate predicate = prepareLandlordNamePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPropertyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPropertyList());
    }

    @Test
    public void toStringMethodForLandlordName() {
        LandlordNameContainsKeywordsPredicate predicate =
                new LandlordNameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{landlordPredicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate prepareAddressPredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code LandlordNameContainsKeywordsPredicate}.
     */
    private LandlordNameContainsKeywordsPredicate prepareLandlordNamePredicate(String userInput) {
        return new LandlordNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
