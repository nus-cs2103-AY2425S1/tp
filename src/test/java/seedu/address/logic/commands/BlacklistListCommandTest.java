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
import seedu.address.model.person.ArgumentPredicate;
import seedu.address.model.person.ClientStatus;

public class BlacklistListCommandTest {

    private static final ArgumentPredicate PREDICATE_BLACKLISTED = new ArgumentPredicate(
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
    public void list_noBlacklistedPersons_success() {
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        expectedModel.updateFilteredPersonList(PREDICATE_BLACKLISTED);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);

        assertEquals(0, expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(new BlacklistListCommand(), normalModel, expectedMessage, expectedModel);
    }

    @Test
    public void list_allBlacklistedPersons_success() {
        Model expectedModel = new ModelManager(getBlacklistedAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        expectedModel.updateFilteredPersonList(PREDICATE_BLACKLISTED);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);

        assertEquals(5, expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(new BlacklistListCommand(), blacklistedModel, expectedMessage, expectedModel);
    }

    @Test
    public void list_someBlacklistedPersons_success() {
        Model expectedModel = new ModelManager(getMixedAddressBook(), new UserPrefs(), new ArchivedAddressBook());
        expectedModel.updateFilteredPersonList(PREDICATE_BLACKLISTED);

        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);

        assertEquals(3, expectedModel.getFilteredPersonList().size());
        assertCommandSuccess(new BlacklistListCommand(), mixedModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_archivedList_throwsCommandException() {
        normalModel.setArchivedListMode(true);
        assertCommandFailure(new BlacklistListCommand(), normalModel, Messages.MESSAGE_NOT_IN_MAIN_LIST);
    }
}
