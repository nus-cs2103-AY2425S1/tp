package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import keycontacts.logic.Messages;
import keycontacts.logic.commands.FindCommand.FindStudentDescriptor;
import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.UserPrefs;
import keycontacts.model.student.StudentDescriptorMatchesPredicate;
import keycontacts.testutil.FindStudentDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());

    @Test
    public void equals() {
        FindStudentDescriptor firstDescriptor = new FindStudentDescriptorBuilder().withName("first").build();
        FindStudentDescriptor secondDescriptor = new FindStudentDescriptorBuilder().withName("second").build();

        StudentDescriptorMatchesPredicate firstPredicate = new StudentDescriptorMatchesPredicate(firstDescriptor);
        StudentDescriptorMatchesPredicate secondPredicate = new StudentDescriptorMatchesPredicate(secondDescriptor);

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_multipleStudentsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 5);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("e").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(5, model.getFilteredStudentList().size());
    }

    @Test
    public void execute_singleStudentsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Carl").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getFilteredStudentList().size());
    }

    @Test
    public void toStringMethod() {
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("keyword").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    @Test
    public void execute_singleKeyword_singleStudentFound() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Alice").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getFilteredStudentList().size());
    }

    @Test
    public void execute_partialKeyword_multipleStudentsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Ku").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(2, model.getFilteredStudentList().size());
    }
}
