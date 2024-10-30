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
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ddd.model.Model;
import seedu.ddd.model.ModelManager;
import seedu.ddd.model.UserPrefs;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.predicate.ClientTypePredicate;
import seedu.ddd.model.contact.common.predicate.NameContainsKeywordsPredicate;
import seedu.ddd.model.contact.common.predicate.VendorTypePredicate;
import seedu.ddd.model.contact.vendor.Vendor;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListClientCommand.
 */
public class ListClientCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        expectedModel.updateFilteredContactList(new ClientTypePredicate());
        assertCommandSuccess(new ListClientCommand(new ClientTypePredicate()),
                model, String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW,
                        expectedModel.getFilteredContactListSize()), expectedModel);
    }

    @Test
    public void execute_clientsAndVendorsInList_containsOnlyClients() {
        // should contain both clients and vendors initially
        assertTrue(model.getFilteredContactList().stream().anyMatch((contact) -> contact instanceof Client));
        assertTrue(model.getFilteredContactList().stream().anyMatch((contact) -> contact instanceof Vendor));

        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 4);
        Predicate<Contact> predicate = new ClientTypePredicate();
        Command command = new ListVendorCommand(predicate);

        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // should only contain vendors after the command
        assertTrue(model.getFilteredContactList().stream().anyMatch((contact) -> contact instanceof Client));
        assertFalse(model.getFilteredContactList().stream().anyMatch((contact) -> contact instanceof Vendor));
    }

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        ListClientCommand listClientFirstCommand = new ListClientCommand(firstPredicate);
        ListClientCommand listClientSecondCommand = new ListClientCommand(secondPredicate);

        // same object -> return true
        assertTrue(listClientFirstCommand.equals(listClientFirstCommand));

        // same values -> return true
        ListClientCommand listFirstClientCommandCopy = new ListClientCommand(firstPredicate);
        assertTrue(listClientFirstCommand.equals(listFirstClientCommandCopy));

        // different types -> return false
        assertFalse(listClientFirstCommand.equals(1));

        // null -> return false
        assertFalse(listClientFirstCommand.equals(null));

        // different person -> return false
        assertFalse(listClientFirstCommand.equals(listClientSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noClientsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        ListClientCommand command = new ListClientCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredContactList());
    }
    @Test
    public void execute_multipleKeywords_multipleClientsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        ListClientCommand command = new ListClientCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredContactList());
    }
    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicateOne = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        ClientTypePredicate predicateTwo = new ClientTypePredicate();
        VendorTypePredicate predicateThree = new VendorTypePredicate();
        ListClientCommand listCommandOne = new ListClientCommand(predicateOne);
        ListClientCommand listCommandTwo = new ListClientCommand(predicateTwo);
        ListClientCommand listCommandThree = new ListClientCommand(predicateThree);
        String expectedOne = ListClientCommand.class.getCanonicalName() + "{predicate=" + predicateOne + "}";
        String expectedTwo = ListClientCommand.class.getCanonicalName() + "{predicate=" + predicateTwo + "}";
        String expectedThree = ListClientCommand.class.getCanonicalName() + "{predicate=" + predicateThree + "}";

        assertEquals(expectedOne, listCommandOne.toString());
        assertEquals(expectedTwo, listCommandTwo.toString());
        assertEquals(expectedThree, listCommandThree.toString());

    }
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
