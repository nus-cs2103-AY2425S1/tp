package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.findcommands.FindStudentCommand.NO_STUDENTS_FOUND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommands.FindStudentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.StudentMatchesQueryPredicate;

public class FindStudentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        StudentMatchesQueryPredicate firstPredicate = new StudentMatchesQueryPredicate("friends");
        StudentMatchesQueryPredicate secondPredicate = new StudentMatchesQueryPredicate("group 4");

        FindStudentCommand findFirstCommand = new FindStudentCommand(firstPredicate);
        FindStudentCommand findSecondCommand = new FindStudentCommand(secondPredicate);

        // same object --> true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values --> true
        FindStudentCommand findFirstCommandCopy = new FindStudentCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types --> false
        assertFalse(findFirstCommand.equals(1));

        // null --> false
        assertFalse(findFirstCommand.equals(null));

        // different predicate
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noStudentFound() {
        String expectedMessage = NO_STUDENTS_FOUND;
        StudentMatchesQueryPredicate predicate = new StudentMatchesQueryPredicate("adfasd");
        FindStudentCommand command = new FindStudentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_multipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        StudentMatchesQueryPredicate predicate = new StudentMatchesQueryPredicate("friend");
        FindStudentCommand command = new FindStudentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

}
