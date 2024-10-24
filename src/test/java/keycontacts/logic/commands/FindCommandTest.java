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
 * Contains integration tests (interaction with the Model) for
 * {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());

    @Test
    public void equals() {
        FindStudentDescriptor firstDescriptor = new FindStudentDescriptorBuilder().withName("Alice Pauline").build();
        FindStudentDescriptor secondDescriptor = new FindStudentDescriptorBuilder().withName("Benson Meier").build();

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
        assertFalse(findFirstCommand.equals(new Object()));

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
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(5, model.getStudentList().size());
    }

    @Test
    public void execute_singleStudentsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Carl Kurz").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
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
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Alice Pauline").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_partialKeyword_multipleStudentsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 2);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Ku").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(2, model.getStudentList().size());
    }

    @Test
    public void execute_multipleStudentsFoundByPhone() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 3);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withPhone("9482").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(3, model.getStudentList().size());
    }

    @Test
    public void execute_singleStudentFoundByAddress() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withAddress("wall street").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_multipleStudentsFoundByGradeLevel() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 3);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withGradeLevel("ABRSM").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(3, model.getStudentList().size());
    }

    @Test
    public void execute_multipleStudentsFoundByNameAndPhone() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Alice Pauline")
                .withPhone("94351253").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_singleStudentFoundByNameAndAddress() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Carl Kurz")
                .withAddress("wall street").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_multipleStudentsFoundByNameAndGradeLevel() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Daniel Meier")
                .withGradeLevel("ABRSM 3").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_multipleStudentsFoundByPhoneAndAddress() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withPhone("9482442")
                .withAddress("4th street").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_singleStudentFoundByPhoneAndGradeLevel() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withPhone("9482427")
                .withGradeLevel("RCM 2").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_multipleStudentsFoundByAddressAndGradeLevel() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withAddress("10th street")
                .withGradeLevel("ABRSM 3").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_singleStudentFoundByNamePhoneAndAddress() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Alice Pauline")
                .withPhone("94351253").withAddress("123, Jurong West Ave 6, #08-111").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_multipleStudentsFoundByNamePhoneAndGradeLevel() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Benson Meier")
                .withPhone("98765432").withGradeLevel("RSL 3").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_singleStudentFoundByNameAddressAndGradeLevel() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Elle Meyer")
                .withAddress("michegan ave").withGradeLevel("IMEB 1").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_multipleStudentsFoundByPhoneAddressAndGradeLevel() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withPhone("9482427")
                .withAddress("little tokyo").withGradeLevel("RCM 2").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }

    @Test
    public void execute_singleStudentFoundByAllFields() {
        String expectedMessage = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, 1);
        FindStudentDescriptor descriptor = new FindStudentDescriptorBuilder().withName("Alice Pauline")
                .withPhone("94351253").withAddress("123, Jurong West Ave 6, #08-111").withGradeLevel("ABRSM 2").build();
        StudentDescriptorMatchesPredicate predicate = new StudentDescriptorMatchesPredicate(descriptor);
        FindCommand command = new FindCommand(predicate);
        expectedModel.filterStudentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(1, model.getStudentList().size());
    }
}
