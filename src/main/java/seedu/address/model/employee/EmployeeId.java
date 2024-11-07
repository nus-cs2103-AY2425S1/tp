package seedu.address.model.employee;

import seedu.address.model.id.Id;

/**
 * Represents an employee's id in the address book.
 * Guarantees: immutable; is always valid
 */
public class EmployeeId extends Id {

    /**
     * Constructs an {@code EmployeeId}.
     *
     * @param employeeId An employee id.
     */
    public EmployeeId(String employeeId) {
        super(employeeId);
    }

}
