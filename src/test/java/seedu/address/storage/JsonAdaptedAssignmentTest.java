package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedAssignment.MISSING_ASSIGNMENT_ID_MESSAGE;
import static seedu.address.storage.JsonAdaptedAssignment.MISSING_PERSON_ID_MESSAGE;
import static seedu.address.storage.JsonAdaptedAssignment.MISSING_PROJECT_ID_MESSAGE;
import static seedu.address.storage.JsonAdaptedAssignment.PERSON_NOT_FOUND_MESSAGE;
import static seedu.address.storage.JsonAdaptedAssignment.PROJECT_NOT_FOUND_MESSAGE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ALICE_ALPHA;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalProjects.ALPHA;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.ProjectId;


public class JsonAdaptedAssignmentTest {
    private static final String INVALID_PROJECT_ID = "+651234";
    private static final String INVALID_EMPLOYEE_ID = "kk12658j";
    private static final String INVALID_ASSIGNMENT_ID = "kk12658j";

    private static final String VALID_PROJECT_ID = ALPHA.getId().toString();
    private static final String VALID_EMPLOYEE_ID = ALICE.getEmployeeId().toString();
    private static final String VALID_ASSIGNMENT_ID = ALICE_ALPHA.getAssignmentId().toString();

    @Test
    public void toModelType_validProjectIdAndValidEmployeeId_returnsProject() throws Exception {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_ID,
                VALID_PROJECT_ID,
                VALID_EMPLOYEE_ID);
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(ALICE);
        addressBook.addProject(ALPHA);
        assertEquals(ALICE_ALPHA, assignment.toModelType(addressBook));
    }

    @Test
    public void toModelType_validAssignment_returnsProject() throws Exception {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(ALICE_ALPHA);
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(ALICE);
        addressBook.addProject(ALPHA);
        assertEquals(ALICE_ALPHA, assignment.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidProjectId_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_ID,
                INVALID_PROJECT_ID,
                VALID_EMPLOYEE_ID);
        String expectedMessage = ProjectId.MESSAGE_CONSTRAINTS;
        AddressBook addressBook = new AddressBook();
        assertThrows(IllegalValueException.class, expectedMessage, () -> assignment.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidEmployeeId_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_ID,
                VALID_PROJECT_ID,
                INVALID_EMPLOYEE_ID);
        String expectedMessage = EmployeeId.MESSAGE_CONSTRAINTS;
        AddressBook addressBook = new AddressBook();
        assertThrows(IllegalValueException.class, expectedMessage, () -> assignment.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidAssignmentId_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(INVALID_ASSIGNMENT_ID,
                VALID_PROJECT_ID,
                VALID_EMPLOYEE_ID);
        String expectedMessage = AssignmentId.MESSAGE_CONSTRAINTS;
        AddressBook addressBook = new AddressBook();
        assertThrows(IllegalValueException.class, expectedMessage, () -> assignment.toModelType(addressBook));
    }

    @Test
    public void toModelType_nullProjectId_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_ID,
                null,
                VALID_EMPLOYEE_ID);
        AddressBook addressBook = new AddressBook();
        assertThrows(IllegalValueException.class,
                MISSING_PROJECT_ID_MESSAGE, () -> assignment.toModelType(addressBook));
    }

    @Test
    public void toModelType_nullEmployeeId_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_ID,
                VALID_PROJECT_ID,
                null);
        AddressBook addressBook = new AddressBook();
        assertThrows(IllegalValueException.class, MISSING_PERSON_ID_MESSAGE, () -> assignment.toModelType(addressBook));
    }

    @Test
    public void toModelType_nullAssignmentId_throwsIllegalValueException() {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(null,
                VALID_PROJECT_ID,
                VALID_EMPLOYEE_ID);
        AddressBook addressBook = new AddressBook();
        assertThrows(IllegalValueException.class,
                MISSING_ASSIGNMENT_ID_MESSAGE, () -> assignment.toModelType(addressBook));
    }

    @Test
    public void toModelType_projectNotFound_throwsIllegalValueException() throws Exception {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_ID,
                VALID_PROJECT_ID,
                VALID_EMPLOYEE_ID);
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(ALICE);
        assertThrows(IllegalValueException.class, PROJECT_NOT_FOUND_MESSAGE, () -> assignment.toModelType(addressBook));
    }

    @Test
    public void toModelType_personNotFound_throwsIllegalValueException() throws Exception {
        JsonAdaptedAssignment assignment = new JsonAdaptedAssignment(VALID_ASSIGNMENT_ID,
                VALID_PROJECT_ID,
                VALID_EMPLOYEE_ID);
        AddressBook addressBook = new AddressBook();
        addressBook.addProject(ALPHA);
        assertThrows(IllegalValueException.class, PERSON_NOT_FOUND_MESSAGE, () -> assignment.toModelType(addressBook));
    }
}
