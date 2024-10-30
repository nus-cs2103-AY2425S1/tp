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
 * Contains integration tests (interaction with the Model) and unit tests for ListContactCommand.
 */
public class ListContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListContactCommand(Model.PREDICATE_SHOW_ALL_CONTACTS),
                model, String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW,
                        expectedModel.getFilteredContactListSize()), expectedModel);
    }

    @Test
    public void executeFilterListByClient() {
        expectedModel.updateFilteredContactList(new ClientTypePredicate());
        assertCommandSuccess(new ListContactCommand(new ClientTypePredicate()),
                model, String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW,
                        expectedModel.getFilteredContactListSize()), expectedModel);
    }

    @Test
    public void executeFilterListByVendor() {
        expectedModel.updateFilteredContactList(new VendorTypePredicate());
        assertCommandSuccess(new ListContactCommand(new VendorTypePredicate()),
                model, String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW,
                        expectedModel.getFilteredContactListSize()), expectedModel);
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        ListContactCommand listContactFirstCommand = new ListContactCommand(firstPredicate);
        ListContactCommand listContactSecondCommand = new ListContactCommand(secondPredicate);

        // same object -> return true
        assertTrue(listContactFirstCommand.equals(listContactFirstCommand));

        // same values -> return true
        ListContactCommand listFirstContactCommandCopy = new ListContactCommand(firstPredicate);
        assertTrue(listContactFirstCommand.equals(listFirstContactCommandCopy));

        // different types -> return false
        assertFalse(listContactFirstCommand.equals(1));

        // null -> return false
        assertFalse(listContactFirstCommand.equals(null));

        // different person -> return false
        assertFalse(listContactFirstCommand.equals(listContactSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noContactFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        ListContactCommand command = new ListContactCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredContactList());
    }
    @Test
    public void execute_multipleKeywords_multipleContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        ListContactCommand command = new ListContactCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredContactList());
    }
    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicateOne = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        ClientTypePredicate predicateTwo = new ClientTypePredicate();
        VendorTypePredicate predicateThree = new VendorTypePredicate();
        ListContactCommand listCommandOne = new ListContactCommand(predicateOne);
        ListContactCommand listCommandTwo = new ListContactCommand(predicateTwo);
        ListContactCommand listCommandThree = new ListContactCommand(predicateThree);
        String expectedOne = ListContactCommand.class.getCanonicalName() + "{predicate=" + predicateOne + "}";
        String expectedTwo = ListContactCommand.class.getCanonicalName() + "{predicate=" + predicateTwo + "}";
        String expectedThree = ListContactCommand.class.getCanonicalName() + "{predicate=" + predicateThree + "}";

        assertEquals(expectedOne, listCommandOne.toString());
        assertEquals(expectedTwo, listCommandTwo.toString());
        assertEquals(expectedThree, listCommandThree.toString());

    }
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
