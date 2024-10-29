package seedu.address.logic.commands.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

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
}
