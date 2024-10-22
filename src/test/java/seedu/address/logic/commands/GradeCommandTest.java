package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for GradeCommand.
 */
public class GradeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_gradeAcceptedByModel_addSuccessful() {
        Person student = model.getFilteredPersonList().get(0);
        Person newStudent = new PersonBuilder(student).build();

        Module validModule = new Module(VALID_MODULE_AMY);
        newStudent = newStudent.addModule(validModule);
        model.setPerson(model.getFilteredPersonList().get(0), newStudent);

        Grade validGrade = new Grade(VALID_GRADE_AMY);
        newStudent = newStudent.setModuleGrade(validModule, validGrade);
        StudentId studentId = student.getStudentId();
        GradeCommand gradeCommand = new GradeCommand(studentId, validModule, validGrade);

        String expectedMessage = String.format(GradeCommand.MESSAGE_SUCCESS, validModule);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), newStudent);

        assertCommandSuccess(gradeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        StudentId invalidStudentId = new StudentId("99999999"); // id that doesn't exist in the address book
        Module validModule = new Module(VALID_MODULE_AMY);
        Grade validGrade = new Grade(VALID_GRADE_AMY);
        GradeCommand gradeCommand = new GradeCommand(invalidStudentId, validModule, validGrade);

        assertThrows(CommandException.class, GradeCommand.MESSAGE_PERSON_NOT_FOUND, () -> gradeCommand.execute(model));
    }

    @Test
    public void execute_moduleNotFound_throwsCommandException() {
        Person student = model.getFilteredPersonList().get(0);
        StudentId studentId = student.getStudentId();
        Module invalidModule = new Module(VALID_MODULE_BOB);
        Grade validGrade = new Grade(VALID_GRADE_AMY);
        GradeCommand gradeCommand = new GradeCommand(studentId, invalidModule, validGrade);

        assertThrows(CommandException.class,
                String.format(GradeCommand.MESSAGE_MODULE_NOT_FOUND, invalidModule.value), ()
                        -> gradeCommand.execute(model));
    }

    @Test
    public void equals() {
        StudentId amyId = new StudentId(VALID_STUDENTID_AMY);
        StudentId bobId = new StudentId(VALID_STUDENTID_BOB);
        Module amyModule = new Module(VALID_MODULE_AMY);
        Module bobModule = new Module(VALID_MODULE_BOB);
        Grade amyGrade = new Grade(VALID_GRADE_AMY);
        Grade bobGrade = new Grade(VALID_GRADE_BOB);
        GradeCommand addAmyGradeCommand = new GradeCommand(amyId, amyModule, amyGrade);
        GradeCommand addBobGradeCommand = new GradeCommand(bobId, bobModule, bobGrade);

        // same object -> returns true
        assertTrue(addAmyGradeCommand.equals(addAmyGradeCommand));

        // same values -> returns true
        GradeCommand addAliceGradeCommandCopy = new GradeCommand(amyId, amyModule, amyGrade);
        assertTrue(addAmyGradeCommand.equals(addAliceGradeCommandCopy));

        // different types -> returns false
        assertFalse(addAmyGradeCommand.equals(1));

        // null -> returns false
        assertFalse(addAmyGradeCommand.equals(null));

        // different person -> returns false
        assertFalse(addAmyGradeCommand.equals(addBobGradeCommand));
    }

    @Test
    public void toStringMethod() {
        StudentId studentId = new StudentId(VALID_STUDENTID_AMY);
        Module module = new Module(VALID_MODULE_AMY);
        Grade grade = new Grade(VALID_GRADE_AMY);
        GradeCommand gradeCommand = new GradeCommand(studentId, module, grade);
        String expected = GradeCommand.class.getCanonicalName()
                + "{studentId=" + studentId + ", module=" + module + ", toAdd=" + grade + "}";
        assertEquals(expected, gradeCommand.toString());
    }

}
