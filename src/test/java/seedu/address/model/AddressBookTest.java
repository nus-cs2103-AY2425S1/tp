package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ALICE_ALPHA;
import static seedu.address.testutil.TypicalAssignments.BENSON_BETA;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProjects.ALPHA;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.exceptions.DuplicateEmployeeException;
import seedu.address.model.project.Project;
import seedu.address.testutil.EmployeeBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getEmployeeList());
        assertEquals(Collections.emptyList(), addressBook.getProjectList());
        assertEquals(Collections.emptyList(), addressBook.getAssignmentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateEmployees_throwsDuplicateEmployeeException() {
        // Two employees with the same identity fields
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Employee> newEmployees = Arrays.asList(ALICE, editedAlice);
        List<Project> newProjects = Arrays.asList(ALPHA);
        List<Assignment> newAssignments = Arrays.asList();
        AddressBookStub newData = new AddressBookStub(newEmployees, newProjects, newAssignments);

        assertThrows(DuplicateEmployeeException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasEmployee_nullEmployee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasEmployee(null));
    }

    @Test
    public void hasEmployee_employeeNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEmployee(ALICE));
    }

    @Test
    public void hasEmployeeId_employeeIdNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEmployeeId(ALICE.getEmployeeId()));
    }

    @Test
    public void hasEmployee_employeeInAddressBook_returnsTrue() {
        addressBook.addEmployee(ALICE);
        assertTrue(addressBook.hasEmployee(ALICE));
    }

    @Test
    public void hasEmployeeId_employeeIdInAddressBook_returnsTrue() {
        addressBook.addEmployee(ALICE);
        assertTrue(addressBook.hasEmployeeId(ALICE.getEmployeeId()));
    }

    @Test
    public void hasEmployee_employeeWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addEmployee(ALICE);
        Employee editedAlice = new EmployeeBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasEmployee(editedAlice));
    }

    @Test
    public void hasProject_projectNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasProject(ALPHA));
    }

    @Test
    public void hasProjectId_projectIdNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasProjectId(ALPHA.getId()));
    }

    @Test
    public void hasProject_projectInAddressBook_returnsTrue() {
        addressBook.addProject(ALPHA);
        addressBook.getProjectList();
        assertTrue(addressBook.hasProject(ALPHA));
    }

    @Test
    public void hasProjectId_projectIdInAddressBook_returnsTrue() {
        addressBook.addProject(ALPHA);
        addressBook.getProjectList();
        assertTrue(addressBook.hasProjectId(ALPHA.getId()));
    }

    @Test
    public void getEmployeeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getEmployeeList().remove(0));
    }

    @Test
    public void hasAssignment_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasAssignment((Assignment) null));
    }

    @Test
    public void hasAssignment_nullAssignmentId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasAssignment((AssignmentId) null));
    }

    @Test
    public void hasAssignment_nullProjectIdAndEmployeeId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasAssignment(null, null));
    }

    @Test
    public void hasAssignment_assignmentNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasAssignment(ALICE_ALPHA));
    }

    @Test
    public void hasAssignment_assignmentInAddressBook_returnsTrue() {
        addressBook.addAssignment(ALICE_ALPHA);
        assertTrue(addressBook.hasAssignment(ALICE_ALPHA));
    }

    @Test
    public void hasAssignment_assignmentIdInAddressBook_returnsTrue() {
        addressBook.addAssignment(ALICE_ALPHA);
        assertTrue(addressBook.hasAssignment(ALICE_ALPHA.getAssignmentId()));
    }

    @Test
    public void hasAssignment_assignmentIdNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasAssignment(ALICE_ALPHA.getAssignmentId()));
    }

    @Test
    public void hasAssignment_projectIdAndEmployeeIdInAddressBook_returnsTrue() {
        addressBook.addAssignment(ALICE_ALPHA);
        assertTrue(addressBook.hasAssignment(ALPHA.getId(), ALICE.getEmployeeId()));
    }

    @Test
    public void hasAssignment_projectIdAndEmployeeIdNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasAssignment(ALPHA.getId(), ALICE.getEmployeeId()));
    }

    @Test
    public void removeAssignment_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.removeAssignment((Assignment) null));
    }

    @Test
    public void removeAssignment_nullAssignmentId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.removeAssignment((AssignmentId) null));
    }

    @Test
    public void removeAssignment_nullProjectIdAndEmployeeId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.removeAssignment(null, null));
    }

    @Test
    public void removeAssignment_assignmentNotInAddressBook_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () -> addressBook.removeAssignment(ALICE_ALPHA));
    }

    @Test
    public void removeAssignment_assignmentIdNotInAddressBook_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () -> addressBook.removeAssignment(
                ALICE_ALPHA.getAssignmentId()));
    }

    @Test
    public void removeAssignment_projectIdAndEmployeeIdNotInAddressBook_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () -> addressBook.removeAssignment(
                ALPHA.getId(), ALICE.getEmployeeId()));
    }

    // EP: valid project id, addressbook has 1 assignment
    @Test
    public void removeAllAssignments_projectIdInAddressBookSingleAssignment_returnsFalse() {
        addressBook.addAssignment(ALICE_ALPHA);
        assertTrue(addressBook.removeAllAssignments(ALPHA.getId()));
    }

    // EP: valid project id, addressbook has more than 1 assignment
    @Test
    public void removeAllAssignments_projectIdInAddressBookMultipleAssignments_returnsFalse() {
        addressBook.addAssignment(ALICE_ALPHA);
        addressBook.addAssignment(BENSON_BETA);
        assertTrue(addressBook.removeAllAssignments(ALPHA.getId()));
    }

    // EP: invalid project id, addressbook is not empty
    @Test
    public void removeAllAssignments_projectIdNotInAddressBook_returnsFalse() {
        addressBook.addAssignment(BENSON_BETA);
        assertFalse(addressBook.removeAllAssignments(ALPHA.getId()));
    }

    // EP: invalid project id, addressbook is empty
    @Test
    public void removeAllAssignments_projectIdNotInEmptyAddressBook_returnsFalse() {
        assertFalse(addressBook.removeAllAssignments(ALPHA.getId()));
    }

    // EP: valid employee id, addressbook has 1 assignment
    @Test
    public void removeAllAssignments_employeeIdInAddressBookSingleAssignment_returnsFalse() {
        addressBook.addAssignment(ALICE_ALPHA);
        assertTrue(addressBook.removeAllAssignments(ALICE.getEmployeeId()));
    }

    // EP: valid employee id, addressbook has more than 1 assignment
    @Test
    public void removeAllAssignments_employeeIdInAddressBookMultipleAssignment_returnsFalse() {
        addressBook.addAssignment(ALICE_ALPHA);
        addressBook.addAssignment(BENSON_BETA);
        assertTrue(addressBook.removeAllAssignments(ALICE.getEmployeeId()));
    }

    // EP: invalid employee id, addressbook is not empty
    @Test
    public void removeAllAssignments_employeeIdNotInAddressBook_returnsFalse() {
        addressBook.addAssignment(BENSON_BETA);
        assertFalse(addressBook.removeAllAssignments(ALICE.getEmployeeId()));
    }

    // EP: invalid employee id, addressbook is empty
    @Test
    public void removeAllAssignments_employeeIdNotInEmptyAddressBook_returnsFalse() {
        assertFalse(addressBook.removeAllAssignments(ALICE.getEmployeeId()));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{employees=" + addressBook.getEmployeeList()
                + ", projects=" + addressBook.getProjectList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose employees list can violate interface
     * constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Employee> employees = FXCollections.observableArrayList();
        private final ObservableList<Project> projects = FXCollections.observableArrayList();
        private final ObservableList<Assignment> assignments = FXCollections.observableArrayList();

        AddressBookStub(Collection<Employee> employees, Collection<Project> projects,
                Collection<Assignment> assignments) {
            this.employees.setAll(employees);
            this.projects.setAll(projects);
            this.assignments.setAll(assignments);
        }

        @Override
        public ObservableList<Employee> getEmployeeList() {
            return employees;
        }

        @Override
        public ObservableList<Project> getProjectList() {
            return projects;
        }

        @Override
        public ObservableList<Assignment> getAssignmentList() {
            return assignments;
        }
    }

}
