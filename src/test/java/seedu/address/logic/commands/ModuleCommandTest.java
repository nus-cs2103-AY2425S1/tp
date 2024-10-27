package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalEduContacts;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EduContacts;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Module;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ModuleCommand.
 */
public class ModuleCommandTest {

    private Model model = new ModelManager(getTypicalEduContacts(), new UserPrefs());

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() {
        Person student = model.getFilteredPersonList().get(0);
        Person expectedStudent = new PersonBuilder(student).build();
        StudentId studentId = student.getStudentId();
        Module validModule = new Module(VALID_MODULE_AMY);
        expectedStudent = expectedStudent.addModule(validModule);
        ModuleCommand moduleCommand = new ModuleCommand(studentId, validModule);

        String expectedMessage = String.format(ModuleCommand.MESSAGE_SUCCESS, studentId);

        Model expectedModel = new ModelManager(new EduContacts(model.getEduContacts()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), expectedStudent);

        assertCommandSuccess(moduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Person student = model.getFilteredPersonList().get(0);
        Person expectedStudent = new PersonBuilder(student).build();
        Module validModule = new Module(VALID_MODULE_AMY);
        expectedStudent = expectedStudent.addModule(validModule);
        model.setPerson(model.getFilteredPersonList().get(0), expectedStudent);

        StudentId studentId = student.getStudentId();
        ModuleCommand moduleCommand = new ModuleCommand(studentId, validModule);
        assertThrows(CommandException.class,
            String.format(ModuleCommand.MESSAGE_DUPLICATE_MODULE, VALID_MODULE_AMY), ()
                -> moduleCommand.execute(model));
    }

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        StudentId invalidStudentId = new StudentId("99999999"); // id that doesn't exist in EduContacts
        Module validModule = new Module(VALID_MODULE_AMY);
        ModuleCommand moduleCommand = new ModuleCommand(invalidStudentId, validModule);

        assertThrows(CommandException.class, ModuleCommand.MESSAGE_PERSON_NOT_FOUND, ()
                -> moduleCommand.execute(model));
    }

    @Test
    public void equals() {
        StudentId amyId = new StudentId(VALID_STUDENTID_AMY);
        StudentId bobId = new StudentId(VALID_STUDENTID_BOB);
        Module amyModule = new Module(VALID_MODULE_AMY);
        Module bobModule = new Module(VALID_MODULE_BOB);
        ModuleCommand addAmyModuleCommand = new ModuleCommand(amyId, amyModule);
        ModuleCommand addBobModuleCommand = new ModuleCommand(bobId, bobModule);

        // same object -> returns true
        assertTrue(addAmyModuleCommand.equals(addAmyModuleCommand));

        // same values -> returns true
        ModuleCommand addAliceModuleCommandCopy = new ModuleCommand(amyId, amyModule);
        assertTrue(addAmyModuleCommand.equals(addAliceModuleCommandCopy));

        // different types -> returns false
        assertFalse(addAmyModuleCommand.equals(1));

        // null -> returns false
        assertFalse(addAmyModuleCommand.equals(null));

        // different person -> returns false
        assertFalse(addAmyModuleCommand.equals(addBobModuleCommand));
    }

    @Test
    public void toStringMethod() {
        StudentId studentId = new StudentId(VALID_STUDENTID_AMY);
        Module module = new Module(VALID_MODULE_AMY);
        ModuleCommand moduleCommand = new ModuleCommand(studentId, module);
        String expected = ModuleCommand.class.getCanonicalName()
                + "{studentId=" + studentId + ", toAdd=" + module + "}";
        assertEquals(expected, moduleCommand.toString());
    }

}
