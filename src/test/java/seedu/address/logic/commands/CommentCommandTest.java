package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Comment;

/**
 * Tests CommentCommand class.
 */
public class CommentCommandTest {
    public static final String VALID_COMMENT_BOB = "Favourite pastime is Eating";
    public static final String VALID_COMMENT_AMY = "Like skiing";
    private static final String COMMENT_STUB = "Some comment";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CommentCommand commentCommand = new CommentCommand(outOfBoundIndex, new Comment(VALID_COMMENT_BOB));
        assertCommandFailure(commentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        CommentCommand commentCommand = new CommentCommand(outOfBoundIndex, new Comment(VALID_COMMENT_BOB));

        assertCommandFailure(commentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void equals() {
        final CommentCommand standardCommand = new CommentCommand(INDEX_FIRST_PERSON, new Comment(VALID_COMMENT_AMY));

        // same valies -> return true
        CommentCommand commandWithSameValues = new CommentCommand(INDEX_FIRST_PERSON, new Comment(VALID_COMMENT_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> return true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> return false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new CommentCommand(INDEX_SECOND_PERSON, new Comment(VALID_COMMENT_AMY))));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new CommentCommand(INDEX_FIRST_PERSON, new Comment(VALID_COMMENT_BOB))));
    }
}
