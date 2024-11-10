package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.util.StringUtil.INDENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_FOUND;
import static seedu.address.model.addresses.PublicAddress.MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_INVALID_CHAR;
import static seedu.address.model.addresses.PublicAddress.MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_BTC_NOT_IN_ADDRESS_BOOK_BTC_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ETH_MAIN_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ONLY_CHARS_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_ONLY_NUMBERS_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_SOL_MAIN_STRING;
import static seedu.address.testutil.TypicalPublicAddresses.VALID_PUBLIC_ADDRESS_SOL_SUB_STRING;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.addresses.Network;
import seedu.address.model.addresses.PublicAddressesComposition;
import seedu.address.model.addresses.SolAddress;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SearchPublicAddressCommand.
 */
public class SearchPublicAddressCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        model.setAddressBook(getTypicalAddressBook());
    }
    //---------------- Tests for execute method ----------------

    //    EP: valid public address with single match
    @Test
    public void execute_searchPublicAddressUnique_success() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_SOL_SUB_STRING);

        PublicAddressesComposition publicAddressesComposition = new PublicAddressesComposition(Map.of(Network.SOL,
            Set.of(new SolAddress(VALID_PUBLIC_ADDRESS_SOL_SUB_STRING, "sub"))));
        String expectedMessage =
            String.format(SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_FOUND,
                VALID_PUBLIC_ADDRESS_SOL_SUB_STRING.toLowerCase() + "\n"
                    + secondPerson.getName() + "\n" + INDENT
                    + publicAddressesComposition.toStringIndented());


        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }

    //EP: valid public address with multiple matches
    @Test
    public void execute_searchPublicAddressMultiplePersons_success() {
        //Initialise third person with SOL PublicAddress
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());

        //Initialise second person with SOL PublicAddress
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_SOL_MAIN_STRING);

        PublicAddressesComposition publicAddressesCompositionSol =
            new PublicAddressesComposition(Map.of(Network.SOL,
                Set.of(new SolAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN_STRING, "main"))));

        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_FOUND,
                VALID_PUBLIC_ADDRESS_SOL_MAIN_STRING.toLowerCase() + "\n"
                    + secondPerson.getName() + "\n" + INDENT
                    + publicAddressesCompositionSol.toStringIndented() + "\n"
                    + thirdPerson.getName() + "\n" + INDENT
                    + publicAddressesCompositionSol.toStringIndented());

        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }

    //EP: valid public address with no matches
    @Test
    public void execute_searchPublicAddressNoMatch_success() {
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_NOT_IN_ADDRESS_BOOK_BTC_STRING);
        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND,
                VALID_PUBLIC_ADDRESS_BTC_NOT_IN_ADDRESS_BOOK_BTC_STRING);
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }


    //EP: null input
    @Test
    public void execute_searchPublicAddressNull_failure() {
        assertThrows(NullPointerException.class, () ->
            new SearchPublicAddressCommand(null));
    }

    //EP: string with only spaces
    @Test
    public void execute_searchPublicAddressOnlySpaces_failure() {
        SearchPublicAddressCommand searchPublicAddressCommand = new SearchPublicAddressCommand("   ");
        String expectedMessage = MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_INVALID_CHAR;
        assertCommandFailure(searchPublicAddressCommand, model, expectedMessage);
    }

    //EP: string with leading/trailing spaces
    @Test
    public void execute_searchPublicAddressWithSpaces_failure() {
        SearchPublicAddressCommand searchPublicAddressCommand = new SearchPublicAddressCommand(" abc123 ");
        String expectedMessage = MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_INVALID_CHAR;
        assertCommandFailure(searchPublicAddressCommand, model, expectedMessage);
    }


    //EP: typical crypto address length
    @Test
    public void execute_searchPublicAddressTypicalLength_success() {
        // Typical ETH address length (42 chars)
        String typicalEthAddress = "0x" + "a".repeat(40);
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(typicalEthAddress);
        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND, typicalEthAddress);
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }

    //EP: only numbers
    @Test
    public void execute_searchPublicAddressOnlyNumbers_success() {
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_ONLY_NUMBERS_STRING);
        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND, VALID_PUBLIC_ADDRESS_ONLY_NUMBERS_STRING);
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }

    //EP: only letters
    @Test
    public void execute_searchPublicAddressOnlyLetters_success() {
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_ONLY_CHARS_STRING);
        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND, VALID_PUBLIC_ADDRESS_ONLY_CHARS_STRING);
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }

    //EP: mixed case with hex prefix
    @Test
    public void execute_searchPublicAddressHexPrefix_success() {
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_NOT_IN_ADDRESS_BOOK_BTC_STRING);
        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND,
                VALID_PUBLIC_ADDRESS_BTC_NOT_IN_ADDRESS_BOOK_BTC_STRING);
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }
    //---------------- Tests for equals() method ----------------

    @Test
    public void equals() {
        final SearchPublicAddressCommand standardCommand =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_ETH_MAIN_STRING);
        // same values -> returns true
        SearchPublicAddressCommand commandWithSameValues =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_ETH_MAIN_STRING);

        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different publicAddress -> returns false
        assertNotEquals(standardCommand,
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN_STRING));
    }


}
