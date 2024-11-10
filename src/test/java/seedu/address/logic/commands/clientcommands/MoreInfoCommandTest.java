package seedu.address.logic.commands.clientcommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.PERSON_INDEX_OUT_OF_BOUNDS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class MoreInfoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Listings());
    @Test
    public void execute_clientIndexOutOfBoundsUnfilteredList_throwsCommandException() {
        MoreInfoCommand moreInfoCommand = new MoreInfoCommand(PERSON_INDEX_OUT_OF_BOUNDS);

        assertCommandFailure(moreInfoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_clientIndexOutOfBoundsFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        MoreInfoCommand moreInfoCommand = new MoreInfoCommand(INDEX_SECOND_PERSON);

        assertCommandFailure(moreInfoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MoreInfoCommand moreInfoFirstCommand = new MoreInfoCommand(INDEX_FIRST_PERSON);
        MoreInfoCommand moreInfoSecondCommand = new MoreInfoCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(moreInfoFirstCommand.equals(moreInfoFirstCommand));

        // same values -> returns true
        MoreInfoCommand moreInfoFirstCommandCopy = new MoreInfoCommand(INDEX_FIRST_PERSON);
        assertTrue(moreInfoFirstCommand.equals(moreInfoFirstCommandCopy));

        // different types -> returns false
        assertFalse(moreInfoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(moreInfoFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(moreInfoFirstCommand.equals(moreInfoSecondCommand));
    }

    @Test
    public void toStringMethod() {
        MoreInfoCommand moreInfoCommand = new MoreInfoCommand(INDEX_FIRST_PERSON);
        String expected = MoreInfoCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_PERSON + "}";
        assertEquals(expected, moreInfoCommand.toString());
    }
}
