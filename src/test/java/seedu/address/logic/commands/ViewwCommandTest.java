package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WEDDING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE_WEDDING;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookFilterWithWeddings;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonMatchesWeddingPredicate;
import seedu.address.model.wedding.NameMatchesWeddingPredicate;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.WeddingBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewwCommand.
 */
public class ViewwCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookFilterWithWeddings(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Wedding weddingToView = model.getFilteredWeddingList().get(INDEX_FIRST_WEDDING.getZeroBased());
        ViewwCommand viewwCommand = new ViewwCommand(INDEX_FIRST_WEDDING, null);

        String expectedMessage = String.format(ViewwCommand.MESSAGE_VIEW_WEDDING_SUCCESS,
                Messages.format(weddingToView));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(new PersonMatchesWeddingPredicate(weddingToView));

        assertCommandSuccess(viewwCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWeddingList().size() + 1);
        ViewwCommand viewwCommand = new ViewwCommand(outOfBoundIndex, null);

        assertCommandFailure(viewwCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        model.getFilteredWeddingList().size()));
    }

    @Test
    public void execute_emptyWeddingList_throwsCommandException() {
        // Clear the wedding list
        Model model1 = new ModelManager(getTypicalAddressBookFilterWithWeddings(), new UserPrefs());
        model1.setAddressBook(new AddressBook());
        ViewwCommand viewwCommand = new ViewwCommand(INDEX_FIRST_PERSON, null);

        assertCommandFailure(viewwCommand, model1, ViewwCommand.MESSAGE_VIEW_EMPTY_LIST_ERROR);
    }

    @Test
    public void execute_validKeyword_success() {
        Wedding weddingToView = model.getFilteredWeddingList().get(INDEX_FIRST_WEDDING.getZeroBased());
        List<String> keyword = Arrays.asList(weddingToView.getName().fullName);
        NameMatchesWeddingPredicate predicate = new NameMatchesWeddingPredicate(keyword);
        ViewwCommand viewwCommand = new ViewwCommand(null, predicate);

        String expectedMessage = String.format(ViewwCommand.MESSAGE_VIEW_WEDDING_SUCCESS,
                Messages.format(weddingToView));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredWeddingList(predicate);
        expectedModel.updateFilteredPersonList(new PersonMatchesWeddingPredicate(weddingToView));

        assertCommandSuccess(viewwCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleMatchesKeyword_returnsHandlingMessage() {
        // Set up scenario where multiple weddings match the keyword
        List<String> commonKeyword = Arrays.asList("common");
        NameMatchesWeddingPredicate predicate = new NameMatchesWeddingPredicate(commonKeyword);
        ViewwCommand viewwCommand = new ViewwCommand(null, predicate);
        Model model1 = new ModelManager(getTypicalAddressBookFilterWithWeddings(), new UserPrefs());
        model1.addWedding(new WeddingBuilder().withName("common wedding").build());
        model1.addWedding(new WeddingBuilder().withName("john common wedding").build());

        assertCommandSuccess(viewwCommand, model1, ViewwCommand.MESSAGE_DUPLICATE_HANDLING, model);
    }

    @Test
    public void execute_noMatchesKeyword_throwsCommandException() {
        List<String> nonExistentKeyword =Arrays.asList("nonexistent");
        NameMatchesWeddingPredicate predicate = new NameMatchesWeddingPredicate(nonExistentKeyword);
        ViewwCommand viewwCommand = new ViewwCommand(null, predicate);

        assertCommandFailure(viewwCommand, model, ViewwCommand.MESSAGE_VIEW_EMPTY_LIST_ERROR);
    }

    @Test
    public void equals() {
        ViewwCommand viewwFirstCommand = new ViewwCommand(INDEX_FIRST_PERSON, null);
        ViewwCommand viewwSecondCommand = new ViewwCommand(INDEX_SECOND_PERSON, null);

        // same object -> returns true
        assertTrue(viewwFirstCommand.equals(viewwFirstCommand));

        // same values -> returns true
        ViewwCommand viewwFirstCommandCopy = new ViewwCommand(INDEX_FIRST_PERSON, null);
        assertTrue(viewwFirstCommand.equals(viewwFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewwFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewwFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewwFirstCommand.equals(viewwSecondCommand));

        // Test with predicates
        NameMatchesWeddingPredicate predicate1 = new NameMatchesWeddingPredicate(Arrays.asList("first"));
        NameMatchesWeddingPredicate predicate2 = new NameMatchesWeddingPredicate(Arrays.asList("second"));
        ViewwCommand viewwPredicateCommand1 = new ViewwCommand(null, predicate1);
        ViewwCommand viewwPredicateCommand2 = new ViewwCommand(null, predicate2);

        // same predicate -> returns true
        assertTrue(viewwPredicateCommand1.equals(new ViewwCommand(null, predicate1)));

        // different predicate -> returns false
        assertFalse(viewwPredicateCommand1.equals(viewwPredicateCommand2));
    }
}