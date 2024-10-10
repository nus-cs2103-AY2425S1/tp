package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TrackCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.TrackCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CategoryContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.Collections;

public class TrackCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private static final CategoryContainsKeywordsPredicate STUDENT_PREDICATE = new CategoryContainsKeywordsPredicate("student");
    private static final CategoryContainsKeywordsPredicate COMPANY_PREDICATE = new CategoryContainsKeywordsPredicate("company");

    @Test
    public void execute() {
        TrackCommand command = new TrackCommand(STUDENT_PREDICATE);
        assertCommandSuccess(command, model,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()), expectedModel);
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
