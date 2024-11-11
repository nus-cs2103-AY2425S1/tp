package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.EventIdsContainsIdsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TempPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AddressContainsKeywordsPredicate firstAddressPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Street"));
        AddressContainsKeywordsPredicate secondAddressPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Serangoon"));

        SearchCommand firstSearchAddressCommand = new SearchCommand(firstAddressPredicate);
        SearchCommand secondSearchAddressCommand = new SearchCommand(secondAddressPredicate);

        // same object -> returns true
        assertTrue(firstSearchAddressCommand.equals(firstSearchAddressCommand));

        // same values -> returns true
        SearchCommand firstSearchAddressCommandCopy = new SearchCommand(firstAddressPredicate);
        assertTrue(firstSearchAddressCommand.equals(firstSearchAddressCommandCopy));

        // different types -> returns false
        assertFalse(firstSearchAddressCommand.equals(1));

        // null -> returns false
        assertFalse(firstSearchAddressCommand.equals(null));

        // different person -> returns false
        assertFalse(firstSearchAddressCommand.equals(secondSearchAddressCommand));

        EmailContainsKeywordsPredicate firstEmailPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("example.com"));
        EmailContainsKeywordsPredicate secondEmailPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("gmail.com"));

        SearchCommand firstSearchEmailCommand = new SearchCommand(firstEmailPredicate);
        SearchCommand secondSearchEmailCommand = new SearchCommand(secondEmailPredicate);

        // same object -> returns true
        assertTrue(firstSearchEmailCommand.equals(firstSearchEmailCommand));

        // same values -> returns true
        SearchCommand firstSearchEmailCommandCopy = new SearchCommand(firstEmailPredicate);
        assertTrue(firstSearchEmailCommand.equals(firstSearchEmailCommandCopy));

        // different types -> returns false
        assertFalse(firstSearchEmailCommand.equals(1));

        // null -> returns false
        assertFalse(firstSearchEmailCommand.equals(null));

        // different person -> returns false
        assertFalse(firstSearchEmailCommand.equals(secondSearchEmailCommand));

        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("John"));
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("Doe"));

        SearchCommand firstSearchNameCommand = new SearchCommand(firstNamePredicate);
        SearchCommand secondSearchNameCommand = new SearchCommand(secondNamePredicate);

        // same object -> returns true
        assertTrue(firstSearchNameCommand.equals(firstSearchNameCommand));

        // same values -> returns true
        SearchCommand firstSearchNameCommandCopy = new SearchCommand(firstNamePredicate);
        assertTrue(firstSearchNameCommand.equals(firstSearchNameCommandCopy));

        // different types -> returns false
        assertFalse(firstSearchNameCommand.equals(1));

        // null -> returns false
        assertFalse(firstSearchNameCommand.equals(null));

        // different person -> returns false
        assertFalse(firstSearchNameCommand.equals(secondSearchNameCommand));

        PhoneContainsKeywordsPredicate firstPhonePredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("12345678"));
        PhoneContainsKeywordsPredicate secondPhonePredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("87654321"));

        SearchCommand firstSearchPhoneCommand = new SearchCommand(firstPhonePredicate);
        SearchCommand secondSearchPhoneCommand = new SearchCommand(secondPhonePredicate);

        // same object -> returns true
        assertTrue(firstSearchPhoneCommand.equals(firstSearchPhoneCommand));

        // same values -> returns true
        SearchCommand firstSearchPhoneCommandCopy = new SearchCommand(firstPhonePredicate);
        assertTrue(firstSearchPhoneCommand.equals(firstSearchPhoneCommandCopy));

        // different types -> returns false
        assertFalse(firstSearchPhoneCommand.equals(1));

        // null -> returns false
        assertFalse(firstSearchPhoneCommand.equals(null));

        // different person -> returns false
        assertFalse(firstSearchPhoneCommand.equals(secondSearchPhoneCommand));

        TagContainsKeywordsPredicate firstTagPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("friend"));
        TagContainsKeywordsPredicate secondTagPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("member"));

        SearchCommand firstSearchTagCommand = new SearchCommand(firstTagPredicate);
        SearchCommand secondSearchTagCommand = new SearchCommand(secondTagPredicate);

        // same object -> returns true
        assertTrue(firstSearchTagCommand.equals(firstSearchTagCommand));

        // same values -> returns true
        SearchCommand firstSearchTagCommandCopy = new SearchCommand(firstTagPredicate);
        assertTrue(firstSearchTagCommand.equals(firstSearchTagCommandCopy));

        // different types -> returns false
        assertFalse(firstSearchTagCommand.equals(1));

        // null -> returns false
        assertFalse(firstSearchTagCommand.equals(null));

        // different person -> returns false
        assertFalse(firstSearchTagCommand.equals(secondSearchTagCommand));
    }

    @Test
    public void execute_zeroKeywordsForAddressField_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate(" ");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroKeywordsForEmailField_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        EmailContainsKeywordsPredicate predicate = prepareEmailPredicate(" ");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroKeywordsForNameField_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate(" ");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroKeywordsForPhoneField_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PhoneContainsKeywordsPredicate predicate = preparePhonePredicate(" ");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroKeywordsForTagField_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = prepareTagPredicate(" ");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_zeroKeywordsForEventField_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TempPredicate tempPredicate = new TempPredicate(Arrays.asList());
        SearchCommand command = new SearchCommand(tempPredicate);
        EventIdsContainsIdsPredicate predicate = prepareEventIdsContainsIdsPredicate();
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywordsForAddressField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate("street");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywordForEmailField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        EmailContainsKeywordsPredicate predicate = prepareEmailPredicate("example.com");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywordForNameField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Meier");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywordForPhoneField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PhoneContainsKeywordsPredicate predicate = preparePhonePredicate("948");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywordsForTagField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        TagContainsKeywordsPredicate predicate = prepareTagPredicate("friends");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywordForAddressField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate("nonexistent");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywordForEmailField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        EmailContainsKeywordsPredicate predicate = prepareEmailPredicate("notfound@example.com");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywordForNameField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Unknown");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywordForPhoneField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PhoneContainsKeywordsPredicate predicate = preparePhonePredicate("00000000");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeywordsForTagField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = prepareTagPredicate("UnknownTag");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsForAddressField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 6);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate("street ave");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsForEmailField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        EmailContainsKeywordsPredicate predicate = prepareEmailPredicate("johnd heinz cornelia");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsForNameField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Meier Pauline");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsForPhoneField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        PhoneContainsKeywordsPredicate predicate = preparePhonePredicate("948 987");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsForTagField_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        TagContainsKeywordsPredicate predicate = prepareTagPredicate("friend member");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsForAddressField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        AddressContainsKeywordsPredicate predicate = prepareAddressPredicate("unknown gibberish");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsForEmailField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        EmailContainsKeywordsPredicate predicate = prepareEmailPredicate("gmail yahoo");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsForNameField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = prepareNamePredicate("Hoon Ida");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsForPhoneField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PhoneContainsKeywordsPredicate predicate = preparePhonePredicate("00000000 11111111");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordsForTagField_noPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagContainsKeywordsPredicate predicate = prepareTagPredicate("UnknownTag");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethodWithAddressPredicate() {
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Arrays.asList("keyword"));
        SearchCommand searchCommand = new SearchCommand(predicate);
        String expected = SearchCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, searchCommand.toString());
    }

    @Test
    public void toStringMethodWithNamePredicate() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        SearchCommand searchCommand = new SearchCommand(predicate);
        String expected = SearchCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, searchCommand.toString());
    }

    @Test
    public void toStringMethodWithEmailPredicate() {
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Arrays.asList("keyword"));
        SearchCommand searchCommand = new SearchCommand(predicate);
        String expected = SearchCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, searchCommand.toString());
    }

    @Test
    public void toStringMethodWithPhonePredicate() {
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("keyword"));
        SearchCommand searchCommand = new SearchCommand(predicate);
        String expected = SearchCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, searchCommand.toString());
    }

    @Test
    public void toStringMethodWithTagPredicate() {
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList("keyword"));
        SearchCommand searchCommand = new SearchCommand(predicate);
        String expected = SearchCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, searchCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code AddressContainsKeywordsPredicate}.
     */
    private AddressContainsKeywordsPredicate prepareAddressPredicate(String userInput) {
        return new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code EmailContainsKeywordsPredicate}.
     */
    private EmailContainsKeywordsPredicate prepareEmailPredicate(String userInput) {
        return new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code PhoneContainsKeywordsPredicate}.
     */
    private PhoneContainsKeywordsPredicate preparePhonePredicate(String userInput) {
        return new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TagContainsKeywordsPredicate}.
     */
    private TagContainsKeywordsPredicate prepareTagPredicate(String userInput) {
        return new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code TagContainsKeywordsPredicate}.
     */
    private EventIdsContainsIdsPredicate prepareEventIdsContainsIdsPredicate(Integer... userInput) {
        return new EventIdsContainsIdsPredicate(Arrays.stream(userInput).toList());
    }
}

