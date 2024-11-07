package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.commons.util.StringUtil.INDENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PUBLIC_ADDRESS_INVALID_CHAR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PUBLIC_ADDRESS_TOO_LONG;
import static seedu.address.logic.commands.CommandTestUtil.PUBLIC_ADDRESS_NOT_USED_IN_ADDRESS_BOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_BTC_MAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_ETH_MAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_SOL_MAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_SOL_SUB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_TOO_LONG;
import static seedu.address.logic.commands.SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Map;
import java.util.Set;

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
    //---------------- Tests for execute method ----------------

    //EP: valid public address with single match
    @Test
    public void execute_searchPublicAddressUnique_success() {
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_SOL_SUB);

        PublicAddressesComposition publicAddressesComposition = new PublicAddressesComposition(Map.of(Network.SOL,
            Set.of(new SolAddress(VALID_PUBLIC_ADDRESS_SOL_SUB, "Sub wallet"))));
        String expectedMessage =
            String.format(SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_FOUND,
                VALID_PUBLIC_ADDRESS_SOL_SUB + "\n"
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
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_SOL_MAIN);

        PublicAddressesComposition publicAddressesCompositionSol =
            new PublicAddressesComposition(Map.of(Network.SOL,
                Set.of(new SolAddress(VALID_PUBLIC_ADDRESS_SOL_MAIN, "Main wallet"))));

        String expectedMessage =
            String.format(SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_FOUND,
                VALID_PUBLIC_ADDRESS_SOL_MAIN + "\n"
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
            new SearchPublicAddressCommand(PUBLIC_ADDRESS_NOT_USED_IN_ADDRESS_BOOK);
        String expectedMessage =
            String.format(SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND,
                PUBLIC_ADDRESS_NOT_USED_IN_ADDRESS_BOOK);
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }


    @Test
    public void execute_searchPublicAddressTooLong_faliure() {
        assertThrows(IllegalArgumentException.class, () ->
            new SearchPublicAddressCommand(MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_TOO_LONG));
    }


    //EP: invalid input with special characters
    @Test
    public void execute_searchPublicAddressInvalidChar_failure() {
        assertThrows(IllegalArgumentException.class, () ->
            new SearchPublicAddressCommand(INVALID_PUBLIC_ADDRESS_INVALID_CHAR));
    }


    //EP: invalid input with length > 100 characters
    @Test
    public void execute_searchPublicAddressTooLong_failure() {
        assertThrows(IllegalArgumentException.class, () ->
            new SearchPublicAddressCommand(INVALID_PUBLIC_ADDRESS_TOO_LONG));
    }
    //---------------- Tests for other equivalence partitions ----------------

    //EP: empty string input
    @Test
    public void execute_searchPublicAddressEmpty_failure() {
        assertThrows(IllegalArgumentException.class, () ->
            new SearchPublicAddressCommand(""));
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
        assertThrows(IllegalArgumentException.class, () ->
            new SearchPublicAddressCommand("   "));
    }

    //EP: string with leading/trailing spaces
    @Test
    public void execute_searchPublicAddressWithSpaces_failure() {
        assertThrows(IllegalArgumentException.class, () ->
            new SearchPublicAddressCommand(" abc123 "));
    }

    //EP: border case length (exactly 100 chars)
    @Test
    public void execute_searchPublicAddressBorderLength_success() {
        String addressWith100Chars = "a".repeat(100);
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(addressWith100Chars);
        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND, addressWith100Chars);
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }

    //EP: very short length (1-2 chars)
    @Test
    public void execute_searchPublicAddressVeryShort_success() {
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand("a1");
        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND, "a1");
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
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
            new SearchPublicAddressCommand("123456");
        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND, "123456");
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }

    //EP: only letters
    @Test
    public void execute_searchPublicAddressOnlyLetters_success() {
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand("abcdef");
        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND, "abcdef");
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }

    //EP: mixed case with hex prefix
    @Test
    public void execute_searchPublicAddressHexPrefix_success() {
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand("0xAbC123DeF");
        String expectedMessage =
            String.format(MESSAGE_SEARCH_PUBLIC_ADDRESS_SUCCESS_NOT_FOUND, "0xAbC123DeF");
        assertCommandSuccess(searchPublicAddressCommand, model, expectedMessage, model);
    }
    //---------------- Tests for equals() method ----------------

    @Test
    public void equals() {
        final SearchPublicAddressCommand standardCommand =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_ETH_MAIN);
        // same values -> returns true
        SearchPublicAddressCommand commandWithSameValues =
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_ETH_MAIN);

        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different publicAddress -> returns false
        assertNotEquals(standardCommand,
            new SearchPublicAddressCommand(VALID_PUBLIC_ADDRESS_BTC_MAIN));
    }


}
