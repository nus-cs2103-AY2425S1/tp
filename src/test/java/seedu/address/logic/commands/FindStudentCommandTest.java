package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.findcommands.FindStudentCommand.NO_STUDENTS_FOUND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommands.FindStudentCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.StudentMatchesQueryPredicate;

//@@author gho7sie

public class FindStudentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        StudentMatchesQueryPredicate firstPredicate = new StudentMatchesQueryPredicate(List.of("friends"));
        StudentMatchesQueryPredicate secondPredicate = new StudentMatchesQueryPredicate(List.of("group 4"));

        FindStudentCommand findFirstCommand = new FindStudentCommand(firstPredicate);
        FindStudentCommand findSecondCommand = new FindStudentCommand(secondPredicate);

        // same object --> true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values --> true
        FindStudentCommand findFirstCommandCopy = new FindStudentCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types --> false
        assertNotEquals(1, findFirstCommand);

        // null --> false
        assertNotEquals(null, findFirstCommand);

        // different predicate
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noStudentFound() {
        String expectedMessage = NO_STUDENTS_FOUND;
        StudentMatchesQueryPredicate predicate = new StudentMatchesQueryPredicate(List.of("adfasd"));
        FindStudentCommand command = new FindStudentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_singleKeyword_multipleStudentsFound() {
        String expectedMessage = String.format(MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);
        StudentMatchesQueryPredicate predicate = new StudentMatchesQueryPredicate(List.of("Alice", "A0888888M"));
        FindStudentCommand command = new FindStudentCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, GEORGE), model.getFilteredPersonList());
    }

}
