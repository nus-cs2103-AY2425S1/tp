package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TrackCommand.MESSAGE_NO_CONTACTS_TO_TRACK;
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
    public void execute_validCategoryStudent_success() {
        TrackCommand command = new TrackCommand(STUDENT_PREDICATE);
        expectedModel.updateFilteredPersonList(STUDENT_PREDICATE);

        int size = expectedModel.getFilteredPersonList().size();
        String expectedMessage = String.format(TrackCommand.MESSAGE_SUCCESS, "student", size);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validCategoryCompany_success() {
        TrackCommand command = new TrackCommand(COMPANY_PREDICATE);
        expectedModel.updateFilteredPersonList(COMPANY_PREDICATE);

        int size = expectedModel.getFilteredPersonList().size();
        String expectedMessage = String.format(TrackCommand.MESSAGE_SUCCESS, "company", size);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noContactsToTrack_failure() {
        Model emptyModel = new ModelManager();
        TrackCommand command = new TrackCommand(STUDENT_PREDICATE);

        assertCommandFailure(command, emptyModel, MESSAGE_NO_CONTACTS_TO_TRACK);
    }

    @Test
    public void equals() {
        final TrackCommand studentCommand = new TrackCommand(STUDENT_PREDICATE);
        final TrackCommand companyCommand = new TrackCommand(COMPANY_PREDICATE);

        // same object -> returns true
        assertTrue(studentCommand.equals(studentCommand));

        // same values -> returns true
        TrackCommand studentCommandCopy = new TrackCommand(STUDENT_PREDICATE);
        assertTrue(studentCommand.equals(studentCommandCopy));

        // different types -> returns false
        assertFalse(studentCommand.equals(1));

        // null -> returns false
        assertFalse(studentCommand.equals(null));

        // different categories -> returns false
        assertFalse(studentCommand.equals(companyCommand));
    }
}
