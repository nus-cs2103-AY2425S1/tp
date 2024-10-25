package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code FindCommand}.
 */
public class FindCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentIdUnfilteredList_success() {
        Person personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId studentIdToFind = personToFind.getStudentId();
        FindCommand findCommand = new FindCommand(studentIdToFind);

        String expectedMessage = String.format(FindCommand.MESSAGE_FIND_PERSON_SUCCESS,
                Messages.format(personToFind));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPersonToDisplay(personToFind);

        assertCommandSuccess(findCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIdUnfilteredList_throwsCommandException() {
        StudentId invalidStudentId = new StudentId("12345679");
        FindCommand findCommand = new FindCommand(invalidStudentId);
        assertCommandFailure(
                findCommand, model, String.format(FindCommand.MESSAGE_PERSON_NOT_FOUND, invalidStudentId));
    }

    @Test
    public void execute_validStudentIdFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId studentIdToFind = personToFind.getStudentId();
        FindCommand findCommand = new FindCommand(studentIdToFind);

        String expectedMessage = String.format(FindCommand.MESSAGE_FIND_PERSON_SUCCESS,
                Messages.format(personToFind));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), personToFind);
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);

        assertCommandSuccess(findCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        StudentId firstStudentId = firstPerson.getStudentId();
        StudentId secondStudentId = secondPerson.getStudentId();

        FindCommand findFirstCommand = new FindCommand(firstStudentId);
        FindCommand findSecondCommand = new FindCommand(secondStudentId);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstStudentId);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId targetStudentId = firstPerson.getStudentId();

        FindCommand findCommand = new FindCommand(targetStudentId);
        String expected = FindCommand.class.getCanonicalName() + "{targetStudentId=" + targetStudentId + "}";
        assertEquals(expected, findCommand.toString());
    }
}
