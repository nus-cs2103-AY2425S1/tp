package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonHaveSubjectPredicate;

/**
 * Contains unit tests (interaction with the Model) for {@code FindSubjectCommand}.
 */
public class FindSubjectCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validSubjectMath_PersonFound() {
        FindSubjectCommand command = new FindSubjectCommand(new PersonHaveSubjectPredicate("Math"));
        expectedModel.updateFilteredPersonList(new PersonHaveSubjectPredicate("Math"));
        assertCommandSuccess(command, model, commandHistory,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2), expectedModel);
    }

    @Test
    public void execute_validSubjectEnglish_PersonFound() {
        FindSubjectCommand command = new FindSubjectCommand(new PersonHaveSubjectPredicate("English"));
        expectedModel.updateFilteredPersonList(new PersonHaveSubjectPredicate("English"));
        assertCommandSuccess(command, model, commandHistory,
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2), expectedModel);
    }

    @Test
    public void equals() {
        PersonHaveSubjectPredicate firstPredicate =
                new PersonHaveSubjectPredicate("first");
        PersonHaveSubjectPredicate secondPredicate =
                new PersonHaveSubjectPredicate("second");

        FindSubjectCommand findFirstCommand = new FindSubjectCommand(firstPredicate);
        FindSubjectCommand findSecondCommand = new FindSubjectCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindSubjectCommand findFirstCommandCopy = new FindSubjectCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(findFirstCommand, 1);

        // null -> returns false
        assertNotEquals(findFirstCommand, null);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void toStringMethod() {
        PersonHaveSubjectPredicate predicate = new PersonHaveSubjectPredicate("Math");
        FindSubjectCommand command = new FindSubjectCommand(predicate);
        String expected = FindSubjectCommand.class.getCanonicalName() + "{predicate subject=" + predicate + "}";
        assertEquals(expected, command.toString());
    }





}
