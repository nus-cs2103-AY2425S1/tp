package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalStudents.DIDDY;
import static seedu.address.testutil.TypicalStudents.HUGH;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewStudentCommand}.
 */
public class ViewStudentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Name diddy = DIDDY.getName();
        Name hugh = HUGH.getName();

        ViewStudentCommand viewStudentDiddyCommand = new ViewStudentCommand(diddy);
        ViewStudentCommand viewStudentHughCommand = new ViewStudentCommand(hugh);

        // same object -> returns true
        assertTrue(viewStudentDiddyCommand.equals(viewStudentDiddyCommand));

        // same values -> returns true
        ViewStudentCommand viewStudentDiddyCommandCopy = new ViewStudentCommand(diddy);
        assertTrue(viewStudentDiddyCommand.equals(viewStudentDiddyCommandCopy));

        // different types -> returns false
        assertFalse(viewStudentDiddyCommand.equals(1));

        // null -> returns false
        assertFalse(viewStudentDiddyCommand.equals(null));

        // different names -> returns false
        assertFalse(viewStudentDiddyCommand.equals(viewStudentHughCommand));
    }

    @Test
    public void execute_studentWithNameExists_oneStudentFound() {
        Name nameWithMatch = model.getAddressBook().getStudentList().get(0).getName();
        Predicate<Student> predicate = preparePredicate(nameWithMatch);
        String expectedMessage = String.format(ViewStudentCommand.MESSAGE_SUCCESS, 1, nameWithMatch);

        ViewStudentCommand command = new ViewStudentCommand(nameWithMatch);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(HUGH), model.getFilteredStudentList());
    }

    @Test
    public void execute_studentWithNameDoesNotExist_throwsCommandException() {
        Name nameWithNoMatch = new StudentBuilder().withName("Matt").build().getName();
        Predicate<Student> predicate = preparePredicate(nameWithNoMatch);
        String expectedMessage = ViewStudentCommand.MESSAGE_NO_SUCH_STUDENT;

        ViewStudentCommand command = new ViewStudentCommand(nameWithNoMatch);
        expectedModel.updateFilteredStudentList(predicate);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void toStringMethod() {
        Name diddy = DIDDY.getName();
        ViewStudentCommand viewStudentCommand = new ViewStudentCommand(diddy);
        String expected = ViewStudentCommand.class.getCanonicalName() + "{name=" + diddy + "}";
        assertEquals(expected, viewStudentCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code Predicate<Student>}.
     */
    private Predicate<Student> preparePredicate(Name name) {
        return student -> student.getName().equals(name);
    }
}
