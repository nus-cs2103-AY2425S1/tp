package seedu.address.logic.commands;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.commons.util.StringUtil.INDENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PUBLIC_ADDRESS_INVALID_CHAR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PUBLIC_ADDRESS_TOO_LONG;
import static seedu.address.logic.commands.CommandTestUtil.PUBLIC_ADDRESS_NOT_USED_IN_ADDRESS_BOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_BTC_MAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_ETH_MAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_SOL_MAIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PUBLIC_ADDRESS_SOL_SUB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_INVALID_CHAR;
import static seedu.address.logic.commands.SearchPublicAddressCommand.MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_TOO_LONG;
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
    public void execute_searchPublicAddressInvalidChar_failure() {
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(INVALID_PUBLIC_ADDRESS_INVALID_CHAR);
        String expectedMessage = MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_INVALID_CHAR;
        assertCommandFailure(searchPublicAddressCommand, model, expectedMessage);
    }

    @Test
    public void execute_searchPublicAddressTooLong_faliure() {
        SearchPublicAddressCommand searchPublicAddressCommand =
            new SearchPublicAddressCommand(INVALID_PUBLIC_ADDRESS_TOO_LONG);
        String expectedMessage = MESSAGE_SEARCH_PUBLIC_ADDRESS_FAILURE_TOO_LONG;
        assertCommandFailure(searchPublicAddressCommand, model, expectedMessage);
    }


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
