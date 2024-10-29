package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) for {@code FavouriteCommand}.
 */

public class FavouriteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_validIndexUnfilteredList_success() throws ParseException {
        Person personToFavourite = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ArrayList<Index> contactIndexes = new ArrayList<>();
        contactIndexes.add(INDEX_FIRST_PERSON);
        FavouriteCommand favouriteCommand = new FavouriteCommand(contactIndexes);
        String expectedMessage = String.format(FavouriteCommand.MESSAGE_SUCCESS,
            Messages.format(personToFavourite));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.favouritePerson(personToFavourite);
        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArrayList<Index> contactIndexes = new ArrayList<>();
        contactIndexes.add(outOfBoundIndex);
        FavouriteCommand favouriteCommand = new FavouriteCommand(contactIndexes);
        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSONS_INDEX);
    }
    @Test
    public void equals() {
        ArrayList<Index> contactIndexes = new ArrayList<>();
        contactIndexes.add(INDEX_FIRST_PERSON);
        final FavouriteCommand standardCommand = new FavouriteCommand(contactIndexes);
        // same values -> returns true
        ArrayList<Index> copyContactIndexes = new ArrayList<>();
        copyContactIndexes.add(INDEX_FIRST_PERSON);
        FavouriteCommand commandWithSameValues = new FavouriteCommand(copyContactIndexes);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> return false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different values -> returns false
        ArrayList<Index> differentContactIndexes = new ArrayList<>();
        differentContactIndexes.add(INDEX_SECOND_PERSON);
        assertFalse(standardCommand.equals(new FavouriteCommand(differentContactIndexes)));
    }
}
