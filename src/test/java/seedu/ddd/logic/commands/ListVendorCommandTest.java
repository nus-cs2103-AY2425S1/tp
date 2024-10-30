package seedu.ddd.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.ddd.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ddd.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.ddd.testutil.contact.TypicalContacts.CARL;
import static seedu.ddd.testutil.contact.TypicalContacts.ELLE;
import static seedu.ddd.testutil.contact.TypicalContacts.FIONA;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ddd.model.Model;
import seedu.ddd.model.ModelManager;
import seedu.ddd.model.UserPrefs;
import seedu.ddd.model.contact.common.predicate.ClientTypePredicate;
import seedu.ddd.model.contact.common.predicate.NameContainsKeywordsPredicate;
import seedu.ddd.model.contact.common.predicate.VendorTypePredicate;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListVendorCommand.
 */
public class ListVendorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        expectedModel.updateFilteredContactList(new VendorTypePredicate());
        assertCommandSuccess(new ListVendorCommand(new VendorTypePredicate()),
                model, String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW,
                        expectedModel.getFilteredContactListSize()), expectedModel);
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        ListVendorCommand listVendorFirstCommand = new ListVendorCommand(firstPredicate);
        ListVendorCommand listVendorSecondCommand = new ListVendorCommand(secondPredicate);

        // same object -> return true
        assertTrue(listVendorFirstCommand.equals(listVendorFirstCommand));

        // same values -> return true
        ListVendorCommand listFirstVendorCommandCopy = new ListVendorCommand(firstPredicate);
        assertTrue(listVendorFirstCommand.equals(listFirstVendorCommandCopy));

        // different types -> return false
        assertFalse(listVendorFirstCommand.equals(1));

        // null -> return false
        assertFalse(listVendorFirstCommand.equals(null));

        // different person -> return false
        assertFalse(listVendorFirstCommand.equals(listVendorSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noContactFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        ListVendorCommand command = new ListVendorCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredContactList());
    }
    @Test
    public void execute_multipleKeywords_multipleContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        ListVendorCommand command = new ListVendorCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredContactList());
    }
    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicateOne = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        ClientTypePredicate predicateTwo = new ClientTypePredicate();
        VendorTypePredicate predicateThree = new VendorTypePredicate();
        ListVendorCommand listCommandOne = new ListVendorCommand(predicateOne);
        ListVendorCommand listCommandTwo = new ListVendorCommand(predicateTwo);
        ListVendorCommand listCommandThree = new ListVendorCommand(predicateThree);
        String expectedOne = ListVendorCommand.class.getCanonicalName() + "{predicate=" + predicateOne + "}";
        String expectedTwo = ListVendorCommand.class.getCanonicalName() + "{predicate=" + predicateTwo + "}";
        String expectedThree = ListVendorCommand.class.getCanonicalName() + "{predicate=" + predicateThree + "}";

        assertEquals(expectedOne, listCommandOne.toString());
        assertEquals(expectedTwo, listCommandTwo.toString());
        assertEquals(expectedThree, listCommandThree.toString());

    }
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
