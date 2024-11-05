package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_STATUS_BLACKLISTED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.BlacklistedPersons.getBlacklistedAddressBook;
import static seedu.address.testutil.MixedPersons.getMixedAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.ArchivedAddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ArgumentPredicateToFail;
import seedu.address.model.person.ClientStatus;

public class WhitelistListCommandTest {
    private static final ArgumentPredicateToFail PREDICATE_NOT_BLACKLISTED = new ArgumentPredicateToFail(
        Map.of("client status", new ClientStatus(VALID_CLIENT_STATUS_BLACKLISTED), "tags", new HashSet<>())
    );

    private Model normalModel;
    private Model blacklistedModel;
    private Model mixedModel;

    @BeforeEach
    public void setUp() {
        normalModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        blacklistedModel = new ModelManager(getBlacklistedAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        mixedModel = new ModelManager(getMixedAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        normalModel.setArchivedListMode(false);
    }

    @Test
    public void list_allWhitelistedPersons_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        expectedModel.updateFilteredPersonList(PREDICATE_NOT_BLACKLISTED);

        int numPersons = normalModel.getFilteredPersonList().size();
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, numPersons);

        assertEquals(numPersons, expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(new WhitelistListCommand(), normalModel, expectedMessage, expectedModel);
    }

    @Test
    public void list_noWhitelistedPersons_success() {
        Model expectedModel = new ModelManager(getBlacklistedAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        expectedModel.updateFilteredPersonList(PREDICATE_NOT_BLACKLISTED);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        assertEquals(0, expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(new WhitelistListCommand(), blacklistedModel, expectedMessage, expectedModel);
    }

    @Test
    public void list_someWhitelistedPersons_success() {
        Model expectedModel = new ModelManager(getMixedAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        expectedModel.updateFilteredPersonList(PREDICATE_NOT_BLACKLISTED);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);

        assertEquals(4, expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(new WhitelistListCommand(), mixedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_archivedList_throwsCommandException() {
        normalModel.setArchivedListMode(true);
        assertCommandFailure(new WhitelistListCommand(), normalModel, Messages.MESSAGE_NOT_IN_MAIN_LIST);
    }
}
