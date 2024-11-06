package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CategoryContainsKeywordPredicate;


public class TrackCommandTest {
    private static final CategoryContainsKeywordPredicate STUDENT_PREDICATE =
            new CategoryContainsKeywordPredicate(VALID_CATEGORY_STUDENT);
    private static final CategoryContainsKeywordPredicate COMPANY_PREDICATE =
            new CategoryContainsKeywordPredicate(VALID_CATEGORY_COMPANY);
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute() {
        TrackCommand command = new TrackCommand(STUDENT_PREDICATE);
        expectedModel.updateFilteredPersonList(STUDENT_PREDICATE);

        int size = expectedModel.getFilteredPersonList().size();
        String expectedMessage = String.format(TrackCommand.MESSAGE_SUCCESS, "student", size);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final TrackCommand standardCommand1 = new TrackCommand(STUDENT_PREDICATE);
        // same category values -> returns true
        TrackCommand commandWithSameValues = new TrackCommand(STUDENT_PREDICATE);
        assertTrue(standardCommand1.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand1.equals(standardCommand1));
        // null -> returns false
        assertFalse(standardCommand1.equals(null));
        // different types -> returns false
        assertFalse(standardCommand1.equals(new ClearCommand()));
        // different category values -> returns false
        assertFalse(standardCommand1.equals(new TrackCommand(COMPANY_PREDICATE)));
    }
}
